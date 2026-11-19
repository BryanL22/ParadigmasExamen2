package co.edu.poli.Examen2.aplicacion.evento;

/**
 * Observador de eventos de exámenes (patrón de comportamiento <b>Observer</b>).
 * La interfaz gráfica y el registro de auditoría lo implementan sin que los casos de uso los conozcan.
 */
@FunctionalInterface
public interface ObservadorExamen {

    /**
     * @param evento evento ocurrido
     */
    void notificar(EventoExamen evento);
}
