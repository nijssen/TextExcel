/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package textexcel;

import textexcel.cell.*;

/**
 *
 * @author thomas
 */
public class CellMatrix {
    
    private Cell[][] data;
    
    /**
     * Create a new CellMatrix class.
     * 
     * @author thomas
     * @returns new CellMatrix class
     */
    public CellMatrix(int width, int height) {
        this.data = new Cell[height][width];
    }
}
