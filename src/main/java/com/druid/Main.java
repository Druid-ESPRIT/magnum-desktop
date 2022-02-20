package com.druid;

import com.druid.enums.UserStatus;
import com.druid.models.Token;
import com.druid.models.User;
import com.druid.services.TokenService;
import com.druid.services.UserService;
import com.druid.utils.Debugger;

public class Main {
  public static void main(String[] args) {
    // TODO: Populate entry-point.
    // Faker generates fake data so we don't have to do it by hand.
    UserService user_svc = new UserService();
    TokenService token_svc = new TokenService();

    User user = user_svc.fetchOne(new User().setEmail("ba.tahaaziz@gmail.com")).get();
    user.setPassword("password");
    Debugger.log(user_svc.authenticate(user));

  }
}
