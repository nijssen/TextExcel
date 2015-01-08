package textexcel.cell;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 *
 * @author thomas
 */
public class FormulaCell extends Cell {
    private String formulaStr;
    
    private ScriptEngine scriptEngine;
    
    
    /**
     * Create a new FormulaCell class.
     * 
     * @author thomas
     * @returns new FormulaCell class
     */
    public FormulaCell() {
        this.value = 0.0;
        
        //Init ScriptEngine
        ScriptEngineManager sem = new ScriptEngineManager();
        scriptEngine = sem.getEngineByMimeType("text/javascript");
    }
    
    @Override
    public void set(String expr) throws Exception {
        checkIfFormula(expr); //throw exception if not
        super.set(expr);
    }
    
    private static void checkIfFormula(String expr) throws Exception {
        if(expr.charAt(0) == '(' && expr.charAt(expr.length() - 1) == ')') return;
        else throw new Exception("Not a formula.");
    }
}
