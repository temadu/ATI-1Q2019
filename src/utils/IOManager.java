package utils;

import models.ImageColor;
import models.ImageGrey;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
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
        return new ImageGrey(data2D, picHeight, picWidth);
    }

    public static void savePGM(String filePath, ImageGrey image){
        try {
            Integer[][] rawImage = image.getImage();
            OutputStream w = new FileOutputStream(filePath);
            int height = image.getHeight();
            int width = image.getWidth();

            String header = "P6\n" + width + " " + height + "\n" + 255 + "\n";
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
        Integer[][] red = new Integer[picHeight][picWidth];
        Integer[][] green = new Integer[picHeight][picWidth];
        Integer[][] blue = new Integer[picHeight][picWidth];
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                red[row][col] = dis.readUnsignedByte();
                green[row][col] = dis.readUnsignedByte();
                blue[row][col] = dis.readUnsignedByte();
            }
        }
        return new ImageColor(red, green, blue, picHeight, picWidth);
    }

    public static void savePPM(String filePath, ImageColor image){
        try {

            OutputStream w = new FileOutputStream(filePath);
            int height = image.getHeight();
            int width = image.getWidth();

            String header = "P6\n" + width + " " + height + "\n" + 255 + "\n";
            byte[] headerBytes = header.getBytes();

            for (int i = 0; i < headerBytes.length; i++) {
                w.write(headerBytes[i]);
            }
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    w.write(image.getRed()[row][col].byteValue());
                    w.write(image.getGreen()[row][col].byteValue());
                    w.write(image.getBlue()[row][col].byteValue());
                }
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ImageGrey loadRAW(String filePath) throws IOException{
        //Parse header
        File f = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(filePath);
        DataInputStream dis = new DataInputStream(fileInputStream);
        int picHeight = 0;
        int picWidth = 0;
        // look for 4 lines (i.e.: the header) and discard them
        try (BufferedReader br = new BufferedReader(new FileReader("images/informacion.txt"))) {
            String line;
            br.readLine();
            br.readLine();
            while ((line = br.readLine()) != null) {
                // process the line.
                String[] spl = line.split("\\s+");
                if(spl.length > 0 && spl[0].equals(f.getName())){
                    picWidth = Integer.parseInt(spl[1]);
                    picHeight = Integer.parseInt(spl[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // read the image data
        Integer[][] data2D = new Integer[picHeight][picWidth];
        int maxvalue = 0;
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                data2D[row][col] = dis.readUnsignedByte();
                maxvalue = Math.max(data2D[row][col], maxvalue);
            }
        }
        return new ImageGrey(data2D, picHeight, picWidth);
    }

    public static void saveRAW(String filePath, String name ,ImageGrey image){
        try {
            System.out.println(filePath);
            Integer[][] rawImage = image.getImage();
            OutputStream w = new FileOutputStream(filePath);
            int height = image.getHeight();
            int width = image.getWidth();

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    w.write(rawImage[row][col].byteValue());
                }
            }
            w.close();

            String info = "\r\n" + name + "      " + width + "       " + height;

            Files.write(Paths.get("images/informacion.txt"), info.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ImageColor loadJPG(String filePath) throws IOException {

        File input_file = new File(filePath);
        // Reading input file
        BufferedImage image = ImageIO.read(input_file);

        int picWidth = image.getWidth();
        int picHeight = image.getHeight();
        Integer[][] red = new Integer[picHeight][picWidth];
        Integer[][] green = new Integer[picHeight][picWidth];
        Integer[][] blue = new Integer[picHeight][picWidth];

        // read the image data
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                int p = image.getRGB(col,row);
                // get alpha
                int a = (p>>24) & 0xff;
                red[row][col] = (p>>16) & 0xff;
                green[row][col] = (p>>8) & 0xff;
                blue[row][col] = p & 0xff;
            }
        }
        return new ImageColor(red, green, blue, picHeight, picWidth);
    }


}
