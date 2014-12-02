package textexcel;

import com.sun.xml.internal.ws.policy.AssertionValidationProcessor;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thomas
 */
public class CellMatrixTest {
    private CellMatrix instance;
    
    public CellMatrixTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @Before
    public void setUp() {
        this.instance = new CellMatrix(10, 10);
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
    public void clearAll() throws Exception {
        //do some setting
        setString();
        setDate();
        
        //now clear it
        this.instance.setDefaultValues();
        
        //check to make sure
        assertEquals("", this.instance.get("A2"));
        assertEquals("", this.instance.get("A3"));
    }
}
