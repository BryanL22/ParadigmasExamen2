package co.edu.poli.Examen2.dominio.excepcion;

/**
 * Se lanza cuando se intenta registrar un examen con un código que ya existe.
 */
public class ExamenDuplicadoException extends ExamenInvalidoException {

    private static final long serialVersionUID = 1L;

    /**
     * @param codigo código repetido
     */
    public ExamenDuplicadoException(String codigo) {
        super("Ya existe un examen con el código " + codigo + ".");
    }
}
