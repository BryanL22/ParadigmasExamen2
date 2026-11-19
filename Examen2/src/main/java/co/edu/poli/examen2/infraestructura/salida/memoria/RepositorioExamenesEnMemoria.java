package co.edu.poli.examen2.infraestructura.salida.memoria;

import co.edu.poli.examen2.aplicacion.puerto.salida.ExamenRepositoryPort;
import co.edu.poli.examen2.dominio.modelo.ExamenSangre;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Adaptador de salida que guarda los exámenes en memoria, indexados por código
 * y conservando el orden de registro.
 */
public class RepositorioExamenesEnMemoria implements ExamenRepositoryPort {

    private final Map<String, ExamenSangre> examenes = new LinkedHashMap<>();

    @Override
    public void guardar(ExamenSangre examen) {
        examenes.put(examen.getCodigo(), examen);
    }

    @Override
    public boolean existe(String codigo) {
        return examenes.containsKey(codigo);
    }

    @Override
    public List<ExamenSangre> listarTodos() {
        return new ArrayList<>(examenes.values());
    }

    @Override
    public void reemplazarTodos(List<ExamenSangre> nuevos) {
        examenes.clear();
        nuevos.forEach(this::guardar);
    }
}
