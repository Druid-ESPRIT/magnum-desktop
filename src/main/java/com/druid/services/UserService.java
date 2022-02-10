package com.druid.services;

import com.druid.interfaces.IUser;
import com.druid.models.User;
import com.druid.utils.DBConnection;
import com.druid.enums.UserStatus;
import com.druid.utils.Debugger;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUser {
    Connection con = DBConnection.getInstance().getConnection();

    @Override
    public void addUser(User u) {
        // Check that the user being passed doesn't
        // already exist in the database.
        if (this.getUser(u)) {
            return;
        }

        String query = "INSERT INTO `Users`" +
                "(`firstName`, `lastName`, `username`, `email`, `password`, `biography`, `avatar`, `status`) VALUES" +
                " ('"+u.getFirstName()+"'," +
                "'"+u.getLastName()+"'," +
                "'"+u.getUsername()+"'," +
                "'"+u.getEmail()+"'," +
                "'"+u.getPassword()+"'," +
                "'"+u.getBiography()+"'," +
                "'"+u.getAvatar()+"'," +
                "'"+u.getStatus()+"')";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INFO: New user added.");
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
                        result.getInt("id"),
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

            Debugger.log("INFO: Successfully fetched all users.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean getUser(User u) {
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
        String query = "UPDATE `Users` SET " +
                "`firstName`='"+u.getFirstName()+"', " +
                "`lastName`='"+u.getLastName()+"', " +
                "`email`='"+u.getEmail()+"', " +
                "`password`='"+u.getPassword()+"', " +
                "`biography=`'"+u.getBiography()+"', " +
                "`avatar`='"+u.getAvatar()+"', " +
                "`status`='"+u.getStatus()+"') " +
                "WHERE username = '"+u.getUsername()+"'";
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            Debugger.log("INFO: User (with username='"+u.getUsername()+"' updated.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User u) {

    }
}
