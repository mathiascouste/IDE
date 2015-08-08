package ch.makery.ide.model.compilation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import ch.makery.ide.model.Project;
import ch.makery.ide.model.SourceFile;
import ch.makery.ide.view.TerminalController;

public class Compiler {
	public static final String CC = "g++";
	public static final String OPT = "-std=c++11 -Wall";
	public static final String BUILD = "-c";
	public static final String OBJ = ".obj";

	public void build(SourceFile src, Project pro) {
		String[] spl = src.getName().split("\\.");
		if (spl[spl.length - 1].equals("cpp") || spl[spl.length - 1].equals("c")) {
			String cmd = CC + " ";
			cmd += OPT + " ";
			cmd += BUILD + " ";
			cmd += src.getFilepath() + " ";
			cmd += "-o " + pro.getObjectDirectoryPath() + System.getProperty("file.separator") + spl[spl.length - 2]
					+ OBJ;

			execute(cmd);
		}
	}

	private void execute(String cmd) {
		TerminalController.getInstance().executeCommand(cmd);
	}

	public void build(Project pro) {
		for (SourceFile src : pro.getSourceFiles()) {
			build(src, pro);
		}
	}

	public void construct(Project pro) {
		String cmd = CC + " ";
		cmd += OPT + " ";
		cmd += listObjFiles(pro.getObjectDirectoryPath(), OBJ);
		cmd += "-o " + pro.getBuildDirectoryPath() + System.getProperty("file.separator") + pro.getExecutableName();
		execute(cmd);
	}

	private String listObjFiles(String objectDirectoryPath, String... endWith) {
		String fileList = "";
		File dir = new File(objectDirectoryPath);
		if (dir.isDirectory()) {
			for (File f : dir.listFiles()) {
				if (f.isFile()) {
					for (String suffix : endWith) {
						if (f.getAbsolutePath().endsWith(suffix)) {
							fileList += f.getAbsolutePath() + " ";
						}
					}
				}
			}
		}
		return fileList;
	}
}
