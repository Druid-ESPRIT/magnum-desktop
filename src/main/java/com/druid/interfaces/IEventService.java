package com.druid.interfaces;

import com.druid.models.Event;
import com.druid.models.User;

import java.util.List;

public interface IEventService {

    public boolean addEvent(Event e);
    public boolean updateEvent(Event e);
    public boolean cancelEvent(Event e);
    public List<Event> getAll();
    public Event getEvent(int id);

    public boolean userParticipation(Event e, User u);
    public boolean userParticipationCancel(Event e,User u);

    public int numberOfEventsPodcaster(int podcasterid);
    public double totalIncome(int podcasterid);
    public double incomePerEvent(int eventid);
    public int numberOfParticipants(int eventid);

    //for badges
    public Event highestParticipation();
}
