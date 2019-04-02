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

public class Main {

//    public Stage stage;
//    public VBox box;
//    public List<HBox> rows;
//    public ImageView imageView;
//
//    @Override
//    public void start(Stage stage) throws FileNotFoundException {
//        //Creating a Group object
//        this.stage = stage;
//        this.rows = new ArrayList<>();
//        this.box = new VBox();
//
////        ScrollPane s1 = new ScrollPane();
////        s1.setPrefSize(512, 512);
////        s1.setFitToHeight(true);
////        s1.setContent(box);
//
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
//
//        Text scenetitle = new Text("Use the menu bar to open or generate a file");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        grid.add(scenetitle, 0, 0, 2, 1);
//
//        VBox root = new VBox();
//        root.getChildren().addAll(generateMenuBar(stage), grid);
//
//        Scene scene = new Scene(root, 512,256);
//
//        stage.setTitle("ATI 1Q2019");
//
//        stage.setScene(scene);
//        stage.show();
//    }
    public static void main(String args[]) {
        new ATIApp().launch(args);
    }
//
//    private MenuBar generateMenuBar(Stage stage){
//        final Menu fileMenu = new Menu("File");
//        MenuItem openItem = new MenuItem("Open...");
//        openItem.setOnAction(e -> {
//            this.openImage();
////            this.testZone();
//        });
//
//        MenuItem exit = new MenuItem("Exit");
//        exit.setOnAction(e-> stage.close());
//
//        fileMenu.getItems().addAll(openItem,exit);
//
//        final Menu generateMenu = new Menu("Generate");
//        MenuItem generateSquare = new MenuItem("Square");
//        generateSquare.setOnAction(e -> {
//            ImageCreator.createSquare();
//            this.stage.close();
//        });
//
//        MenuItem generateCircle = new MenuItem("Circle");
//        generateCircle.setOnAction(e-> {
//            ImageCreator.createCircle();
//            this.stage.close();
//        });
//        MenuItem generateGreyGradient = new MenuItem("Grey Gradient");
//        generateGreyGradient.setOnAction(e-> {
//            ImageCreator.createGreyGradient();
//            this.stage.close();
//        });
//        MenuItem generateColorGradient = new MenuItem("Color Gradient");
//        generateColorGradient.setOnAction(e-> {
//            ImageCreator.createColorGradient();
//            this.stage.close();
//        });
//        MenuItem createBase = new MenuItem("Base image");
//        createBase.setOnAction(event -> {
//            ImageCreator.createBaseImageGray();
//            this.stage.close();
//        });
//        generateMenu.getItems().addAll(generateSquare,generateCircle, generateGreyGradient, generateColorGradient, createBase);
//
//
//        MenuBar menuBar = new MenuBar();
//        menuBar.getMenus().addAll(fileMenu, generateMenu);
//
//        return menuBar;
//    }
//    private void testZone(){
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File("./images"));
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Image files", "*.pgm","*.ppm","*.raw")
//        );
//        File file = fileChooser.showOpenDialog(stage);
//
////            ImageColor openedImage;
//        ImageGrey openedImage2;
//        try {
////                openedImage = IOManager.loadPPM(file.getPath());
////                openedImage2 = IOManager.loadPPM("./images/duck.ppm");
//            openedImage2 = IOManager.loadPGM(file.getPath());
//        } catch (IOException e1) {
//            e1.printStackTrace();
//            return;
//        }
//        Functions f = new Functions(openedImage2);
////            ImageColor sum = f.negative();
//            ImageGrey sum = f.addExponentialNoise(1, 2);
////            System.out.println(Arrays.toString(f.greyHistogram()));
//
//        // CREATE HISTOGRAM
////        ImageGrey sum = f.histogramEqualization();
//        Functions f2 = new Functions(sum);
////        ImageGrey sum2 = f2.histogramEqualization();
////        Functions f3 = new Functions(sum2);
////
//        double[] histogramData2 = f2.greyHistogram();
////        double[] histogramData3 = f3.greyHistogram();
////
////
//        double[] histogramData = f.greyHistogram();
//        String[] labels = new String[256];
//        for (int i = 0; i < 256; i++) {
//            labels[i] = String.valueOf(i);
//        }
//
//        this.addRow(
//                new ImageView(openedImage2.matrixToGreyImage()),
//                Histogram.createHistogram(labels,histogramData)
//        );
//        this.addRow(
//                new ImageView(sum.matrixToGreyImage()),
//                Histogram.createHistogram(labels,histogramData2)
//        );
//
//
//    }
//
//    private void openImage(){
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File("./images"));
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Image files", "*.pgm","*.ppm","*.raw")
//        );
//        File file = fileChooser.showOpenDialog(stage);
//
//        String extension = file.getName();
//
//        int i = extension.lastIndexOf('.');
//        if (i > 0) {
//            extension = extension.substring(i+1);
//        }
//
//        if(extension.toLowerCase().equals("ppm")){
//            ImageColor openedImage;
//            try {
//                openedImage = IOManager.loadPPM(file.getPath());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//                return;
//            }
//            new ImageColorViewer(openedImage);
//            this.stage.close();
//        }else if(extension.toLowerCase().equals("pgm")){
//            ImageGrey openedImage;
//            try {
//                openedImage = IOManager.loadPGM(file.getPath());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//                return;
//            }
//            new ImageGreyViewer(openedImage);
//            this.stage.close();
//        }else if(extension.toLowerCase().equals("raw")) {
//            ImageGrey openedImage;
//
//            try {
//                openedImage = IOManager.loadRAW(file.getPath());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//                return;
//            }
//            new ImageGreyViewer(openedImage);
//            this.stage.close();
//        }
//
//
//    }
//
//
//
//    private void addRow(Node... nodes){
//        HBox h = new HBox();
//        h.getChildren().addAll(nodes);
//        rows.add(h);
//        this.box.getChildren().add(h);
//    }
//
//
//

}
