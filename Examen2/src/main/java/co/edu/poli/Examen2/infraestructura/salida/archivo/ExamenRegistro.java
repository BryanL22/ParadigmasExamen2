package co.edu.poli.Examen2.infraestructura.salida.archivo;

import co.edu.poli.Examen2.dominio.modelo.ExamenSangre;
import co.edu.poli.Examen2.dominio.modelo.FactorRh;
import co.edu.poli.Examen2.dominio.modelo.TipoSangre;

import java.io.Serializable;

/**
 * Representación serializable de un examen. Mantiene {@link java.io.Serializable}
 * fuera del dominio y desacopla el formato del archivo de la entidad.
 *
 * @param codigo         código del examen
 * @param nombrePaciente nombre del paciente
 * @param costo          costo del examen
 * @param tipoSangre     nombre del grupo ABO
 * @param factorRh       nombre del factor Rh
 */
public record ExamenRegistro(String codigo, String nombrePaciente, double costo,
                             String tipoSangre, String factorRh) implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @param examen entidad de dominio
     * @return registro equivalente
     */
    public static ExamenRegistro desde(ExamenSangre examen) {
        return new ExamenRegistro(examen.getCodigo(), examen.getNombrePaciente(), examen.getCosto(),
                examen.getTipoSangre().name(), examen.getFactorRh().name());
    }

    /**
     * Reconstruye la entidad pasando de nuevo por las validaciones del dominio.
     *
     * @return entidad de dominio
     */
    public ExamenSangre aDominio() {
        return ExamenSangre.builder()
                .codigo(codigo)
                .nombrePaciente(nombrePaciente)
                .costo(costo)
                .tipoSangre(TipoSangre.valueOf(tipoSangre))
                .factorRh(FactorRh.valueOf(factorRh))
                .build();
    }
}
