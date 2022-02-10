package com.druid;

import com.druid.enums.UserStatus;
import com.druid.models.User;
import com.druid.services.UserService;
import com.druid.utils.DBConnection;
import com.github.javafaker.Faker;

import java.nio.file.Path;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // TODO: Populate the entrypoint
        UserService u_svc = new UserService();
        u_svc.getUsers().stream().forEach(u -> System.out.println(u.toString()));
        // User user = new User();
        // Faker faker = new Faker();

        // user.setUsername(faker.name().username());
        // user.setEmail(faker.internet().emailAddress());
        // user.setPassword(faker.internet().password());
        // user.setFirstName(faker.name().firstName());
        // user.setLastName(faker.name().lastName());
        // user.setBiography(faker.company().catchPhrase());
        // user.setStatus(UserStatus.Active);
        // u_svc.addUser(user);
    }
}