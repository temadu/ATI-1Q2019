package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;
import tp1.Functions;
import utils.IOManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    public Stage stage;
    public VBox box;
    public ImageView imageView;
    public List<ImageColor> images = new ArrayList<>();

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        //Creating a Group object
        this.stage = stage;
        this.box = new VBox();
        this.imageView = new ImageView();

        box.getChildren().addAll(generateMenuBar(stage), imageView);

        Scene scene = new Scene(box, 512, 512);


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
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image files", "*.pgm","*.ppm","*.raw")
            );
            fileChooser.setInitialDirectory(new File("./images"));
            File file = fileChooser.showOpenDialog(stage);

//            ImageColor openedImage;
            ImageGrey openedImage2;
            try {
//                openedImage = IOManager.loadPPM(file.getPath());
//                openedImage2 = IOManager.loadPPM("./images/duck.ppm");
                openedImage2 = IOManager.loadPGM(file.getPath());
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


//            WritableImage wim = this.matrixToBWImage(openedImage);
//            this.imageView.setImage(wim);
//
//            imageView.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
//                wim.getPixelWriter().setColor((int) mouseEvent.getX(), (int) mouseEvent.getY(),Color.rgb(128,128,128));
//            });

        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e-> stage.close());

        fileMenu.getItems().addAll(openItem,exit);


        final Menu optionsMenu = new Menu("Options");
        final Menu helpMenu = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, optionsMenu, helpMenu);

        return menuBar;
    }

    private void updateWindowSize(){

    }



}
