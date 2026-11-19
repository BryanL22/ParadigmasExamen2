package co.edu.poli.examen2.aplicacion.servicio;

import co.edu.poli.examen2.aplicacion.evento.PublicadorEventos;
import co.edu.poli.examen2.aplicacion.evento.TipoEvento;
import co.edu.poli.examen2.aplicacion.puerto.entrada.SerializarExamenesUseCase;
import co.edu.poli.examen2.aplicacion.puerto.salida.AlmacenamientoExamenesPort;
import co.edu.poli.examen2.aplicacion.puerto.salida.ExamenRepositoryPort;
import co.edu.poli.examen2.dominio.modelo.ExamenSangre;

import java.util.List;

/**
 * Caso de uso que persiste todos los exámenes registrados.
 */
public class SerializarExamenesService implements SerializarExamenesUseCase {

    private final ExamenRepositoryPort repositorio;
    private final AlmacenamientoExamenesPort almacenamiento;
    private final PublicadorEventos publicador;

    /**
     * @param repositorio    origen de los exámenes
     * @param almacenamiento destino persistente
     * @param publicador     sujeto al que se notifica el resultado
     */
    public SerializarExamenesService(ExamenRepositoryPort repositorio,
                                     AlmacenamientoExamenesPort almacenamiento,
                                     PublicadorEventos publicador) {
        this.repositorio = repositorio;
        this.almacenamiento = almacenamiento;
        this.publicador = publicador;
    }

    @Override
    public int serializar() {
        List<ExamenSangre> examenes = repositorio.listarTodos();
        almacenamiento.guardarTodos(examenes);
        publicador.publicar(TipoEvento.EXAMENES_SERIALIZADOS,
                "Se serializaron " + examenes.size() + " examen(es).");
        return examenes.size();
    }
}
