/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magnum;

import java.sql.Date;
import magnum.enums.EventStatus;
import magnum.enums.EventType;
import magnum.models.Event;
import magnum.models.Review;
import magnum.models.User;
import magnum.services.EventService;
import magnum.services.ReviewService;
import magnum.services.UserService;

/**
 *
 * @author Litai
 */
public class Magnum {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        EventService eventService = new EventService();
        UserService userService = new UserService();
        ReviewService reviewService = new ReviewService();
        User user1 = userService.getUser(1);
        User user2 = userService.getUser(2);

        //Events
        //Add
        //Event e = new Event(user1, "TestEvent", "TestEventDescription", EventType.LIVE, "Example : Meet Link", new Date(System.currentTimeMillis()), false);
        //eventService.addEvent(e);
        
        
        //Update
        //Event event1 = eventService.getEvent(2);
        //event1.setStatus(EventStatus.FINISHED);
        //eventService.updateEvent(event1);
        
        //Delete 
        //Event event1 = eventService.getEvent(2);
        //eventService.cancelEvent(event1);
        
        //Reviews
        
        //add
        //Event ev = eventService.getEvent(2);
        //Review r = new Review(user1, ev, "Event was good , podcaster was cool !! I really Enjoyed his event");
        //reviewService.addReview(r);
        
        
        //update
        //Review rev = reviewService.getReview(1);
        //rev.setReview("Event was good , podcaster was cool !!");
        //reviewService.updateReview(rev);
        
        
        //delete
        //Review rev = reviewService.getReview(1);
        //reviewService.removeReview(rev);
        
        
        // User Participation
        // Event event = eventService.getEvent(2);
        //eventService.userParticipation(event, user1);
        //eventService.userParticipationCancel(event, user1);
    }

}
