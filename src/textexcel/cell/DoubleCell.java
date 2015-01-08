package textexcel.cell;

/**
 *
 * @author thomas
 */
public class DoubleCell extends Cell {
    /**
     * Create a new DoubleCell class.
     * 
     * @author thomas
     * @returns new DoubleCell class
     */
    public DoubleCell() {
        this.value = 0.0;
    }
    
    @Override
    public void set(String expr) throws Exception {
        super.set(expr);
        this.value = Double.parseDouble(expr);
    }
}
