package data;

import java.util.Random;

public class maleNames{
    String[] data;

    public maleNames(String[] data){
        this.data = data;
    }

    public String getMaleName() {
        Random random = new Random();
        int rand = random.nextInt(data.length);
        return data[rand];
    }
}
