package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Scoreboard {
  protected Scoreboard instance;
  protected static FileWriter writer;

  public Scoreboard() {
    instance = this;
  }

  public static ArrayList<String> readScoreboard(String filePath){
    ArrayList<String> scoreboard = new ArrayList<>(5);
     try {
      FileReader fileReader = new FileReader(filePath);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line;

      while((line = bufferedReader.readLine()) != null){
        scoreboard.add(line);
      }
    } catch (Exception e){
      e.printStackTrace();
    }
    return scoreboard;
  }

  public static void checkNewScore(int points){
    ArrayList<String> scoreboard = readScoreboard("src/galacticMail/Scoreboard.txt");
    int score = points;
    if(score > Integer.parseInt(scoreboard.get(scoreboard.size() - 1))) {
      for (int i = 0; i < scoreboard.size(); i++) {
        if (score > Integer.parseInt(scoreboard.get(i))) {
          int temp = Integer.parseInt(scoreboard.get(i));
          scoreboard.set(i, Integer.toString(score));
          score = temp;
        }
      }
      updateScores(scoreboard);
    }
  }

  public static void updateScores(ArrayList<String> scoreboard){
    try{
      writer = new FileWriter("src/galacticMail/Scoreboard.txt");
      BufferedWriter bufferedWriter= new BufferedWriter(writer);
      for (int i = 0; i < scoreboard.size(); i++) {
        bufferedWriter.write(scoreboard.get(i));
        bufferedWriter.newLine();
        bufferedWriter.flush();
      }
      writer.close();
    } catch (Exception e){
      e.printStackTrace();
    }
  }

}
