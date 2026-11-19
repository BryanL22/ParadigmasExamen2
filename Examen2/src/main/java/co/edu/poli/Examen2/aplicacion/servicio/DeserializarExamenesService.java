package co.edu.poli.Examen2.aplicacion.servicio;

import co.edu.poli.Examen2.aplicacion.evento.PublicadorEventos;
import co.edu.poli.Examen2.aplicacion.evento.TipoEvento;
import co.edu.poli.Examen2.aplicacion.puerto.entrada.DeserializarExamenesUseCase;
import co.edu.poli.Examen2.aplicacion.puerto.salida.AlmacenamientoExamenesPort;
import co.edu.poli.Examen2.aplicacion.puerto.salida.ExamenRepositoryPort;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.List;

/**
 * Caso de uso que carga los exámenes persistidos y reemplaza los que hay en memoria.
 */
public class DeserializarExamenesService implements DeserializarExamenesUseCase {

    private final ExamenRepositoryPort repositorio;
    private final AlmacenamientoExamenesPort almacenamiento;
    private final PublicadorEventos publicador;

    /**
     * @param repositorio    destino de los exámenes cargados
     * @param almacenamiento origen persistente
     * @param publicador     sujeto al que se notifica el resultado
     */
    public DeserializarExamenesService(ExamenRepositoryPort repositorio,
                                       AlmacenamientoExamenesPort almacenamiento,
                                       PublicadorEventos publicador) {
        this.repositorio = repositorio;
        this.almacenamiento = almacenamiento;
        this.publicador = publicador;
    }

    @Override
    public int deserializar() {
        List<ExamenSangre> examenes = almacenamiento.cargarTodos();
        repositorio.reemplazarTodos(examenes);
        publicador.publicar(TipoEvento.EXAMENES_DESERIALIZADOS,
                "Se deserializaron " + examenes.size() + " examen(es).");
        return examenes.size();
    }
}
