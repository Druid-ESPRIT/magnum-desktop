package com.druid.services;

import com.druid.interfaces.IUser;
import com.druid.models.User;
import com.druid.utils.DBConnection;
import com.druid.enums.UserStatus;
import com.druid.utils.Debugger;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUser {
    Connection con = DBConnection.getInstance().getConnection();

    @Override
    public void addUser(User u) {
        // Check that the user being passed doesn't
        // already exist in the database.
        if (this.checkIfUserExists(u)) {
            return;
        }

        String query = "INSERT INTO `Users`(`firstName`, `lastName`, `username`, `email`, `password`, `biography`, `avatar`, `status`) VALUES ('"+u.getFirstName()+"','"+u.getLastName()+"','"+u.getUsername()+"','"+u.getEmail()+"' ,'"+u.getPassword()+"','"+u.getBiography()+"','"+u.getAvatar()+"','"+u.getStatus()+"')";

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            Debugger.log("INFO: User (with username='"+u.getUsername()+"') successfully added.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";

        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()) {
                users.add(new User(
                        result.getInt("ID"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getString("biography"),
                        Paths.get(result.getString("avatar")),
                        UserStatus.valueOf(result.getString("status"))
                ));
            }

            Debugger.log("INFO: Users successfully fetched.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean checkIfUserExists(User u) {
        List<User> users = new ArrayList<>();
        String query = "SELECT ID, username, email " +
                "FROM Users " +
                "WHERE ID='"+u.getId()+"' OR " +
                "email='"+u.getEmail()+"' OR " +
                "username='"+u.getUsername()+"'";
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            if (result.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public void updateUser(User u) {
        String query = "UPDATE Users SET " +
                "firstName = ?" +
                "lastName = ?" +
                "email= ?" +
                "password = ?" +
                "biography = ? " +
                "avatar = ? " +
                "status = ? " +
                "WHERE username = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, u.getFirstName());
            stmt.setString(2, u.getLastName());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPassword());
            stmt.setString(5, u.getBiography());
            stmt.setString(6, u.getAvatar().toString());
            stmt.setString(7, u.getStatus().toString());
            stmt.setString(8, u.getUsername());
            stmt.executeQuery(query);
            Debugger.log("INFO: User (with username='"+u.getUsername()+"' successfully updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User u) {
        return;
    }
}
