package co.edu.poli.examen2.infraestructura.configuracion;

import co.edu.poli.examen2.aplicacion.evento.EventoExamen;
import co.edu.poli.examen2.aplicacion.fachada.GestionExamenesFacade;
import co.edu.poli.examen2.aplicacion.puerto.salida.PersistenciaException;
import co.edu.poli.examen2.dominio.modelo.ExamenSangre;
import co.edu.poli.examen2.dominio.modelo.FactorRh;
import co.edu.poli.examen2.dominio.modelo.TipoSangre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Prueba la fachada con los adaptadores reales (memoria + archivo serializado).
 */
class GestionExamenesIntegracionTest {

    @TempDir
    Path carpeta;

    private static ExamenSangre examen(String codigo, TipoSangre tipo, FactorRh rh) {
        return ExamenSangre.builder().codigo(codigo).nombrePaciente("Paciente")
                .costo(80.25).tipoSangre(tipo).factorRh(rh).build();
    }

    @Test
    void flujoCompletoRegistrarSerializarYDeserializar() {
        Path archivo = carpeta.resolve("examenes.dat");
        GestionExamenesFacade fachada = ContenedorDependencias.crearFachada(archivo);
        List<EventoExamen> eventos = new ArrayList<>();
        fachada.suscribir(eventos::add);

        fachada.registrar(examen("EXM-001", TipoSangre.A, FactorRh.POSITIVO));
        fachada.registrar(examen("EXM-002", TipoSangre.B, FactorRh.NEGATIVO));
        assertEquals(2, fachada.serializar());
        assertTrue(Files.exists(archivo));

        GestionExamenesFacade otraSesion = ContenedorDependencias.crearFachada(archivo);
        assertEquals(2, otraSesion.deserializar());
        List<ExamenSangre> cargados = otraSesion.listar();

        assertEquals("B-", cargados.get(1).getGrupoSanguineo());
        assertEquals(80.25, cargados.get(0).getCosto());
        assertEquals(3, eventos.size());
    }

    @Test
    void deserializarSinArchivoLanzaPersistenciaException() {
        GestionExamenesFacade fachada = ContenedorDependencias.crearFachada(carpeta.resolve("no-existe.dat"));
        assertThrows(PersistenciaException.class, fachada::deserializar);
    }

    @Test
    void archivoCorruptoLanzaPersistenciaException() throws IOException {
        Path archivo = carpeta.resolve("corrupto.dat");
        Files.writeString(archivo, "esto no es un objeto serializado");
        GestionExamenesFacade fachada = ContenedorDependencias.crearFachada(archivo);
        assertThrows(PersistenciaException.class, fachada::deserializar);
    }

    @Test
    void escribirEnCarpetaInexistenteLanzaPersistenciaException() {
        GestionExamenesFacade fachada = ContenedorDependencias.crearFachada(carpeta.resolve("x/y/examenes.dat"));
        assertThrows(PersistenciaException.class, fachada::serializar);
    }
}
