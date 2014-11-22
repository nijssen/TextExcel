package textexcel;

import java.util.ArrayList;
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
        for(int r = 0; r < this.data.length; r++) {
            char rname = (char) ('A' + r);
            ret.append(new StringCell(rname).getDisplayValue(COLUMN_WIDTH));
            ret.append('|');
        }
        ret.append('\n');
        return ret.toString();
    }
    
    
    
}
