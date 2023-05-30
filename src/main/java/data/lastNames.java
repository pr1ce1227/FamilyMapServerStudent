package data;

import java.util.Random;

public class lastNames {
    String[] data;
    public lastNames(String[] data){
        this.data = data;
    }

    public String getLastName() {
        Random random = new Random();
        int rand = random.nextInt(data.length);
        return data[rand];
    }
}
