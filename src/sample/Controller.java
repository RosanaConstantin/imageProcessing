package sample;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
    TableView<Details> table;
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

        Details details1 = new Details("10", "10", "10");
        ObservableList<Details> details = FXCollections.observableArrayList();
        details.add(details1);

        this.width.setCellValueFactory(new PropertyValueFactory<Details,String>("latime"));
        this.height.setCellValueFactory(new PropertyValueFactory<Details,String>("inaltime"));
        this.rgb.setCellValueFactory(new PropertyValueFactory<Details,String>("rgb"));
        this.table.setItems(details);
        return;
    }

    public static class Details {
        public Details(String latime, String inaltime, String rgb) {
            this.latime = new SimpleStringProperty(latime);
            this.inaltime = new SimpleStringProperty(inaltime);
            this.rgb = new SimpleStringProperty(rgb);
        }

        private StringProperty latime;
        public void setLatime(String value) { latimeProperty().set(value); }
        public String getLatime() { return latimeProperty().get(); }
        public StringProperty latimeProperty() {
            return latime;
        }

        private StringProperty inaltime;
        public void setInaltime(String value) { inaltimeProperty().set(value); }
        public String getInaltime() { return inaltimeProperty().get(); }
        public StringProperty inaltimeProperty() {
            return inaltime;
        }

        private StringProperty rgb;
        public void setRgb(String value) { rgbProperty().set(value); }
        public String getRgb() { return rgbProperty().get(); }
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
           // PrewittVertical process = new PrewittVertical(this.img, this.imgOrg.getImage(), this.progressInd);
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
