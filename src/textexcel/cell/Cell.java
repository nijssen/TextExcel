/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textexcel.cell;

/**
 *
 * @author thomas
 */
public interface Cell {    
    public void setValue(Object input);
    public Object getInputValue();
    public void clear(); //optional
    
    /**
     * If length is 0, then print it for getting the value.
     * @param length
     * @return 
     */
    public String getDisplayValue(int length);
    
}
