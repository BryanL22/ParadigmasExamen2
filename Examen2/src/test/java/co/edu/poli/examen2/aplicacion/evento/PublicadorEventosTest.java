package co.edu.poli.examen2.aplicacion.evento;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PublicadorEventosTest {

    @Test
    void notificaATodosLosObservadoresSuscritos() {
        PublicadorEventos publicador = new PublicadorEventos();
        List<EventoExamen> primero = new ArrayList<>();
        List<EventoExamen> segundo = new ArrayList<>();
        publicador.suscribir(primero::add);
        publicador.suscribir(segundo::add);

        publicador.publicar(TipoEvento.EXAMEN_REGISTRADO, "hola");

        EventoExamen esperado = new EventoExamen(TipoEvento.EXAMEN_REGISTRADO, "hola");
        assertEquals(List.of(esperado), primero);
        assertEquals(List.of(esperado), segundo);
    }

    @Test
    void observadorDesuscritoNoRecibeEventos() {
        PublicadorEventos publicador = new PublicadorEventos();
        List<EventoExamen> recibidos = new ArrayList<>();
        ObservadorExamen observador = recibidos::add;
        publicador.suscribir(observador);
        publicador.desuscribir(observador);

        publicador.publicar(TipoEvento.EXAMENES_SERIALIZADOS, "x");

        assertTrue(recibidos.isEmpty());
    }
}
