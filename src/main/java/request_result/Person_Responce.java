package request_result;

import model.Person;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;

/**
 * Request for information on a specific person
 */
public class Person_Responce {
    /**
     * username of the person
     */
    private String associatedUsername;
    /**
     * unique ID of that person
     */
    private String personID;
    /**
     * first name of the person
     */
    private String firstName;
    /**
     * last name of the person
     */
    private String lastName;
    /**
     * gender of the person either male or female
     */
    private String gender;
    /**
     * father ID of the person, not required
     */
    private String fatherID;
    /**
     * mother ID of the person, not required
     */
    private String motherID;
    /**
     * Spouce ID of the person, not requried
     */
    private String spouseID;
    /**
     * wether or not the person was retrieved succesfully
     */
    private boolean success;
    /**
     * Either a success or failure message
     */
    private String messgae;

    /**
     * initialize the reponce variables
     * @param associatedUsername
     * @param personID
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     * @param success
     * @param messgae
     */
    public Person_Responce(String associatedUsername, String personID, String  firstName, String lastName, String gender, String fatherID, String motherID,   String spouseID, boolean success, String messgae){
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
        this.messgae = messgae;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessgae() {
        return messgae;
    }
}
