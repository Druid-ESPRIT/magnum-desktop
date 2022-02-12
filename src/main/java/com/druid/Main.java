package com.druid;

import com.druid.enums.UserStatus;
import com.druid.models.User;
import com.druid.services.UserService;
import com.druid.utils.Debugger;
import com.github.javafaker.Faker;

public class Main {
  public static void getUsersScenario() {
    // Get a list of users and print them to the console.
    UserService u_svc = new UserService();
    u_svc.getUsers().forEach(x -> Debugger.log(x.toString()));
  }

  public static void addUserScenario(User user) {
    UserService u_svc = new UserService();
    u_svc.addUser(user);
  }

  public static void updateUserScenario(User user) {
    UserService u_svc = new UserService();
    user.setStatus(UserStatus.DISABLED);
    u_svc.updateUser(user);
  }

  public static void deleteUserScenario(User user) {
    UserService u_svc = new UserService();
    u_svc.deleteUser(user);
  }

  public static void main(String[] args) {
    // Faker generates fake data so we don't have to do it by hand.
    Faker faker = new Faker();
    User user = new User();

    user.setUsername("elda.pfannerstill");
    user.setEmail(faker.internet().emailAddress());
    user.setPassword(faker.internet().password());
    user.setStatus(UserStatus.ACTIVE);

//    addUserScenario(user);
    UserService u_svc = new UserService();
    Debugger.log(u_svc.findUser(user).toString());
  }
}
