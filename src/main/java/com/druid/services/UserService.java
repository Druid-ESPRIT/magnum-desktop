package com.druid.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.druid.enums.HistoryActivity;
import com.druid.enums.UserStatus;
import com.druid.errors.login.BannedUserException;
import com.druid.errors.login.InvalidCredentialsException;
import com.druid.errors.register.EmailTakenException;
import com.druid.errors.register.UsernameTakenException;
import com.druid.interfaces.IUser;
import com.druid.models.History;
import com.druid.models.User;
import com.druid.utils.Debugger;
import com.druid.utils.Mail;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserService {

    public void add(User user) throws UsernameTakenException, EmailTakenException {
        // Check that the user being passed doesn't
        // already exist in the database.
        Optional<User> match = this.fetchOne(user);

        // We got a match
        if (match.isPresent()) {
            // Compare against username
            if (match.get().getUsername().equals(user.getUsername())) {
                throw new UsernameTakenException("This username is already in use.");
            }
            // Compare against email
            else if (match.get().getEmail().equals(user.getEmail())) {
                throw new EmailTakenException("This email is already in use.");
            }
        }

        String query =
                "INSERT INTO `Users` (`username`, `email`, `password`,"
                        + " `avatar`, `status`) VALUES ('"
                        + user.getUsername()
                        + "','"
                        + user.getEmail()
                        + "','"
                        + encrypt(user.getPassword())
                        + "','"
                        + user.getAvatar()
                        + "','"
                        + user.getStatus().toString()
                        + "')";

        try {
            Statement stmt = IUser.con.createStatement();
            stmt.executeUpdate(query);
            Debugger.log("INFO: User (with username='" + user.getUsername() + "') successfully added.");

            // Fetch the recently created user
            // since we need their ID.
            fetchOne(user)
                    .ifPresent(
                            u -> {
                                // Set the fetched ID
                                user.setID(u.getID());
                                // Record this action
                                HistoryService hist_svc = new HistoryService();
                                History hist = new History();
                                hist.setTime(new Timestamp(new Date().getTime()));
                                hist.setUserID(user.getID());
                                hist.setActivity(HistoryActivity.CORE);
                                hist.setDescription("The moment you created your account");
                                hist_svc.add(hist, user);
                            });
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> fetchAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM `Users`";

        try {
            Statement stmt = IUser.con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                users.add(
                        new User(
                                result.getInt("ID"),
                                result.getString("username"),
                                result.getString("email"),
                                result.getString("password"),
                                Paths.get(result.getString("avatar")),
                                UserStatus.fromString(result.getString("status"))));
            }

            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public Optional<User> fetchOne(User user) {
        String query = "SELECT * FROM `Users` WHERE `ID` = ? OR `username` = ? OR `email` = ?";
        try {
            PreparedStatement stmt = IUser.con.prepareStatement(query);
            stmt.setInt(1, user.getID());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return Optional.of(
                        new User(
                                result.getInt("ID"),
                                result.getString("username"),
                                result.getString("email"),
                                result.getString("password"),
                                Paths.get(result.getString("avatar")),
                                UserStatus.fromString(result.getString("status"))));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * This function sends a mail containing the username of a particular user.
     *
     * @param user The user who will be mailed their username.
     */
    public void mailUsername(User user) {
        if (!this.fetchOne(user).isPresent()) {
            return;
        }

        String subject = "Your username on Magnum";
        String text =
                "Hi!\n"
                        + "You recently forgot your username, but we haven't; here it is: "
                        + user.getUsername();
        Mail.send(user.getEmail(), subject, text, false);
    }

    public void update(User user) {
        String query =
                "UPDATE `Users` SET "
                        + "`email`= '"
                        + user.getEmail()
                        + "', "
                        + "`password` = '"
                        + encrypt(user.getPassword())
                        + "', "
                        + "`avatar` = '"
                        + user.getAvatar()
                        + "', "
                        + "`status` = '"
                        + user.getStatus().toString()
                        + "' "
                        + "WHERE `username` = '"
                        + user.getUsername()
                        + "'";

        try {
            Statement stmt = IUser.con.createStatement();
            stmt.executeUpdate(query);
            Debugger.log("INFO: User (with username='" + user.getUsername() + "') successfully updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This function allows the deletion of particular users from the database.
     *
     * @param user A user that is to be deleted from the database.
     * @return Returns true if deleted, and false if not.
     */
    public boolean delete(User user) {
        if (!this.fetchOne(user).isPresent()) {
            Debugger.log("WARN: User (with username='" + user.getUsername() + "') does not exist.");
            return false;
        }

        String query = "DELETE FROM `Users` WHERE `username` = '" + user.getUsername() + "'";
        try {
            Statement stmt = IUser.con.createStatement();
            stmt.executeUpdate(query);
            Debugger.log("INFO: User (with username='" + user.getUsername() + "') successfully deleted.");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * This function encrypts a given password with BCrypt.
     *
     * @param password The password to encrypt.
     * @return The encrypted password.
     */
    public String encrypt(String password) {
        return BCrypt.with(BCrypt.Version.VERSION_2Y).hashToString(12, password.toCharArray());
    }

    /**
     * This function provides the mechanism for user authentication.
     *
     * @param user A user to be compared against existing users in the database.
     * @return If a match is found, a User object, with their full details is returned.
     */
    public Optional<User> authenticate(User user) throws BannedUserException, InvalidCredentialsException {
        String query = "SELECT * FROM `Users` WHERE `username` = ?";
        try {
            PreparedStatement stmt = IUser.con.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                User match =
                        new User(
                                result.getInt("ID"),
                                result.getString("username"),
                                result.getString("email"),
                                result.getString("password"),
                                Paths.get(result.getString("avatar")),
                                UserStatus.fromString(result.getString("status")));

                BCrypt.Result BResult =
                        BCrypt.verifyer().verify(user.getPassword().toCharArray(), match.getPassword());

                // Do not authenticate if the
                // passwords do not match.
                if (!BResult.verified) {
                    throw new InvalidCredentialsException("The username/password is incorrect");
                }

                // Do not authenticate if the user
                // has been previously banned.
                if (match.isBanned()) {
                    throw new BannedUserException("This user has been banned.");
                }

                // Re-enable the user if their account
                // has been previously disabled.
                if (match.isDisabled()) {
                    match.setStatus(UserStatus.ACTIVE);
                    update(match);
                }

                return Optional.of(match);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }
    public User getUser(int id){
        String request = "select * from users where id='" + id+"'";
        Statement st;
        try {
            st = IUser.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(request);
            rs.last();
            int nb = rs.getRow();
            rs.beforeFirst();
            if (nb != 0) {
                rs.next();
                User u = new User();
                u.setID(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setEmail(rs.getString(3));
                u.setPassword(rs.getString(4));



                return u;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

        return null;
    }



}
