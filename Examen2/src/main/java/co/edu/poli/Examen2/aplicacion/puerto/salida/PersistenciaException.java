package co.edu.poli.Examen2.aplicacion.puerto.salida;

/**
 * Error técnico de un adaptador de persistencia, expresado sin exponer detalles de infraestructura.
 */
public class PersistenciaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param mensaje descripción del error
     * @param causa   excepción original
     */
    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
