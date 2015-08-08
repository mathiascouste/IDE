package ch.makery.ide.view;

import java.io.File;

import ch.makery.ide.MainApp;
import ch.makery.ide.model.SourceFile;
import ch.makery.ide.model.compilation.Compiler;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

public class ToolBarController {

	// Reference to the main application.
	private MainApp mainApp;

	@FXML
	private void open() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select file to add");
		File file = fileChooser.showOpenDialog(this.mainApp.getPrimaryStage());
		if (file != null && this.mainApp.getCurrentProject() != null) {
			this.mainApp.getCurrentProject().addFileToProject(new SourceFile(file));
		}
	}

	@FXML
	private void save() {
		this.mainApp.getFilesOverviewController().saveCurrentFile();
	}

	@FXML
	private void compile() {
		Compiler c = new Compiler();
		c.build(this.mainApp.getFilesOverviewController().getCurrentFile(), this.mainApp.getCurrentProject());
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public MainApp getMainApp() {
		return mainApp;
	}
}
