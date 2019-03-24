package models;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ImageColor implements ImageInt{
    private int[][][] image;
    private int maxColor;
    private int height;
    private int width;

    private WritableImage renderer;

    public ImageColor(String filePath) throws IOException{
        maxColor = 0;
        parse(filePath);
    }

    public ImageColor(int[][][] image, int maxColor, int height, int width){
        this.image = image;
        this.maxColor = maxColor;
        this.height = height;
        this.width = width;
        this.renderer = new WritableImage(width, height);;
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
        image = new int[height][width][3];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                image[row][col][0] = dis.readUnsignedByte();
                image[row][col][1] = dis.readUnsignedByte();
                image[row][col][2] = dis.readUnsignedByte();
                maxColor = Math.max(maxColor, Math.max(image[row][col][0], Math.max(image[row][col][1], image[row][col][2])));

            }
        }
    }

    public Image matrixToColorImage(boolean red, boolean green, boolean blue){
        WritableImage wImage = new WritableImage(width, height);
        PixelWriter writer = wImage.getPixelWriter();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                writer.setColor(x, y, Color.rgb(red ? image[y][x][0] : 0, green ? image[y][x][1] : 0, blue ? image[y][x][2] : 0));
            }
        }
        return wImage;
    }

    public int[][][] getImage() {
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
                writer.setColor(x, y, Color.rgb(image[y][x][0], image[y][x][1], image[y][x][2]));
            }
        }
    }

    public WritableImage getRenderer() { return renderer; }

}
