package utils;

public class Utils {

//    public Image hsvMatrixToColor (double[][][] imageMatrix, boolean hue, boolean saturation, boolean value){
//        int width = imageMatrix[0].length;
//        int height = imageMatrix.length;
//
//        WritableImage wImage = new WritableImage(width, height);
//        PixelWriter writer = wImage.getPixelWriter();
//
//        for(int y = 0; y < height; y++) {
//            for(int x = 0; x < width; x++) {
//                int color = 0;
//                if(hue) {
//                    if (imageMatrix[y][x][0] == -1) {
//                        color = 255;
//                    } else {
//                        color = (int) ((imageMatrix[y][x][0]/360) * 255);
//                    }
//                } else if (saturation) {
//                    color = (int) (imageMatrix[y][x][1] * 255);
//                } else if (value) {
//                    color = (int) (imageMatrix[y][x][2] * 255);
//                }
//                writer.setColor(x, y, Color.rgb(color, color, color));
//            }
//        }
//        return wImage;
//    }

    public static double[][][] RGBtoHSVmatrix (int[][][] rgbImage) {
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

    public static int[] HSVtoRGB(double[] color) {
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

    public static double[] RGBtoHSV(int[] color){
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
