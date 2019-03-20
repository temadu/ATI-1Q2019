package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException {

        String filePath = "images/TEST.PGM";
        int[][] image;

        try {
            image = this.parsePGM(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;

        }
        //Creating an image
//        Image img = new Image(new FileInputStream("images/lenna.png"));
//        int width = (int)img.getWidth();
//        int height = (int)img.getHeight();
//        System.out.println("WIDTH: " + width);
//        System.out.println("HEIGHT: " + height);
        //Creating a writable image

        int width = image[0].length;
        int height = image.length;

        WritableImage wImage = new WritableImage(width, height);

        //Reading color from the loaded image
//        PixelReader pixelReader = image.getPixelReader();

        //getting the pixel writer
        PixelWriter writer = wImage.getPixelWriter();

        //Reading the color of the image
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                //Retrieving the color of the pixel of the loaded image
//                Color color = pixelReader.getColor(x, y);

                //Setting the color to the writable image
                writer.setColor(x, y, Color.grayRgb(image[y][x]));
            }
        }
        //Setting the view for the writable image
        ImageView imageViewOriginal = new ImageView(wImage);
        ImageView imageViewChanged = new ImageView(wImage);

        HBox rootBox = new HBox();
        rootBox.getChildren().add(imageViewOriginal);
        rootBox.getChildren().add(imageViewChanged);
        //Creating a Group object
        Group root = new Group(rootBox);

        //Creating a scene object
        Scene scene = new Scene(root, width*2, height);

        //Setting title to the Stage
        stage.setTitle("Writing pixels ");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]) {
        launch(args);
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

}
