package data;

import model.User;

import java.util.Objects;

public class users {
    User[] users;

    public users(User[] users){
        this.users = users;
    }

    public User[] getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        users uv = (users) o;
        for(int i = 0; i < ((users) o).getUsers().length; ++i) {
            for(int j = 0; j < ((users) o).getUsers().length; ++j ) {
                if (Objects.equals(this.getUsers()[i].getUsername(), ((users) o).getUsers()[j].getUsername())) {
                    if(!Objects.equals(this.getUsers()[i].getPersonId(),
                            ((users) o).getUsers()[j].getPersonId()))
                        return false;
                }
            }
        }
        return true;
    }
}
