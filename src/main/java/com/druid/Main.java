package com.druid;

import com.druid.models.Flag;
import com.druid.models.History;
import com.druid.models.User;
import com.druid.services.TokenService;
import com.druid.services.UserService;
import com.druid.utils.Debugger;
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
    TokenService token_svc = new TokenService();

    History hist = new History();
    Flag flag = new Flag();
    User user = user_svc.findUser(73).get();
    token_svc.generate(user);
    Debugger.log(token_svc.getMostRecent(user));
  }
}
