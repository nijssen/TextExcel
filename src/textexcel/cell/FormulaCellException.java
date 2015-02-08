package textexcel.cell;

/**
 *
 * @author Thomas Nijssen
 */
public class FormulaCellException extends Exception {

    /**
     * Creates a new instance of
     * <code>FormulaCellException</code> without detail message.
     */
    public FormulaCellException() {
    }

    /**
     * Constructs an instance of
     * <code>FormulaCellException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public FormulaCellException(String msg) {
        super(msg);
    }
}
