package co.edu.poli.Examen2.aplicacion.servicio;

import co.edu.poli.Examen2.aplicacion.puerto.entrada.ListarExamenesUseCase;
import co.edu.poli.Examen2.aplicacion.puerto.salida.ExamenRepositoryPort;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.List;

/**
 * Caso de uso que consulta los exámenes registrados.
 */
public class ListarExamenesService implements ListarExamenesUseCase {

    private final ExamenRepositoryPort repositorio;

    /**
     * @param repositorio puerto de donde se leen los exámenes
     */
    public ListarExamenesService(ExamenRepositoryPort repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<ExamenSangre> listar() {
        return List.copyOf(repositorio.listarTodos());
    }
}
