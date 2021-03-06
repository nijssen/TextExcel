package textexcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import textexcel.cell.*;

/**
 *
 * @author thomas
 */
public class CellMatrix {
    private Cell[][] data;
    private ArrayList<String> cellNames;
    private static final int COLUMN_WIDTH = 12;
    private static final Class[] CELL_CLASSES = { FormulaCell.class, DoubleCell.class, DateCell.class, StringCell.class };
    private static final String FILE_RECORD_SEPERATOR = ";";
    
    /**
     * Create a new CellMatrix class.
     * 
     * @author thomas
     * @returns new CellMatrix class
     */
    private CellMatrix(int width, int height) {
        this.data = new Cell[height][width]; //TODO support more types
        this.cellNames = new ArrayList<>();
        this.setCellNames();
        this.setDefaultValues();
    }
    
    public static CellMatrix newInstance(int width, int height) {
        CellMatrix m = new CellMatrix(width, height);
        CellMatrixSingletonHolder.INSTANCE = m;
        return m;
    }
    
    public static CellMatrix getInstance() {
        return CellMatrixSingletonHolder.INSTANCE;
    }

    public ArrayList<String> getCellNames() {
        return this.cellNames;
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
        ret.row = cellIndex / this.data[0].length;
        ret.column = cellIndex % this.data[0].length; 
        
        return ret;
    }
    
    public void set(String cellName, String expr) throws Exception {
        Coordinate c = this.cellNameToCoord(cellName);
        
        this.set(c.row, c.column, expr);
    }

    public void set(int cellIndexR, int cellIndexC, String expr) throws Exception {
        //Try to fit the expression to each type of cell
        for(Class classToTry : CELL_CLASSES) {
            Cell that = null;
            try {
                that = (Cell) classToTry.newInstance();
                that.set(expr); //this will fail if the input isn't the right type for it
            } catch(InstantiationException | ExceptionInInitializerError ie) {
                throw new Exception("Instantiation failed: " + ie.getMessage());
            } catch(FormulaCellException fe) {
                throw new Exception("Error parsing formula: " + fe.getMessage());
            } catch(Exception e) {
                continue; //try the next one
            }
            
            this.data[cellIndexR][cellIndexC] = that; //only happens if prev is success
            return;
        }
        
        throw new Exception("There is no cell type available for that expression");
    }
    
    public String get(String cellName) {
        return this.get(cellName, true);
    }
    
    public String get(String cellName, boolean showCellType) {
        Coordinate c = this.cellNameToCoord(cellName);
        return this.get(c.row, c.column, showCellType);
    }

    private String get(int row, int column, boolean showCellType) {
        return this.data[row][column].getDisplayValue(showCellType ? 0 : -1);
    }
    
    public void clear(String cellName) throws Exception {
        Coordinate c = this.cellNameToCoord(cellName);
        
        this.clear(c.row, c.column);
    }

    private void clear(int row, int column) throws Exception {
        this.set(row, column, "");
    }

    public String[] getSaveData() {
        ArrayList<String> ret = new ArrayList<>();
        
        for(Cell[] row : this.data) {
            StringBuilder cellLine = new StringBuilder();
            Iterator<Cell> rowI = Arrays.asList(row).iterator();
            while(rowI.hasNext()) {
                cellLine.append(rowI.next().getInputValue());
                cellLine.append(rowI.hasNext() ? FILE_RECORD_SEPERATOR : "");
            }
            ret.add(cellLine.toString());
        }
        
        String[] out = new String[this.data.length];
        ret.toArray(out);
        return out;
    }

    public void loadFrom(String[] input) throws Exception {
        for(int r = 0; r < this.data.length; r++) {
            String[] cellStrings = input[r].split(FILE_RECORD_SEPERATOR);
            for(int c = 0; c < this.data[0].length; c++) {
                this.set(r, c, cellStrings[c]);
            }
        }
    }

    public ArrayList<Cell> getRectangularRange(String lowerName, String upperName) {
        Coordinate lowerCoord = this.cellNameToCoord(lowerName);
        Coordinate upperCoord = this.cellNameToCoord(upperName);
        
        //Check to make sure they're the right way around
        if(lowerCoord.row > upperCoord.row || lowerCoord.column > upperCoord.column) {
            //switch them
            Coordinate tmp = lowerCoord;
            lowerCoord = upperCoord;
            upperCoord = tmp;
        }
        
        ArrayList<Cell> range = new ArrayList<>();
        for(int r = lowerCoord.row; r <= upperCoord.row; r++) {
            for(int c = lowerCoord.column; c <= upperCoord.column; c++) {
                range.add(this.data[r][c]);
            }
        }
        
        return range;
    }

    //If only Java had structs...
    private static class Coordinate {
        public int row;
        public int column;
    }
    
    private static class CellMatrixSingletonHolder {
        private static CellMatrix INSTANCE;
    }
}
