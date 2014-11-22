package textexcel.cell;

/**
 *
 * @author thomas
 */
public class StringCell implements Cell {
    private String value;
    
    /**
     * Create a new StringCell class.
     * 
     * @author thomas
     * @returns new StringCell class
     */
    public StringCell() {
        this.value = "";
    }
    
    public StringCell(String value) {
        this.value = value;
    }
    
    public StringCell(char value) {
        this.value = "" + value;
    }

    @Override
    public void setValue(Object input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getInputValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * Special case for when length is -1, this is for the header row.
     * @param length 
     * @return 
     */
    @Override
    public String getDisplayValue(int length) {
        if(length == -1) return this.value;
        if(length == 0) return '"' + this.value + '"';
        
    }
}
