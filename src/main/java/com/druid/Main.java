package com.druid;

import com.druid.enums.FlagOffense;
import com.druid.enums.HistoryActivity;
import com.druid.enums.UserStatus;
import com.druid.models.Flag;
import com.druid.models.History;
import com.druid.models.User;
import com.druid.services.FlagService;
import com.druid.services.HistoryService;
import com.druid.services.UserService;
import com.druid.utils.Debugger;
import com.github.javafaker.Faker;
import java.sql.Timestamp;
import java.util.Date;

public class Main {
  public static void main(String[] args) {
    // Faker generates fake data so we don't have to do it by hand.
    Faker faker = new Faker();
    Date date = new Date();
    Timestamp time = new Timestamp(date.getTime());
    UserService user_svc = new UserService();
    HistoryService hist_svc = new HistoryService();
    FlagService flag_svc = new FlagService();

    History hist = new History();
    Flag flag = new Flag();
    User user = user_svc.findUser(72).get();

    hist.setActivity(HistoryActivity.BILLING);
    hist.setDescription("Subscription purchased for $" + faker.number().randomNumber());
    hist.setTime(time);

    hist_svc.addToHistory(hist, user);
    hist_svc.getHistory(user).stream().forEach(x -> Debugger.log(x));

    flag.setDescription(faker.color().name() + " said something nasty.");
    flag.setOffense(FlagOffense.HARASSMENT);
    flag.setTime(time);
    flag.setUserID(user.getID());

    flag_svc.flag(flag, user);
    flag_svc.unflag(5);
    flag_svc.getFlags(user).stream().forEach(x -> Debugger.log(x));
  }
}
