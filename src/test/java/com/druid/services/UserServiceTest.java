package com.druid.services;

import com.druid.enums.UserStatus;
import com.druid.models.User;
import com.druid.utils.Debugger;
import com.github.javafaker.Faker;

class UserServiceTest {

  @org.junit.jupiter.api.Test
  void checkIfUserExists() {
    // Instantiations
    Faker faker = new Faker();
    User user = new User();
    UserService u_svc = new UserService();

    // Populate the object.
    user.setUsername("everett.schiller");
    user.setEmail(faker.internet().emailAddress());
    user.setPassword(faker.internet().password());
    user.setFirstName(faker.name().firstName());
    user.setLastName(faker.name().lastName());
    user.setBiography(faker.app().version());

    u_svc.addUser(user);

    // Assertions
    assert u_svc.doesUserExist(user) == true;
  }

  @org.junit.jupiter.api.Test
  void getUsers() {
    // Instantiations
    Faker faker = new Faker();
    User user = new User();
    UserService u_svc = new UserService();

    // Store the username so we can use it later for our assertion
    String username = faker.name().username();

    // Populate the object.
    user.setUsername(username);
    user.setEmail(faker.internet().emailAddress());
    user.setPassword(faker.internet().password());
    user.setFirstName(faker.name().firstName());
    user.setLastName(faker.name().lastName());
    user.setBiography(faker.app().version());
    user.setStatus(UserStatus.ACTIVE);

    u_svc.addUser(user);

    u_svc.getUsers().forEach(x -> Debugger.log(x.toString()));

    // Assertions
    assert u_svc.getUsers().stream().anyMatch(x -> x.getUsername() == username) == true;
  }
}
