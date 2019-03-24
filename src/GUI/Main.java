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
import models.ImageInt;
import tp1.Functions;

import java.io.*;
import java.util.ArrayList;
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

            ImageColor openedImage;
            ImageColor openedImage2;
            try {
                openedImage = new ImageColor(file.getPath());
                openedImage2 = new ImageColor(new File("./images/duck.ppm").getPath());
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
            Functions f = new Functions(openedImage);
            ImageColor sum = f.rangeCompressor();
            HBox h = new HBox();
            h.getChildren().add(new ImageView(openedImage.matrixToColorImage(true, true, true)));
            h.getChildren().add(new ImageView(sum.matrixToColorImage(true, true, true)));
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
    public int[][] parsePGM(String filePath) throws IOException {

        //Parse header
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Scanner scan = new Scanner(fileInputStream);

        // Parse the magic number
        String magicNum = scan.nextLine();
        System.out.println(magicNum);
        // Discard the comment lines
//        scan.nextLine();
        // Read pic width, height and max value
        int picWidth = scan.nextInt();
        int picHeight = scan.nextInt();
        int maxvalue = scan.nextInt();

        fileInputStream.close();

        // Now parse the file as binary data
        fileInputStream = new FileInputStream(filePath);
        DataInputStream dis = new DataInputStream(fileInputStream);

        // look for 4 lines (i.e.: the header) and discard them
        int numnewlines = 3;
        while (numnewlines > 0) {
            char c;
            do {
                c = (char)(dis.readUnsignedByte());
            } while (c != '\n');
            numnewlines--;
        }

        // read the image data
        int[][] data2D = new int[picHeight][picWidth];
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                data2D[row][col] = dis.readUnsignedByte();
            }
        }
        return data2D;
    }

    public Image matrixToBWImage(int[][] imageMatrix){
        int width = imageMatrix[0].length;
        int height = imageMatrix.length;

        WritableImage wImage = new WritableImage(width, height);
        PixelWriter writer = wImage.getPixelWriter();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                writer.setColor(x, y, Color.grayRgb(imageMatrix[y][x]));
            }
        }
        return wImage;
    }




}
