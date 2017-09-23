import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class TextNormalizer {

    public static void main(String[] args){
        System.out.println("started");
        String filepath = "C:/Users/juneg/IdeaProjects/KaggleEnglishTextNormalization/Data/en_train.csv/en_train.csv";
        LinkedList<Pair<String, String>> inputText = readTrainCsv(filepath);
        System.out.println("read file");
//        LinkedList<Pair<String, String>> changedEntries = normalizedTextEntries(inputText);
//
//        System.out.println("found normalized");
//
//        printLL(changedEntries);
    }

    private static void printLL(LinkedList<Pair<String, String>> changedEntries) {
        int i = 0;
        while(i < changedEntries.size()){
            System.out.println(changedEntries.get(i).getKey() + " ; " + changedEntries.get(i).getValue());
            i++;
        }
    }

    public static LinkedList<Pair<String, String>> readTrainCsv(String csvFile){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        LinkedList<Pair<String, String>> l = new LinkedList<Pair<String, String>>();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] lines = line.split(cvsSplitBy);
                Pair p = new Pair(lines[3], lines[4]);
                l.add(p);
                if(!lines[3].equals(lines[4]))
                {
                    System.out.println(lines[3] + " ; " + lines[4]);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return l;
    }

    public static LinkedList<Pair<String, String>> normalizedTextEntries(LinkedList<Pair<String, String>> l){
        int llSize = l.size();
        int i = 0;
        while(i < llSize){
            Pair p = l.get(i);
            if(p.getKey().equals(p.getValue())){
                l.remove(i);
                llSize--;
            }
            else{
                i++;
            }
        }

        return l;
    }


}
