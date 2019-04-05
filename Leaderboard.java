import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Leaderboard {
    String fileName = "./GundersonTeam3/leaderboard.txt";
    private ArrayList<ScoreEntry> scores = new ArrayList<>();
    public Leaderboard(){

    }

    public void read() {
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                scores.add(new ScoreEntry(line));
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("File '" + fileName + "' not found");
        }
        catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    public void sort(){
        Collections.reverse(scores);
    }

    public void write(){
        sort();
        try {

            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (ScoreEntry s : scores){
                bufferedWriter.write(s.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");

        }
    }

    public ArrayList<ScoreEntry> getScores() {
        return scores;
    }

    public void addScore(String name, int score){
        scores.add(new ScoreEntry(name, score));
    }

    public int calculateScores(String word){
        

        return 0;
    }
}

