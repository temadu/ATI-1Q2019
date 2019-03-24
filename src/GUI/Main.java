package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;
import tp1.Functions;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    public VBox box;
    public List<Image> images = new ArrayList<>();

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        //Creating a Group object

        box = new VBox();
        MenuBar menu = generateMenuBar(stage);

        box.getChildren().add(menu);
//        Group root = new Group(box);

        Scene scene = new Scene(box, 512, 512);


        stage.setTitle("ATI 1Q2019");

        stage.setScene(scene);
//        stage.setFullScreen(true);
        stage.setMaximized(true);
        stage.show();
//        Stage secondStage = new Stage();
//        secondStage.setScene(new Scene(new HBox(4, new Label("Second window"))));
//        secondStage.show();

    }
    public static void main(String args[]) {
        launch(args);
    }

    private MenuBar generateMenuBar(Stage stage){
        final Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open...");
        openItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Portable PixMap Files", "*.ppm")
                    ,new FileChooser.ExtensionFilter("Portable Gray Map Files", "*.pgm")
                    ,new FileChooser.ExtensionFilter("Raw Files", "*.raw")
            );
            fileChooser.setInitialDirectory(new File("./images"));
            File file = fileChooser.showOpenDialog(stage);

//            ImageColor openedImage;
            ImageGrey openedImage2;
            try {
//                openedImage = new ImageColor(file.getPath());
                openedImage2 = new ImageGrey(file.getPath());
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
            Functions f = new Functions(openedImage2);
//            ImageColor sum = f.negative();
//            ImageGrey sum = f.greyHistogram();
            System.out.println(Arrays.toString(f.greyHistogram()));
            HBox h = new HBox();
            h.getChildren().add(new ImageView(openedImage2.matrixToGreyImage()));
//            h.getChildren().add(new ImageView(sum.matrixToColorImage(true, true, true)));
            this.box.getChildren().add(h);

        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e->{
            stage.close();
        });
        fileMenu.getItems().addAll(openItem,exit);

        final Menu optionsMenu = new Menu("Options");
        final Menu helpMenu = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, optionsMenu, helpMenu);

        return menuBar;
    }






}
