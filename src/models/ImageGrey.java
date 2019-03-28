package models;

import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ImageGrey implements ImageInt{
    private Integer[][] image;
    private int maxColor;
    private int height;
    private int width;
    private double mean;
    private double sigma;
    private XYChart.Series greySeries;

    private WritableImage renderer;
    private ImageView view;

    public ImageGrey(String filePath) throws IOException{
        maxColor = 0;
        parse(filePath);
    }

    public ImageGrey(Integer[][] image, int maxColor, int height, int width){
        this.image = image;
        this.maxColor = maxColor;
        this.height = height;
        this.width = width;
        this.renderer = new WritableImage(width, height);
        this.updateRenderer();
        this.view = new ImageView(this.renderer);
        calcMean();
        calcStd();
    }
    public ImageGrey(){

    }

    public void parse(String filePath) throws IOException {

        //Parse header
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Scanner scan = new Scanner(fileInputStream);

        // Parse the magic number
        String magicNum = scan.nextLine();
        System.out.println(magicNum);
        // Discard the comment lines
//        scan.nextLine();
        // Read pic width, height and max value
        width = scan.nextInt();
        height = scan.nextInt();
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
        image = new Integer[height][width];
        int sum = 0;
        int sumsq = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                image[row][col] = dis.readUnsignedByte();
                maxColor = Math.max(maxColor, image[row][col]);
                sum += image[row][col];
            }
        }
        this.mean = (double) sum / (double) (height * width);
        this.renderer = new WritableImage(width, height);
        this.view = new ImageView(this.renderer);

    }

    public Image matrixToGreyImage(){
        WritableImage wImage = new WritableImage(width, height);
        PixelWriter writer = wImage.getPixelWriter();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                writer.setColor(x, y, Color.grayRgb(image[y][x]));
            }
        }
        return wImage;
    }

    public void calcStd() {
        double sumsq = 0;
        sumsq = Arrays.stream(image).map(a -> Arrays.stream(a).reduce(0, (ac, n) -> ac + (int) Math.pow(n - mean, 2))).reduce(0, (ac, n) -> ac + n);
        this.sigma = Math.sqrt(sumsq / (double) (height * width));
    }

    public void calcMean() {
        double sum = Arrays.stream(image).map(a -> Arrays.stream(a).reduce(0, (ac, n) -> ac + n)).reduce(0, (ac, n) -> ac + n);
        this.mean = sum / (height * width);
    }

    public Integer[][] getImage() {
        return image;
    }

    public int getMaxColor() {
        return maxColor;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void updateRenderer(){
        PixelWriter writer = this.renderer.getPixelWriter();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                writer.setColor(x, y, Color.grayRgb(image[y][x]));
            }
        }
    }

    public WritableImage getRenderer() { return renderer; }
    public ImageView getView() { return view; }

    public double getMean() {
        return mean;
    }

    public double getSigma() {
        return sigma;
    }
}
