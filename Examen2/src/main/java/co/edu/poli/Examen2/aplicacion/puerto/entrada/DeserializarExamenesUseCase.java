package co.edu.poli.Examen2.aplicacion.puerto.entrada;

/**
 * Puerto de entrada: recuperar los exámenes desde el almacenamiento persistente,
 * reemplazando los que están en memoria.
 */
public interface DeserializarExamenesUseCase {

    /**
     * @return cantidad de exámenes recuperados
     * @throws co.edu.poli.Examen2.aplicacion.puerto.salida.PersistenciaException si falla la lectura
     */
    int deserializar();
}
