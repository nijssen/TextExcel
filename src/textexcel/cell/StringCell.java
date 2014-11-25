package textexcel.cell;

/**
 *
 * @author thomas
 */
public class StringCell extends Cell {
    private boolean quoteOnPrint = true;
    
    /**
     * Create a new StringCell class.
     * 
     * @author thomas
     * @returns new StringCell class
     */
    public StringCell() {
        this(null, true);
    }
    
    public StringCell(String value) {
        this(value, true);
    }
    
    public StringCell(char value) {
        this("" + value, true);
    }
    
    public StringCell(char value, boolean quoteOnPrint) {
        this("" + value, quoteOnPrint);
    }
    
    public StringCell(String value, boolean quoteOnPrint) {
        this.value = value;
        this.quoteOnPrint = quoteOnPrint;
    }
    
    @Override
    public void setValue(Object input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param length 
     * @return 
     */
    @Override
    public String getDisplayValue(int length) {
        String qc = this.quoteOnPrint ? "\"" : "";
        
        String tmpStor = this.value + ""; //copy
        this.value = qc + this.value + qc; //add quotes only for displaying...
        String ret = super.getDisplayValue(length);
        this.value = tmpStor; //...and return them afterwards
        
        return ret;
    }
}
