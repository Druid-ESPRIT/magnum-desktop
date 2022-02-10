package com.druid;

import com.druid.enums.UserStatus;
import com.druid.models.User;
import com.druid.services.UserService;
import com.druid.utils.DBConnection;
import com.github.javafaker.Faker;
import com.github.javafaker.HowIMetYourMother;
import com.druid.utils.Debugger;

import java.nio.file.Path;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // TODO: Populate the entrypoint
        Faker faker = new Faker();
        UserService u_svc = new UserService();
        User user = new User();
        u_svc.getUsers();

        user.setUsername(faker.name().username());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setBiography(faker.shakespeare().romeoAndJulietQuote());
        user.setStatus(UserStatus.Active);

        u_svc.addUser(user);
        Debugger.log(user);
    }
}