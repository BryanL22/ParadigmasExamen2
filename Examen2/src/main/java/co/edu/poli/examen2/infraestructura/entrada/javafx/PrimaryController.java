package co.edu.poli.examen2.infraestructura.entrada.javafx;

import co.edu.poli.examen2.aplicacion.evento.EventoExamen;
import co.edu.poli.examen2.aplicacion.fachada.GestionExamenesFacade;
import co.edu.poli.examen2.aplicacion.puerto.salida.PersistenciaException;
import co.edu.poli.examen2.dominio.excepcion.ExamenInvalidoException;
import co.edu.poli.examen2.dominio.modelo.ExamenSangre;
import co.edu.poli.examen2.dominio.modelo.FactorRh;
import co.edu.poli.examen2.dominio.modelo.TipoSangre;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.util.List;

/**
 * Adaptador de entrada JavaFX. Traduce los eventos de la vista en llamadas a la
 * fachada de la aplicación y muestra los resultados; no contiene reglas de negocio.
 * Se suscribe como observador para mostrar los mensajes de éxito de los casos de uso.
 */
public class PrimaryController {

	@FXML
	private TextField txtCodigoExamen;
	@FXML
	private TextField txtNombrePaciente;
	@FXML
	private TextField txtCosto;
	@FXML
	private ComboBox<String> cmbTipoSangre;
	@FXML
	private RadioButton rbPositivo;
	@FXML
	private RadioButton rbNegativo;
	@FXML
	private ToggleGroup tgFactorRH;
	@FXML
	private TextArea txtAreaMensajes;

	private final GestionExamenesFacade fachada;

	/**
	 * @param fachada punto de acceso a los casos de uso
	 */
	public PrimaryController(GestionExamenesFacade fachada) {
		this.fachada = fachada;
	}

	@FXML
	public void initialize() {
		fachada.suscribir(this::mostrarEvento);
	}

	@FXML
	private void handleGuardar() {
		try {
			ExamenSangre examen = ExamenSangre.builder()
					.codigo(txtCodigoExamen.getText())
					.nombrePaciente(txtNombrePaciente.getText())
					.costo(leerCosto())
					.tipoSangre(TipoSangre.desdeTexto(cmbTipoSangre.getValue()))
					.factorRh(rbNegativo.isSelected() ? FactorRh.NEGATIVO : FactorRh.POSITIVO)
					.build();
			fachada.registrar(examen);
			handleLimpiar();
		} catch (ExamenInvalidoException e) {
			mostrarError(e);
		}
	}

	@FXML
	private void handleLimpiar() {
		txtCodigoExamen.clear();
		txtNombrePaciente.clear();
		txtCosto.clear();
		cmbTipoSangre.getSelectionModel().clearSelection();
		rbPositivo.setSelected(true);
	}

	@FXML
	private void handleSerializar() {
		try {
			fachada.serializar();
		} catch (PersistenciaException e) {
			mostrarError(e);
		}
	}

	@FXML
	private void handleDeserializar() {
		try {
			fachada.deserializar();
		} catch (PersistenciaException | ExamenInvalidoException e) {
			mostrarError(e);
		}
	}

	@FXML
	private void handleListar() {
		List<ExamenSangre> examenes = fachada.listar();
		if (examenes.isEmpty()) {
			mostrar("No hay exámenes registrados.");
			return;
		}
		StringBuilder texto = new StringBuilder("Exámenes registrados (" + examenes.size() + "):");
		examenes.forEach(e -> texto.append(System.lineSeparator()).append("  ").append(e));
		mostrar(texto.toString());
	}

	private double leerCosto() {
		String valor = txtCosto.getText() == null ? "" : txtCosto.getText().trim().replace(',', '.');
		try {
			return Double.parseDouble(valor);
		} catch (NumberFormatException e) {
			throw new ExamenInvalidoException("El costo debe ser un número válido.");
		}
	}

	private void mostrarEvento(EventoExamen evento) {
		mostrar(evento.mensaje());
	}

	private void mostrarError(RuntimeException e) {
		mostrar("Error: " + e.getMessage());
	}

	private void mostrar(String mensaje) {
		txtAreaMensajes.appendText(mensaje + System.lineSeparator());
	}
}
