package co.edu.poli.Examen2.infraestructura.salida.archivo;

import co.edu.poli.Examen2.aplicacion.puerto.salida.AlmacenamientoExamenesPort;
import co.edu.poli.Examen2.aplicacion.puerto.salida.PersistenciaException;
import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador de salida que persiste los exámenes en un archivo binario usando
 * la serialización estándar de Java.
 */
public class AlmacenamientoArchivoSerializado implements AlmacenamientoExamenesPort {

    private final Path archivo;

    /**
     * @param archivo ruta del archivo de datos
     */
    public AlmacenamientoArchivoSerializado(Path archivo) {
        this.archivo = archivo;
    }

    @Override
    public void guardarTodos(List<ExamenSangre> examenes) {
        ArrayList<ExamenRegistro> registros = new ArrayList<>();
        examenes.forEach(e -> registros.add(ExamenRegistro.desde(e)));
        try (ObjectOutputStream salida = new ObjectOutputStream(Files.newOutputStream(archivo))) {
            salida.writeObject(registros);
        } catch (IOException e) {
            throw new PersistenciaException("No se pudo escribir el archivo " + archivo, e);
        }
    }

    @Override
    public List<ExamenSangre> cargarTodos() {
        if (!Files.exists(archivo)) {
            throw new PersistenciaException("No existe el archivo " + archivo
                    + ". Serialice primero.", null);
        }
        try (ObjectInputStream entrada = new ObjectInputStream(Files.newInputStream(archivo))) {
            List<ExamenSangre> examenes = new ArrayList<>();
            for (Object registro : (List<?>) entrada.readObject()) {
                examenes.add(((ExamenRegistro) registro).aDominio());
            }
            return examenes;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new PersistenciaException("No se pudo leer el archivo " + archivo, e);
        }
    }
}
