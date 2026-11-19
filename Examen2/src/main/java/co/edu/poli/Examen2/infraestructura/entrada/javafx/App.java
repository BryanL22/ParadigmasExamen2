package co.edu.poli.Examen2.infraestructura.entrada.javafx;

import co.edu.poli.Examen2.aplicacion.fachada.GestionExamenesFacade;
import co.edu.poli.Examen2.infraestructura.configuracion.ContenedorDependencias;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punto de arranque JavaFX. Obtiene la fachada de la raíz de composición y la
 * inyecta en el controlador mediante una fábrica de controladores.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        GestionExamenesFacade fachada = ContenedorDependencias.crearFachada(ContenedorDependencias.ARCHIVO_POR_DEFECTO);

        FXMLLoader loader = new FXMLLoader(App.class.getResource("/co/edu/poli/Examen2/primary.fxml"));
        loader.setControllerFactory(tipo -> new PrimaryController(fachada));
        Parent raiz = loader.load();

        stage.setTitle("Exámenes de Sangre");
        stage.setScene(new Scene(raiz, 640, 620));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}
