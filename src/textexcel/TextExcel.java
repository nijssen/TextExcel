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
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        matrix = new CellMatrix(10, 10);
        
        while(true) {
            System.out.print("Enter a command: ");
            String line = "print"; //sc.nextLine();
            
            evaluateExpression(line);
            break;
        }
        
    }

    private static void evaluateExpression(String line) {
        if(line.equals("print")) {
            System.out.println("\n" + matrix);
        }
    }
    
}
