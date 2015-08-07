package ch.makery.ide.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.xml.bind.annotation.XmlTransient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SourceFile {
	public static final String UNLOADED = "UNLOADED";
	public static final String SAVED = "SAVED";
	public static final String NON_EXISTENT = "NON-EXISTENT";
	public static final String UNSAVED = "UNSAVED";
	public static final String COMPILED = "COMPILED";
	public static final String BUILT = "BUILT";
	private StringProperty name;
	private String filepath;
	@XmlTransient
	private StringProperty state;
	@XmlTransient
	private String content;

	public SourceFile() {
		this.name = new SimpleStringProperty("undefined name");
		this.filepath = "undefined filepath";
		this.state = new SimpleStringProperty(UNLOADED);
		this.content = "trololo";
	}

	public SourceFile(File file) {
		this.name = new SimpleStringProperty(file.getName());
		this.filepath = file.getAbsolutePath();
		this.state = new SimpleStringProperty(UNLOADED);
		this.content = "";
	}

	public String getFilepath() {
		return this.filepath;
	}

	public String getName() {
		return this.name.get();
	}

	@XmlTransient
	public String getState() {
		return this.state.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public void setState(String state) {
		this.state.set(state);
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	public StringProperty stateProperty() {
		return this.state;
	}

	@XmlTransient
	public String getContent() {
		if (this.state.get().equals(UNLOADED)) {
			this.loadContent();
		}
		return content;
	}

	private void loadContent() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.filepath))));
			this.content = "";
			String ligne;
			while ((ligne = br.readLine()) != null) {
				this.content += ligne + "\n";
			}
			br.close();
			this.setState(SAVED);
		} catch (Exception e) {
			this.setState(NON_EXISTENT);
		}
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void save() {
		try {
			PrintWriter fichierSortie = new PrintWriter(new BufferedWriter(new FileWriter(new File(this.filepath))));
			fichierSortie.println(this.content);
			fichierSortie.close();
			this.setState(SAVED);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
