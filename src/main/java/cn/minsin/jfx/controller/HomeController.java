package cn.minsin.jfx.controller;

import cn.minsin.core.tools.date.DateUtil;
import cn.minsin.core.tools.date.DefaultDateFormat;
import cn.minsin.jfx.core.CreateSql;
import cn.minsin.jfx.enums.DBType;
import cn.minsin.jfx.enums.Mysql;
import cn.minsin.jfx.tools.ExtensionUtils;
import cn.minsin.jfx.tools.FxDialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Cleanup;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @author: minton.zhang
 * @since: 2020/4/4 19:56
 */
public class HomeController implements Initializable {

    //top
    public TextField fileName;

    public ComboBox<String> databaseCharset;

    public CheckBox databaseType;

    public TextField inputDataBaseName;

    private File file;

    public void about(ActionEvent actionEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://github.com/mintonzhang/sql-auto-creator"));
    }

    public void downLoadTemplate(ActionEvent actionEvent) throws IOException {
        //查找到本地模板文件
        String fileName = DateUtil.date2String(new Date(), DefaultDateFormat.yyyy_MM_dd);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择保存目录");
        fileChooser.getExtensionFilters().addAll(ExtensionUtils.excel());
        fileChooser.setInitialFileName("template_" + fileName + ".xlsx");
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            File absoluteFile = file.getAbsoluteFile();
            if (absoluteFile.exists()) {
                absoluteFile.delete();
            }
            @Cleanup
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("template/template.xlsx");
            if (inputStream != null) {
                FileOutputStream fileOutputStream = new FileOutputStream(absoluteFile);
                fileOutputStream.write(inputStream.readAllBytes());
                Desktop.getDesktop().open(absoluteFile);
            }
        }
    }

    public void chooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择Excel文件");
        Stage selectFile = new Stage();
        fileChooser.getExtensionFilters().addAll(ExtensionUtils.excel());
        File file = fileChooser.showOpenDialog(selectFile);
        if (file != null) {
            fileName.setText(file.getName());
            this.file = file;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> charSet = FXCollections.observableArrayList(new Mysql().allCharset());
        databaseCharset.setItems(charSet);
        databaseCharset.setValue(charSet.get(0));
    }

    public void commit(MouseEvent actionEvent) {

        String charSet = databaseCharset.getValue();
        if (charSet == null) {
            FxDialogs.showInformation("请选择编码格式");
            return;
        }
        if (file == null) {
            FxDialogs.showInformation("请选择Excel文件");
            return;
        }
        String dataBaseNameText = inputDataBaseName.getText();
        if (dataBaseNameText == null) {
            FxDialogs.showInformation("请输入数据库名称");
            return;
        }
        boolean selected = databaseType.isSelected();
        if (!selected) {
            FxDialogs.showInformation("请选择数据库类型");
            return;
        }

        String text = databaseType.getText();
        try{
            String charset = DBType.findByName(text, charSet);
            StringBuffer sql = CreateSql.createSql(dataBaseNameText, file,charset);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("请选择保存目录");
            fileChooser.getExtensionFilters().addAll(ExtensionUtils.sql());
            fileChooser.setInitialFileName(dataBaseNameText + ".sql");
            Stage stage = new Stage();
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                File absoluteFile = file.getAbsoluteFile();
                if (absoluteFile.exists()) {
                    absoluteFile.delete();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(absoluteFile);
                fileOutputStream.write(sql.toString().getBytes());
                Desktop.getDesktop().open(absoluteFile);
            }
        } catch (Exception e) {
            FxDialogs.showException("错误信息", e);
        }

    }

    public void reset(ActionEvent actionEvent) {
        this.file = null;
        this.inputDataBaseName.setText("demo");
        this.initialize(null, null);
        this.databaseType.setSelected(false);
    }
}
