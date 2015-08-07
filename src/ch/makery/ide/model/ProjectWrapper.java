package ch.makery.ide.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "project")
public class ProjectWrapper {

	@XmlTransient
	private Project project;

	public ProjectWrapper() {
		this.project = new Project();
	}

	public ProjectWrapper(Project project) {
		this.project = project;
	}

	@XmlElement(name = "sourcefile")
	public List<SourceFile> getSourceFiles() {
		return this.project.getSourceFiles();
	}

	public void setSourceFiles(List<SourceFile> files) {
		for (SourceFile src : files) {
			this.project.getSourceFiles().add(src);
		}
	}

	@XmlElement(name = "projectrepertorypath")
	public String getProjectRepertoryPath() {
		return this.project.getProjectRepertoryPath();
	}

	public void setProjectRepertoryPath(String path) {
		this.project.setProjectRepertoryPath(path);
	}

	@XmlElement(name = "executablename")
	public String getExecutableName() {
		return this.project.getExecutableName();
	}

	public void setExecutableName(String eName) {
		this.project.setExecutableName(eName);
	}

	@XmlElement(name = "projectname")
	public String getProjectName() {
		return this.project.getProjectName();
	}

	public void setProjectName(String pName) {
		this.project.setProjectName(pName);
	}

	@XmlTransient
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
