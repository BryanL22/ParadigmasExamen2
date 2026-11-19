package co.edu.poli.examen2.aplicacion.puerto.salida;

import co.edu.poli.examen2.dominio.modelo.ExamenSangre;

import java.util.List;

/**
 * Puerto de salida: almacén de trabajo de los exámenes registrados.
 */
public interface ExamenRepositoryPort {

    /**
     * @param examen examen a guardar
     */
    void guardar(ExamenSangre examen);

    /**
     * @param codigo código a buscar
     * @return {@code true} si existe un examen con ese código
     */
    boolean existe(String codigo);

    /**
     * @return todos los exámenes en orden de registro
     */
    List<ExamenSangre> listarTodos();

    /**
     * Sustituye el contenido completo del repositorio.
     *
     * @param examenes nuevos exámenes
     */
    void reemplazarTodos(List<ExamenSangre> examenes);
}
