package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

public class Controller {

    // elements from view
    @FXML
    Pane parentPane;
    @FXML
    Pane homePane;
    @FXML
    Pane helpPane;
    @FXML
    Button loadButton;
    @FXML
    Button processButton;
    @FXML
    DatePicker dateHolder;
    @FXML
    TableView table;
    @FXML
    TableColumn width;
    @FXML
    TableColumn height;
    @FXML
    TableColumn rgb;
    @FXML
    ProgressIndicator progressInd;
    @FXML
    ImageView imgOrg;
    @FXML
    MenuBar menuBar;
    @FXML
    Menu acasaMenu;
    @FXML
    Menu procesareMenu;
    @FXML
    Menu ajutorMenu;
    @FXML
    ImageView img;
    @FXML
    TextArea homeText;
    @FXML
    TextArea helpText;
    @FXML
    ToggleButton toggle;
    @FXML
    Label imgOrgLabel;
    @FXML
    Label imgLabel;
    @FXML
    TextField info;
    @FXML
    Hyperlink gitLink;
    @FXML
    Label textGit;
    private String path;


    public void openWebpage() throws Exception {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        URI url = new URI(this.gitLink.getText());
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecteaza imaginea");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BMP files", "*.bmp"));
        File selectedImage = fileChooser.showOpenDialog(parentPane.getScene().getWindow());
        this.path = selectedImage.getPath();
        try {
            this.imgOrg.setImage(new Image(new FileInputStream(this.path)));
//            this.table = new TableView();
//            TableColumn nameColumn = new TableColumn("Name");
//            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//            TableColumn surnameColumn = new TableColumn("Surname");
//            surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
//
//            table.getColumns().addAll(nameColumn, surnameColumn);
//            Person person = new Person("John", "Doe");
//            table.getItems().add(person);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
