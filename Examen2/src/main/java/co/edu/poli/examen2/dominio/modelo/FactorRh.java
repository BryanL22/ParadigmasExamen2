package co.edu.poli.examen2.dominio.modelo;

/**
 * Factor Rh de una muestra de sangre.
 */
public enum FactorRh {
    POSITIVO("+"),
    NEGATIVO("-");

    private final String simbolo;

    FactorRh(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @return símbolo del factor ("+" o "-")
     */
    public String getSimbolo() {
        return simbolo;
    }
}
