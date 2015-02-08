package textexcel.cell;

import java.util.Arrays;

/**
 *
 * @author thomas
 */
public abstract class Cell {
    protected Object value;
    protected String expr;

    public Object get() {
        return this.value;
    }
    
    public void set(String expr) throws Exception {
        this.expr = expr;
    }

    public String getInputValue() {
        return this.expr;
    }

    public void clear() {
        this.value = this.expr = null;
    }

    /**
     * Return the display value, and center if needed.
     *
     * If length is 0, then print it for getting the value.
     *
     * @param length
     * @return
     */
    public String getDisplayValue(int length) {
        String cv = this.get().toString();
        
        if (cv != null && cv.length() == 0 && length == 0) {
            return "<empty>";
        } else if (cv == null && length > 0) {
            return "";
        } else if(cv != null && length == 0) {
            //special case for string cell in which we need quotes
            String quotes = this instanceof StringCell ? "\"" : "";
            return quotes + cv + quotes + "\n" + this.getClassType();
        } else if(cv != null && length < 0) {
            //like previous, but without the nonsense of the cell type
            String quotes = this instanceof StringCell ? "\"" : "";
            return quotes + cv + quotes;
        } else {
            //truncate to length
            if (cv.length() > 12) {
                cv = cv.substring(0, 11) + ">";
                return cv; //no need to center
            }

            //padding, to center
            int padLength = length - cv.length();
            char[] pad = new char[padLength / 2];
            Arrays.fill(pad, ' ');
            String padStr = new String(pad, 0, pad.length);

            return padStr + cv + padStr + (padLength % 2 != 0 ? " " : "");
        }
    }

    private String getClassType() {
        String cn = this.getClass().getName();
        String name = cn.substring(cn.lastIndexOf('.') + 1, cn.indexOf("Cell"));
        name = (this instanceof DoubleCell) ? "Number" : name; //we'll kindly call this a "special" case...
        return "[" + name + "]";
    }
}
