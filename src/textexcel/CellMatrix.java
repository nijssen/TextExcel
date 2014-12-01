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
        this.setDefaultValues();
    }

    private void setCellNames() {
        for(int i = 0; i < this.data.length; i++) {
            for(int j = 0; j < this.data[0].length; j++) {
                char col = (char) ('A' + j);
                this.cellNames.add(col + "" + (i + 1));
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
        ret.append(new StringCell("").getDisplayValue(COLUMN_WIDTH)); //empty cell
        ret.append('|');
        
        for(int i = 0; i < this.data[0].length; i++) {
            char rname = (char) ('A' + i);
            ret.append(new StringCell(rname).getDisplayValue(COLUMN_WIDTH));
            ret.append('|');
        }
        ret.append('\n');
        ret.append(line);
        
        //Data rows
        for(int i = 0; i < this.data.length; i++) {
            ret.append('\n');
            ret.append(new StringCell(i + 1 + "", true).getDisplayValue(COLUMN_WIDTH));
            ret.append('|');
            
            //Cells within that row
            for(int j = 0; j < this.data[i].length; j++) {
                Cell c = this.data[i][j];
                ret.append(c.getDisplayValue(COLUMN_WIDTH));
                ret.append('|');
            }
            ret.append('\n');
            ret.append(line);
        }
        
        
        return ret.toString();
        
    }

    private void setDefaultValues() {        
        for(int i = 0; i < this.data.length; i++) {
            for(int j = 0; j < this.data[0].length; j++) {
                this.data[i][j] = new StringCell("");
            }
        }
        
    }
    
    private Coordinate cellNameToCoord(String cellName) {
        //check to make sure cell exists
        int cellIndex = this.cellNames.indexOf(cellName);
        if(cellIndex < 0) {
            throw new IndexOutOfBoundsException("That cell does not exist");
        }
        
        //convert into a coordinates
        Coordinate ret = new Coordinate();
        ret.row = cellIndex / this.data.length;
        ret.column = cellIndex % this.data.length; 
        
        return ret;
    }
    
    public void setValue(String cellName, String expr) {
        Coordinate c = this.cellNameToCoord(cellName);
        
        this.setValue(c.row, c.column, expr);
    }

    public void setValue(int cellIndexR, int cellIndexC, String expr) {
        this.data[cellIndexR][cellIndexC].setValue(expr);
    }
    
    public String getValue(String cellName) {
        Coordinate c = this.cellNameToCoord(cellName);
        
        return this.getValue(c.row, c.column);
    }

    private String getValue(int row, int column) {
        return this.data[row][column].getDisplayValue(0);
    }

    private static class Coordinate {
        public int row;
        public int column;
    }
    
    
    
}
