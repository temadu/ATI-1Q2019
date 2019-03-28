package utils;

import models.ImageColor;
import models.ImageGrey;

import java.io.*;
import java.util.Scanner;

public class IOManager {

    public static ImageGrey loadPGM(String filePath) throws IOException {

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
        Integer[][] data2D = new Integer[picHeight][picWidth];
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                data2D[row][col] = dis.readUnsignedByte();
            }
        }
        return new ImageGrey(data2D, maxvalue, picHeight, picWidth);
    }

    public static void savePGM(String filePath, ImageGrey image){
        try {
            Integer[][] rawImage = image.getImage();
            OutputStream w = new FileOutputStream(filePath);
            int height = image.getHeight();
            int width = image.getWidth();

            String header = "P6\n" + width + " " + height + "\n" + image.getMaxColor() + "\n";
            byte[] headerBytes = header.getBytes();

            for (int i = 0; i < headerBytes.length; i++) {
                w.write(headerBytes[i]);
            }
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    w.write(rawImage[row][col].byteValue());
                }
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ImageColor loadPPM(String filePath) throws IOException {

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
        Integer[][][] data2D = new Integer[picHeight][picWidth][3];
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                data2D[row][col][0] = dis.readUnsignedByte();
                data2D[row][col][1] = dis.readUnsignedByte();
                data2D[row][col][2] = dis.readUnsignedByte();
            }
        }
        return new ImageColor(data2D, maxvalue, picHeight, picWidth);
    }

    public static void savePPM(String filePath, ImageColor image){
        try {

            Integer[][][] rawImage = image.getImage();
            OutputStream w = new FileOutputStream(filePath);
            int height = image.getHeight();
            int width = image.getWidth();

            String header = "P6\n" + width + " " + height + "\n" + image.getMaxColor() + "\n";
            byte[] headerBytes = header.getBytes();

            for (int i = 0; i < headerBytes.length; i++) {
                w.write(headerBytes[i]);
            }
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    for(int rgb = 0; rgb < 3; rgb++){
                        w.write(rawImage[row][col][rgb].byteValue());
                    }
                }
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
