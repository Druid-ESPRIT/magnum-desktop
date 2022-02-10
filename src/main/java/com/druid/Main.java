package com.druid;

import com.druid.enums.UserStatus;
import com.druid.models.User;
import com.druid.services.UserService;
import com.druid.utils.DBConnection;

import java.nio.file.Path;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // TODO: Populate the entrypoint
        UserService u_svc = new UserService();
        User u = new User();
        u.setUsername("grtcdr");
        u.setEmail("ba.tahaaziz@gmail.com");
        u.setPassword("hahahaha");
        u.setStatus(UserStatus.Active);
        u_svc.addUser(u);
    }
}