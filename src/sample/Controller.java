package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import static java.lang.Math.round;

public class Controller {

    // elements from view
    @FXML
    Pane parentPane;
    @FXML
    Pane homePane;
    @FXML
    Pane helpPane;
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
    TableColumn size;
    @FXML
    TableView<Details> tableModif;
    @FXML
    TableColumn widthModif;
    @FXML
    TableColumn heightModif;
    @FXML
    TableColumn sizeModif;
    @FXML
    ProgressIndicator progressInd;
    @FXML
    ImageView imgOrg;
    @FXML
    MenuBar menuBar;
    @FXML
    RadioButton radioInfo;
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
    ChoiceBox chooseBox;

    private String originalPath;
    private String imageName;
    private String path;

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
        this.originalPath = selectedImage.getPath();
        this.imageName = selectedImage.getName();
        try {
            this.imgOrg.setImage(new Image( new FileInputStream(this.originalPath)));
            this.chooseBox.getItems().addAll("Vertical", "Horizontal", "Hor&Vert");
            this.progressInd.setVisible(true);
            this.progressInd.setProgress(Double.valueOf(0));
            getImageDetails(this.imgOrg.getImage(), this.originalPath, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getOrientation() {
        String output =  this.chooseBox.getSelectionModel().getSelectedItem().toString().toLowerCase();
        return output;
    }

    public void getImageDetails(Image image, String path, boolean original) {
        String width = String.valueOf(image.getWidth());
        String height = String.valueOf(image.getHeight());
        double size = new Double(new File(path).length()) / 1024 / 1024;
        String dimensiune = String.valueOf(size);

        Details details1 = new Details(width, height, dimensiune);
        ObservableList<Details> details = FXCollections.observableArrayList();
        details.add(details1);

        if(original) {
            this.width.setCellValueFactory(new PropertyValueFactory<Details, String>("latime"));
            this.height.setCellValueFactory(new PropertyValueFactory<Details, String>("inaltime"));
            this.size.setCellValueFactory(new PropertyValueFactory<Details, String>("dimensiune"));
            this.table.setItems(details);
        } else {
            this.widthModif.setCellValueFactory(new PropertyValueFactory<Details, String>("latime"));
            this.heightModif.setCellValueFactory(new PropertyValueFactory<Details, String>("inaltime"));
            this.sizeModif.setCellValueFactory(new PropertyValueFactory<Details, String>("dimensiune"));
            this.tableModif.setItems(details);
        }
        return;
    }

    public static class Details {
        public Details(String latime, String inaltime, String dimensiune) {
            this.latime = new SimpleStringProperty(latime);
            this.inaltime = new SimpleStringProperty(inaltime);
            this.dimensiune = new SimpleStringProperty(dimensiune);
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

        private StringProperty dimensiune;
        public void setdimensiune(String value) { dimensiuneProperty().set(value); }
        public String getdimensiune() { return dimensiuneProperty().get(); }
        public StringProperty dimensiuneProperty() {
            return dimensiune;
        }
    }

    public void saveToFile() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Alegeti directorul");
        File selectedDirectory = directoryChooser.showDialog(parentPane.getScene().getWindow());
        this.path = selectedDirectory.getPath() + "/" + this.imageName.substring(0, this.imageName.length() - 4) + ".bmp";
        File outputFile = new File(this.path);
        BufferedImage bImage = SwingFXUtils.fromFXImage(this.img.getImage(), null);
        try {
            ImageIO.write(bImage, "png", outputFile);
            getImageDetails(this.img.getImage(), this.path, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processImage() {
        String orientation = getOrientation();

        if(orientation.equals("vertical")){
            Image resultImage = PrewittVertical.PrewittVertical(this.imgOrg.getImage(), this.progressInd);
            this.img.setImage(resultImage);
            this.progressInd.setProgress(Double.valueOf(100));
        } else if (orientation.equals("horizontal")){
            Image resultImage = PrewittHorizontal.PrewittHorizontal(this.imgOrg.getImage(), this.progressInd);
            this.img.setImage(resultImage);
            this.progressInd.setProgress(Double.valueOf(100));
        } else if (orientation.equals("hor&vert")){
            Image resultImage = PrewittHV.PrewittHV( this.imgOrg.getImage(), this.progressInd);
            this.img.setImage(resultImage);
            this.progressInd.setProgress(Double.valueOf(100));
        }
        saveToFile();
        return;
    }

    public void changePhoto(){
        if(this.toggle.selectedProperty().getValue()) {
            this.imgOrgLabel.setVisible(false);
            this.imgOrg.setVisible(false);
            this.img.setVisible(true);
            this.imgLabel.setVisible(true);

            if(this.radioInfo.isSelected()){
                this.tableModif.setVisible(true);
                this.table.setVisible(false);
            } else {
                this.tableModif.setVisible(false);
                this.table.setVisible(false);
            }
        } else {
            this.imgOrgLabel.setVisible(true);
            this.imgOrg.setVisible(true);
            this.img.setVisible(false);
            this.imgLabel.setVisible(false);

            if(this.radioInfo.isSelected()){
                this.tableModif.setVisible(false);
                this.table.setVisible(true);
            } else {
                this.tableModif.setVisible(false);
                this.table.setVisible(false);
            }
        }
    }

    public void showInfo() {
        boolean  value = this.radioInfo.isSelected();
        if(value){
            this.info.setVisible(true);
            if(this.toggle.selectedProperty().getValue()) {
                this.tableModif.setVisible(true);
            } else {
                this.table.setVisible(true);
            }
        } else {
            this.info.setVisible(false);
            this.table.setVisible(false);
            this.tableModif.setVisible(false);
        }
        return;
    }
}
