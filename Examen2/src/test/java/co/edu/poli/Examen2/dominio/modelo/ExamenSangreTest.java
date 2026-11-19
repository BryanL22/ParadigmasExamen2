package co.edu.poli.Examen2.dominio.modelo;

import co.edu.poli.Examen2.dominio.excepcion.ExamenInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExamenSangreTest {

    private ExamenSangre.Builder valido() {
        return ExamenSangre.builder()
                .codigo(" exm-001 ")
                .nombrePaciente("  Ana Pérez ")
                .costo(150.5)
                .tipoSangre(TipoSangre.AB)
                .factorRh(FactorRh.NEGATIVO);
    }

    @Test
    void builderCreaExamenNormalizado() {
        ExamenSangre examen = valido().build();

        assertEquals("EXM-001", examen.getCodigo());
        assertEquals("Ana Pérez", examen.getNombrePaciente());
        assertEquals(150.5, examen.getCosto());
        assertEquals(TipoSangre.AB, examen.getTipoSangre());
        assertEquals(FactorRh.NEGATIVO, examen.getFactorRh());
        assertEquals("AB-", examen.getGrupoSanguineo());
        assertEquals("[EXM-001] Ana Pérez | AB- | $150.50", examen.toString());
    }

    @Test
    void rechazaCodigoVacio() {
        ExamenSangre.Builder b = valido().codigo("  ");
        assertEquals("El código del examen es obligatorio.",
                assertThrows(ExamenInvalidoException.class, b::build).getMessage());
    }

    @Test
    void rechazaNombreNulo() {
        ExamenSangre.Builder b = valido().nombrePaciente(null);
        assertThrows(ExamenInvalidoException.class, b::build);
    }

    @Test
    void rechazaCostoCeroONegativo() {
        assertThrows(ExamenInvalidoException.class, valido().costo(0)::build);
        assertThrows(ExamenInvalidoException.class, valido().costo(-10)::build);
        assertThrows(ExamenInvalidoException.class, valido().costo(Double.NaN)::build);
    }

    @Test
    void rechazaTipoOFactorFaltante() {
        assertThrows(ExamenInvalidoException.class, valido().tipoSangre(null)::build);
        assertThrows(ExamenInvalidoException.class, valido().factorRh(null)::build);
    }

    @Test
    void igualdadPorCodigo() {
        ExamenSangre a = valido().build();
        ExamenSangre b = valido().nombrePaciente("Otro").costo(10).build();
        ExamenSangre c = valido().codigo("EXM-002").build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a, "EXM-001");
    }

    @Test
    void tipoSangreDesdeTexto() {
        assertEquals(TipoSangre.O, TipoSangre.desdeTexto(" o "));
        assertThrows(ExamenInvalidoException.class, () -> TipoSangre.desdeTexto(null));
        assertThrows(ExamenInvalidoException.class, () -> TipoSangre.desdeTexto("Z"));
    }
}
