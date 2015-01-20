package textexcel;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thomas
 */
public class CellMatrixTest {
    private CellMatrix instance;
    
    @Before
    public void setUp() {
        this.instance = CellMatrix.newInstance(10, 10);
    }

    @Test
    public void setNumber() throws Exception {
        this.instance.set("A2", "123.45");
        assertEquals("123.45\n[Number]", this.instance.get("A2"));
    }
    
    @Test
    public void setDate() throws Exception {
        this.instance.set("A3", "05/31/1997");
        assertEquals("05/31/1997\n[Date]", this.instance.get("A3"));
    }
    
    @Test
    public void setString() throws Exception {
        this.instance.set("G1", "\"abc\"");
        assertEquals("\"abc\"\n[String]", this.instance.get("G1"));
    }
    
    @Test
    public void setFormulaAdd() throws Exception {
        this.instance.set("G10", "( 9 + 5 )");
        assertEquals("14.0\n[Formula]", this.instance.get("G10"));
    }
    
    @Test
    public void setFormulaSubtract() throws Exception {
        this.instance.set("G9", "( 10 - 5 )");
        assertEquals("5.0\n[Formula]", this.instance.get("G9"));
    }
    
    @Test
    public void setFormulaMultiply() throws Exception {
        this.instance.set("H1", "( 8 * 9 )");
        assertEquals("72.0\n[Formula]", this.instance.get("H1"));
    }
    
    @Test
    public void setFormulaDivide() throws Exception {
        this.instance.set("H2", "( 138 / 2 )");
        assertEquals("69.0\n[Formula]", this.instance.get("H2"));
    }
    
    @Test
    public void setFormulaSeveralThings() throws Exception {
        this.instance.set("H3", "( 69.69 + 0.34 * 5 )");
        assertEquals("71.39\n[Formula]", this.instance.get("H3"));
    }
    
    @Test
    public void clearAll() throws Exception {
        //do some setting
        setString();
        setDate();
        
        //now clear it
        this.instance.setDefaultValues();
        
        //check to make sure
        assertEquals("<empty>", this.instance.get("A2"));
        assertEquals("<empty>", this.instance.get("A3"));
    }
}
