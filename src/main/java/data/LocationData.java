package data;

import java.util.Random;

public class LocationData {
    location[] data;

    public LocationData(location[] data){
        this.data = data;
    }

    public location getLocation() {
        Random random = new Random();
        int rand = random.nextInt(data.length);
        return data[rand];
    }
}
