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
        int[][] data2D = new int[picHeight][picWidth];
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                data2D[row][col] = dis.readUnsignedByte();
            }
        }
        return new ImageGrey(data2D, maxvalue, picHeight, picWidth);
    }

    public static void savePGM(String filePath, int[][] image, int scale){
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
        int[][][] data2D = new int[picHeight][picWidth][3];
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                data2D[row][col][0] = dis.readUnsignedByte();
                data2D[row][col][1] = dis.readUnsignedByte();
                data2D[row][col][2] = dis.readUnsignedByte();
            }
        }
        return new ImageColor(data2D, maxvalue, picHeight, picWidth);
    }

    public static void savePPM(String filePath, int[][][] image, int scale){
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



}
