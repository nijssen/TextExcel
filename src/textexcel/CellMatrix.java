package textexcel;

import textexcel.cell.*;

/**
 *
 * @author thomas
 */
public class CellMatrix {
    
    private Cell[][] data;
    private String[] cellNames;
    
    /**
     * Create a new CellMatrix class.
     * 
     * @author thomas
     * @returns new CellMatrix class
     */
    public CellMatrix(int width, int height) {
        this.data = new StringCell[height][width]; //TODO support more types
        this.setCellNames();
    }

    private void setCellNames() {
        int k = 0;
        for(int i = 0; i < this.data.length; i++) {
            for(int j = 0; j < this.data[0].length; j++) {
                char col = (char) ('A' + j);
                this.cellNames[k++] = col + "" + i;
            }
        }
    }
    
    
    
}
