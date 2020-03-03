import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import fileio.implementations.FileReader;
import  fileio.implementations.FileWriter;

public class InputLoader {
    int numBooks;
    int numLib;
    int numDays;
    ArrayList<Integer> points;
    ArrayList<Character> evidence;
    PriorityQueue<Library> libraries = new PriorityQueue<>(new Comparator<Library>() {
        @Override
        public int compare(Library o1, Library o2) {
            return (int) (o2.score - o1.score);
        }
    });
    public InputLoader() {
        try {
            FileReader fileReader = new FileReader("C:\\Users\\Nick\\Desktop\\HashCode\\src\\input.in");
            FileWriter fileWriter = new FileWriter("C:\\Users\\Nick\\Desktop\\HashCode\\src\\output" + ".in");
            numBooks = fileReader.nextInt();
            numLib = fileReader.nextInt();
            numDays = fileReader.nextInt();
            points = new ArrayList<>(numBooks);
            evidence = new ArrayList<>(numBooks);

            for (int i = 0; i < numBooks; ++i) {
                points.add(fileReader.nextInt());
            }
            for (int i = 0; i < numBooks; ++i) {
                evidence.add((char) 0);
            }
            for (int i = 0; i < numLib; ++i) {
                Library library = new Library();
                library.nBooks = fileReader.nextInt();
                library.signup = fileReader.nextInt();
                library.freq = fileReader.nextInt();
                library.order = new ArrayList<>(library.nBooks);
                library.id = i;
                for (int j = 0; j < library.nBooks; ++j) {
                    library.order.add(fileReader.nextInt());
                }
                library.order.sort(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return points.get(o2) - points.get(o1);
                    }
                });
                library.calculateScore(numDays, points);
                libraries.add(library);
            }


            int daysRemaining = 0;
            Library currentLibrary = libraries.poll();
            ArrayList<Library> signupLibrary = new ArrayList<>();
            for (int day = currentLibrary.signup; day < numDays; ++day) {
                if (daysRemaining == 0) {
                    signupLibrary.add(currentLibrary);
                    currentLibrary = libraries.poll();
                    if (currentLibrary != null) {
                        daysRemaining = currentLibrary.signup;
                    } else {
                        daysRemaining = -1;
                    }
                }

                for(Library currentLib : signupLibrary) {
                    currentLib.ship(evidence);
                }

                --daysRemaining;
            }
            int emptyShip = 0;
            for (Library temp: signupLibrary) {
                if (temp.counter == 0) {
                    emptyShip++;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(signupLibrary.size() - emptyShip );
            sb.append("\n");

            for(Library library : signupLibrary) {
                if (library.counter != 0) {
                    sb.append(library.id);
                    sb.append(" ");
                    sb.append(library.counter);
                    sb.append("\n");
                    sb.append(library.stringBuilder);
                    sb.append("\n");
                }

            }
            fileWriter.writeWord(sb.toString());
            fileReader.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
