package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class TypingException extends Exception {
	public TypingException(String msg, Label errLabel) {
		errLabel.setText(msg);
	}
	public TypingException(String header, String content, Alert a) {
		a.setHeaderText(header);
		a.setContentText(content);
		a.show();
	}
}
