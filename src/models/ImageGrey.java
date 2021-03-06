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
import java.util.Comparator;
import java.util.Scanner;

public class ImageGrey implements ImageInt{
    private Integer[][] image;
    private int height;
    private int width;
    private double mean;
    private double sigma;
    private XYChart.Series greySeries;

    private WritableImage renderer;
    private ImageView view;

    public ImageGrey(String filePath) throws IOException{
        parse(filePath);
    }

    public ImageGrey(Integer[][] image, int height, int width){
        this.image = image;
        this.height = height;
        this.width = width;
        this.renderer = new WritableImage(width, height);
        this.updateRenderer();
        this.view = new ImageView(this.renderer);
        calcMean();
        calcStd();
    }

    public ImageGrey(Integer[][] image, int height, int width, boolean renderer){
        this.image = image;
        this.height = height;
        this.width = width;
        if(renderer){
            this.renderer = new WritableImage(width, height);
            this.updateRenderer();
            this.view = new ImageView(this.renderer);
        }
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
                if(image[y][x] == null)
                    writer.setColor(x, y, Color.grayRgb(0));
                else
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
    public void setPixel(int x, int y, int color){
        if(x >= 0 && x < width && y >= 0 && y < height
                && color >= 0 && color < 256){
            this.image[y][x] = color;
            this.renderer.getPixelWriter().setColor(x,y,Color.grayRgb(color));
        }
    }
    public Integer getPixel(int x, int y){
        if(x >= 0 && x < width && y >= 0 && y < height){
            return this.image[y][x];
        }
        return null;
    }

    public int getMaxGrey() {
        return Arrays.stream(image).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).max().getAsInt();
    }

    public int getMinGrey() {
        return Arrays.stream(image).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).min().getAsInt();
    }

    public void setImage(Integer[][] image) {
        this.image = image;
    }
    
    public static ImageGrey blankImage(int height, int width){
        Integer[][] r = new Integer[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                r[i][j] = 0;
            }
        }
        return new ImageGrey(r,height,width);
    }

}
