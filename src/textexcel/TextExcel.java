package textexcel;

import java.util.Scanner;

/**
 *
 * @author thomas
 */
public class TextExcel {

    private static CellMatrix matrix;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        matrix = new CellMatrix(10, 10);

        while (true) {
            System.out.print("Enter a command: ");
            String line = sc.nextLine();
            try {
                evaluateExpression(line);
            } catch (Exception e) {
                System.err.println(e.getMessage() + "\n");
            }
        }

    }

    private static void evaluateExpression(String line) throws Exception {
        //Is it a command?
        if ("print,exit,clear,save,load,help".contains(line.split("\\s")[0])) {
            evaluateCommand(line);
            return;
        }

        //Is it assignment?
        if (line.contains("=")) {
            evaluateAssignment(line);
            return;
        }

        //Is it getting a value?
        if (line.split("\\s").length == 1) {
            evaluateGetting(line);
        }
    }

    private static void evaluateCommand(String line) throws Exception {
        String[] parts = line.split("\\s");
        switch (parts[0]) {
            case "print":
                System.out.println(matrix);
                break;
            case "exit":
                System.out.println("Farewell!");
                System.exit(0);
                break;
            case "clear":
                if (parts.length == 1) { //not given a cell ID, clear all
                    matrix.setDefaultValues();
                } else { //given a cell ID to clear
                    try {
                        matrix.clear(parts[1]);
                    } catch (Exception ex) {
                        throw new Exception("Could not clear " + parts[1]);
                    }
                }
                break;
            case "save":
                if(parts.length != 2) throw new Exception("No file name given");
                PersistenceHelper.save(parts[1], matrix);
                break;
            case "load":
                if(parts.length != 2) throw new Exception("No file name given");
                PersistenceHelper.load(parts[1], matrix);
                break;
            case "help":
                if(parts.length == 2) {
                    Help.showHelpFor(parts[1]);
                } else {
                    Help.listTopics();
                }
                break;
        }
    }

    private static void evaluateAssignment(String line) throws Exception {
        String parts[] = line.split("=");

        String cellName = parts[0].trim();
        String intendedValue = parts[1].trim();

        //Set the value
        matrix.set(cellName, intendedValue);
    }

    private static void evaluateGetting(String line) {
        System.out.println(line + " = " + matrix.get(line));
    }

}
