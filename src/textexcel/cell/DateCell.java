package textexcel.cell;

import java.text.SimpleDateFormat;

public class DateCell extends Cell {
    //private Date value;
    
    private final static String DATE_FORMAT = "MM/dd/yyyy";
    private final SimpleDateFormat sdf;
    
    public DateCell() {
        this.sdf = new SimpleDateFormat(DATE_FORMAT);
    }
    
    @Override
    public void set(String expr) throws Exception {
        super.set(expr);
        
        this.value = this.sdf.parse(expr);
    }
    
    @Override
    protected String computeValue() {
        return this.sdf.format(this.value);
    }
}
