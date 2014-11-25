/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textexcel.cell;

import java.util.Arrays;

/**
 *
 * @author thomas
 */
public abstract class Cell {

    protected Object value;
    protected String expr;

    public void setValue(Object input) {
        this.value = input;
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
        if (this.value == null && length == 0) {
            return "<empty>";
        } else if (this.value == null && length > 0) {
            return "";
        } else if (this.value != null && length == 0) {
            return (String)this.value;
        } else {

            //the value
            String v = this.value.toString();

            //truncate to length
            if (v.length() > 12) {
                v = v.substring(0, 10) + ">";
                return v; //no need to center
            }

            //padding, to center
            int padLength = length - v.length();
            char[] pad = new char[padLength / 2];
            Arrays.fill(pad, ' ');
            String padStr = new String(pad, 0, pad.length);

            return padStr + v + padStr + (padLength % 2 != 0 ? " " : "");
        }
    }
}
