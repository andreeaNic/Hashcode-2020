import java.util.ArrayList;

public class Library {
    int id;
    int nBooks;
    int signup;
    int freq;
    ArrayList<Integer> order;
    int index = 0;
    double score = 0;
    int counter = 0;
    StringBuilder stringBuilder = new StringBuilder();
    public Library() {

    }

    public void calculateScore(int numDays,ArrayList<Integer> points) {
        int otherScore = 0;
        for (int i = 0; i < nBooks; i++) {
            otherScore += points.get(i);
        }
        score = (numDays - signup) * Constants.KD + freq * Constants.KZ + otherScore * Constants.KS;
    }

    public void ship(ArrayList<Character> evidence) {
        int shipmentToday = freq;
        while (index < nBooks && shipmentToday != 0 ) {
            int temp = order.get(index);
            if (evidence.get(temp) == 0) {
                stringBuilder.append( temp + " ");
                evidence.set(temp, (char) 1);
                counter++;
            }
            ++index;
            --shipmentToday;
        }
    }

}
