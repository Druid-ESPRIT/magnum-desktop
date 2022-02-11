package com.druid.services;

import static org.junit.jupiter.api.Assertions.*;

import com.druid.models.User;
import com.github.javafaker.Faker;

class UserServiceTest {

  @org.junit.jupiter.api.Test
  void checkIfUserExists() {
    // Instantiations
    Faker faker = new Faker();
    User user = new User();
    UserService u_svc = new UserService();

    // Fill em' up boys
    user.setUsername("everett.schiller");
    user.setEmail(faker.internet().emailAddress());
    user.setPassword(faker.internet().password());
    user.setFirstName(faker.name().firstName());
    user.setLastName(faker.name().lastName());
    user.setBiography(faker.app().version());

    u_svc.addUser(user);

    // Assertions
    assert u_svc.checkIfUserExists(user) == true;
  }
}
