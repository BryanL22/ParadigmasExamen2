package co.edu.poli.examen2.dominio.excepcion;

/**
 * Se lanza cuando un examen no cumple las reglas de negocio del dominio.
 */
public class ExamenInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param mensaje descripción de la regla incumplida
     */
    public ExamenInvalidoException(String mensaje) {
        super(mensaje);
    }
}
