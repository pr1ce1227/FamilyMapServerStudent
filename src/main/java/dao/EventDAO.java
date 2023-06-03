package dao;

import model.Event;
import model.Person;
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
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Events WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }

    }

    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Events";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    public void delete(User user) throws DataAccessException {
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
        String sql = "SELECT * FROM Events WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while(rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"), rs.getInt("year"));
                events.add(event);
            }
            if(events.isEmpty()){
                return null;
            }
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
