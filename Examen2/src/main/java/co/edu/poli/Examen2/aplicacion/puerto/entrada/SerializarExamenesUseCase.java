package co.edu.poli.Examen2.aplicacion.puerto.entrada;

/**
 * Puerto de entrada: guardar en almacenamiento persistente los exámenes registrados.
 */
public interface SerializarExamenesUseCase {

    /**
     * @return cantidad de exámenes guardados
     * @throws co.edu.poli.Examen2.aplicacion.puerto.salida.PersistenciaException si falla el almacenamiento
     */
    int serializar();
}
