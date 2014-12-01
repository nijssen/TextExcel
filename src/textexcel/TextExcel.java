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
        
        while(true) {
            System.out.print("Enter a command: ");
            String line = sc.nextLine();
            try {
                evaluateExpression(line);
            } catch(Exception e) {
                System.err.println(e.getMessage() + "\n");
            }
        }
        
    }

    private static void evaluateExpression(String line) throws Exception {
        //Is it a command?
        if("print,quit".contains(line)) {
            evaluateCommand(line);
            return;
        }
        
        //Is it assignment?
        if(line.contains("=")) {
            evaluateAssignment(line);
            return;
        }
        
        //Is it getting a value?
        if(line.split("\\s").length == 1) {
            evaluateGetting(line);
        }
    }

    private static void evaluateCommand(String line) {
        switch (line) {
            case "print":
                System.out.println(matrix);
                return;
            case "quit":
                System.exit(0);
                return;
        }
        
        throw new UnsupportedOperationException("Bad command");
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
