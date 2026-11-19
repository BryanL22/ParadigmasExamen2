package co.edu.poli.Examen2.aplicacion.fachada;

import co.edu.poli.Examen2.aplicacion.evento.ObservadorExamen;
import co.edu.poli.Examen2.aplicacion.evento.PublicadorEventos;
import co.edu.poli.Examen2.aplicacion.puerto.entrada.DeserializarExamenesUseCase;
import co.edu.poli.Examen2.aplicacion.puerto.entrada.ListarExamenesUseCase;
import co.edu.poli.Examen2.aplicacion.puerto.entrada.RegistrarExamenUseCase;
import co.edu.poli.Examen2.aplicacion.puerto.entrada.SerializarExamenesUseCase;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.util.List;

/**
 * Fachada (patrón estructural <b>Facade</b>) que ofrece a los adaptadores de entrada
 * un único punto de acceso a todos los casos de uso de exámenes y a la suscripción de eventos.
 * Así la interfaz gráfica depende de una sola clase en lugar de cinco colaboradores.
 */
public class GestionExamenesFacade {

    private final RegistrarExamenUseCase registrar;
    private final ListarExamenesUseCase listar;
    private final SerializarExamenesUseCase serializar;
    private final DeserializarExamenesUseCase deserializar;
    private final PublicadorEventos publicador;

    /**
     * @param registrar    caso de uso de registro
     * @param listar       caso de uso de consulta
     * @param serializar   caso de uso de serialización
     * @param deserializar caso de uso de deserialización
     * @param publicador   sujeto de eventos
     */
    public GestionExamenesFacade(RegistrarExamenUseCase registrar,
                                 ListarExamenesUseCase listar,
                                 SerializarExamenesUseCase serializar,
                                 DeserializarExamenesUseCase deserializar,
                                 PublicadorEventos publicador) {
        this.registrar = registrar;
        this.listar = listar;
        this.serializar = serializar;
        this.deserializar = deserializar;
        this.publicador = publicador;
    }

    /**
     * @see RegistrarExamenUseCase#registrar(ExamenSangre)
     */
    public ExamenSangre registrar(ExamenSangre examen) {
        return registrar.registrar(examen);
    }

    /**
     * @see ListarExamenesUseCase#listar()
     */
    public List<ExamenSangre> listar() {
        return listar.listar();
    }

    /**
     * @see SerializarExamenesUseCase#serializar()
     */
    public int serializar() {
        return serializar.serializar();
    }

    /**
     * @see DeserializarExamenesUseCase#deserializar()
     */
    public int deserializar() {
        return deserializar.deserializar();
    }

    /**
     * @param observador observador que recibirá los eventos de los casos de uso
     */
    public void suscribir(ObservadorExamen observador) {
        publicador.suscribir(observador);
    }
}
