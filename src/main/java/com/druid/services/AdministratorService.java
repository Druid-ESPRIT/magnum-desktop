package com.druid.services;

// This service is modeled after class-table
// inheritance and borrows ideas found in:
// https://docs.oracle.com/cd/E28280_01/apirefs.1111/e13946/ejb3_overview_mapping_inher.html#ejb3_overview_mapping_inher_single

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.druid.enums.UserStatus;
import com.druid.interfaces.IUser;
import com.druid.models.Administrator;
import com.druid.models.Token;
import com.druid.models.User;
import com.druid.utils.DBConnection;
import com.druid.utils.Debugger;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdministratorService implements IUser<Administrator> {
    Connection con = DBConnection.getInstance().getConnection();

    public void add(Administrator administrator) {
        // Check that the user being passed doesn't
        // already exist in the database.
        if (this.fetchOne(administrator).isPresent()) {
            return;
        }

        // First, add a new user.
        UserService user_svc = new UserService();
        user_svc.add(administrator);

        user_svc
                .fetchOne(administrator)
                .ifPresent(
                        u -> {
                            // Set the fetched ID
                            administrator.setID(u.getID());
                        });

        // Second, insert a new Administrator.
        String query =
                "INSERT INTO `Administrators` (`ID`, `firstName`, `lastName`, `biography`,"
                        + " `avatar`) VALUES ('"
                        + administrator.getID()
                        + "','"
                        + administrator.getFirstName()
                        + "','"
                        + administrator.getLastName()
                        + "','"
                        + administrator.getAvatar()
                        + "')";

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            Debugger.log(
                    "INFO: User (with username='" + administrator.getUsername() + "') successfully added.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Administrator> fetchAll() {
        List<Administrator> administrators = new ArrayList<>();
        String query =
                "SELECT u.*, p.firstName, p.lastName, p.biography, p.avatar"
                        + "FROM Users as u "
                        + "INNER JOIN Administrators AS a "
                        + "WHERE a.id = u.id";

        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                administrators.add(
                        new Administrator(
                                result.getInt("ID"),
                                result.getString("username"),
                                result.getString("email"),
                                result.getString("password"),
                                Paths.get(result.getString("avatar")),
                                UserStatus.fromString(result.getString("status")),
                                result.getString("firstName"),
                                result.getString("lastName")));
            }

            return administrators;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public Optional<Administrator> fetchOne(Administrator administrator) {
        String query = "SELECT * FROM `Users` WHERE `ID` = ? OR `username` = ? OR `email` = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, administrator.getID());
            stmt.setString(2, administrator.getUsername());
            stmt.setString(3, administrator.getEmail());
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return Optional.of(
                        new Administrator(
                                result.getInt("ID"),
                                result.getString("username"),
                                result.getString("email"),
                                result.getString("password"),
                                Paths.get(result.getString("avatar")),
                                UserStatus.fromString(result.getString("status")),
                                result.getString("firstName"),
                                result.getString("lastName")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    public void resetPassword(Token token, User user) {
        UserService user_svc = new UserService();
        user_svc.resetPassword(token, user);
    }

    /**
     * This function sends a mail containing the username of a particular user.
     *
     * @param administrator The user who will be mailed their username.
     */
    public void mailUsername(Administrator administrator) {
        UserService user_svc = new UserService();
        user_svc.mailUsername(administrator);
    }

    public void update(Administrator administrator) {
        String query =
                "UPDATE `Users` SET "
                        + "`email`= '"
                        + administrator.getEmail()
                        + "', "
                        + "`password` = '"
                        + IUser.encrypt(administrator.getPassword())
                        + "', "
                        + "`avatar` = '"
                        + administrator.getAvatar()
                        + "', "
                        + "`status` = '"
                        + administrator.getStatus().toString()
                        + "' "
                        + "WHERE `username` = '"
                        + administrator.getUsername()
                        + "'";

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            Debugger.log(
                    "INFO: Administrator (with username='"
                            + administrator.getUsername()
                            + "') successfully updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This function allows the deletion of particular users from the database.
     *
     * @param administrator A user that is to be deleted from the database.
     * @return Returns true if deleted, and false if not.
     */
    public boolean delete(Administrator administrator) {
        if (!this.fetchOne(administrator).isPresent()) {
            Debugger.log(
                    "WARN: Administrator (with username='"
                            + administrator.getUsername()
                            + "') does not exist.");
            return false;
        }

        String query =
                "DELETE FROM `Administrators` "
                        + "WHERE ID=("
                        + "SELECT ID FROM `Users` "
                        + "WHERE username='"
                        + administrator.getUsername()
                        + "')";

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            Debugger.log(
                    "INFO: Administrator (with username='"
                            + administrator.getUsername()
                            + "') successfully deleted.");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * This function provides the mechanism for user authentication.
     *
     * @param administrator A user to be compared against existing users in the database.
     * @return If a match is found, a User object, with their full details is returned.
     */
    public Optional<Administrator> authenticate(Administrator administrator) {
        String query =
                "SELECT u.*, p.firstName, p.lastName, p.biography, p.avatar"
                        + "FROM Users as u "
                        + "INNER JOIN Administrator AS a "
                        + "WHERE a.id = u.id";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, administrator.getUsername());
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Administrator match =
                        new Administrator(
                                result.getInt("ID"),
                                result.getString("username"),
                                result.getString("email"),
                                result.getString("password"),
                                Paths.get(result.getString("avatar")),
                                UserStatus.fromString(result.getString("status")),
                                result.getString("firstName"),
                                result.getString("lastName"));

                BCrypt.Result BResult =
                        BCrypt.verifyer()
                                .verify(administrator.getPassword().toCharArray(), match.getPassword());

                // Do not authenticate if the
                // passwords do not match.
                if (!BResult.verified) return Optional.empty();

                // Do not authenticate if
                // previously banned.
                if (match.getStatus().equals(UserStatus.BANNED)) {
                    Debugger.log("Unable to authenticate as this Administrator has been banned.");
                    return Optional.empty();
                }

                // Re-enable account
                // if previously disabled.
                if (match.getStatus().equals(UserStatus.DISABLED)) {
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
}
