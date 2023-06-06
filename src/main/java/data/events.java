package data;

import model.Event;

import java.util.Objects;

public class events {
    Event[] data;

    public events(Event[] data){
        this.data = data;

    }
    public Event[] getEvents() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        events ev = (events) o;
        for(int i = 0; i < ((events) o).getEvents().length; ++i) {
            for(int j = 0; j < ((events) o).getEvents().length; ++j ) {
                if (Objects.equals(this.getEvents()[i].getEventID(), ((events) o).getEvents()[j].getEventID())) {
                    if(!Objects.equals(this.getEvents()[i].getAssociatedUsername(),
                            ((events) o).getEvents()[j].getAssociatedUsername()))
                    return false;
                }
            }
        }
        return true;
    }
}
