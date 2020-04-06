package cn.minsin.jfx.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: minton.zhang
 * @since: 2020/4/6 18:20
 */
public class FxDialogs {

    public static void showInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("提示");
        alert.setHeaderText("");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("警告");
        alert.setHeaderText("");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("错误");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showException(String message, Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("异常");
        alert.setHeaderText("");
        alert.setContentText(message);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Details:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public static boolean showConfirm(String message, ConfirmButtonType trueButton, ConfirmButtonType... options) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("再次确认");
        alert.setHeaderText("");
        alert.setContentText(message);

        if (options == null || options.length == 0) {
            options = new ConfirmButtonType[]{ConfirmButtonType.OK, ConfirmButtonType.CANCEL};
        }

        List<ButtonType> buttons = new ArrayList<>();
        for (ConfirmButtonType option : options) {
            buttons.add(new ButtonType(option.getName()));
        }

        alert.getButtonTypes().setAll(buttons);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().getText().equals(trueButton.getName());
    }

    public static String showTextInput(String title, String message, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Input");
        dialog.setHeaderText(title);
        dialog.setContentText(message);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);

    }
}
