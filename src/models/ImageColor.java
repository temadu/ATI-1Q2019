package models;

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

public class ImageColor implements ImageInt{
    private Integer[][] red;
    private Integer[][] green;
    private Integer[][] blue;
    private int height;
    private int width;

    private WritableImage renderer;
    private ImageView view;

    public ImageColor(String filePath) throws IOException{
        parse(filePath);
    }

    public ImageColor(Integer[][] red, Integer[][] green, Integer[][] blue, int height, int width){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.height = height;
        this.width = width;
        this.renderer = new WritableImage(width, height);
        this.updateRenderer();
        this.view = new ImageView(this.renderer);
    }

    public ImageColor(Integer[][] red, Integer[][] green, Integer[][] blue, int height, int width, boolean render){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.height = height;
        this.width = width;
        if(render) {
            this.renderer = new WritableImage(width, height);
            this.updateRenderer();
            this.view = new ImageView(this.renderer);
        }
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
        System.out.println(width);
        System.out.println(height);
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
        red = new Integer[height][width];
        green = new Integer[height][width];
        blue = new Integer[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                red[row][col] = dis.readUnsignedByte();
                green[row][col] = dis.readUnsignedByte();
                blue[row][col] = dis.readUnsignedByte();
            }
        }
    }

    public Image matrixToColorImage(boolean red, boolean green, boolean blue){
        WritableImage wImage = new WritableImage(width, height);
        PixelWriter writer = wImage.getPixelWriter();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                writer.setColor(x, y, Color.rgb(red ? this.red[y][x] : 0, green ? this.green[y][x] : 0, blue ? this.blue[y][x] : 0));
            }
        }
        return wImage;
    }

    public Integer[][] getRed() {
        return red;
    }

    public Integer[][] getGreen() {
        return green;
    }

    public Integer[][] getBlue() {
        return blue;
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
                writer.setColor(x, y, Color.rgb(red[y][x], green[y][x], blue[y][x]));
            }
        }
    }

    public int getMaxRed() {
        return Arrays.stream(red).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).max().getAsInt();
    }

    public int getMaxGreen() {
        return Arrays.stream(green).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).max().getAsInt();
    }

    public int getMaxBlue() {
        return Arrays.stream(blue).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).max().getAsInt();
    }

    public int getMinRed() {
        return Arrays.stream(red).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).min().getAsInt();
    }

    public int getMinGreen() {
        return Arrays.stream(green).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).min().getAsInt();
    }

    public int getMinBlue() {
        return Arrays.stream(blue).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).min().getAsInt();
    }

    public WritableImage getRenderer() { return renderer; }
    public ImageView getView() { return view; }

    public void setPixel(int x, int y, Integer[] color){
        if(x >= 0 && x < width
            && y >= 0 && y < height
            && color.length == 3
            && color[0] >= 0 && color[0] < 256
            && color[1] >= 0 && color[1] < 256
            && color[2] >= 0 && color[2] < 256){
            this.red[y][x] = color[0];
            this.green[y][x] = color[1];
            this.blue[y][x] = color[2];
            this.renderer.getPixelWriter().setColor(x,y,Color.rgb(color[0], color[1], color[2]));
        }
    }
    public Integer[] getPixel(int x, int y){
        if(x >= 0 && x < width && y >= 0 && y < height){
            return new Integer[] {this.red[y][x], this.green[y][x], this.blue[y][x]};
        }
        return null;
    }

    public static ImageColor blankImage(int height, int width){
        Integer[][] r = new Integer[height][width];
        Integer[][] g = new Integer[height][width];
        Integer[][] b = new Integer[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                r[i][j] = 0;
                g[i][j] = 0;
                b[i][j] = 0;
            }
        }
        return new ImageColor(r,g,b,height,width);
    }
}
