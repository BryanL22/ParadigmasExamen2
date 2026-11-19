package co.edu.poli.Examen2.aplicacion.puerto.salida;

import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.List;

/**
 * Puerto de salida: almacenamiento persistente (archivo, base de datos, etc.) de los exámenes.
 */
public interface AlmacenamientoExamenesPort {

    /**
     * @param examenes exámenes a persistir
     * @throws PersistenciaException si no se pueden escribir
     */
    void guardarTodos(List<ExamenSangre> examenes);

    /**
     * @return exámenes persistidos
     * @throws PersistenciaException si no se pueden leer
     */
    List<ExamenSangre> cargarTodos();
}
