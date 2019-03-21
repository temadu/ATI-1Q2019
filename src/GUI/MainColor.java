package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class MainColor extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        testPPM(stage);

    }
    public static void main(String args[]) {
        launch(args);
    }


    private void testPPM(Stage stage){
        String filePath = "images/lena.ppm";
        int[][][] inputImage;
        try {
            inputImage = this.parsePPM(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(RGBtoHSV(new int[]{212,169,106})[0]);
        System.out.println(RGBtoHSV(new int[]{212,169,106})[1]);
        System.out.println(RGBtoHSV(new int[]{212,169,106})[2]);

        Image originalImage = matrixToColorImage(inputImage);
        Image circleImage = matrixToColorImage(drawCircle(inputImage, 100, 100, 50, new int[]{ 255,128,0 }));
        Image rectImage = matrixToColorImage(drawRectangle(inputImage, 50, 100, 200, 200,  new int[]{ 0,255,128 }));
        Image gradientImage = matrixToColorImage(generateColorGradient(256,100));

        savePPM("images/savecolor.pgm",drawCircle(inputImage, 100, 100, 50, new int[]{ 255,128,0 }), 255);



        //Reading color from the loaded image
//        PixelReader pixelReader = image.getPixelReader();
//        Color color = pixelReader.getColor(x, y);


        HBox rootBox = new HBox();
        rootBox.getChildren().add(new ImageView(originalImage));
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

    public int[][][] parsePPM(String filePath) throws IOException {

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
        int[][][] data2D = new int[picHeight][picWidth][3];
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                data2D[row][col][0] = dis.readUnsignedByte();
                data2D[row][col][1] = dis.readUnsignedByte();
                data2D[row][col][2] = dis.readUnsignedByte();
            }
        }
        return data2D;
    }

    public void savePPM(String filePath, int[][][] image, int scale){
        try {

            OutputStream w = new FileOutputStream(filePath);
            int height = image.length;
            int width = image[0].length;

            String header = "P6\n" + image[0].length + " " + image.length + "\n" + scale + "\n";
            byte[] headerBytes = header.getBytes();

            for (int i = 0; i < headerBytes.length; i++) {
                w.write(headerBytes[i]);
            }
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    for(int rgb = 0; rgb < 3; rgb++){
                        w.write((byte) image[row][col][rgb]);
                    }
                }
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int[][][] drawCircle( int[][][] image, float cx, float cy, float radius, int[] color){
        int[][][] retImage =  new int[image.length][image[0].length][3];
        for(int row = 0; row < image.length; row++) {
            for(int col = 0; col < image[0].length; col++) {
                double d = Math.sqrt((cy-row)*(cy-row)+(cx-col)*(cx-col));
                if(d <= radius) {
                    retImage[row][col][0] = color[0];
                    retImage[row][col][1] = color[1];
                    retImage[row][col][2] = color[2];
                } else {
                    retImage[row][col][0] = image[row][col][0];
                    retImage[row][col][1] = image[row][col][1];
                    retImage[row][col][2] = image[row][col][2];
                }
            }
        }
        return retImage;
    }

    public int[][][] drawRectangle( int[][][] image, int x1, int y1, int x2, int y2, int[] color){
        int[][][] retImage =  new int[image.length][image[0].length][3];
        int minX = Math.min(x1,x2);
        int maxX = Math.max(x1,x2);
        int minY = Math.min(y1,y2);
        int maxY = Math.max(y1,y2);
        for(int row = 0; row < image.length; row++) {
            for(int col = 0; col < image[0].length; col++) {
                if(minX <= col && col <= maxX && minY <= row && row <= maxY) {
                    retImage[row][col][0] = color[0];
                    retImage[row][col][1] = color[1];
                    retImage[row][col][2] = color[2];
                } else {
                    retImage[row][col][0] = image[row][col][0];
                    retImage[row][col][1] = image[row][col][1];
                    retImage[row][col][2] = image[row][col][2];
                }
            }
        }
        return retImage;
    }

    public Image matrixToColorImage(int[][][] imageMatrix){
        int width = imageMatrix[0].length;
        int height = imageMatrix.length;

        WritableImage wImage = new WritableImage(width, height);
        PixelWriter writer = wImage.getPixelWriter();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                writer.setColor(x, y, Color.rgb(imageMatrix[y][x][0], imageMatrix[y][x][1], imageMatrix[y][x][2]));
            }
        }
        return wImage;
    }

    public int[][][] generateColorGradient(int length, int height){
        int[][][] retImage =  new int[height][length][3];
        int lengthCounter = 0;
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImage[y][lengthCounter][0] = x*255*6/length;
                retImage[y][lengthCounter][1] = 255;
                retImage[y][lengthCounter][2] = 0;
            }
            lengthCounter++;
        }
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImage[y][lengthCounter][0] = 255;
                retImage[y][lengthCounter][1] = 255 - x*255*6/length;
                retImage[y][lengthCounter][2] = 0;
            }
            lengthCounter++;
        }
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImage[y][lengthCounter][0] = 255;
                retImage[y][lengthCounter][1] = 0;
                retImage[y][lengthCounter][2] = x*255*6/length;
            }
            lengthCounter++;
        }
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImage[y][lengthCounter][0] = 255 - x*255*6/length;
                retImage[y][lengthCounter][1] = 0;
                retImage[y][lengthCounter][2] = 255;
            }
            lengthCounter++;
        }
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImage[y][lengthCounter][0] = 0;
                retImage[y][lengthCounter][1] = x*255*6/length;
                retImage[y][lengthCounter][2] = 255;
            }
            lengthCounter++;
        }
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImage[y][lengthCounter][0] = 0;
                retImage[y][lengthCounter][1] = 255;
                retImage[y][lengthCounter][2] = 255 - x*255*6/length;
            }
            lengthCounter++;
        }
        for (int x=lengthCounter; x<length; x++){
            for (int y=0; y<height; y++){
                retImage[y][lengthCounter][0] = 0;
                retImage[y][lengthCounter][1] = 255;
                retImage[y][lengthCounter][2] = 0;
            }
        }
        return retImage;
    }

    public double[] RGBtoHSV(int[] color){
        double r = color[0];
        double g = color[1];
        double b = color[2];

        double h = 0;
        double s = 0;
        double v = 0;

        double max = Math.max(Math.max(r,g),b);
        double min = Math.min(Math.min(r,g),b);

        v = max/255;
        double delta = max - min;
        s = max!=0?(delta/max):0;
        if(s == 0){
            h = -1;
        }else{
            if (r==max)
                h = (g-b)/delta;
            else if (g==max)
                h = 2.0 + (b-r)/delta;
            else if (b==max)
                h = 4.0 + (r-g)/delta;
            h = h * 60.0;
            if (h<0.0) h = h + 360.0;
        }
        return new double[]{h,s,v};



    }
}
