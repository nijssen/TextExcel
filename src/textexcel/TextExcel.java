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
            String line = sc.nextLine();
            
            evaluateExpression(line);
        }
        
    }

    private static void evaluateExpression(String line) {
        if(line.equals("print")) {
            System.out.println(matrix);
        }
    }
    
}
