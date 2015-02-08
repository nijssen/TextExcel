package textexcel.cell;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.*;
import textexcel.CellMatrix;

/**
 *
 * @author thomas
 */
public class FormulaCell extends Cell {
    private ScriptEngine scriptEngine;
    
    /**
     * Create a new FormulaCell class.
     * 
     * @author thomas
     * @returns new FormulaCell class
     */
    public FormulaCell() {
        this.value = 0.0;
        
        //Init ScriptEngine to evaluate the formula
        ScriptEngineManager sem = new ScriptEngineManager();
        scriptEngine = sem.getEngineByMimeType("text/javascript");
    }
    
    @Override
    public void set(String expr) throws Exception {
        checkIfFormula(expr); //throw exception if not
        super.set(expr);
        this.evaluateFormula();   
    }
    
    private static void checkIfFormula(String expr) throws Exception {
        if(expr.charAt(0) == '(' && expr.charAt(expr.length() - 1) == ')') return;
        throw new Exception("Not a formula.");
    }
    
    private void evaluateFormula() throws Exception {
        Bindings b = this.scriptEngine.createBindings();
        CellMatrix theMatrix = CellMatrix.getInstance(); //get the CellMatrix instance
        
        for (Iterator<String> it = theMatrix.getCellNames().iterator(); it.hasNext();) {
            String cellName = it.next();
            try {
                b.put(cellName, Double.parseDouble(theMatrix.get(cellName, false)));
            } catch(Exception e) {
                continue; //not something that can be used in a formula
            }
        }
        
        //Now evaluate it, and set the value
        this.scriptEngine.eval("var RET = " + this.expr + ";", b);
        this.value = b.get("RET");
    }
}
