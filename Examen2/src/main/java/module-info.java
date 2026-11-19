/**
 * Aplicación de registro de exámenes de sangre con arquitectura hexagonal.
 */
module co.edu.poli.Examen2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens co.edu.poli.Examen2.infraestructura.entrada.javafx to javafx.fxml;

    exports co.edu.poli.Examen2.infraestructura.entrada.javafx;
}
