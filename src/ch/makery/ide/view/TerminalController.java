package ch.makery.ide.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import ch.makery.ide.util.MyQueue;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TerminalController {
	private static TerminalController instance;
	@FXML
	private TextArea textArea;
	@FXML
	private TextField commandTextField;
	private MyQueue<String> messageQueue;

	public TerminalController() {
		this.messageQueue = new MyQueue<>(10);
	}

	@FXML
	private void initialize() {
		instance = this;
	}

	public static TerminalController getInstance() {
		return instance;
	}

	@FXML
	private void handleEnterPressed(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			String cmd = this.commandTextField.getText();
			this.commandTextField.setText("");
			this.executeCommand(cmd);
		}
	}

	public void executeCommand(String cmd) {
		String msg = "empty";
		Runtime runtime = Runtime.getRuntime();
		try {
			Process proc = runtime.exec(cmd);
			BufferedInputStream err = new BufferedInputStream(proc.getErrorStream());
			BufferedReader err_reader = new BufferedReader(new InputStreamReader(err));
			BufferedInputStream nor = new BufferedInputStream(proc.getInputStream());
			BufferedReader nor_reader = new BufferedReader(new InputStreamReader(nor));

			String line = null;
			msg = " > " + cmd + "\n";
			while ((line = err_reader.readLine()) != null || (line = nor_reader.readLine()) != null) {
				msg += line + "\n";
			}

			proc.waitFor();
			proc.destroy();
		} catch (IOException e) {
			msg = e.getMessage();
		} catch (InterruptedException e) {
			msg = e.getMessage();
		}
		this.messageQueue.push(msg);
		this.update();
	}

	private void update() {
		String newText = "";
		for (String str : this.messageQueue.getQueue()) {
			newText += str + "\n";
		}
		this.textArea.setText(newText);
	}
}
