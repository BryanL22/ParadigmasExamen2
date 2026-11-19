package co.edu.poli.examen2.aplicacion.puerto.entrada;

import co.edu.poli.examen2.dominio.modelo.ExamenSangre;

/**
 * Puerto de entrada: registrar un nuevo examen de sangre.
 */
public interface RegistrarExamenUseCase {

    /**
     * Registra el examen si su código no existe todavía.
     *
     * @param examen examen válido a registrar
     * @return el examen registrado
     * @throws co.edu.poli.examen2.dominio.excepcion.ExamenDuplicadoException si el código ya existe
     */
    ExamenSangre registrar(ExamenSangre examen);
}
