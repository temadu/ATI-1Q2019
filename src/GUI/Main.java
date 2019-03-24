package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    public List<HBox> rows;
    public ImageView imageView;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        //Creating a Group object
        this.stage = stage;
        this.rows = new ArrayList<>();
        this.box = new VBox();

        ScrollPane s1 = new ScrollPane();
//        s1.setPrefSize(512, 512);
        s1.setFitToHeight(true);
        s1.setContent(box);

        VBox root = new VBox();
        root.getChildren().addAll(generateMenuBar(stage), s1);

        Scene scene = new Scene(root);

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
        openItem.setOnAction(e -> openImage());

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e-> stage.close());

        fileMenu.getItems().addAll(openItem,exit);


        final Menu optionsMenu = new Menu("Transform");
        final Menu helpMenu = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, optionsMenu, helpMenu);

        return menuBar;
    }
    private void openImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.pgm","*.ppm","*.raw")
        );
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
//            System.out.println(Arrays.toString(f.greyHistogram()));

        // CREATE HISTOGRAM
        int[] histogramData = f.greyHistogram();
        String[] labels = new String[256];
        for (int i = 0; i < 256; i++) {
            labels[i] = String.valueOf(i);
        }

        this.addRow(
                new ImageView(openedImage2.matrixToGreyImage()),
//                new ImageView(sum.matrixToColorImage(true, true, true)),
                Histogram.createHistogram(labels,histogramData)
        );


//            WritableImage wim = this.matrixToBWImage(openedImage);
//            this.imageView.setImage(wim);
//
//            imageView.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
//                wim.getPixelWriter().setColor((int) mouseEvent.getX(), (int) mouseEvent.getY(),Color.rgb(128,128,128));
//            });
    }

    private void addRow(Node... nodes){
        HBox h = new HBox();
        h.getChildren().addAll(nodes);
        rows.add(h);
        this.box.getChildren().add(h);
    }




}
