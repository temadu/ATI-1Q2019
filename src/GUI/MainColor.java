package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.ImageColor;
import utils.IOManager;

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
        ImageColor inputImage;
        try {
            inputImage = IOManager.loadPPM(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

//        System.out.println(RGBtoHSV(new int[]{212,169,106})[0]);
//        System.out.println(RGBtoHSV(new int[]{212,169,106})[1]);
//        System.out.println(RGBtoHSV(new int[]{212,169,106})[2]);

        Image originalImage = matrixToColorImage(inputImage.getImage(), true, true, true);
        Image originalImageGB = matrixToColorImage(inputImage.getImage(), false, true, true);
        Image originalImageRB = matrixToColorImage(inputImage.getImage(), true, false, true);
        Image originalImageRG = matrixToColorImage(inputImage.getImage(), true, true, false);
        Image originalImageR = matrixToColorImage(inputImage.getImage(), true, false, false);
        Image originalImageG = matrixToColorImage(inputImage.getImage(), false, true, false);
        Image originalImageB = matrixToColorImage(inputImage.getImage(), false, false, true);
        Image hueImage = hsvMatrixToColor(RGBtoHSVmatrix(inputImage.getImage()), false, false, true);
        Image valImage = hsvMatrixToColor(RGBtoHSVmatrix(inputImage.getImage()), false, true, false);
        Image satImaage = hsvMatrixToColor(RGBtoHSVmatrix(inputImage.getImage()), true, false, false);

        Image circleImage = matrixToColorImage(drawCircle(inputImage.getImage(), 100, 100, 50, new int[]{ 255,128,0 }), true, true, true);
        Image rectImage = matrixToColorImage(drawRectangle(inputImage.getImage(), 50, 100, 200, 200,  new int[]{ 0,255,128 }), true, true, true);
        Image gradientImage = matrixToColorImage(generateColorGradient(256,100), true, true, true);

//        IOManager.savePPM("images/savecolor.pgm",drawCircle(inputImage.getImage(), 100, 100, 50, new int[]{ 255,128,0 }), 255);



        //Reading color from the loaded image
//        PixelReader pixelReader = image.getPixelReader();
//        Color color = pixelReader.getColor(x, y);


        HBox rootBox = new HBox();
        rootBox.getChildren().add(new ImageView(originalImage));
        rootBox.getChildren().add(new ImageView(hueImage));
        rootBox.getChildren().add(new ImageView(satImaage));

        HBox box2 = new HBox();
        box2.getChildren().add(new ImageView(valImage));
//        box2.getChildren().add(new ImageView(originalImageG));
//        box2.getChildren().add(new ImageView(originalImageR));

//        HBox box3 = new HBox();
//        box3.getChildren().add(new ImageView(originalImageB));
//        rootBox.getChildren().add(new ImageView(circleImage));
//        rootBox.getChildren().add(new ImageView(rectImage));
//        rootBox.getChildren().add(new ImageView(gradientImage));

        VBox vBox = new VBox();
        vBox.getChildren().add(rootBox);
        vBox.getChildren().add(box2);
//        vBox.getChildren().add(box3);

        //Creating a Group object
        Group root = new Group(vBox);

        //Creating a scene object
        Scene scene = new Scene(root, 1024, 512);

        //Setting title to the Stage
        stage.setTitle("Writing pixels ");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
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

    public Image matrixToColorImage(int[][][] imageMatrix, boolean red, boolean green, boolean blue){
        int width = imageMatrix[0].length;
        int height = imageMatrix.length;

        WritableImage wImage = new WritableImage(width, height);
        PixelWriter writer = wImage.getPixelWriter();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                writer.setColor(x, y, Color.rgb(red ? imageMatrix[y][x][0] : 0, green ? imageMatrix[y][x][1] : 0, blue ? imageMatrix[y][x][2] : 0));
            }
        }
        return wImage;
    }

    public Image hsvMatrixToColor (double[][][] imageMatrix, boolean hue, boolean saturation, boolean value){
        int width = imageMatrix[0].length;
        int height = imageMatrix.length;

        WritableImage wImage = new WritableImage(width, height);
        PixelWriter writer = wImage.getPixelWriter();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = 0;
                if(hue) {
                    if (imageMatrix[y][x][0] == -1) {
                        color = 255;
                    } else {
                        color = (int) ((imageMatrix[y][x][0]/360) * 255);
                    }
                } else if (saturation) {
                    color = (int) (imageMatrix[y][x][1] * 255);
                } else if (value) {
                    color = (int) (imageMatrix[y][x][2] * 255);
                }
                writer.setColor(x, y, Color.rgb(color, color, color));
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

    public double[][][] RGBtoHSVmatrix (int[][][] rgbImage) {
        int height = rgbImage.length;
        int width = rgbImage[0].length;
        double[][][] hsvImage = new double[height][width][3];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                hsvImage[row][col] = RGBtoHSV(rgbImage[row][col]);
            }
        }
        return hsvImage;
    }


    public int[] HSVtoRGB(double[] color) {
        double c = color[2] * color[1];
        double x = c * (1.0 - Math.abs(Math.floorMod( (int)color[0] / 60, 2) - 1.0));
        double m = color[2] - c;
        if (color[0] >= 0.0 && color[0] < 60.0) {
            return new int[]{(int) (c + m), (int) (x + m), (int) m};
        } else if (color[0] >= 60.0 && color[0] < 120.0) {
            return new int[]{(int) (x + m), (int) (c + m), (int) m};
        } else if (color[0] >= 120.0 && color[0] < 180.0) {
            return new int[]{(int) m, (int) (c + m), (int) (x + m)};
        } else if (color[0] >= 180.0 && color[0] < 240.0) {
            return new int[]{(int) m, (int) (x + m), (int) (c + m)};
        } else if (color[0] >= 240.0 && color[0] < 300.0) {
            return new int[]{(int) (x + m), (int) m, (int) (c + m)};
        } else if (color[0] >= 300.0 && color[0] < 360.0) {
            return new int[]{(int) (c + m), (int) m, (int) (x + m)};
        } else {
            return new int[]{(int) m, (int) m, (int) m};
        }
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
    public void changePixel(int[][][] image, int x, int y, int[] color){
        image[x][y][0] = color[0];
        image[x][y][1] = color[1];
        image[x][y][2] = color[2];
    }

    public int[] getPixel(int[][][] image, int x, int y){
        return new int[]{ image[x][y][0],image[x][y][1],image[x][y][2]};
    }
}
