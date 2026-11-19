package co.edu.poli.examen2.aplicacion.servicio;

import co.edu.poli.examen2.aplicacion.evento.PublicadorEventos;
import co.edu.poli.examen2.aplicacion.evento.TipoEvento;
import co.edu.poli.examen2.aplicacion.puerto.entrada.RegistrarExamenUseCase;
import co.edu.poli.examen2.aplicacion.puerto.salida.ExamenRepositoryPort;
import co.edu.poli.examen2.dominio.excepcion.ExamenDuplicadoException;
import co.edu.poli.examen2.dominio.modelo.ExamenSangre;

/**
 * Caso de uso que registra un examen verificando que su código sea único.
 */
public class RegistrarExamenService implements RegistrarExamenUseCase {

    private final ExamenRepositoryPort repositorio;
    private final PublicadorEventos publicador;

    /**
     * @param repositorio puerto donde se guardan los exámenes
     * @param publicador  sujeto al que se notifican los registros
     */
    public RegistrarExamenService(ExamenRepositoryPort repositorio, PublicadorEventos publicador) {
        this.repositorio = repositorio;
        this.publicador = publicador;
    }

    @Override
    public ExamenSangre registrar(ExamenSangre examen) {
        if (repositorio.existe(examen.getCodigo())) {
            throw new ExamenDuplicadoException(examen.getCodigo());
        }
        repositorio.guardar(examen);
        publicador.publicar(TipoEvento.EXAMEN_REGISTRADO, "Examen guardado: " + examen);
        return examen;
    }
}
