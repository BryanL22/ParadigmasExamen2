package co.edu.poli.Examen2.aplicacion.evento;

/**
 * Evento publicado cuando un caso de uso termina con éxito.
 *
 * @param tipo    tipo de evento
 * @param mensaje descripción legible para el usuario
 */
public record EventoExamen(TipoEvento tipo, String mensaje) {
}
