package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class MainBW extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        testPGM(stage);

    }
    public static void main(String args[]) {
        launch(args);
    }


    private void testPGM(Stage stage){
        String filePath = "images/TEST.PGM";
        int[][] inputImage;
        try {
            inputImage = this.parsePGM(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;

        }

//        int[][] circle = drawCircle(new int[512][512], 512/2, 512/2, 50, 255);
//        savePGM("images/circle.pgm",circle,255);
//        int[][] square = drawRectangle(new int[512][512], 200, 200, 400, 400,255);
//        savePGM("images/square.pgm",square,255);


        Image originalImage = matrixToBWImage(inputImage);
        Image circleImage = matrixToBWImage(drawCircle(inputImage, 100, 100, 50, (int) 255));
        Image rectImage = matrixToBWImage(drawRectangle(inputImage, 50, 100, 200, 200, (int) 128));
        Image gradientImage = matrixToBWImage(generateBWGradient(256,100));

        //Reading color from the loaded image
//        PixelReader pixelReader = image.getPixelReader();
//        Color color = pixelReader.getColor(x, y);
        savePGM("images/save.pgm",drawCircle(inputImage, 100, 100, 50, (int) 255), 255);


        HBox rootBox = new HBox();
        ImageView v = new ImageView(originalImage);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                int x = (int) e.getX();
                int y = (int) e.getY();
                System.out.println("X: " + e.getX() + ", Y: " + e.getY() + ", color: " + inputImage[y][x]);
            }
        };
        //Registering the event filter
        v.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        rootBox.getChildren().add(v);
        rootBox.getChildren().add(new ImageView(circleImage));
        rootBox.getChildren().add(new ImageView(rectImage));
        rootBox.getChildren().add(new ImageView(gradientImage));

        //Creating a Group object
        Group root = new Group(rootBox);

        //Creating a scene object
        Scene scene = new Scene(root, 1024, 512);

        //Setting title to the Stage
        stage.setTitle("Writing pixels ");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
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

    public void savePGM(String filePath, int[][] image, int scale){
        try {

            OutputStream w = new FileOutputStream(filePath);
            int height = image.length;
            int width = image[0].length;

            String header = "P5\n" + image[0].length + " " + image.length + "\n" + scale + "\n";
            byte[] headerBytes = header.getBytes();

            for (int i = 0; i < headerBytes.length; i++) {
                w.write(headerBytes[i]);
            }
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    w.write((byte) image[row][col]);
                }
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[][] drawCircle( int[][] circle, float cx, float cy, float radius, int color){
        int[][] retCircle =  new int[circle.length][circle[0].length];
        for(int row = 0; row < circle.length; row++) {
            for(int col = 0; col < circle[0].length; col++) {
                double d = Math.sqrt((cy-row)*(cy-row)+(cx-col)*(cx-col));
                if(d <= radius) {
                    retCircle[row][col] = color;
                } else {
                    retCircle[row][col] = circle[row][col];
                }
            }
        }
        return retCircle;
    }

    public int[][] drawRectangle( int[][] image, int x1, int y1, int x2, int y2, int color){
        int[][] retImage =  new int[image.length][image[0].length];
        int minX = Math.min(x1,x2);
        int maxX = Math.max(x1,x2);
        int minY = Math.min(y1,y2);
        int maxY = Math.max(y1,y2);
        for(int row = 0; row < image.length; row++) {
            for(int col = 0; col < image[0].length; col++) {
                if(minX <= col && col <= maxX && minY <= row && row <= maxY) {
                    retImage[row][col] = color;
                } else {
                    retImage[row][col] = image[row][col];
                }
            }
        }
        return retImage;
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

    public int[][] generateBWGradient(int length, int height){
        int[][] retImage =  new int[height][length];
        for (int x=0; x<length; x++)
            for (int y=0; y<height; y++)
                retImage[y][x] = x*255/length;

        return retImage;
    }

}
