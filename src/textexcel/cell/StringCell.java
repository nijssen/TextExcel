package textexcel.cell;

/**
 *
 * @author thomas
 */
public class StringCell extends Cell {

    private String internalValue;

    /**
     * Create a new StringCell class.
     *
     * @author thomas
     * @returns new StringCell class
     */
    public StringCell() {
        this("");
    }

    public StringCell(char value) {
        this("" + value);
    }

    public StringCell(String expr) {
        this.expr = expr;
        this.internalValue = "";
        this.setValue(expr);
    }

    @Override
    public void setValue(String expr) {
        super.setValue(expr);
        this.internalValue = this.expr.trim();

        if (this.internalValue.length() > 0) {

            //remove extra quotes
            if (this.internalValue.charAt(0) == '"') {
                this.internalValue = this.internalValue.substring(1);
            }
            if (this.internalValue.charAt(this.internalValue.length() - 1) == '"') {
                this.internalValue = this.internalValue.substring(0, this.internalValue.length() - 2);
            }
        }
        this.value = this.internalValue;
    }

    @Override
    protected boolean needsQuotes() {
        return true;
    }
}
