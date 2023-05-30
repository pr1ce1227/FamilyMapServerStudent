package data;
import java.util.Random;

public class femaleNames {
     String[] data;

    public femaleNames(String[] data){
        this.data = data;
    }

    public String getFemaleName() {
        Random random = new Random();
        int rand = random.nextInt(data.length);
        return data[rand];
    }
}
