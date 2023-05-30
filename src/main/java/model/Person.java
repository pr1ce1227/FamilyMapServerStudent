package model;

import java.util.Objects;

/**
 * Keeps track of a persons ID, username, full nae, gender and family
 */
public class Person {
    /**
     * unique id for each person
     */
    private String personID;
    /**
     * unique username for each person
     */
    private String associatedUsername;
    /**
     * first name of the person
     */
    private String firstName;
    /**
     * last name of the person
     */
    private String lastName;
    /**
     * Gender type of the person, either male or female
     */
    private String gender;
    /**
     * id of the father, not required
     */
    private String fatherId;
    /**
     * id of the mother, not required
     */
    private String motherId;
    /**
     * id of the spouse, not required
     */
    private String spouseId;
    public Person(){
    }

    /**
     * Constructor to initialize a person, family fields can be set to null if neeeded
     * @param personID
     * @param associatedUsername
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherId
     * @param motherId
     * @param spouseId
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherId, String motherId, String spouseId){
        this.personID = personID;
        this. associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.spouseId = spouseId;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }

    /**
     * Test to see if 2 people are in fact the same person, checks by value
     * @param o  The person obecte being compared to the current object
     * @return True or false, dependent on if the people are the same person
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personID, person.personID) && Objects.equals(associatedUsername, person.associatedUsername) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(gender, person.gender) && Objects.equals(fatherId, person.fatherId) && Objects.equals(motherId, person.motherId) && Objects.equals(spouseId, person.spouseId);
    }
}
