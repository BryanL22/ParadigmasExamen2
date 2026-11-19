package co.edu.poli.Examen2.aplicacion.puerto.entrada;

import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.List;

/**
 * Puerto de entrada: consultar los exámenes registrados.
 */
public interface ListarExamenesUseCase {

    /**
     * @return lista inmodificable de exámenes en orden de registro
     */
    List<ExamenSangre> listar();
}
