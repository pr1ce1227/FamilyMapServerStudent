package dao;

import model.Event;
import model.User;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class EventDAO {
    private final Connection conn;

    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Event event) throws DataAccessException {

        // Insert row into Event table
        String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Update with actual values
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        }

        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;


        // Find event based on eventID
        String sql = "SELECT * FROM Events WHERE eventID = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();

            // If there is an event found create an event object and return it
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }

    }

    public void clear() throws DataAccessException {

        // Delete all the events
        String sql = "DELETE FROM Events";

        // Ececute the clear
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }

        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    public void delete(User user) throws DataAccessException {

        // Delete row from event based on username
        String sql = "DELETE FROM Events WHERE associatedUsername = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            stmt.setString(1, user.getUsername());
            // execute the delete statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Event e) throws DataAccessException {

        // Update entire event based on person id with input event object
        String sql = "UPDATE Events SET eventID = ?, associatedUsername = ?, latitude = ?, longitude = ?, country = ?, city = ?, eventType = ?, year = ? WHERE personID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, e.getEventID());
            stmt.setString(2, e.getAssociatedUsername());
            stmt.setFloat(3, e.getLatitude());
            stmt.setFloat(4, e.getLongitude());
            stmt.setString(5, e.getCountry());
            stmt.setString(6, e.getCity());
            stmt.setString(7, e.getEventType());
            stmt.setInt(8, e.getYear());
            stmt.setString(9, e.getPersonID());

            // execute the delete statement
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Event[] getEvents(String username){
        Set<Event> events = new HashSet<>();
        Event event = null;
        ResultSet rs;

        // Grab every row from events with the associated username
        String sql = "SELECT * FROM Events WHERE associatedUsername = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            // Grab every event and add it to the event Set
            while(rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"), rs.getInt("year"));
                events.add(event);
            }

            // Check that an event was actually added
            if(events.isEmpty()){
                return null;
            }

            // Insert all the events into an array, this is to help with json serialization
            else{
                Event[] rEvent = new Event[events.size()];
                int i = 0;
                for(Event e : events){
                    rEvent[i] = e;
                    ++i;
                }
                return  rEvent;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
