package co.edu.poli.examen2.dominio.modelo;

import co.edu.poli.examen2.dominio.excepcion.ExamenInvalidoException;

import java.util.Locale;
import java.util.Objects;

/**
 * Entidad de dominio que representa un examen de sangre de un paciente.
 * <p>
 * Es inmutable y solo puede crearse mediante su {@link Builder} (patrón creacional
 * <b>Builder</b>), que garantiza que toda instancia cumpla las reglas de negocio:
 * código y nombre obligatorios, costo mayor que cero, tipo de sangre y factor Rh definidos.
 * Dos exámenes son iguales si tienen el mismo código.
 */
public final class ExamenSangre {

    private final String codigo;
    private final String nombrePaciente;
    private final double costo;
    private final TipoSangre tipoSangre;
    private final FactorRh factorRh;

    private ExamenSangre(Builder builder) {
        this.codigo = builder.codigo;
        this.nombrePaciente = builder.nombrePaciente;
        this.costo = builder.costo;
        this.tipoSangre = builder.tipoSangre;
        this.factorRh = builder.factorRh;
    }

    /**
     * @return un nuevo constructor de exámenes
     */
    public static Builder builder() {
        return new Builder();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public double getCosto() {
        return costo;
    }

    public TipoSangre getTipoSangre() {
        return tipoSangre;
    }

    public FactorRh getFactorRh() {
        return factorRh;
    }

    /**
     * @return grupo sanguíneo completo, por ejemplo "AB+" u "O-"
     */
    public String getGrupoSanguineo() {
        return tipoSangre.name() + factorRh.getSimbolo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamenSangre)) {
            return false;
        }
        return codigo.equals(((ExamenSangre) o).codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%s] %s | %s | $%.2f",
                codigo, nombrePaciente, getGrupoSanguineo(), costo);
    }

    /**
     * Constructor paso a paso de {@link ExamenSangre} (patrón Builder).
     * Las validaciones se ejecutan en {@link #build()}.
     */
    public static final class Builder {

        private String codigo;
        private String nombrePaciente;
        private double costo;
        private TipoSangre tipoSangre;
        private FactorRh factorRh;

        private Builder() {
        }

        public Builder codigo(String codigo) {
            this.codigo = codigo == null ? null : codigo.trim().toUpperCase();
            return this;
        }

        public Builder nombrePaciente(String nombrePaciente) {
            this.nombrePaciente = nombrePaciente == null ? null : nombrePaciente.trim();
            return this;
        }

        public Builder costo(double costo) {
            this.costo = costo;
            return this;
        }

        public Builder tipoSangre(TipoSangre tipoSangre) {
            this.tipoSangre = tipoSangre;
            return this;
        }

        public Builder factorRh(FactorRh factorRh) {
            this.factorRh = factorRh;
            return this;
        }

        /**
         * Valida los datos acumulados y crea el examen.
         *
         * @return examen válido e inmutable
         * @throws ExamenInvalidoException si algún dato incumple las reglas de negocio
         */
        public ExamenSangre build() {
            if (codigo == null || codigo.isEmpty()) {
                throw new ExamenInvalidoException("El código del examen es obligatorio.");
            }
            if (nombrePaciente == null || nombrePaciente.isEmpty()) {
                throw new ExamenInvalidoException("El nombre del paciente es obligatorio.");
            }
            if (Double.isNaN(costo) || costo <= 0) {
                throw new ExamenInvalidoException("El costo debe ser mayor que cero.");
            }
            if (tipoSangre == null) {
                throw new ExamenInvalidoException("Debe seleccionar un tipo de sangre.");
            }
            if (factorRh == null) {
                throw new ExamenInvalidoException("Debe seleccionar el factor Rh.");
            }
            return new ExamenSangre(this);
        }
    }
}
