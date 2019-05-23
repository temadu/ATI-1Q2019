package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;
import tp1.Functions;
import utils.IOManager;
import utils.ImageCreator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ATIApp extends Application {

    public static ArrayList<ATIApp> WINDOWS = new ArrayList<>();

    public Stage stage;
    public MenuBar bar;
    public HBox imageViews;
    public List<ImageViewer> images;
    public int windowIndex;

    public ATIApp() {
        WINDOWS.add(this);
        windowIndex = WINDOWS.size()-1;
    }

    @Override
    public void start(Stage stage) {
        //Creating a Group object
        this.stage = stage;
        this.imageViews = new HBox();
        this.images = new ArrayList<>();

        ScrollPane scroller = new ScrollPane();
//        scroller.setPrefSize(512, 512);
//        scroller.setFitToHeight(true);
        scroller.setFitToWidth(true);
        scroller.setContent(imageViews);

        VBox root = new VBox();
        root.getChildren().addAll(generateMenuBar(stage), scroller);

        Scene scene = new Scene(root, 512,256);

        stage.setTitle("ATI 1Q2019");

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]) {
        launch(args);
    }

    private MenuBar generateMenuBar(Stage stage){
        final Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open...");
        openItem.setOnAction(e -> {
            this.openImage();
//            this.testZone();
        });
        MenuItem openVideo = new MenuItem("Open video...");
        openVideo.setOnAction(e -> {
            this.openVideo();
//            this.testZone();
        });
        MenuItem removeAll = new MenuItem("Remove All");
        removeAll.setOnAction(e -> {
            this.imageViews.getChildren().clear();
//            this.testZone();
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e-> stage.close());

        fileMenu.getItems().addAll(openItem, openVideo,removeAll,exit);

        final Menu generateMenu = new Menu("Generate");
        MenuItem generateSquare = new MenuItem("Square");
        generateSquare.setOnAction(e -> {
            ImageCreator.createSquare(this.windowIndex);
//            this.stage.close();
        });

        MenuItem generateCircle = new MenuItem("Circle");
        generateCircle.setOnAction(e-> {
            ImageCreator.createCircle(this.windowIndex);
//            this.stage.close();
        });
        MenuItem generateGreyGradient = new MenuItem("Grey Gradient");
        generateGreyGradient.setOnAction(e-> {
            ImageCreator.createGreyGradient(this.windowIndex);
//            this.stage.close();
        });
        MenuItem generateColorGradient = new MenuItem("Color Gradient");
        generateColorGradient.setOnAction(e-> {
            ImageCreator.createColorGradient(this.windowIndex);
//            this.stage.close();
        });
        MenuItem createBase = new MenuItem("Base image");
        createBase.setOnAction(event -> {
            ImageCreator.createBaseImageGray(this.windowIndex);
//            this.stage.close();
        });
        generateMenu.getItems().addAll(generateSquare,generateCircle, generateGreyGradient, generateColorGradient, createBase);


        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, generateMenu);

        return menuBar;
    }
    private void openImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.pgm","*.ppm","*.raw","*.jpg","*.jpeg")
        );
        File file = fileChooser.showOpenDialog(stage);

        if(file != null){
            String extension = file.getName();

            int i = extension.lastIndexOf('.');
            if (i > 0) {
                extension = extension.substring(i+1);
            }

            if(extension.toLowerCase().equals("ppm")){
                ImageColor openedImage;
                try {
                    openedImage = IOManager.loadPPM(file.getPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return;
                }
                this.addImageViewer(new ImageColorViewer(openedImage, this.windowIndex));

//            this.stage.close();
            }else if(extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("jpeg")){
                ImageColor openedImage;
                try {
                    openedImage = IOManager.loadJPG(file.getPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return;
                }
                this.addImageViewer(new ImageColorViewer(openedImage, this.windowIndex));

//            this.stage.close();
            }else if(extension.toLowerCase().equals("pgm")){
                ImageGrey openedImage;
                try {
                    openedImage = IOManager.loadPGM(file.getPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return;
                }
                this.addImageViewer(new ImageGreyViewer(openedImage, this.windowIndex));

//            this.stage.close();
            }else if(extension.toLowerCase().equals("raw")) {
                ImageGrey openedImage;

                try {
                    openedImage = IOManager.loadRAW(file.getPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return;
                }
                this.addImageViewer(new ImageGreyViewer(openedImage, this.windowIndex));
//            this.stage.close();
            }
        }
    }

    private void openVideo(){
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File("./images"));
        File directory = dirChooser.showDialog(stage);

        if(directory != null){
            System.out.println(directory.getAbsolutePath());
            new VideoViewer(directory.getAbsolutePath());


        }
    }

    public void addImageViewer(ImageViewer imageViewer){
        this.images.add(imageViewer);
        this.imageViews.getChildren().add(imageViewer.getPane());
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        WINDOWS.set(windowIndex,null);
    }
}
