package com.druid;

import com.druid.enums.HistoryActivities;
import com.druid.enums.UserStatus;
import com.druid.models.History;
import com.druid.models.User;
import com.druid.services.HistoryService;
import com.druid.services.UserService;
import com.druid.utils.Debugger;
import com.github.javafaker.Faker;
import java.sql.Timestamp;
import java.util.Date;

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

    Date date = new Date();
    Timestamp time = new Timestamp(date.getTime());

    History hist = new History();
    hist.setActivity(HistoryActivities.BILLING);
    hist.setDescription("Subscription purchased for $" + faker.number().randomNumber());
    hist.setTime(time);

    UserService user_svc = new UserService();
    HistoryService hist_svc = new HistoryService();
    User user = user_svc.findUser(72).get();

    hist_svc.addToHistory(hist, user);
    hist_svc.getHistory(user).stream().forEach(x -> Debugger.log(x));
    hist_svc.deleteHistory(user);
  }
}
