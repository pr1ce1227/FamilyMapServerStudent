package data;

import model.Person;

import java.util.Objects;

public class persons {
    Person[] persons;

    public persons(Person[] persons){
        this.persons = persons;
    }

    public Person[] getPersons() {
        return persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        persons pp = (persons) o;
        for(int i = 0; i < ((persons) o).getPersons().length; ++i) {
            for(int j = 0; j < ((persons) o).getPersons().length; ++j) {
                if (Objects.equals(this.getPersons()[i].getPersonID(), ((persons) o).getPersons()[j].getPersonID())) {
                    if(!Objects.equals(this.getPersons()[i].getAssociatedUsername(),
                            ((persons) o).getPersons()[j].getAssociatedUsername())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
