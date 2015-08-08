package ch.makery.ide;

import java.io.IOException;

import ch.makery.ide.model.Project;
import ch.makery.ide.view.FilesOverviewController;
import ch.makery.ide.view.ProjectEditDialogController;
import ch.makery.ide.view.RootLayoutController;
import ch.makery.ide.view.TerminalController;
import ch.makery.ide.view.ToolBarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Project currentProject;
	private FilesOverviewController filesOverviewController;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("IDE app");

		initRootLayout();

		showTerminal();

	}

	private void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showProject() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProjectOverview.fxml"));
			AnchorPane filesOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(filesOverview);

			// Give the controller access to the main app.
			this.filesOverviewController = loader.getController();
			filesOverviewController.setMainApp(this);

			// Load person overview.
			loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ToolBar.fxml"));
			AnchorPane toolbar = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setRight(toolbar);

			// Give the controller access to the main app.
			ToolBarController toolbarController = loader.getController();
			toolbarController.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isTermShown = false;

	public void showTerminal() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Terminal.fxml"));
			BorderPane terminal = (BorderPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setBottom(terminal);
			this.isTermShown = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void hideTerminal() {
		this.rootLayout.setBottom(null);
		this.isTermShown = false;
	}

	public void hideProject() {
		this.rootLayout.setCenter(null);
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 *
	 * @param person
	 *            the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showProjectEditDialog(Project project) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProjectEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			ProjectEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setProject(project);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Project getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(Project currentProject) {
		this.currentProject = currentProject;
		if (this.currentProject != null) {
			this.showProject();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public FilesOverviewController getFilesOverviewController() {
		return filesOverviewController;
	}

	public void showHideTerm() {
		if (this.isTermShown) {
			this.hideTerminal();
		} else {
			this.showTerminal();
		}
	}
}
