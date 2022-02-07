package com.druid.interfaces;

import java.util.List;
import com.druid.models.User;

public interface IUser {
    public void addUser(User p);
    public List<User> getUsers();
    public void updateUser(User u);
    public void deleteUser(User u);
}
