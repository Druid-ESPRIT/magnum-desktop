package com.druid.services;

import com.druid.enums.EventStatus;
import com.druid.enums.EventType;
import com.druid.interfaces.IEventService;
import com.druid.models.Event;
import com.druid.models.User;
import com.druid.utils.ConnectedUser;
import com.druid.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventService implements IEventService {
    Connection cnx = DBConnection.getInstance().getConnection();
    UserService userService = new UserService();

    private User connectedUser = ConnectedUser.getInstance().getUser();

    @Override
    public boolean addEvent(Event e) {

        String request = "INSERT INTO event (userid, name, description, type, location, date, payant, prix, status,image)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, e.getUser().getID());
            pst.setString(2, e.getName());
            pst.setString(3, e.getDescription());
            pst.setString(4, e.getType().name());
            pst.setString(5, e.getLocation());
            pst.setDate(6, e.getDate());
            pst.setBoolean(7, e.isPayant());
            pst.setDouble(8,e.getPrix());
            pst.setInt(9, 2); // 2 Not Finished // 1 Finished
            pst.setString(10,e.getImage());

            pst.executeUpdate();

            System.out.println("Event Added");

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean updateEvent(Event e) {
        String request = "UPDATE event SET userid=?, name=?, description=?, type=?, location=?, date=?, payant=?, prix=?, status=? WHERE id=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, e.getUser().getID());
            pst.setString(2, e.getName());
            pst.setString(3, e.getDescription());
            pst.setString(4, e.getType().name());
            pst.setString(5, e.getLocation());
            pst.setDate(6, e.getDate());
            pst.setBoolean(7, e.isPayant());
            pst.setDouble(8,e.getPrix());
            pst.setString(9, e.getStatus().name());
            pst.setInt(10, e.getId());

            pst.executeUpdate();

            System.out.println("Event Updated");

            return true;

        } catch (SQLException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }

    @Override
    public boolean cancelEvent(Event e) {
        String request = "DELETE FROM event where id=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, e.getId());
            pst.executeUpdate();
            System.out.println("Event Canceled");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<Event> getAll() {
        List<Event> myList = new ArrayList<>();
        String request = "SELECT * FROM event";
        Statement st;
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt(1));
                User u = new User();
                u.setID(rs.getInt(2));
                if(userService.fetchOne(u).isPresent())
                {
                    e.setUser(userService.fetchOne(u).get());
                }

                e.setName(rs.getString("name"));
                e.setDescription(rs.getString("description"));
                e.setType(EventType.valueOf(rs.getString("type")));
                e.setLocation(rs.getString("location"));
                e.setDate(rs.getDate("date"));
                e.setPayant(rs.getBoolean("payant"));
                e.setPrix(rs.getDouble("prix"));
                e.setStatus(EventStatus.valueOf(rs.getString("status")));
                e.setImage(rs.getString("image"));
                myList.add(e);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return myList;
    }
    @Override
    public Event getEvent(int id) {
        String request = "select * from event where id=" + id;
        Statement st;
        try {

            st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(request);
            rs.last();
            int nb = rs.getRow();
            rs.beforeFirst();
            if (nb != 0) {
                rs.next();
                Event e = new Event();
                e.setId(rs.getInt(1));
                User u = new User();
                u.setID(rs.getInt(2));
                if(userService.fetchOne(u).isPresent())
                {
                    e.setUser(userService.fetchOne(u).get());
                }
                e.setName(rs.getString("name"));
                e.setDescription(rs.getString("description"));
                e.setType(EventType.valueOf(rs.getString("type")));
                e.setLocation(rs.getString("location"));
                e.setDate(rs.getDate("date"));
                e.setPayant(rs.getBoolean("payant"));
                e.setPrix(rs.getDouble("prix"));
                e.setStatus(EventStatus.valueOf(rs.getString("status")));

                return e;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return null;
    }

    @Override
    public boolean userParticipation(Event e, User u) {
        String request = "INSERT INTO event_user (userid,eventid)"
                + " VALUES (?,?)";

        try {
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, u.getID());
            pst.setInt(2, e.getId());

            pst.executeUpdate();

            System.out.println("User participate in Event : " + e.getName());

            return true;

        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean userParticipationCancel(Event e, User u) {

        String request = "DELETE FROM event_user where userid=? and eventid=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(request);
            pst.setInt(1, u.getID());
            pst.setInt(2, e.getId());
            pst.executeUpdate();
            System.out.println("User Participation Canceled from Event : " + e.getName());
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public int numberOfEventsPodcaster(int podcasterid) {
        int numberOfEvents = 0;
        String request = "SELECT * FROM event where userid="+podcasterid;
        Statement st;
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                numberOfEvents++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return numberOfEvents;
    }

    @Override
    public double incomePerEvent(int eventid) {
        Event e = getEvent(eventid);
        int participants = numberOfParticipants(e.getId());
        return  e.getPrix()*participants;
    }

    @Override
    public int numberOfParticipants( int eventid) {
        int numberOfParticipants = 0;
        String request = "SELECT * FROM event_user where eventid="+eventid;
        Statement st;
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                numberOfParticipants++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return numberOfParticipants;
    }

    @Override
    public Event highestParticipation() {
        List<Event> list = getAll();
        Event max = list.get(0);
        for (Event event:list) {
            if(numberOfParticipants(event.getId())> numberOfParticipants(max.getId()))
            {
                max=event;
            }
        }

        return  max;
    }

    @Override
    public double totalIncome(int podcasterid) {

        List<Event> myList = new ArrayList<>();
        double totalIncome=0;

        String request = "SELECT * FROM event where userid="+podcasterid;
        Statement st;
        try {
            st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt(1));
                User u = new User();
                u.setID(rs.getInt(2));
                if(userService.fetchOne(u).isPresent())
                {
                    e.setUser(userService.fetchOne(u).get());
                }
                e.setName(rs.getString("name"));
                e.setDescription(rs.getString("description"));
                e.setType(EventType.valueOf(rs.getString("type")));
                e.setLocation(rs.getString("location"));
                e.setDate(rs.getDate("date"));
                e.setPayant(rs.getBoolean("payant"));
                e.setStatus(EventStatus.valueOf(rs.getString("status")));
                myList.add(e);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }



        for (Event event : myList) {
            double incomePerEvent = incomePerEvent(event.getId());
            totalIncome+=incomePerEvent;
        }


        return totalIncome;
    }

}
