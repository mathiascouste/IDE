package ch.makery.ide.view;

import ch.makery.ide.model.Project;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProjectEditDialogController {
	@FXML
	private TextField projectNameTextField;
	@FXML
	private TextField execNameTextField;

	private Stage dialogStage;
	private Project project;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 *
	 * @param person
	 */
	public void setProject(Project project) {
		this.project = project;

		projectNameTextField.setText(project.getProjectName());
		execNameTextField.setText(project.getExecutableName());
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			project.setProjectName(projectNameTextField.getText());
			project.setExecutableName(execNameTextField.getText());

			okClicked = true;
			dialogStage.close();
		}
	}

	private boolean isInputValid() {
		return !projectNameTextField.getText().isEmpty() && !execNameTextField.getText().isEmpty();
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
}
