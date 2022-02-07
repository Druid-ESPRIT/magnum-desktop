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
    Connection con = DBConnection.getInstance().getCon();

    @Override
    public void addUser(User u) {
        String query = "INSERT INTO `Users`(``firstName`, `lastName`, `username`, `email`, `password`, `biography`, `avatar`, `status`, `admin`, `score`) VALUES ('"+u.getFirstName()+"','"+u.getLastName()+"','"+u.getUsername()+"','"+u.getEmail()+"' ,'"+u.getPassword()+"','"+u.getBiography()+"','"+u.getAvatar()+"','"+u.getStatus()+"','"+u.isAdmin()+"','"+u.getScore()+"')";
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
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("username"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getString("biography"),
                        Paths.get(result.getString("avatar")),
                        UserStatus.valueOf(result.getString("status")),
                        result.getBoolean("admin"),
                        result.getInt("score")
                ));
            }

            Debugger.log("INFO: Successfully fetched all users.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUser(User u) {

    }

    @Override
    public void deleteUser(User u) {

    }
}
