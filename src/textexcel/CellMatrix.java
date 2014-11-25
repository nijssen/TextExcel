package textexcel;

import java.util.ArrayList;
import java.util.Arrays;
import textexcel.cell.*;

/**
 *
 * @author thomas
 */
public class CellMatrix {
    
    private Cell[][] data;
    private ArrayList<String> cellNames;
    private static final int COLUMN_WIDTH = 12;
    
    /**
     * Create a new CellMatrix class.
     * 
     * @author thomas
     * @returns new CellMatrix class
     */
    public CellMatrix(int width, int height) {
        this.data = new StringCell[height][width]; //TODO support more types
        this.cellNames = new ArrayList<>();
        this.setCellNames();
    }

    private void setCellNames() {
        for(int i = 0; i < this.data.length; i++) {
            for(int j = 0; j < this.data[0].length; j++) {
                char col = (char) ('A' + j);
                this.cellNames.add(col + "" + i);
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        
        //Re-usable line
        int lineLength = (COLUMN_WIDTH * (this.data[0].length + 1)) + this.data[0].length;
        char[] line = new char[lineLength];
        Arrays.fill(line, '-');
        for(int i = COLUMN_WIDTH; i < lineLength; i += COLUMN_WIDTH + 1) {
            line[i] = '+';
        }
        
        //Header row
        ret.append(new StringCell("", false).getDisplayValue(COLUMN_WIDTH)); //empty cell
        for(int i = 0; i < this.data[0].length; i++) {
            char rname = (char) ('A' + i);
            ret.append(new StringCell(rname, false).getDisplayValue(COLUMN_WIDTH));
            ret.append('|');
        }
        ret.append('\n');
        ret.append(line);
        
        //Data rows
        for(int i = 0; i < this.data.length; )
        
        
        return ret.toString();
    }
    
    
    
}
