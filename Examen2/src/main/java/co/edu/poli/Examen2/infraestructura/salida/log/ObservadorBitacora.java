package co.edu.poli.Examen2.infraestructura.salida.log;

import co.edu.poli.Examen2.aplicacion.evento.EventoExamen;
import co.edu.poli.Examen2.aplicacion.evento.ObservadorExamen;

import java.util.logging.Logger;

/**
 * Observador que deja constancia de cada evento en el log de la aplicación (auditoría).
 */
public class ObservadorBitacora implements ObservadorExamen {

    private static final Logger LOG = Logger.getLogger(ObservadorBitacora.class.getName());

    @Override
    public void notificar(EventoExamen evento) {
        LOG.info(() -> evento.tipo() + ": " + evento.mensaje());
    }
}
