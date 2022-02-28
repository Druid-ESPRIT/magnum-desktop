/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magnum.interfaces;

import java.util.List;
import magnum.models.Event;
import magnum.models.User;

/**
 *
 * @author Litai
 */
public interface IEventService {
    
    public boolean addEvent(Event e);
    public boolean updateEvent(Event e);
    public boolean cancelEvent(Event e);
    public List<Event> getAll();
    public List<Event> getAll(int podcaster);
    public Event getEvent(int id);
    
    public boolean userParticipation(Event e,User u);
    public boolean userParticipationCancel(Event e,User u);
    
    
    //Les fonctionnalités:
    
       public int numberOfEventsPodcaster(int podcasterid);
    public double totalIncome(int podcasterid);
    public double incomePerEvent(int eventid);
    public int numberOfParticipants(int eventid);
    
    
    
}
