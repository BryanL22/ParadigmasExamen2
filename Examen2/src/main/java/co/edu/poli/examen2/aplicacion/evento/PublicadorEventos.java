package co.edu.poli.examen2.aplicacion.evento;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Sujeto del patrón Observer: mantiene la lista de observadores y les reenvía cada evento.
 */
public class PublicadorEventos {

    private final List<ObservadorExamen> observadores = new CopyOnWriteArrayList<>();

    /**
     * @param observador observador a registrar
     */
    public void suscribir(ObservadorExamen observador) {
        observadores.add(observador);
    }

    /**
     * @param observador observador a retirar
     */
    public void desuscribir(ObservadorExamen observador) {
        observadores.remove(observador);
    }

    /**
     * Notifica el evento a todos los observadores suscritos.
     *
     * @param tipo    tipo de evento
     * @param mensaje descripción del evento
     */
    public void publicar(TipoEvento tipo, String mensaje) {
        EventoExamen evento = new EventoExamen(tipo, mensaje);
        observadores.forEach(o -> o.notificar(evento));
    }
}
