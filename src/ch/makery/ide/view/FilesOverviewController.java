package ch.makery.ide.view;

import ch.makery.ide.MainApp;
import ch.makery.ide.model.SourceFile;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class FilesOverviewController {

	@FXML
	private TableView<SourceFile> filesTable;
	@FXML
	private TableColumn<SourceFile, String> nameColumn;
	@FXML
	private TableColumn<SourceFile, String> stateColumn;
	@FXML
	private TextArea sourceFileArea;

	// Reference to the main application.
	private MainApp mainApp;
	private SourceFile currentFile;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		stateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());

		// Listen for selection changes and show the person details when
		// changed.
		filesTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showFile(newValue));
	}

	@FXML
	private void handleTextChange() {
		this.currentFile.setState(SourceFile.UNSAVED);
		this.currentFile.setContent(this.sourceFileArea.getText());
	}

	private void showFile(SourceFile newValue) {
		if(currentFile != null) {
			this.currentFile.setContent(this.sourceFileArea.getText());
		}
		this.currentFile = newValue;
		this.sourceFileArea.setText(this.currentFile.getContent());
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		filesTable.setItems(mainApp.getCurrentProject().getSourceFiles());
	}

	public MainApp getMainApp() {
		return mainApp;
	}

	public SourceFile getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(SourceFile currentFile) {
		this.currentFile = currentFile;
	}

	public void saveCurrentFile() {
		if(currentFile != null) {
			this.currentFile.setContent(this.sourceFileArea.getText());
			this.currentFile.save();
		}
	}
}
