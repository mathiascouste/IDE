package ch.makery.ide.view;

import java.io.File;

import ch.makery.ide.MainApp;
import ch.makery.ide.model.Project;
import ch.makery.ide.model.SourceFile;
import ch.makery.ide.model.compilation.Compiler;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class RootLayoutController {

	// Reference to the main application
	private MainApp mainApp;

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void handleNewProject() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(this.mainApp.getPrimaryStage());

		if (selectedDirectory != null) {
			Project project = new Project();
			project.setProjectRepertoryPath(selectedDirectory.getAbsolutePath());
			if (this.mainApp.showProjectEditDialog(project)) {
				project.buildWorkspace();
				this.mainApp.setCurrentProject(project);
			}
		}
	}

	@FXML
	private void handleLoadProject() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select project property file");
		File file = fileChooser.showOpenDialog(this.mainApp.getPrimaryStage());
		if (file != null) {
			this.mainApp.setCurrentProject(Project.load(file));
		}
	}

	@FXML
	private void handleSaveProject() {
		if (this.mainApp.getCurrentProject() != null) {
			this.mainApp.getCurrentProject().save();
		}
	}

	@FXML
	private void handleCloseProject() {
		this.mainApp.hideProject();
	}

	@FXML
	private void handleSaveFile() {
		this.mainApp.getFilesOverviewController().saveCurrentFile();
	}

	@FXML
	private void handleSaveAllFile() {
		for (SourceFile src : this.mainApp.getCurrentProject().getSourceFiles()) {
			src.save();
		}
	}

	@FXML
	private void handleAddFileToProject() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select file to add");
		fileChooser.setInitialDirectory(new File(this.mainApp.getCurrentProject().getProjectRepertoryPath()));
		File file = fileChooser.showOpenDialog(this.mainApp.getPrimaryStage());
		if (file != null && this.mainApp.getCurrentProject() != null) {
			this.mainApp.getCurrentProject().addFileToProject(new SourceFile(file));
		}
	}

	@FXML
	private void handleEditProject() {
		this.mainApp.showProjectEditDialog(this.mainApp.getCurrentProject());
	}

	@FXML
	private void handleCompileCurrentFile() {
		Compiler c = new Compiler();
		c.build(this.mainApp.getFilesOverviewController().getCurrentFile(), this.mainApp.getCurrentProject());
	}

	@FXML
	private void handleCompileAllFiles() {
		Compiler c = new Compiler();
		c.build(this.mainApp.getCurrentProject());
	}

	@FXML
	private void handleConstructProject() {
		Compiler c = new Compiler();
		c.construct(this.mainApp.getCurrentProject());
	}

	@FXML
	private void handleShowHideTerm() {
		this.mainApp.showHideTerm();
	}

	@FXML
	private void handleShowHideToolBox() {

	}

}
