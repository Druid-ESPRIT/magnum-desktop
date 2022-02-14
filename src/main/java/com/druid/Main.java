package com.druid;

import com.druid.enums.*;
import com.druid.models.*;
import com.druid.services.*;
import com.druid.utils.*;
import com.github.javafaker.Faker;
import java.sql.Timestamp;
import java.util.Date;

public class Main {
  public static void main(String[] args) {
    // TODO: Populate entry-point.
    // Faker generates fake data so we don't have to do it by hand.
    Faker faker = new Faker();
    Date date = new Date();
    Timestamp time = new Timestamp(date.getTime());
    UserService user_svc = new UserService();

    History hist = new History();
    Flag flag = new Flag();
    User user = new User();
    user.setEmail(faker.internet().emailAddress());
    user.setFirstName(faker.name().firstName());
    user.setLastName(faker.name().lastName());
    user.setUsername(faker.name().username());
    user.setPassword(faker.internet().password());
    user.setBiography(faker.hacker().abbreviation());
    user.setStatus(UserStatus.ACTIVE);
    user_svc.addUser(user);
  }
}
