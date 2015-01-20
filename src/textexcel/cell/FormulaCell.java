package textexcel.cell;

import java.util.StringTokenizer;
import javax.script.*;
import textexcel.CellMatrix;

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
        
        //Init ScriptEngine to evaluate the formula
        ScriptEngineManager sem = new ScriptEngineManager();
        scriptEngine = sem.getEngineByMimeType("text/javascript");
    }
    
    @Override
    public void set(String expr) throws Exception {
        checkIfFormula(expr); //throw exception if not
        super.set(expr);
        
        //Evaluate the expression
        this.evaluateFormula();
    }
    
    private static void checkIfFormula(String expr) throws Exception {
        if(expr.charAt(0) == '(' && expr.charAt(expr.length() - 1) == ')') return;
        throw new Exception("Not a formula.");
    }
    
    private void evaluateFormula() throws Exception {
        Bindings b = this.scriptEngine.createBindings();
        CellMatrix theMatrix = CellMatrix.getInstance(); //get the CellMatrix instance
        
        //For each token, check its type
        StringTokenizer st = new StringTokenizer(this.expr);
        for(String token = st.nextToken(); st.hasMoreTokens(); token = st.nextToken()) {
            //It can be an operator
            if("()+-/*".contains(token)) continue;
            
            //It can be a number
            try { Double.parseDouble(token); continue; }
            catch(Exception e) {} //It isn't a double
            
            //It can be a cell name
            try {
                String v = theMatrix.get(token, false);
                b.put(token, Double.parseDouble(v));
                continue;
            } catch(Exception e) {} //cell doesn't exist
            
            //Wasn't anything, throw exception
            throw new Exception("Invalid token in expression: " + token);
        }
        
        //Now evaluate it, and set the value
        this.value = (double) this.scriptEngine.eval(this.expr, b);
    }
}
