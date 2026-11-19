package co.edu.poli.Examen2.aplicacion.servicio;

import co.edu.poli.Examen2.aplicacion.evento.EventoExamen;
import co.edu.poli.Examen2.aplicacion.evento.PublicadorEventos;
import co.edu.poli.Examen2.aplicacion.evento.TipoEvento;
import co.edu.poli.Examen2.aplicacion.puerto.salida.AlmacenamientoExamenesPort;
import co.edu.poli.Examen2.dominio.excepcion.ExamenDuplicadoException;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;
import co.edu.poli.Examen2.dominio.modelo.FactorRh;
import co.edu.poli.Examen2.dominio.modelo.TipoSangre;
import co.edu.poli.Examen2.infraestructura.salida.memoria.RepositorioExamenesEnMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiciosExamenTest {

    /** Almacenamiento falso que guarda en una lista. */
    private static final class AlmacenamientoFalso implements AlmacenamientoExamenesPort {
        private List<ExamenSangre> guardados = new ArrayList<>();

        @Override
        public void guardarTodos(List<ExamenSangre> examenes) {
            guardados = new ArrayList<>(examenes);
        }

        @Override
        public List<ExamenSangre> cargarTodos() {
            return guardados;
        }
    }

    private RepositorioExamenesEnMemoria repositorio;
    private AlmacenamientoFalso almacenamiento;
    private PublicadorEventos publicador;
    private final List<EventoExamen> eventos = new ArrayList<>();
    private RegistrarExamenService registrar;

    private static ExamenSangre examen(String codigo) {
        return ExamenSangre.builder().codigo(codigo).nombrePaciente("Paciente " + codigo)
                .costo(100).tipoSangre(TipoSangre.O).factorRh(FactorRh.POSITIVO).build();
    }

    @BeforeEach
    void preparar() {
        repositorio = new RepositorioExamenesEnMemoria();
        almacenamiento = new AlmacenamientoFalso();
        publicador = new PublicadorEventos();
        publicador.suscribir(eventos::add);
        registrar = new RegistrarExamenService(repositorio, publicador);
    }

    @Test
    void registrarGuardaYNotifica() {
        registrar.registrar(examen("EXM-001"));

        assertTrue(repositorio.existe("EXM-001"));
        assertEquals(1, eventos.size());
        assertEquals(TipoEvento.EXAMEN_REGISTRADO, eventos.get(0).tipo());
    }

    @Test
    void registrarRechazaCodigoDuplicado() {
        registrar.registrar(examen("EXM-001"));
        ExamenSangre repetido = examen("EXM-001");

        assertThrows(ExamenDuplicadoException.class, () -> registrar.registrar(repetido));
        assertEquals(1, repositorio.listarTodos().size());
        assertEquals(1, eventos.size());
    }

    @Test
    void listarDevuelveCopiaInmodificable() {
        registrar.registrar(examen("EXM-001"));
        registrar.registrar(examen("EXM-002"));

        List<ExamenSangre> lista = new ListarExamenesService(repositorio).listar();

        assertEquals(List.of(examen("EXM-001"), examen("EXM-002")), lista);
        ExamenSangre otro = examen("EXM-003");
        assertThrows(UnsupportedOperationException.class, () -> lista.add(otro));
    }

    @Test
    void serializarYDeserializarRestauranLosExamenes() {
        registrar.registrar(examen("EXM-001"));
        registrar.registrar(examen("EXM-002"));

        int guardados = new SerializarExamenesService(repositorio, almacenamiento, publicador).serializar();
        repositorio.reemplazarTodos(List.of());
        int cargados = new DeserializarExamenesService(repositorio, almacenamiento, publicador).deserializar();

        assertEquals(2, guardados);
        assertEquals(2, cargados);
        assertEquals(2, repositorio.listarTodos().size());
        assertEquals(TipoEvento.EXAMENES_SERIALIZADOS, eventos.get(2).tipo());
        assertEquals(TipoEvento.EXAMENES_DESERIALIZADOS, eventos.get(3).tipo());
    }
}
