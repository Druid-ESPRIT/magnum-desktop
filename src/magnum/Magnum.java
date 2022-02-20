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
import magnum.utils.Mail;

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
         User user3 = userService.getUser(3);
        User user4 = userService.getUser(4);
         User user5 = userService.getUser(5);
          User user6 = userService.getUser(6);
    User user7 = userService.getUser(7);
        User user8= userService.getUser(8);
        //Events
        //Add
//     Event e = new Event(user3, "dsfsg", "tesqf", EventType.LIVE, "Example : Meet Link", new Date(System.currentTimeMillis()), true , 55.00 );
//      eventService.addEvent(e);
        
        
        //Update
        
//        Event event1 = eventService.getEvent(5);
//        event1.setDescription("yyyyyyy");
//        eventService.updateEvent(event1);
////      System.out.println(eventService.getEvent(5));

        //Delete 
//        Event event1 = eventService.getEvent(5);
//        eventService.cancelEvent(event1);
//        
        //Reviews
        
        //add
//        Event ev = eventService.getEvent(2);
//        Review r = new Review(user1, ev, "Event was good , I really Enjoyed ");
//        reviewService.addReview(r);
        
        
        //update
//        Review rev = reviewService.getReview(2);
//        rev.setReview("XD");
//        reviewService.updateReview(rev);
        
        
        //delete
//        Review rev = reviewService.getReview(2);
//        reviewService.removeReview(rev);
        
        
        // User Participation
//        
//        Event event = eventService.getEvent(6);
//        eventService.userParticipation(event, user6);
//       
       // Cancel Participation
       //eventService.userParticipationCancel(event, user1);
       
       
//             Mail mailer = new Mail();
//        mailer.sendEmail("essia.litaiem@esprit.tn", "Test Event Ticket");

       //Number of Participants
        //System.out.println(eventService.numberOfParticipants(6));
       
       //Income per Event
      //  System.out.println( eventService.incomePerEvent( 6));

        // Number Of events of Podcaster
        // System.out.println( eventService.numberOfEventsPodcaster(2));
        
        // Total Income
    //   System.out.println(eventService.totalIncome(3));
        
    }

}
