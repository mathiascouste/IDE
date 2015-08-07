package ch.makery.ide.model;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Project {
	private static final String OBJECTS_DIR = "objects";
	private static final String BUILD_DIR = "build";
	private static final String SRC_DIR = "src";
	private static final String PROPERTY_FILE = "project.properties";
	private ObservableList<SourceFile> sourceFiles = FXCollections.observableArrayList();
	private String projectRepertoryPath;
	private String executableName;
	private String projectName;

	public Project() {
	}

	public ObservableList<SourceFile> getSourceFiles() {
		return sourceFiles;
	}

	public void setSourceFiles(ObservableList<SourceFile> sourceFiles) {
		this.sourceFiles = sourceFiles;
	}

	public void addFileToProject(SourceFile sourceFile) {
		this.sourceFiles.add(sourceFile);
	}

	public String getProjectRepertoryPath() {
		return projectRepertoryPath;
	}

	public String getExecutableName() {
		return executableName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectRepertoryPath(String projectRepertoryPath) {
		this.projectRepertoryPath = projectRepertoryPath;
	}

	public void setExecutableName(String executableName) {
		this.executableName = executableName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getObjectDirectoryPath() {
		return this.projectRepertoryPath + System.getProperty("file.separator") + OBJECTS_DIR;
	}

	public String getBuildDirectoryPath() {
		return this.projectRepertoryPath + System.getProperty("file.separator") + BUILD_DIR;
	}

	public String getSourceDirectoryPath() {
		return this.projectRepertoryPath + System.getProperty("file.separator") + SRC_DIR;
	}

	public void buildWorkspace() {
		File obj = new File(this.getObjectDirectoryPath());
		File bui = new File(this.getBuildDirectoryPath());
		File src = new File(this.getSourceDirectoryPath());
		if (!obj.exists()) {
			try {
				obj.mkdir();
			} catch (SecurityException se) {
			}
		}
		if (!bui.exists()) {
			try {
				bui.mkdir();
			} catch (SecurityException se) {
			}
		}
		if (!src.exists()) {
			try {
				src.mkdir();
			} catch (SecurityException se) {
			}
		}
	}

	public static Project load(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(ProjectWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			ProjectWrapper wrapper = (ProjectWrapper) um.unmarshal(file);
			return wrapper.getProject();
		} catch (Exception e) {
			return null;
		}
	}

	public void save() {
		File file = new File(this.getProjectRepertoryPath() + System.getProperty("file.separator") + PROPERTY_FILE);
		try {
			JAXBContext context = JAXBContext.newInstance(ProjectWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our person data.
			ProjectWrapper wrapper = new ProjectWrapper(this);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);
		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
		}
	}
}
