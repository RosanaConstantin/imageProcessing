package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import utils.PrewittHV;
import utils.PrewittHorizontal;
import utils.PrewittVertical;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;

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
    MenuItem helpItem;
    @FXML
    MenuItem homeItem;
    @FXML
    MenuItem processItem;
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
    @FXML
    ChoiceBox chooseBox;

    private String path;
    private String imageName;

    public void initialize(){
        this.dateHolder.setValue(LocalDate.now());
    }

    public void  homeInitialize(){
        this.homePane.setVisible(true);
        this.homePane.toFront();
        this.helpPane.setVisible(false);
    }

    public void  processInitialize(){
        this.parentPane.toFront();
        this.homePane.setVisible(false);
        this.helpPane.setVisible(false);
    }

    public void  helpInitialize(){
        this.helpPane.setVisible(true);
        this.helpPane.toFront();
        this.homePane.setVisible(false);

    }
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
        this.imageName = selectedImage.getName();
        try {
            this.imgOrg.setImage(new Image(new FileInputStream(this.path)));
            this.chooseBox.getItems().addAll("Vertical", "Horizontal", "Hor&Vert");
            getImageDetails();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getOrientation() {
        String output =  this.chooseBox.getSelectionModel().getSelectedItem().toString().toLowerCase();
        return output;
    }

    public void getImageDetails() {
//       this.table = new TableView<Details>();
//        final ObservableList<Details> data
//                = FXCollections.observableArrayList(
//                new Details("Jacob", "Smith", "jacob.smith@example.com"));
//
//        this.table.setItems(data);
//        this.table.setEditable(true);
//        TableColumn<Details, String> latime = new TableColumn<>("Latime");
//        latime.setCellValueFactory(
//                new PropertyValueFactory<>("latime"));
//        TableColumn<Details, String> inaltime = new TableColumn<>("Inaltime");
//        inaltime.setCellValueFactory(
//                new PropertyValueFactory<>("inaltime"));
//        TableColumn<Details, String> rgb = new TableColumn<>("RGB");
//        rgb.setCellValueFactory(
//                new PropertyValueFactory<>("rgb"));
//        this.table.getColumns().addAll(latime, inaltime, rgb);
//        return;
    }

    public static class Details {

        private final StringProperty latime;
        private final StringProperty inaltime;
        private final StringProperty rgb;

        public Details(String latime, String inaltime, String rgb) {
            this.latime = new SimpleStringProperty(this, "latime", latime);
            this.inaltime = new SimpleStringProperty(this, "inaltime", inaltime);
            this.rgb = new SimpleStringProperty(this, "rgb", rgb);
        }

        public String getLatime() {
            return latime.get();
        }

        public void setLatime(String value) {
            latime.set(value);
        }

        public StringProperty latimeProperty() {
            return latime;
        }

        public String getInaltime() {
            return inaltime.get();
        }

        public void setInaltime(String value) {
            inaltime.set(value);
        }

        public StringProperty inaltimeProperty() {
            return inaltime;
        }

        public String getRgb() {
            return rgb.get();
        }

        public void setRgb(String value) {
            rgb.set(value);
        }

        public StringProperty rgbProperty() {
            return rgb;
        }
    }

    public void saveToFile() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Alegeti directorul");
        File selectedDirectory = directoryChooser.showDialog(parentPane.getScene().getWindow());
        File outputFile = new File(selectedDirectory.getPath() + "/processedImages/" + this.imageName);
        BufferedImage bImage = SwingFXUtils.fromFXImage(this.img.getImage(), null);
        try {
            ImageIO.write(bImage, "bmp", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processImage() {
        String orientation = getOrientation();
        if(orientation.equals("vertical")){
           // PrewittVertical process = new PrewittVertical(this.img, this.imgOrg.getImage());
        } else if (orientation.equals("horizontal")){
          //  PrewittHorizontal process = new PrewittHorizontal(this.img, this.imgOrg.getImage());
        } else if (orientation.equals("hor&vert")){
          //  PrewittHV process = new PrewittHV(this.img, this.imgOrg.getImage());
        }
        return;
    }

    public void changePhoto(){
        if(this.toggle.selectedProperty().getValue()) {
            this.imgOrgLabel.setVisible(false);
            this.imgOrg.setVisible(false);
            //  this.img.setVisible(true);
            this.imgLabel.setVisible(true);
        } else {
            this.imgOrgLabel.setVisible(true);
            this.imgOrg.setVisible(true);
            //  this.img.setVisible(false);
            this.imgLabel.setVisible(false);
        }
    }
}
