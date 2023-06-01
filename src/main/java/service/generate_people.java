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
    public Person generatePerson(String gender, int genLevel, String username, int generations){

        /////////////////////
        // Person Creation //
        /////////////////////

        Person mother = null;
        Person father = null;
        boolean user = false;

        if(genLevel >= 1){
            mother = generatePerson("f", genLevel - 1, username, generations);
            father = generatePerson("m", genLevel - 1, username, generations);
            mother.setSpouseId(father.getPersonID());
            father.setSpouseId(mother.getPersonID());
        }

        if(genLevel == generations && father != null){
            user = true;
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

        if(father == null) {
            person.setLastName(lNames.getLastName());
        }
        else{
            person.setLastName(father.getLastName());
        }


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

        ////////////
        // Events //
        ////////////

        //Locations
        location momBirthLoc = locData.getLocation();
        location dadBirthLoc = locData.getLocation();
        location marriageLoc = locData.getLocation();
        location deathLoc = locData.getLocation();

        // ID's
        String personBirthID = UUID.randomUUID().toString();
        String momMarriageID = UUID.randomUUID().toString();
        String dadMarriageID = UUID.randomUUID().toString();
        String momBirthID = UUID.randomUUID().toString();
        String dadBirthdID = UUID.randomUUID().toString();
        String momDeathID = UUID.randomUUID().toString();
        String dadDeathID = UUID.randomUUID().toString();

        // Random number generator
        Random random = new Random();

        // Years
        int momBirthYear = random.nextInt(100) + 1900;
        int dadBirthYear = random.nextInt(7) + momBirthYear;
        int marriageYear = random.nextInt(10) + dadBirthYear + 13;
        int personBirthYear = random.nextInt(6) + 13 + marriageYear;
        int momDeathYear = personBirthYear + random.nextInt(55) + 1;
        int dadDeathYear = personBirthYear + random.nextInt(55) + 1;

        // Events
        Event personBirth = null;
        Event momBirth = null;
        Event dadBirth = null;
        Event momMarriage = null;
        Event dadMarriage = null;
        Event momDeath = null;
        Event dadDeath = null;

        // Event creation
        if(user) {
            personBirth = new Event(personBirthID, username, personID, marriageLoc.getLatitude(), marriageLoc.getLongitude(), marriageLoc.getCountry(), marriageLoc.getCity(), "Birth", personBirthYear);
        }
        if(father != null) {
            momBirth = new Event(momBirthID, username, mother.getPersonID(), momBirthLoc.getLatitude(), momBirthLoc.getLongitude(), momBirthLoc.getCountry(), momBirthLoc.getCity(), "Birth", momBirthYear);
            dadBirth = new Event(dadBirthdID, username, father.getPersonID(), dadBirthLoc.getLatitude(), dadBirthLoc.getLongitude(), dadBirthLoc.getCountry(), dadBirthLoc.getCity(), "Birth", dadBirthYear);
            momMarriage = new Event(momMarriageID, username, mother.getPersonID(), marriageLoc.getLatitude(), marriageLoc.getLongitude(), marriageLoc.getCountry(), marriageLoc.getCity(), "Marriage", marriageYear);
            dadMarriage = new Event(dadMarriageID, username, father.getPersonID(), marriageLoc.getLatitude(), marriageLoc.getLongitude(), marriageLoc.getCountry(), marriageLoc.getCity(), "Marriage", marriageYear);
            momDeath = new Event(momDeathID, username, mother.getPersonID(), deathLoc.getLatitude(), deathLoc.getLongitude(), deathLoc.getCountry(), deathLoc.getCity(), "Death", momDeathYear);
            dadDeath = new Event(dadDeathID, username, father.getPersonID(), deathLoc.getLatitude(), deathLoc.getLongitude(), deathLoc.getCountry(), deathLoc.getCity(), "Death", dadDeathYear);
        }



        // Event Insert //
        try{
            EventDAO ed = new EventDAO(db.openConnection());
            if(user) {
                ed.insert(personBirth);
            }
            if(father != null) {
                ed.insert(momBirth);
                ed.insert(dadBirth);
                ed.insert(momMarriage);
                ed.insert(dadMarriage);
                ed.insert(momDeath);
                ed.insert(dadDeath);
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
