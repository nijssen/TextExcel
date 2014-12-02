package textexcel;

import java.io.*;
import java.util.*;

public class PersistenceHelper {

    public static void save(String filePath, CellMatrix toSave) throws Exception {

        String[] data = toSave.getSaveData();

        File f = new File(filePath);
        PrintStream out = new PrintStream(f);

        try {
            for (String datum : data) {
                out.println(datum);
            }
        } finally {
            out.close();
            System.out.println("Saved to " + f.getAbsolutePath());
        }
    }

    public static void load(String filePath, CellMatrix toLoadTo) throws Exception {
        ArrayList<String> data = new ArrayList<>();

        File f = new File(filePath);
        if (!f.exists()) {
            System.err.println("File not exist : " + f.getAbsolutePath() + "\n");
            return;
        }

        Scanner input = new Scanner(f);

        try {
            while (input.hasNextLine()) {
                data.add(input.nextLine());
            }
        } catch (Exception e) {
            System.err.println("Error loaded from " + f.getAbsolutePath() + "\n" + e.getMessage());
        } finally {
            input.close();
            System.out.println("Data loaded from " + f.getAbsolutePath());
        }

        String[] dataArray = new String[data.size()];
        toLoadTo.loadFrom(data.toArray(dataArray));
    }

}
