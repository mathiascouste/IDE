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

public class Compiler {
	public static final String CC = "g++";
	public static final String OPT = "-std=c++11 -Wall";
	public static final String BUILD = "-c";
	public static final String OBJ = ".obj";

	public String build(SourceFile src, Project pro) {
		String[] spl = src.getName().split("\\.");
		System.out.println();
		if (spl[spl.length - 1].equals("cpp") || spl[spl.length - 1].equals("c")) {
			String cmd = CC + " ";
			cmd += OPT + " ";
			cmd += BUILD + " ";
			cmd += src.getFilepath() + " ";
			cmd += "-o " + pro.getObjectDirectoryPath() + System.getProperty("file.separator") + spl[spl.length - 2]
					+ OBJ;

			return execute(cmd);
		}
		return "cannot compile this file";
	}

	private String execute(String cmd) {
		System.out.println("EXEC : " + cmd);
		String msg = "";
		Runtime runtime = Runtime.getRuntime();
		try {
			Process proc = runtime.exec(cmd);
			BufferedInputStream in = new BufferedInputStream(proc.getErrorStream());
			BufferedOutputStream out = new BufferedOutputStream(proc.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

			String line = null;
			while ((line = reader.readLine()) != null) {
				msg += line + "\n";
			}

			proc.waitFor();
			proc.destroy();
		} catch (IOException e) {
			msg = e.getMessage();
		} catch (InterruptedException e) {
			msg = e.getMessage();
		}
		return msg;
	}

	public String build(Project pro) {
		String message = "";
		for (SourceFile src : pro.getSourceFiles()) {
			message += build(src, pro);
		}
		return message;
	}

	public String construct(Project pro) {
		String cmd = CC + " ";
		cmd += OPT + " ";
		cmd += listObjFiles(pro.getObjectDirectoryPath(), OBJ);
		cmd += "-o " + pro.getBuildDirectoryPath() + System.getProperty("file.separator") + pro.getExecutableName();
		return execute(cmd);
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
