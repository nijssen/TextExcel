package textexcel.cell;

/**
 *
 * @author thomas
 */
public class StringCell extends Cell {
    private boolean isHeaderCell = false;
    
    /**
     * Create a new StringCell class.
     *
     * @author thomas
     * @returns new StringCell class
     */
    public StringCell() {
        this("", false);
    }

    public StringCell(char value) {
        this("" + value, true);
    }
    
    public StringCell(String expr) {
        this(expr, false);
    }
    
    public StringCell(String expr, boolean isHeaderCell) {
        this.expr = expr;
        this.isHeaderCell = isHeaderCell;
        this.value = "";
        try {
            this.set(expr);
        } catch (Exception ex) {
            //probably won't ever happen
        }
    }

    @Override
    public void set(String expr) throws Exception {
        super.set(expr);
        String ret = expr.trim();

        if (ret.length() > 0 && !this.isHeaderCell) {
            //remove extra quotes
            if (ret.charAt(0) == '"') {
                ret = ret.substring(1);
            } else {
                throw new UnsupportedOperationException("Bad string input");
            }
            
            if (ret.charAt(ret.length() - 1) == '"') {
                ret = ret.substring(0, ret.length() - 1);
            }
        }
        this.value = ret;
    }

}
