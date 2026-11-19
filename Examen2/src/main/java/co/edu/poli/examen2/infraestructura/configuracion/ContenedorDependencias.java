package co.edu.poli.examen2.infraestructura.configuracion;

import co.edu.poli.examen2.aplicacion.evento.PublicadorEventos;
import co.edu.poli.examen2.aplicacion.fachada.GestionExamenesFacade;
import co.edu.poli.examen2.aplicacion.puerto.salida.AlmacenamientoExamenesPort;
import co.edu.poli.examen2.aplicacion.puerto.salida.ExamenRepositoryPort;
import co.edu.poli.examen2.aplicacion.servicio.DeserializarExamenesService;
import co.edu.poli.examen2.aplicacion.servicio.ListarExamenesService;
import co.edu.poli.examen2.aplicacion.servicio.RegistrarExamenService;
import co.edu.poli.examen2.aplicacion.servicio.SerializarExamenesService;
import co.edu.poli.examen2.infraestructura.salida.archivo.AlmacenamientoArchivoSerializado;
import co.edu.poli.examen2.infraestructura.salida.log.ObservadorBitacora;
import co.edu.poli.examen2.infraestructura.salida.memoria.RepositorioExamenesEnMemoria;

import java.nio.file.Path;

/**
 * Raíz de composición: único lugar donde se eligen los adaptadores concretos
 * y se conectan con los casos de uso. Cambiar la persistencia solo exige modificar esta clase.
 */
public final class ContenedorDependencias {

    /** Archivo por defecto donde se serializan los exámenes. */
    public static final Path ARCHIVO_POR_DEFECTO = Path.of("examenes.dat");

    private ContenedorDependencias() {
    }

    /**
     * Construye la fachada con los adaptadores de producción.
     *
     * @param archivo archivo de serialización
     * @return fachada lista para usar desde la interfaz gráfica
     */
    public static GestionExamenesFacade crearFachada(Path archivo) {
        ExamenRepositoryPort repositorio = new RepositorioExamenesEnMemoria();
        AlmacenamientoExamenesPort almacenamiento = new AlmacenamientoArchivoSerializado(archivo);
        PublicadorEventos publicador = new PublicadorEventos();
        publicador.suscribir(new ObservadorBitacora());

        return new GestionExamenesFacade(
                new RegistrarExamenService(repositorio, publicador),
                new ListarExamenesService(repositorio),
                new SerializarExamenesService(repositorio, almacenamiento, publicador),
                new DeserializarExamenesService(repositorio, almacenamiento, publicador),
                publicador);
    }
}
