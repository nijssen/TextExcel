package textexcel.cell;

import java.util.ArrayList;
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
        if (!expressionIsFormula(expr)) {
            throw new Exception("That is not a formula");
        }
        super.set(expr);
        this.evaluateFormula();
    }

    private static boolean expressionIsFormula(String expr) {
        if (expr.charAt(0) == '(' && expr.charAt(expr.length() - 1) == ')') {
            return true;
        }
        return false;
    }

    private void evaluateFormula() throws Exception {
        //remove parentheses for ease
        String exprTmp = expr.substring(1, expr.length() - 2);
        String[] exprParts = exprTmp.trim().split("\\s");

        if ((exprParts[0].equals("sum") || exprParts[0].equals("avg")) && exprParts[2].equals("-")) {
            ArrayList<Cell> range = CellMatrix.getInstance().getRectangularRange(exprParts[1], exprParts[3]);
            this.value = divideCellRange(range, exprParts[0].equals("avg") ? range.size() : 1);
        } else {
            this.evaluateArithmeticFormula();
        }
    }

    private void evaluateArithmeticFormula() throws Exception {
        Bindings b = this.scriptEngine.createBindings();
        CellMatrix theMatrix = CellMatrix.getInstance(); //get the CellMatrix instance

        for (Iterator<String> it = theMatrix.getCellNames().iterator(); it.hasNext();) {
            String cellName = it.next();
            try {
                b.put(cellName, Double.parseDouble(theMatrix.get(cellName, false)));
            } catch (Exception e) {
                continue; //not something that can be used in a formula
            }
        }

        //Now evaluate it, and set the value
        try {
            this.scriptEngine.eval("var RET = " + this.expr + ";", b);
            this.value = b.get("RET");
        } catch (ScriptException e) {
            throw new FormulaCellException("Error in evaluating: " + e.getMessage());
        }
    }

    private static double divideCellRange(ArrayList<Cell> range, int n) throws FormulaCellException {
        double total = 0.0;
        try {
            for (Cell c : range) {
                total += Double.parseDouble(c.getDisplayValue(-1));
            }
        } catch (Exception e) {
            throw new FormulaCellException("One of these cells was not a number");
        }

        return total / n;
    }
}
