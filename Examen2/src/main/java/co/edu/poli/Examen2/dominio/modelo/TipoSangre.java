package co.edu.poli.Examen2.dominio.modelo;

import co.edu.poli.Examen2.dominio.excepcion.ExamenInvalidoException;

/**
 * Grupos sanguíneos del sistema ABO.
 */
public enum TipoSangre {
    A, B, AB, O;

    /**
     * Convierte un texto (por ejemplo, el valor seleccionado en la interfaz) al tipo de sangre.
     *
     * @param valor texto a convertir, sin importar mayúsculas o espacios
     * @return el tipo de sangre correspondiente
     * @throws ExamenInvalidoException si el valor es nulo o no corresponde a un grupo válido
     */
    public static TipoSangre desdeTexto(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new ExamenInvalidoException("Debe seleccionar un tipo de sangre.");
        }
        try {
            return valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ExamenInvalidoException("Tipo de sangre no válido: " + valor);
        }
    }
}
