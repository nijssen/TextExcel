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
    private static final String CELL_CLASSES_PREFIX = "textexcel.cell.";
    private static final String CELL_CLASSES = "DoubleCell,DateCell,StringCell";
    
    /**
     * Create a new CellMatrix class.
     * 
     * @author thomas
     * @returns new CellMatrix class
     */
    public CellMatrix(int width, int height) {
        this.data = new Cell[height][width]; //TODO support more types
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

    public void setDefaultValues() {        
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
    
    public void set(String cellName, String expr) throws Exception {
        Coordinate c = this.cellNameToCoord(cellName);
        
        this.set(c.row, c.column, expr);
    }

    public void set(int cellIndexR, int cellIndexC, String expr) throws Exception {
        //Try to fit the expression to each type of cell
        for(String cn : CELL_CLASSES.split(",")) {
            Cell that = null;
            try {
                that = (Cell) Class.forName(CELL_CLASSES_PREFIX + cn).newInstance();
                that.set(expr); //this will fail if the input isn't the right type for it
            } catch(InstantiationException | ExceptionInInitializerError ie) {
                throw new Exception("Instantiation failed: " + ie.getMessage());
            } catch(Exception e) {
                continue; //try the next one
            }
            
            this.data[cellIndexR][cellIndexC] = (Cell)that; //only happens if prev is success
            return;
        }
        
        throw new Exception("No suitable data type found.");
    }
    
    public String get(String cellName) {
        Coordinate c = this.cellNameToCoord(cellName);
        
        return this.get(c.row, c.column);
    }

    private String get(int row, int column) {
        return this.data[row][column].getDisplayValue(0);
    }

    public void clear(String cellName) throws Exception {
        Coordinate c = this.cellNameToCoord(cellName);
        
        this.clear(c.row, c.column);
    }

    private void clear(int row, int column) throws Exception {
        this.set(row, column, "");
    }

    private static class Coordinate {
        public int row;
        public int column;
    }
    
    
    
}
