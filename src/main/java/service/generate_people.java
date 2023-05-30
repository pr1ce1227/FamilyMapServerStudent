package service;

import com.google.gson.Gson;
import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import dao.PersonDAO;
import data.*;
import model.Event;
import java.util.Random;
import model.Person;

import javax.xml.crypto.Data;
import javax.xml.stream.Location;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.UUID;

public class generate_people {
    maleNames mNames;
    femaleNames fNames;
    LocationData locData;
    lastNames lNames;
    public generate_people() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("json/locations.json");
        locData = (LocationData)gson.fromJson(reader, LocationData.class);
        Reader reader2 = new FileReader("json/fnames.json");
        fNames = (femaleNames)gson.fromJson(reader2, femaleNames.class);
        Reader reader3 = new FileReader("json/mnames.json");
        mNames = (maleNames)gson.fromJson(reader3, maleNames.class);
        Reader reader4 = new FileReader("json/snames.json");
        lNames = (lastNames)gson.fromJson(reader4, lastNames.class);

    }
    public Person generatePerson(String gender, int generations, String username){
        // Person Creation //

        Person mother = null;
        Person father = null;

        if(generations >= 1){
            mother = generatePerson("f", generations - 1, username);
            father = generatePerson("m", generations - 1, username);
            mother.setSpouseId(father.getPersonID());
            father.setSpouseId(mother.getPersonID());
        }

        Person person = new Person();
        person.setGender(gender);
        String personID = UUID.randomUUID().toString();
        person.setPersonID(personID);

        if(gender == "f"){
            person.setFirstName(fNames.getFemaleName());
        }
        else{
            person.setFirstName(mNames.getMaleName());
        }

        person.setLastName(lNames.getLastName());

        person.setAssociatedUsername(username);

        if(mother != null){
            person.setMotherId(mother.getPersonID());
        }
        if(father != null ){
            person.setFatherId(father.getPersonID());
        }

        // Person Insert //

        Database db = new Database();
        try {
            PersonDAO pd = new PersonDAO(db.openConnection());
            pd.insert(person);
            if(father != null){
                pd.update(father);
                pd.update(mother);
            }
            db.closeConnection(true);
            System.out.println("Adding person");
        }
        catch(DataAccessException da){
            db.closeConnection(false);
            System.out.println("Failed to add person");
        }

        // Event Creation //
        location Loc = locData.getLocation();
        String eventIDBIRTH = UUID.randomUUID().toString();
        String eventIDMARRIAGE = UUID.randomUUID().toString();
        String eventIDDEATH = UUID.randomUUID().toString();
        Random random = new Random();
        int ranNum = random.nextInt(1900) + 100;
        boolean user;
        Event eventBirth;
        Event eventMarriage = null;
        Event eventDeath = null;

        if(person.getFatherId() != null && person.getSpouseId() == null) {
            eventBirth = new Event(eventIDBIRTH, username, personID, Loc.getLatitude(), Loc.getLongitude(), Loc.getCountry(), Loc.getCity(), "Birth", ranNum);
            user = true;
        }
        else{
            eventBirth = new Event(eventIDBIRTH, username, personID, Loc.getLatitude(), Loc.getLongitude(), Loc.getCountry(), Loc.getCity(), "Birth", ranNum);
            eventMarriage = new Event(eventIDMARRIAGE, username, personID, Loc.getLatitude(), Loc.getLongitude(), Loc.getCountry(), Loc.getCity(), "Marriage", ranNum);
            eventDeath = new Event(eventIDDEATH, username, personID, Loc.getLatitude(), Loc.getLongitude(), Loc.getCountry(), Loc.getCity(), "Death", ranNum);
            user = false;
        }


        // Event Insert //

        db = new Database();
        try{
            EventDAO ed = new EventDAO(db.openConnection());
            ed.insert(eventBirth);
            if(!user){
                ed.insert(eventMarriage);
                ed.insert(eventDeath);
            }
            db.closeConnection(true);
        }
        catch(DataAccessException da){
            db.closeConnection(false);
            System.out.println("Failed to add event to person");
        }

        return person;
    }

}
