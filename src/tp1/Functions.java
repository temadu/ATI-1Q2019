package tp1;

import javafx.collections.transformation.SortedList;
import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Functions {
    public ImageInt image;
    private boolean greyscale;
    private ArrayList<Point> classLin;
    private ArrayList<Point> classLout;
    private Integer[][] classPhi;
    private double thetaR;
    private double thetaG;
    private double thetaB;


    public Functions(ImageInt image) {
        this.image = image;
        if(image instanceof ImageGrey)
            this.greyscale = true;
        else{
            this.greyscale = false;
        }
    }

    private Integer[][] clamp(Integer[][] m) {
        int max = Arrays.stream(m).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).max().getAsInt();
        int min = Arrays.stream(m).mapToInt(a -> Arrays.stream(a).min(Comparator.naturalOrder()).get()).min().getAsInt();
        if(max < 256 && min >= 0){
            return m;
        }
        double mult = 255.0/(max - min);
        Integer[][] as = Arrays.stream(m).parallel().map(a -> Arrays.stream(a).map(p -> (int) ((double) (p - min) * mult)).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return as;
    }

    private Integer[][] clampAlways(Integer[][] m) {
        int max = Arrays.stream(m).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).max().getAsInt();
        int min = Arrays.stream(m).mapToInt(a -> Arrays.stream(a).min(Comparator.naturalOrder()).get()).min().getAsInt();
        double mult = 255.0/(max - min);
        Integer[][] as = Arrays.stream(m).parallel().map(a -> Arrays.stream(a).map(p -> (int) ((double) (p - min) * mult)).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return as;
    }

    public ImageInt imageSum(ImageInt addend) {
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];

        Integer r;
        Integer[] rgb;

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(greyscale){
                    r = ((ImageGrey) addend).getPixel(j,i);
                    red[i][j] = ((ImageGrey) image).getImage()[i][j] + (r!=null?r:0);
                } else {
                    rgb = ((ImageColor) addend).getPixel(j,i);
                    red[i][j] = ((ImageColor) image).getRed()[i][j] + (rgb!=null?rgb[0]:0);
                    green[i][j] = ((ImageColor) image).getGreen()[i][j] + (rgb!=null?rgb[1]:0);
                    blue[i][j] = ((ImageColor) image).getBlue()[i][j] + (rgb!=null?rgb[2]:0);
                }
            }
        }
        if(greyscale) {
            red = clamp(red);
        } else {
            red = clamp(red);
            green = clamp(green);
            blue = clamp(blue);
        }
        return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth())
                : new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageInt imageSub(ImageInt substract) {
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];

        Integer r;
        Integer[] rgb;

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(greyscale){
                    r = ((ImageGrey) substract).getPixel(j,i);
                    red[i][j] = ((ImageGrey) image).getImage()[i][j] - (r!=null?r:0);
                } else {
                    rgb = ((ImageColor) substract).getPixel(j,i);
                    red[i][j] = ((ImageColor) image).getRed()[i][j] - (rgb!=null?rgb[0]:0);
                    green[i][j] = ((ImageColor) image).getGreen()[i][j] - (rgb!=null?rgb[1]:0);
                    blue[i][j] = ((ImageColor) image).getBlue()[i][j] - (rgb!=null?rgb[2]:0);
                }
            }
        }
        if(greyscale) {
            red = clamp(red);
        } else {
            red = clamp(red);
            green = clamp(green);
            blue = clamp(blue);
        }
        return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth())
                : new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageInt imageProd(double multiplier) {
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (greyscale) {
                    red[i][j] = (int) (((ImageGrey)image).getImage()[i][j] * multiplier);

                } else {
                    red[i][j] = (int) (((ImageColor)image).getRed()[i][j] * multiplier);
                    green[i][j] = (int) (((ImageColor)image).getGreen()[i][j] * multiplier);
                    blue[i][j] = (int) (((ImageColor)image).getBlue()[i][j] * multiplier);
                }
            }
        }
        return rangeCompressorPriv(red, green, blue);
    }

    private ImageInt rangeCompressorPriv(Integer[][] red, Integer[][] green, Integer[][] blue) {
        Integer[][] newRed = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] newGreen = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] newBlue = new Integer[image.getHeight()][image.getWidth()];
        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;
        if(greyscale){
            maxRed = Arrays.stream(red).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).max().getAsInt();;
        } else {
            maxRed = Arrays.stream(red).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).max().getAsInt();;
            maxGreen = Arrays.stream(green).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).max().getAsInt();;
            maxBlue = Arrays.stream(blue).mapToInt(a -> Arrays.stream(a).max(Comparator.naturalOrder()).get()).max().getAsInt();;
        }
        if(maxRed < 256 && maxGreen < 256 && maxBlue < 256){
            return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth())
                    : new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
        }
        double multiplierR = 255.0 / Math.log(1 + maxRed);
        double multiplierG = 255.0 / Math.log(1 + maxGreen);
        double multiplierB = 255.0 / Math.log(1 + maxBlue);

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(greyscale) {
                    newRed[i][j] = (int) (multiplierR * Math.log(1 + red[i][j]));
                } else {
                    newRed[i][j] = (int) (multiplierR * Math.log(1 + red[i][j]));
                    newGreen[i][j] = (int) (multiplierG * Math.log(1 + green[i][j]));
                    newBlue[i][j] = (int) (multiplierB * Math.log(1 + blue[i][j]));
                }
            }
        }
        return greyscale ? new ImageGrey(newRed, image.getHeight(), image.getWidth())
                : new ImageColor(newRed, newGreen, newBlue, image.getHeight(), image.getWidth());
    }

    public ImageInt rangeCompressor() {
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];
//        double multiplier = 255.0 / Math.log(1 + image.getMaxColor());
        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;
        if(greyscale){
            maxRed = ((ImageGrey)image).getMaxGrey();
        } else {
            maxRed = ((ImageColor)image).getMaxRed();
            maxGreen = ((ImageColor)image).getMaxGreen();
            maxBlue = ((ImageColor)image).getMaxBlue();
        }
        double multiplierR = 255.0 / Math.log(1 + maxRed);
        double multiplierG = 255.0 / Math.log(1 + maxGreen);
        double multiplierB = 255.0 / Math.log(1 + maxBlue);

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(greyscale) {
                    red[i][j] = (int) (multiplierR * Math.log(1 + ((ImageGrey)image).getImage()[i][j]));
                } else {
                    red[i][j] = (int) (multiplierR * Math.log(1 + ((ImageColor)image).getRed()[i][j]));
                    green[i][j] = (int) (multiplierG * Math.log(1 + ((ImageColor)image).getGreen()[i][j]));
                    blue[i][j] = (int) (multiplierB * Math.log(1 + ((ImageColor)image).getBlue()[i][j]));
                }
            }
        }
        return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth())
                : new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageInt gammaFunction(double gamma) {
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];
        double multiplier = Math.pow(255.0, 1 - gamma);
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(greyscale){
                    red[i][j] = (int) (multiplier * Math.pow(((ImageGrey)image).getImage()[i][j], gamma));
                } else {
                    red[i][j] = (int) (multiplier * Math.pow(((ImageColor)image).getRed()[i][j], gamma));
                    green[i][j] = (int) (multiplier * Math.pow(((ImageColor)image).getGreen()[i][j], gamma));
                    blue[i][j] = (int) (multiplier * Math.pow(((ImageColor)image).getBlue()[i][j], gamma));
                }
            }
        }
        return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth())
                : new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageInt negative() {
        Integer[][] red =  null;
        Integer[][] green = null;
        Integer[][] blue = null;
        if(greyscale){
            red = Arrays.stream(((ImageGrey) image).getImage()).map(a -> Arrays.stream(a).map(e -> 255 - e).toArray(Integer[]::new)).toArray(Integer[][]::new);
        } else {
            red = Arrays.stream(((ImageColor) image).getRed()).map(a -> Arrays.stream(a).map(e -> 255 - e).toArray(Integer[]::new)).toArray(Integer[][]::new);
            green = Arrays.stream(((ImageColor) image).getGreen()).map(a -> Arrays.stream(a).map(e -> 255 - e).toArray(Integer[]::new)).toArray(Integer[][]::new);
            blue = Arrays.stream(((ImageColor) image).getBlue()).map(a -> Arrays.stream(a).map(e -> 255 - e).toArray(Integer[]::new)).toArray(Integer[][]::new);
        }
        return greyscale ?  new ImageGrey(red, image.getHeight(), image.getWidth())
                :  new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public double[] greyHistogram() {
        double[] hist = new double[256];
        Arrays.stream(((ImageGrey)image).getImage()).forEach(a -> Arrays.stream(a).forEach(e -> hist[e]++));
        return Arrays.stream(hist).map(h -> h / (image.getHeight() * image.getWidth())).toArray();
    }

    public ImageGrey histogramEqualization() {
        double[] g = greyHistogram();
        double[] cum = new double[256];
        cum[0] = g[0];
        IntStream.range(1, cum.length).forEach(i -> cum[i] = (cum[i - 1] + g[i]));
        Arrays.setAll(cum, i -> cum[i] * 255.0);

        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a -> Arrays.stream(a).map(p -> (int) Math.floor(cum[p])).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageGrey(res, image.getHeight(), image.getWidth());
    }

    public ImageGrey greyContrast(double sigmaMult) {
        ((ImageGrey)image).calcMean();
        ((ImageGrey)image).calcStd();
        int s1 = 32;
        int s2 = 224;
        int r1 = Math.max(s1, (int) (((ImageGrey)image).getMean() - ((ImageGrey)image).getSigma() * sigmaMult));
        int r2 = Math.min(s2, (int) (((ImageGrey)image).getMean() + ((ImageGrey)image).getSigma() * sigmaMult));
        double pend1 = (double)s1/(double)r1;
        double rest1 = 0;
        double pend2 = (double)(s2 - s1)/(double)(r2- r1);
        double rest2 = (double)s1 - pend2 * r1;
        double pend3 = (255.0 - (double)s2)/(255.0 - (double)r2);
        double rest3 = (double)s2 - pend3 * r2;
        Integer [][] res = new Integer[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(((ImageGrey)image).getImage()[i][j] < r1) {
                    res[i][j] = (int) ((((ImageGrey)image).getImage()[i][j] * pend1) + rest1);
                } else if(((ImageGrey)image).getImage()[i][j] < r2) {
                    res[i][j] = (int) ((((ImageGrey)image).getImage()[i][j] * pend2) + rest2);
                } else {
                    res[i][j] = (int) ((((ImageGrey)image).getImage()[i][j] * pend3) + rest3);
                }
            }
        }
        return new ImageGrey(res, image.getHeight(), image.getWidth());
    }

    public ImageGrey thresholdization(int threshold) {
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).parallel().map(a -> Arrays.stream(a).parallel().map(p -> p > threshold ? 255 : 0).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageGrey(res, image.getHeight(), image.getWidth());
    }

    public ImageColor thresholdizationColor(int r, int g, int b) {
        Integer[][] red = Arrays.stream(((ImageColor)image).getRed()).parallel().map(a -> Arrays.stream(a).parallel().map(p -> p > r ? 255 : 0).toArray(Integer[]::new)).toArray(Integer[][]::new);
        Integer[][] green = Arrays.stream(((ImageColor)image).getGreen()).parallel().map(a -> Arrays.stream(a).parallel().map(p -> p > g ? 255 : 0).toArray(Integer[]::new)).toArray(Integer[][]::new);
        Integer[][] blue = Arrays.stream(((ImageColor)image).getBlue()).parallel().map(a -> Arrays.stream(a).parallel().map(p -> p > b ? 255 : 0).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageGrey globalThresholdization() {
        ImageGrey imgAux = (ImageGrey) image;
        int threshold = 128;
        int prevThreshold = 0;
        int minThreshDiff = 1;
        while(Math.pow(threshold - prevThreshold, 2) > minThreshDiff){
            double m1 = 0;
            double m2 = 0;
            double tot1 = 0;
            double tot2 = 0;
            for (int i = 0; i < imgAux.getHeight(); i++) {
                for (int j = 0; j < imgAux.getWidth(); j++) {
                    if(imgAux.getImage()[i][j] > threshold) {
                        m1 += imgAux.getImage()[i][j];
                        tot1++;
                    } else {
                        m2 += imgAux.getImage()[i][j];
                        tot2++;
                    }
               }
            }
            prevThreshold = threshold;
            threshold = (int) Math.floor((m1/(tot1 + 1) + m2/(tot2 + 1)) / 2.0);
        }
        return thresholdization(threshold);
    }

    public ImageColor globalThresholdizationColor() {
        ImageColor imgAux = (ImageColor) image;
        int r = 128;
        int g = 128;
        int b = 128;
        int prevR = 0;
        int prevG = 0;
        int prevB = 0;
        int minThreshDiff = 1;
        while(Math.pow(r - prevR, 2) > minThreshDiff || Math.pow(g - prevG, 2) > minThreshDiff || Math.pow(b - prevB, 2) > minThreshDiff){
            double m1r = 0; double m2r = 0; double tot1r = 0; double tot2r = 0;
            double m1g = 0; double m2g = 0; double tot1g = 0; double tot2g = 0;
            double m1b = 0; double m2b = 0; double tot1b = 0; double tot2b = 0;
            for (int i = 0; i < imgAux.getHeight(); i++) {
                for (int j = 0; j < imgAux.getWidth(); j++) {
                    if(imgAux.getRed()[i][j] > r) {
                        m1r += imgAux.getRed()[i][j];
                        tot1r++;
                    } else {
                        m2r += imgAux.getRed()[i][j];
                        tot2r++;
                    }
                    if(imgAux.getGreen()[i][j] > g) {
                        m1g += imgAux.getGreen()[i][j];
                        tot1g++;
                    } else {
                        m2g += imgAux.getGreen()[i][j];
                        tot2g++;
                    }
                    if(imgAux.getBlue()[i][j] > b) {
                        m1b += imgAux.getBlue()[i][j];
                        tot1b++;
                    } else {
                        m2b += imgAux.getBlue()[i][j];
                        tot2b++;
                    }
                }
            }
            prevR = r;
            r = (int) Math.floor((m1r/(tot1r + 1) + m2r/(tot2r + 1)) / 2.0);
            prevG = g;
            g = (int) Math.floor((m1g/(tot1g + 1) + m2g/(tot2g + 1)) / 2.0);
            prevB = b;
            b = (int) Math.floor((m1b/(tot1b + 1) + m2b/(tot2b + 1)) / 2.0);
        }
        return thresholdizationColor(r,g,b);
    }

    public ImageGrey otsuThreshold() {
        double[] hist = new double[256];
        Arrays.stream(((ImageGrey)image).getImage()).forEach(a -> Arrays.stream(a).forEach(e -> hist[e]++));
        double[] histN = Arrays.stream(hist).map(h -> h / (image.getHeight() * image.getWidth())).toArray();
        double[] sums = new double[256];
        double[] meanAc = new double[256];
        sums[0] = histN[0];
        for (int i = 1; i < 256; i++) {
            sums[i] = sums[i-1] + histN[i];
        }
        meanAc[0] = 0;
        for (int i = 1; i < 256; i++) {
            meanAc[i] = meanAc[i-1] + i * histN[i];
        }
        double gloablMean = meanAc[255];
        ArrayList<Integer> maxs = new ArrayList<>();
        double maxVar = 0;
        for (int i = 0; i < 256; i++) {
            if(sums[i] == 0){
                continue;
            }
            if(sums[i] == 1){
                break;
            }
            double var = Math.pow(gloablMean * sums[i] - meanAc[i],2) / (sums[i] * (1.0 - sums[i]));
            if(var > maxVar){
                maxs.clear();
                maxs.add(i);
                maxVar = var;
            }
            if(var == maxVar) {
                maxs.add(i);
            }
        }

        if(maxs.isEmpty()){
            return thresholdization(128);
        } else {
            int t = (int) Math.floor(maxs.stream().mapToInt(e -> e).average().getAsDouble());
            return thresholdization(t);
        }
    }

    public ImageColor otsuThresholdColor() {
        double[] histR = new double[256]; double[] histG = new double[256]; double[] histB = new double[256];
        Arrays.stream(((ImageColor)image).getRed()).forEach(a -> Arrays.stream(a).forEach(e -> histR[e]++));
        Arrays.stream(((ImageColor)image).getGreen()).forEach(a -> Arrays.stream(a).forEach(e -> histG[e]++));
        Arrays.stream(((ImageColor)image).getBlue()).forEach(a -> Arrays.stream(a).forEach(e -> histB[e]++));
        double[] histNr = Arrays.stream(histR).map(h -> h / (image.getHeight() * image.getWidth())).toArray();
        double[] histNg = Arrays.stream(histG).map(h -> h / (image.getHeight() * image.getWidth())).toArray();
        double[] histNb = Arrays.stream(histB).map(h -> h / (image.getHeight() * image.getWidth())).toArray();
        double[] sumsR = new double[256]; double[] sumsG = new double[256]; double[] sumsB = new double[256];
        double[] meanAcR = new double[256]; double[] meanAcG = new double[256]; double[] meanAcB = new double[256];
        sumsR[0] = histNr[0]; sumsG[0] = histNg[0]; sumsB[0] = histNb[0];
        for (int i = 1; i < 256; i++) {
            sumsR[i] = sumsR[i-1] + histNr[i];
            sumsG[i] = sumsG[i-1] + histNg[i];
            sumsB[i] = sumsB[i-1] + histNb[i];
        }
        meanAcR[0] = 0; meanAcG[0] = 0; meanAcB[0] = 0;
        for (int i = 1; i < 256; i++) {
            meanAcR[i] = meanAcR[i-1] + i * histNr[i];
            meanAcG[i] = meanAcG[i-1] + i * histNg[i];
            meanAcB[i] = meanAcB[i-1] + i * histNb[i];
        }
        double gloablMeanR = meanAcR[255];
        double gloablMeanG = meanAcG[255];
        double gloablMeanB = meanAcB[255];
        ArrayList<Integer> maxsR = new ArrayList<>();
        ArrayList<Integer> maxsG = new ArrayList<>();
        ArrayList<Integer> maxsB = new ArrayList<>();
        double maxVarR = 0; double maxVarG = 0; double maxVarB = 0;
        for (int i = 0; i < 256; i++) {
            double varR = 0, varG = 0, varB = 0;
            if(sumsR[i] != 0 && sumsR[i] != 1){
                varR = Math.pow(gloablMeanR * sumsR[i] - meanAcR[i],2) / (sumsR[i] * (1.0 - sumsR[i]));
            }
            if(sumsG[i] != 0 && sumsG[i] != 1) {
                varG = Math.pow(gloablMeanG * sumsG[i] - meanAcG[i], 2) / (sumsG[i] * (1.0 - sumsG[i]));
            }
            if(sumsB[i] != 0 && sumsB[i] != 1) {
                varB = Math.pow(gloablMeanB * sumsB[i] - meanAcB[i], 2) / (sumsB[i] * (1.0 - sumsB[i]));
            }
            if(varR > maxVarR){
                maxsR.clear();
                maxsR.add(i);
                maxVarR = varR;
            } else if(varR == maxVarR) {
                maxsR.add(i);
            }
            if(varG > maxVarG){
                maxsG.clear();
                maxsG.add(i);
                maxVarG = varG;
            } else if(varG == maxVarG) {
                maxsG.add(i);
            }
            if(varB > maxVarB){
                maxsB.clear();
                maxsB.add(i);
                maxVarB = varB;
            } else if(varB == maxVarB) {
                maxsB.add(i);
            }
        }

        int tr = maxsR.isEmpty() ? 128 : (int) Math.floor(maxsR.stream().mapToInt(e -> e).average().getAsDouble());
        int tg = maxsG.isEmpty() ? 128 : (int) Math.floor(maxsG.stream().mapToInt(e -> e).average().getAsDouble());
        int tb = maxsB.isEmpty() ? 128 : (int) Math.floor(maxsB.stream().mapToInt(e -> e).average().getAsDouble());
        return thresholdizationColor(tr,tg,tb);
    }

    public ImageGrey addGaussianNoise(double density, double std) {
        RandomGaussianGenerator rgg = new RandomGaussianGenerator(0, std);
        Random r = new Random();
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a-> Arrays.stream(a).map(p -> {
            double rnd = rgg.nextRandom();
            if(r.nextDouble() > density) {
                return p;
            }
                return (int) Math.floor(p + rnd);
        }).toArray(Integer[]::new)).toArray(Integer[][]::new);

        res = clamp(res);
        return new ImageGrey(res, image.getHeight(), image.getWidth());
    }

    public ImageColor addGaussianNoiseColor(double density, double std) {
        RandomGaussianGenerator rgg = new RandomGaussianGenerator(0, std);
        Random ra = new Random();
        Integer[][] red = Arrays.stream(((ImageColor)image).getRed()).map(a-> Arrays.stream(a)
                .map(p -> ra.nextDouble() > density ? p : (int) Math.floor(p + rgg.nextRandom())).toArray(Integer[]::new)).toArray(Integer[][]::new);
        Integer[][] green = Arrays.stream(((ImageColor)image).getGreen()).map(a-> Arrays.stream(a)
                .map(p -> ra.nextDouble() > density ? p : (int) Math.floor(p + rgg.nextRandom())).toArray(Integer[]::new)).toArray(Integer[][]::new);
        Integer[][] blue = Arrays.stream(((ImageColor)image).getBlue()).map(a-> Arrays.stream(a)
                .map(p -> ra.nextDouble() > density ? p : (int) Math.floor(p + rgg.nextRandom())).toArray(Integer[]::new)).toArray(Integer[][]::new);

        red = clamp(red);
        green = clamp(green);
        blue = clamp(blue);
        return new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageColor addRayleighNoiseColor(double density, double psi) {
        RandomRayleighGenerator rrg = new RandomRayleighGenerator(psi);
        Random r = new Random();
        Integer[][] red = Arrays.stream(((ImageColor)image).getRed()).map(a-> Arrays.stream(a).map(p ->
                r.nextDouble() > density ? p : (int) Math.floor(p * rrg.nextRandom())
        ).toArray(Integer[]::new)).toArray(Integer[][]::new);

        Integer[][] green = Arrays.stream(((ImageColor)image).getGreen()).map(a-> Arrays.stream(a).map(p ->
                r.nextDouble() > density ? p : (int) Math.floor(p * rrg.nextRandom())
        ).toArray(Integer[]::new)).toArray(Integer[][]::new);

        Integer[][] blue = Arrays.stream(((ImageColor)image).getBlue()).map(a-> Arrays.stream(a).map(p ->
                r.nextDouble() > density ? p : (int) Math.floor(p * rrg.nextRandom())
        ).toArray(Integer[]::new)).toArray(Integer[][]::new);

        red = clamp(red);
        green = clamp(green);
        blue = clamp(blue);

        return new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageGrey addRayleighNoise(double density, double psi) {
        RandomRayleighGenerator rrg = new RandomRayleighGenerator(psi);
        Random r = new Random();
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a-> Arrays.stream(a).map(p ->
            r.nextDouble() > density ? p : (int) Math.floor(p * rrg.nextRandom())
        ).toArray(Integer[]::new)).toArray(Integer[][]::new);
        res = clamp(res);

        return new ImageGrey(res, image.getHeight(), image.getWidth());
    }

    public ImageColor addExponentialNoiseColor(double density, double lambda) {
        RandomExpGenerator reg = new RandomExpGenerator(lambda);
        Random r = new Random();
        Integer[][] red = Arrays.stream(((ImageColor)image).getRed()).map(a-> Arrays.stream(a).map(p ->
                r.nextDouble() > density ? p : (int) Math.floor(p * reg.nextRandom())
        ).toArray(Integer[]::new)).toArray(Integer[][]::new);

        Integer[][] green = Arrays.stream(((ImageColor)image).getGreen()).map(a-> Arrays.stream(a).map(p ->
                r.nextDouble() > density ? p : (int) Math.floor(p * reg.nextRandom())
        ).toArray(Integer[]::new)).toArray(Integer[][]::new);

        Integer[][] blue = Arrays.stream(((ImageColor)image).getBlue()).map(a-> Arrays.stream(a).map(p ->
                r.nextDouble() > density ? p : (int) Math.floor(p * reg.nextRandom())
        ).toArray(Integer[]::new)).toArray(Integer[][]::new);

        red = clamp(red);
        green = clamp(green);
        blue = clamp(blue);

        return new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageGrey addExponentialNoise(double density, double lamda) {
        RandomExpGenerator reg = new RandomExpGenerator(lamda);
        Random r = new Random();
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a-> Arrays.stream(a).map(p ->
            r.nextDouble() > density ? p : (int) Math.floor(p * reg.nextRandom())
        ).toArray(Integer[]::new)).toArray(Integer[][]::new);

        res = clamp(res);

        return new ImageGrey(res, image.getHeight(), image.getWidth());
    }

    public ImageColor addSaltAndPepperColor(double density) {
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];
        Random ra = new Random();
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Integer r = ((ImageColor)image).getRed()[i][j];
                Integer g = ((ImageColor)image).getGreen()[i][j];
                Integer b = ((ImageColor)image).getBlue()[i][j];
                double rn = ra.nextDouble();
                if(rn < density) {
                    red[i][j] = 0;
                    green[i][j] = 0;
                    blue[i][j] = 0;
                } else if(rn > 1 - density) {
                    red[i][j] = 255;
                    green[i][j] = 255;
                    blue[i][j] = 255;
                } else {
                    red[i][j] = r;
                    green[i][j] = g;
                    blue[i][j] = b;
                }
            }
        }
        return new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageGrey addSaltAndPepper(double density) {
        Random r = new Random();
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a-> Arrays.stream(a).map(p -> {
            double rn = r.nextDouble();
            if(rn < density) {
                return 0;
            } else if (rn > 1 - density) {
                return 255;
            } else {
                return p;
            }
        }).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageGrey(res, image.getHeight(), image.getWidth());
    }

    public ImageInt meanFilter(int n) {
        double[][] w = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                w[i][j] = 1.0/(double)(n*n);
            }
        }
        return filter(w, false, true);
    }

    public ImageInt medianFilter(int n) {
        double[][] w = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                w[i][j] = 1;
            }
        }
        return filter(w, true, true);
    }

    public ImageInt weightedMedianFilter(int n) {
        double[][] w = new double[3][3];

//        int half = (int) Math.floor(n/2);
        w[0][0] = w[0][2] = w[2][0] = w[2][2] = 1.0;
        w[0][1] = w[1][0] = w[1][2] = w[2][1] = 2.0;
        w[1][1] = 4.0;

//        for (int i = 0; i < half + 1; i++) {
//            for (int j = 0; j < half + 1; j++) {
//                w[i][j] = 1 + i + j;
//                w[n - i - 1][n - j - 1] = 1 + i + j;
//                w[i][n - j - 1] = 1 + i + j;
//                w[n - i - 1][j] = 1 + i + j;
//            }
//        }
        return filter(w, true, true);
    }

    public ImageInt highpassFilter(int n) {
        double[][] w = new double[n][n];
        int half = (int) Math.floor(n/2);
//        double pre = 1/(Math.PI * Math.pow(sigma, 4));
//        double total = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                w[i][j] = -1.0/(double)(n*n);
            }
        }
        w[half][half] = 1.0-(1.0/(double)(n*n));
        return filter(w, false, true);
    }

    public ImageInt gaussFilter(int n, double sigma) {
        double[][] w = new double[n][n];
        int half = (int) Math.floor(n/2);
        double pre = 1/(2 * Math.PI * sigma * sigma);
        double total = 0;
        for (int i = -half; i < n - half; i++) {
            for (int j = -half; j < n - half; j++) {
                w[i + half][j + half] = pre * Math.exp(-(double)(i*i + j*j)/(sigma*sigma));
                total += w[i + half][j + half];
            }
        }
        for (int i = -half; i < n - half; i++) {
            for (int j = -half; j < n - half; j++) {
                w[i + half][j + half] *= (1.0/total);
            }
        }
        return filter(w, false, true);
    }

    public ImageInt gaussFilterFalse(int n, double sigma) {
        double[][] w = new double[n][n];
        int half = (int) Math.floor(n/2);
        double pre = 1/(2 * Math.PI * sigma * sigma);
        double total = 0;
        for (int i = -half; i < n - half; i++) {
            for (int j = -half; j < n - half; j++) {
                w[i + half][j + half] = pre * Math.exp(-(double)(i*i + j*j)/(sigma*sigma));
                total += w[i + half][j + half];
            }
        }
        for (int i = -half; i < n - half; i++) {
            for (int j = -half; j < n - half; j++) {
                w[i + half][j + half] *= (1.0/total);
            }
        }
        return filter(w, false, false);
    }

    public ImageInt laplaceMask() {
        double[][] w = new double[3][3];
        w[0][0] = w[0][2] = w[2][0] = w[2][2] = 0.0;
        w[0][1] = w[1][0] = w[1][2] = w[2][1] = -1.0;
        w[1][1] = 4.0;

        return filter(w, false, true);
    }

    public ImageInt laplaceEvaluated(int threshold, boolean evaluated) {
        double[][] w = new double[3][3];
        w[0][0] = w[0][2] = w[2][0] = w[2][2] = 0.0;
        w[0][1] = w[1][0] = w[1][2] = w[2][1] = -1.0;
        w[1][1] = 4.0;

        return evaluated ? zeroCross(filter(w, false, false), threshold) : filter(w, false, true);
    }

    public ImageInt applyZeroCross(int threshold) {
        return zeroCross(image, threshold);
    }

    public ImageInt zeroCross(ImageInt image, int threshold) {
        int height = image.getHeight();
        int width = image.getWidth();
        Integer[][] horizontalsR = new Integer[height][width];
        Integer[][] horizontalsG = new Integer[height][width];
        Integer[][] horizontalsB = new Integer[height][width];
        double lastR; double lastG; double lastB;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double pastR = 0; double pastG = 0; double pastB = 0;
                double currentR = 0; double currentG = 0; double currentB = 0;

                if(greyscale){

                    currentR = ((ImageGrey)image).getImage()[y][Math.floorMod(x + 1, width)];
                    lastR = pastR;
                    pastR = ((ImageGrey)image).getImage()[y][x];

                    if (pastR == 0 && x > 0) {
                        pastR = lastR;
                    }

                    horizontalsR[y][x] = ((currentR < 0 && pastR > 0) || (currentR > 0 && pastR < 0))
                            && Math.abs(currentR - pastR) >= threshold ? 255 : 0;
                } else {
                    currentR = ((ImageColor)image).getRed()[y][Math.floorMod(x + 1, width)];
                    currentG = ((ImageColor)image).getGreen()[y][Math.floorMod(x + 1, width)];
                    currentB = ((ImageColor)image).getBlue()[y][Math.floorMod(x + 1, width)];
                    lastR = pastR;
                    lastG = pastG;
                    lastB = pastB;
                    pastR = ((ImageColor)image).getRed()[y][x];
                    pastG = ((ImageColor)image).getGreen()[y][x];
                    pastB = ((ImageColor)image).getBlue()[y][x];

                    if (pastR == 0 && x > 0) {
                        pastR = lastR;
                    }
                    if (pastG == 0 && x > 0) {
                        pastG = lastG;
                    }
                    if (pastB == 0 && x > 0) {
                        pastB = lastB;
                    }

                    horizontalsR[y][x] = ((currentR < 0 && pastR > 0) || (currentR > 0 && pastR < 0))
                            && Math.abs(currentR - pastR) >= threshold ? 255 : 0;
                    horizontalsG[y][x] = ((currentG < 0 && pastG > 0) || (currentG > 0 && pastG < 0))
                            && Math.abs(currentG - pastG) >= threshold ? 255 : 0;
                    horizontalsB[y][x] = ((currentB < 0 && pastB > 0) || (currentB > 0 && pastB < 0))
                            && Math.abs(currentB - pastB) >= threshold ? 255 : 0;
                }
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double pastR = 0; double pastG = 0; double pastB = 0;
                double currentR = 0; double currentG = 0; double currentB = 0;

                if(greyscale){

                    currentR = ((ImageGrey)image).getImage()[Math.floorMod(y+1, height)][x];
                    lastR = pastR;
                    pastR = ((ImageGrey)image).getImage()[y][x];

                    if (pastR == 0 && x > 0) {
                        pastR = lastR;
                    }

                    horizontalsR[y][x] = ((currentR < 0 && pastR > 0) || (currentR > 0 && pastR < 0))
                            && Math.abs(currentR - pastR) >= threshold ? 255 : horizontalsR[y][x];
                } else {
                    currentR = ((ImageColor)image).getRed()[Math.floorMod(y+1, height)][x];
                    currentG = ((ImageColor)image).getGreen()[Math.floorMod(y+1, height)][x];
                    currentB = ((ImageColor)image).getBlue()[Math.floorMod(y+1, height)][x];
                    lastR = pastR;
                    lastG = pastG;
                    lastB = pastB;
                    pastR = ((ImageColor)image).getRed()[y][x];
                    pastG = ((ImageColor)image).getGreen()[y][x];
                    pastB = ((ImageColor)image).getBlue()[y][x];

                    if (pastR == 0 && x > 0) {
                        pastR = lastR;
                    }
                    if (pastG == 0 && x > 0) {
                        pastG = lastG;
                    }
                    if (pastB == 0 && x > 0) {
                        pastB = lastB;
                    }

                    horizontalsR[y][x] = ((currentR < 0 && pastR > 0) || (currentR > 0 && pastR < 0))
                            && Math.abs(currentR - pastR) >= threshold ? 255 : horizontalsR[y][x];
                    horizontalsG[y][x] = ((currentG < 0 && pastG > 0) || (currentG > 0 && pastG < 0))
                            && Math.abs(currentG - pastG) >= threshold ? 255 : horizontalsG[y][x];
                    horizontalsB[y][x] = ((currentB < 0 && pastB > 0) || (currentB > 0 && pastB < 0))
                            && Math.abs(currentB - pastB) >= threshold ? 255 : horizontalsB[y][x];
                }

            }
        }

        return greyscale ? new ImageGrey(horizontalsR, image.getHeight(), image.getWidth())
                : new ImageColor(horizontalsR, horizontalsG, horizontalsB, image.getHeight(), image.getWidth());
    }

    public ImageInt laplacianOfGaussian(int size, double sigma){
        double[][] w = new double[size][size];
        int subValue = size/2;
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                double factor = Math.pow(Math.sqrt(2 * Math.PI) * Math.pow(sigma, 3), -1);
                double term = 2 - (Math.pow(x-subValue, 2) + Math.pow(y-subValue, 2))/Math.pow(sigma, 2);
                double exp = - (Math.pow(x-subValue, 2) + Math.pow(y-subValue, 2)) / (2 * Math.pow(sigma, 2));
                double pixelValue = -1 * factor * term * Math.exp(exp);
                w[y][x] = pixelValue;
            }
        }
        return filter(w, false, true);
    }

    public ImageInt laplacianOfGaussianEvaluated(int size, double sigma, int threshold, boolean eval){
        double[][] w = new double[size][size];
        int subValue = size/2;
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                double factor = Math.pow(Math.sqrt(2 * Math.PI) * Math.pow(sigma, 3), -1);
                double term = 2 - (Math.pow(x-subValue, 2) + Math.pow(y-subValue, 2))/Math.pow(sigma, 2);
                double exp = - (Math.pow(x-subValue, 2) + Math.pow(y-subValue, 2)) / (2 * Math.pow(sigma, 2));
                double pixelValue = -1 * factor * term * Math.exp(exp);
                w[y][x] = pixelValue;
            }
        }
        return eval ?  zeroCross(filter(w, false, false), threshold) : filter(w, false, true);
    }

    public ImageInt bilateralFilter(int n, double sigmaS, double sigmaR){
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];
        int half = (int) Math.floor(n/2);

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                double sumR = 0;
                double totR = 0;
                double sumG = 0;
                double totG = 0;
                double sumB = 0;
                double totB = 0;
                for (int k = -half; k < n - half; k++) {
                    for (int l = -half; l < n - half; l++) {
                        if(greyscale) {
                            double w = Math.exp( - (double)((k*k) + (l*l)) / (2.0*sigmaS*sigmaS) - ( Math.pow(((ImageGrey)image).getImage()[i][j] -
                                    ((ImageGrey)image).getImage()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] ,2) / (2.0*sigmaR*sigmaR)));
                            sumR += (double) ((ImageGrey) image).getImage()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] * w;
                            totR += w;
                        } else {
                            ImageColor imgAux = (ImageColor)image;
                            double wR = Math.exp( - (double)((k*k) + (l*l)) / (2.0*sigmaS*sigmaS) - ( Math.pow(imgAux.getRed()[i][j] -
                                    imgAux.getRed()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] ,2) / (2.0*sigmaR*sigmaR)));
                            sumR += (double) imgAux.getRed()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] * wR;
                            totR += wR;
                            double wG = Math.exp( - (double)((k*k) + (l*l)) / (2.0*sigmaS*sigmaS) - ( Math.pow(imgAux.getGreen()[i][j] -
                                    imgAux.getGreen()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] ,2) / (2.0*sigmaR*sigmaR)));
                            sumG += (double) imgAux.getGreen()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] * wG;
                            totG += wG;
                            double wB = Math.exp( - (double)((k*k) + (l*l)) / (2.0*sigmaS*sigmaS) - ( Math.pow(imgAux.getBlue()[i][j] -
                                    imgAux.getBlue()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] ,2) / (2.0*sigmaR*sigmaR)));
                            sumB += (double) imgAux.getBlue()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] * wB;
                            totB += wB;
                        }
                    }
                }
                if(greyscale) {
                    red[i][j] = (int)(sumR/totR);
                } else {
                    red[i][j] = (int)(sumR/totR);
                    green[i][j] = (int)(sumG/totG);
                    blue[i][j] = (int)(sumB/totB);
                }
            }
        }
        if(greyscale){
            red = this.clamp(red);
        } else {
            red = this.clamp(red);
            green = this.clamp(green);
            blue = this.clamp(blue);
        }

        return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth())
            : new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageInt cannyAlgorithm(int n, double sigma){
        DecimalFormat df = new DecimalFormat("#.#");

        //1) Aplico gaussiana
        this.image = gaussFilter(n,sigma);

        //2) Derivo sobel en x e y
//        ImageInt gx = sobelOperator(false,false,false,true,false,false,false,false);
//        ImageInt gy = sobelOperator(false,false,true,false,false,false,false,false);
        double[][] wE = {{-1,0,1},
                        {-2,0,2},
                        {-1,0,1}};
        double[][] wS = rotate(rotate(wE));

        ImageInt gx = filter(wE,false,false);
        ImageInt gy = filter(wS,false,false);

        //3) Angulo de gradiente
        double[][] directionsR = new double[gx.getHeight()][gx.getWidth()];
        double[][] GR = new double[gx.getHeight()][gx.getWidth()];
        double[][] directionsG = new double[gx.getHeight()][gx.getWidth()];
        double[][] GG = new double[gx.getHeight()][gx.getWidth()];
        double[][] directionsB = new double[gx.getHeight()][gx.getWidth()];
        double[][] GB = new double[gx.getHeight()][gx.getWidth()];
        double piRad = 180/Math.PI;
        for (int i = 0; i < gx.getHeight(); i++) {
            for (int j = 0; j < gx.getWidth(); j++) {
                if(greyscale){
                    directionsR[i][j] = Math.atan2(((ImageGrey)gy).getImage()[i][j],((ImageGrey)gx).getImage()[i][j]) * piRad;
                    if (directionsR[i][j] < 0) {
                        directionsR[i][j] += 360.;
                    }
                    GR[i][j] = Math.hypot(((ImageGrey)gx).getImage()[i][j],((ImageGrey)gy).getImage()[i][j]);
                }else{
                    directionsR[i][j] = Math.atan2(((ImageColor)gy).getRed()[i][j], ((ImageColor)gx).getRed()[i][j]) * piRad;
                    GR[i][j] = Math.hypot(((ImageColor)gx).getRed()[i][j],((ImageColor)gy).getRed()[i][j]);

                    directionsG[i][j] = Math.atan2(((ImageColor)gy).getGreen()[i][j], ((ImageColor)gx).getGreen()[i][j]) * piRad;
                    GG[i][j] = Math.hypot(((ImageColor)gx).getGreen()[i][j],((ImageColor)gy).getGreen()[i][j]);

                    directionsB[i][j] = Math.atan2(((ImageColor)gy).getBlue()[i][j], ((ImageColor)gx).getBlue()[i][j]) * piRad;
                    GB[i][j] = Math.hypot(((ImageColor)gx).getBlue()[i][j],((ImageColor)gy).getBlue()[i][j]);
                }
            }
        }

        //4) Supresion no maximos
        List<Integer[][]> rets = new ArrayList<>();
        rets.add(nonMaximumSuppression(GR,directionsR));
        if(!greyscale){
            rets.add(nonMaximumSuppression(GG,directionsG));
            rets.add(nonMaximumSuppression(GB,directionsB));
        }
        return greyscale ? new ImageGrey(clamp(rets.get(0)), image.getHeight(), image.getWidth())
                : new ImageColor(clamp(rets.get(0)),clamp(rets.get(1)), clamp(rets.get(2)), image.getHeight(), image.getWidth());
    }

    private Integer[][] nonMaximumSuppression(double[][] G, double[][] directions){
        Integer[][] ret = new Integer[G.length][G[0].length];
        for (int i = 0; i < G.length; i++) {
            ret[i][0] = 0;
            ret[i][G[0].length-1] = 0;
        }
        for (int j = 0; j < G[0].length; j++) {
            ret[0][j] = 0;
            ret[G.length-1][j] = 0;
        }
        int ver = 0;
        int hor = 0;
        int ne = 0;
        int nw = 0;
        int menos = 0;
        int mas = 0;
        double minDir = 360;
        double maxDir = -360;

        for (int i = 1; i < G.length-1; i++) {
            for (int j = 1; j < G[0].length-1; j++) {
                double direction = directions[i][j];
                double current = G[i][j];
                double prev = 0;
                double next = 0;
                minDir = Math.min(direction,minDir);
                maxDir = Math.max(direction,maxDir);
                if (direction <= 22.5 || (direction >= 157.5 && direction <= 202.5) || direction >= 337.5) {
                    prev = G[i][j-1];
                    next = G[i][j+1];
                    hor++;
                } else if ((direction >= 22.5 && direction <= 67.5) || (direction >= 202.5 && direction <= 247.5)) {
                    prev = G[i-1][j+1];
                    next = G[i+1][j-1];
                    ne++;
                } else if ((direction >= 67.5 && direction <= 112.5) || (direction >= 247.5 && direction <= 292.5)) {
                    prev = G[i-1][j];
                    next = G[i+1][j];
                    ver++;
                } else {
                    prev = G[i-1][j-1];
                    next = G[i+1][j+1];
                    nw++;
                }

                if (prev > current || next > current) {
                    ret[i][j] = 0;
                }else{
                    ret[i][j] = (int)(current);
                }
            }
        }
        System.out.println("HOR: " + hor);
        System.out.println("NE: " + ne);
        System.out.println("VER: " + ver);
        System.out.println("NW: " + nw);
        System.out.println("Menos: " + menos);
        System.out.println("Mas: " + mas);
        System.out.println("MinDir: " + minDir);
        System.out.println("MaxDir: " + maxDir);
        System.out.println();
        return ret;
    }

    public ImageInt hysteresisThreshold(int t1, int t2, boolean eightConnected) {
        List<Integer[][]> images = new ArrayList<>();
        List<Integer[][]> rets = new ArrayList<>();
        if(greyscale){
            images.add(((ImageGrey)this.image).getImage());
        }else{
            images.add(((ImageColor)this.image).getRed());
            images.add(((ImageColor)this.image).getGreen());
            images.add(((ImageColor)this.image).getBlue());
        }

        int tmin =  Math.min(t1,t2);
        int tmax =  Math.max(t1,t2);

        for (Integer[][] image: images) {
            Integer[][] firstPass = new Integer[image.length][image[0].length];
            for (int i = 0; i < image.length; i++) {
                for (int j = 0; j < image[0].length; j++) {
                    if(image[i][j] < tmin){
                        firstPass[i][j] = 0;
                    } else if(image[i][j] > tmax){
                        firstPass[i][j] = 255;
                    } else {
                        firstPass[i][j] = image[i][j];
                    }
                }

            }
            Integer[][] ret = new Integer[image.length][image[0].length];
            for (int i = 0; i < image.length; i++) {
                for (int j = 0; j < image[0].length; j++) {
                    if(image[i][j] <= tmin){
                        ret[i][j] = 0;
                    } else if(image[i][j] > tmax){
                        ret[i][j] = 255;
//                        ret[i][j] = image[i][j];
                    } else {
                        boolean borderUp = i > 0
                                && firstPass[i-1][j] == 255;
                        boolean borderLeft = j > 0
                                && firstPass[i][j-1] == 255;
                        boolean borderDown = i < image.length - 1
                                && firstPass[i+1][j] == 255;
                        boolean borderRight = j < image[0].length - 1
                                && firstPass[i][j+1] == 255;
                        boolean borderLUp = i > 0 && j > 0
                                && firstPass[i-1][j-1] == 255;
                        boolean borderRUp = i > 0 && j < image[0].length - 1
                                && firstPass[i-1][j+1] == 255;
                        boolean borderLDown = i < image.length - 1 && j > 0
                                && firstPass[i+1][j-1] == 255;
                        boolean borderRDown = i < image.length - 1 && j < image[0].length - 1
                                && firstPass[i+1][j+1] == 255;
//                        if (borderUp || borderLeft || borderDown|| borderRight || borderLUp || borderRUp || borderLDown || borderRDown) {
                        if (borderUp || borderLeft || borderDown|| borderRight || (eightConnected && (borderLUp || borderRUp || borderLDown || borderRDown))) {
//                            ret[i][j] = image[i][j];
                            ret[i][j] = 255;
                        } else {
                            ret[i][j] = 0;
                        }
                    }
                }

            }
            rets.add(ret);
        }
        return greyscale ? new ImageGrey(rets.get(0), image.getHeight(), image.getWidth())
                : new ImageColor(rets.get(0), rets.get(1), rets.get(2), image.getHeight(), image.getWidth());    }

    public ImageInt susanDetector(int threshold, boolean borderDetection, boolean cornerDetection){
        List<Integer[][]> images = new ArrayList<>();
        List<Integer[][]> rets = new ArrayList<>();
        if(greyscale){
            images.add(((ImageGrey)this.image).getImage());
        }else{
            images.add(((ImageColor)this.image).getRed());
            images.add(((ImageColor)this.image).getGreen());
            images.add(((ImageColor)this.image).getBlue());
        }
        int[][] w =  {{0,0,1,1,1,0,0},
                {0,1,1,1,1,1,0},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1},
                {0,1,1,1,1,1,0},
                {0,0,1,1,1,0,0}};
        for (Integer[][] image: images) {
            Integer[][] ret = new Integer[this.image.getHeight()][this.image.getWidth()];
            for (int y = 0; y < this.image.getHeight(); y++) {
                for (int x = 0; x < this.image.getWidth(); x++) {
                    int n = 0;
                    int N = 0;
                    for (int i = -3; i < 4; i++) {
                        for (int j = -3; j < 4; j++) {
                            if(!(y+i<0 || x+j<0 || y+i>=this.image.getHeight() || x+j>=this.image.getWidth() || w[i+3][j+3]==0)){
                                N++;
                                if(Math.abs(image[y][x]-image[y+i][x+j])<threshold){
                                    n++;
                                }
                            }
                        }
                    }
                    if(N == 0){
                        ret[y][x] = 0;
                        continue;
                    }
                    double s = 1-(n/ (double)N);
                    if((borderDetection && s>0.375 && s<0.625) ||
                            (cornerDetection && s>0.625 && s<0.875 )){
                        ret[y][x] = 255;
                    }else{
                        ret[y][x] = 0;
                    }
                }
            }
            rets.add(ret);
        }
        return greyscale ? new ImageGrey(rets.get(0), image.getHeight(), image.getWidth())
                : new ImageColor(rets.get(0), rets.get(1), rets.get(2), image.getHeight(), image.getWidth());
    }

    public ImageInt houghLineDetector(double e){
//        1)Find borders
//        this.image = cannyAlgorithm(5,1.4);
//        this.image = this.sobelOperator(false,false,true,true,false,false,false,false);
        this.image = cannyAlgorithm(7,0.1);
        this.image = hysteresisThreshold(5,10,false);

//        2)Binary image
        List<Integer[][]> thresholdeds = new ArrayList<>();
        if(greyscale){
            thresholdeds.add( globalThresholdization().getImage());
        }else{
            ImageColor g = globalThresholdizationColor();
            thresholdeds.add(g.getRed());
            thresholdeds.add(g.getGreen());
            thresholdeds.add(g.getBlue());
        }

//        3)Matriz acumuladora
        int D = Math.max(this.image.getWidth(),this.image.getHeight());
        int thetaMin = -90;
        int thetaMax = 90;
        int thetaSize = thetaMax-thetaMin;
        int pMin = (int) (-Math.sqrt(2)*D);
        int pMax = (int) (Math.sqrt(2)*D);
        int pSize = pMax-pMin;

        int[][][] A;
        if(greyscale)
            A = new int[1][pSize][thetaSize];
        else
            A = new int[3][pSize][thetaSize];

        double[] sinTable = new double[thetaSize];
        double[] cosTable = new double[thetaSize];
        for (int theta = 0; theta < thetaSize; theta++) {
            double thetaRadians = (thetaMin+theta) * Math.PI / thetaSize;
            sinTable[theta] = Math.sin(thetaRadians);
            cosTable[theta] = Math.cos(thetaRadians);
        }


//        4)Para cada pixel blanco que cumple la normal de la recta, acumulador++
        for (int t = 0; t < thresholdeds.size(); t++) {
            for (int i = 0; i < this.image.getHeight(); i++) {
                for (int j = 0; j < this.image.getWidth(); j++) {
                    if (thresholdeds.get(t)[i][j] != 0) {
                        for (int theta = 0; theta < thetaSize; theta++) {
                            double r = cosTable[theta] * j + sinTable[theta] * i;
//                        int p = (int)Math.round(r * pMax / pSize) + pMax;
//                        A[p][theta]++;
                            for (int p = 0; p < pSize; p++) {
                                if (Math.abs((pMin+p) - r) < e) {
                                    A[t][p][theta]++;
                                }
                            }
                        }
                    }
                }
            }
        }


//        5) Ordeno las coordenadas en una lista
        List<List<HoughAcumulatorElement>> elementss = new ArrayList<>();
        for (int t = 0; t < A.length; t++) {
            List<HoughAcumulatorElement> elements = new ArrayList<>();
            for (int p = 0; p < pSize; p++) {
                for (int theta = 0; theta < thetaSize; theta++) {
                    elements.add(new HoughAcumulatorElement(new int[]{ p, theta}, A[t][p][theta]));
                }
            }
            Collections.sort(elements);
            elementss.add(elements);
        }

        List<Integer[][]> rets = new ArrayList<>();
        for (List<HoughAcumulatorElement> elements: elementss){
            Integer[][] newImage = new Integer[image.getHeight()][image.getWidth()];
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    newImage[i][j] = 0;
                }
            }
            int maxVotes = elements.get(0).votes;
            if (maxVotes > 1) {
                for (HoughAcumulatorElement elem : elements) {
                    if (elem.votes < maxVotes/2)
                        break;
                    int pi = pMin + elem.coordinates[0];
                    int theta = elem.coordinates[1];
                    for (int i = 0; i < image.getHeight(); i++) {
                        for (int j = 0; j < image.getWidth(); j++) {
                            if (Math.abs(pi - (cosTable[theta]*j + sinTable[theta]*i)) < e) {
                                newImage[i][j] = 255;
                            }
                        }
                    }

                }
            }
            rets.add(newImage);
        }
        return greyscale ? new ImageGrey(rets.get(0), image.getHeight(), image.getWidth())
                : new ImageColor(rets.get(0), rets.get(1), rets.get(2), image.getHeight(), image.getWidth());
    }

    public ImageInt houghCircleDetector(double e){
//        1)Find borders
//        this.image = cannyAlgorithm(5,1.4);
//        this.image = hysteresisThreshold(50,200);
        this.image = this.sobelOperator(false,false,true,true,false,false,false,false);

//        2)Binary image
        Integer[][] thresholded = globalThresholdization().getImage();

//        3)Matriz acumuladora
        int D = Math.max(this.image.getWidth(),this.image.getHeight());
        int cxMax = this.image.getHeight();
        int cyMax = this.image.getWidth();
        int rMax = 40;
        int[][][] A = new int[cyMax][cxMax][rMax];


//        4)Para cada pixel blanco que cumple la normal de la recta, acumulador++
        for (int i = 0; i < this.image.getHeight(); i++) {
            for (int j = 0; j < this.image.getWidth(); j++) {
                if (thresholded[i][j] != 0) {
                    for (int cy = 0; cy < cyMax; cy++) {
                        double cyPow = Math.pow(i - cy, 2);
                        for (int cx = 0; cx < cxMax; cx++) {
                            double cxPow = Math.pow(j - cx, 2);
                            for (int r = 0; r < rMax; r++) {
                                if (Math.abs(Math.pow(r,2) - (cyPow+cxPow)) < e) {
                                    A[cy][cx][r]++;
                                }
                            }
                        }
                    }
                }
            }
        }
//        5)
        List<HoughAcumulatorElement> elements = new ArrayList<>();
        for (int cy = 0; cy < cyMax; cy++) {
            for (int cx = 0; cx < cxMax; cx++) {
                for (int r = 0; r < rMax; r++) {
                    elements.add(new HoughAcumulatorElement(new int[]{cy, cx, r}, A[cy][cx][r]));
                }
            }
        }
        Collections.sort(elements);


        Integer[][] newImage = new Integer[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                newImage[i][j] = 0;
            }
        }
        int maxVotes = elements.get(0).votes;
        int votesCutOff = 5*maxVotes/6;
        if (maxVotes > 1) {
            for (HoughAcumulatorElement b : elements) {
                if (b.votes < votesCutOff)
                    break;
                double cy = b.coordinates[0];
                double cx = b.coordinates[1];
                double r = b.coordinates[2];
                for (int i = 0; i < this.image.getHeight(); i++) {
                    for (int j = 0; j < this.image.getWidth(); j++) {
                        double aTerm = Math.pow(j - cx, 2);
                        double bTerm = Math.pow(i - cy, 2);
                        double rTerm = Math.pow(r, 2);
                        if (Math.abs(rTerm - (aTerm+bTerm)) < e) {
                            newImage[i][j] = 255;
                        }
                    }
                }
            }
        }
        return new ImageGrey(newImage,image.getHeight(),image.getWidth());

    }

    private double[][] rotate(double[][] w) {
        double[][] rot = new double[3][3];

        rot[0][0] = w[1][0];
        rot[0][1] = w[0][0];
        rot[0][2] = w[0][1];

        rot[1][0] = w[2][0];
        rot[1][1] = w[1][1];
        rot[1][2] = w[0][2];

        rot[2][0] = w[2][1];
        rot[2][1] = w[2][2];
        rot[2][2] = w[1][2];

        return rot;
    }

    public ImageInt prewittOperator(boolean n, boolean w, boolean s, boolean e, boolean nw, boolean ne, boolean sw, boolean se) {
        double[][] wS = new double[3][3];
//        double[][] w2 = new double[3][3];
        ArrayList<ImageInt> imgs = new ArrayList<>();

//        int half = (int) Math.floor(n/2);
        wS[0][0] = wS[0][1] = wS[0][2] = -1.0; //-1 -1 -1
        wS[1][0] = wS[1][1] = wS[1][2] = 0.0;  // 0  0  0
        wS[2][0] = wS[2][1] = wS[2][2] = 1.0;  // 1  1  1

        return multiFilterPre(wS,n,w,s,e,nw,ne,sw,se);
    }

    public ImageInt sobelOperator(boolean n, boolean w, boolean s, boolean e, boolean nw, boolean ne, boolean sw, boolean se) {
        double[][] wS = new double[3][3];
//        ArrayList<ImageInt> imgs = new ArrayList<>();
//        int half = (int) Math.floor(n/2);
        wS[0][0] = wS[0][2] = -1.0;                 // -1  -2  -1
        wS[1][0] = wS[1][1] = wS[1][2] = 0.0;       //  0   0   0
        wS[2][0] = wS[2][2] = 1.0;                  //  1   2   1
        wS[0][1] = -2.0;
        wS[2][1] = 2.0;

        return multiFilterPre(wS,n,w,s,e,nw,ne,sw,se);
    }

    public ImageInt kirshOperator(boolean n, boolean w, boolean s, boolean e, boolean nw, boolean ne, boolean sw, boolean se) {
        double[][] wS = new double[3][3];
        ArrayList<ImageInt> imgs = new ArrayList<>();
//        int half = (int) Math.floor(n/2);
        wS[0][0] = wS[0][1] = wS[0][2] = -3.0;  // -3  -3  -3
        wS[1][0] = wS[1][2] = -3.0;             // -3   0  -3
        wS[2][0] = wS[2][1] = wS[2][2] = 5.0;   //  5   5   5
        wS[1][1] = 0.0;

        return multiFilterPre(wS,n,w,s,e,nw,ne,sw,se);
    }

    public ImageInt mask5aOperator(boolean n, boolean w, boolean s, boolean e, boolean nw, boolean ne, boolean sw, boolean se) {
        double[][] wS = new double[3][3];
//        int half = (int) Math.floor(n/2);
        wS[0][0] = wS[0][1] = wS[0][2] = -1.0;  // -1  -1  -1
        wS[1][0] = wS[1][2] = 1.0;              //  1  -2   1
        wS[2][0] = wS[2][1] = wS[2][2] = 1.0;   //  1   1   1
        wS[1][1] = -2.0;

        return multiFilterPre(wS,n,w,s,e,nw,ne,sw,se);
    }

    public ImageInt multiFilterPre(double[][] wS, boolean n, boolean w, boolean s, boolean e, boolean nw, boolean ne, boolean sw, boolean se) {
        ArrayList<ImageInt> imgs = new ArrayList<>();
        double[][] wSW = rotate(wS);
        double[][] wW = rotate(wSW);
        double[][] wNW = rotate(wW);
        double[][] wN = rotate(wNW);
        double[][] wNE = rotate(wN);
        double[][] wE = rotate(wNE);
        double[][] wSE = rotate(wE);

        if(s){
            imgs.add(filter(wS, false, false));
        }
        if(sw){
            imgs.add(filter(wSW, false, false));
        }
        if(w){
            imgs.add(filter(wW, false, false));
        }
        if(nw){
            imgs.add(filter(wNW, false, false));
        }
        if(n){
            imgs.add(filter(wN, false, false));
        }
        if(ne){
            imgs.add(filter(wNE, false, false));
        }
        if(e){
            imgs.add(filter(wE, false, false));
        }
        if(se){
            imgs.add(filter(wSE, false, false));
        }

        return imgs.size() > 0 ? multiFilter(imgs.toArray()) : image;
    }

    public ImageInt multiFilter(Object[] imgs) {
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                for(Object img : imgs){
                    if(red[i][j] == null) {
                        red[i][j] = 0;
                    }
                    if(green[i][j] == null) {
                        green[i][j] = 0;
                    }
                    if(blue[i][j] == null) {
                        blue[i][j] = 0;
                    }
                    if(greyscale) {
                        red[i][j] += (int) Math.pow(((ImageGrey)img).getImage()[i][j], 2);
                    } else {
                        red[i][j] += (int) Math.pow(((ImageColor)img).getRed()[i][j], 2);
                        green[i][j] += (int) Math.pow(((ImageColor)img).getGreen()[i][j], 2);
                        blue[i][j] += (int) Math.pow(((ImageColor)img).getBlue()[i][j], 2);
                    }
                }
            }
        }

        if(greyscale){
            red = Arrays.stream(red).map(a -> Arrays.stream(a).map(e -> (int) Math.floor(Math.sqrt(e))).toArray(Integer[]::new)).toArray(Integer[][]::new);
            red = this.clamp(red);
        } else {
            red = Arrays.stream(red).map(a -> Arrays.stream(a).map(e -> (int) Math.floor(Math.sqrt(e))).toArray(Integer[]::new)).toArray(Integer[][]::new);
            red = this.clamp(red);
            green = Arrays.stream(green).map(a -> Arrays.stream(a).map(e -> (int) Math.floor(Math.sqrt(e))).toArray(Integer[]::new)).toArray(Integer[][]::new);
            green = this.clamp(green);
            blue = Arrays.stream(blue).map(a -> Arrays.stream(a).map(e -> (int) Math.floor(Math.sqrt(e))).toArray(Integer[]::new)).toArray(Integer[][]::new);
            blue = this.clamp(blue);
        }

        return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth())
                : new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    private ImageInt filter(double[][] w, boolean median, boolean clamp) {
        int n = w.length;
        int half = (int) Math.floor(n/2);

        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                double sum = 0;
                double sum2 = 0;
                double sum3 = 0;
                ArrayList<Integer> med = null;
                ArrayList<Integer> med2 = null;
                ArrayList<Integer> med3 = null;
                if(median){
                    med = new ArrayList<>();
                    med2 = new ArrayList<>();
                    med3 = new ArrayList<>();
                }
                for (int k = -half; k < n - half; k++) {
                    for (int l = -half; l < n - half; l++) {
                        if(median){
                            for (int m = 0; m < w[k + half][l + half]; m++) {
                                if(greyscale){
                                    med.add(((ImageGrey)image).getImage()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())]);
                                } else {
                                    med.add(((ImageColor)image).getRed()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())]);
                                    med2.add(((ImageColor)image).getGreen()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())]);
                                    med3.add(((ImageColor)image).getBlue()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())]);
                                }
                            }
                        } else {
                            if(greyscale) {
                                sum += (double) ((ImageGrey) image).getImage()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] * w[k + half][l + half];
                            } else {
                                sum += (double) ((ImageColor) image).getRed()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] * w[k + half][l + half];
                                sum2 += (double) ((ImageColor) image).getGreen()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] * w[k + half][l + half];
                                sum3 += (double) ((ImageColor) image).getBlue()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] * w[k + half][l + half];
                            }
                        }
                    }
                }
                if(median) {
                    if(greyscale){
                        med.sort(Integer::compareTo);
                        red[i][j] = med.get(med.size()/2);
                    } else {
                        med.sort(Integer::compareTo);
                        red[i][j] = med.get(med.size()/2);
                        med2.sort(Integer::compareTo);
                        green[i][j] = med2.get(med2.size()/2);
                        med3.sort(Integer::compareTo);
                        blue[i][j] = med3.get(med3.size()/2);
                    }
                } else {
                    if(greyscale) {
                        red[i][j] = (int) sum;
                    } else {
                        red[i][j] = (int) sum;
                        green[i][j] = (int) sum2;
                        blue[i][j] = (int) sum3;
                    }
                }

            }
        }
        if(!median && clamp){
            if(greyscale){
                red = this.clamp(red);
            } else {
                red = this.clamp(red);
                green = this.clamp(green);
                blue = this.clamp(blue);
            }
        }

        return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth(), clamp)
                : new ImageColor(red, green, blue, image.getHeight(), image.getWidth(), clamp);

    }

    //leclerc: e ^ (-(|I-i|^2)/sigma^2)
    //lorentz: 1 / (|I-i|^2/sigma^2 + 1)
    public ImageInt diffusion(double sigma, int tmax, boolean anisotropic, boolean lorentz) {
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] prevRed = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] prevGreen = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] prevBlue = new Integer[image.getHeight()][image.getWidth()];

        if(greyscale) {
            prevRed = ((ImageGrey)image).getImage();
        } else {
            prevRed = ((ImageColor)image).getRed();
            prevGreen = ((ImageColor)image).getGreen();
            prevBlue = ((ImageColor)image).getBlue();
        }
        int t = 0;
        while(t < tmax) {
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {

                        int dN = prevRed[Math.floorMod(i-1, image.getHeight())][j] - prevRed[i][j];
                        int dS = prevRed[Math.floorMod(i+1, image.getHeight())][j] - prevRed[i][j];
                        int dE = prevRed[i][Math.floorMod(j+1, image.getWidth())] - prevRed[i][j];
                        int dO = prevRed[i][Math.floorMod(j-1, image.getWidth())] - prevRed[i][j];
                        if(lorentz){
                            red[i][j] = (int) Math.floor(prevRed[i][j] + 0.25 * ((double)dN * (anisotropic ? loretnzOperator(dN, sigma) : 1.0) + (double)dS * (anisotropic ? loretnzOperator(dS, sigma) : 1.0)
                                    + (double)dE * (anisotropic ? loretnzOperator(dE, sigma) : 1.0) + (double)dO * (anisotropic ? loretnzOperator(dO, sigma) : 1.0)));
                        } else {
                            red[i][j] = (int) Math.floor(prevRed[i][j] + 0.25 * ((double)dN * (anisotropic ? lecrercOperator(dN, sigma) : 1.0) + (double)dS * (anisotropic ? lecrercOperator(dS, sigma) : 1.0)
                                    + (double)dE * (anisotropic ? lecrercOperator(dE, sigma) : 1.0) + (double)dO * (anisotropic ? lecrercOperator(dO, sigma) : 1.0)));
                        }

                        if(!greyscale){
                            dN = prevGreen[Math.floorMod(i-1, image.getHeight())][j] - prevGreen[i][j];
                            dS = prevGreen[Math.floorMod(i+1, image.getHeight())][j] - prevGreen[i][j];
                            dE = prevGreen[i][Math.floorMod(j+1, image.getWidth())] - prevGreen[i][j];
                            dO = prevGreen[i][Math.floorMod(j-1, image.getWidth())] - prevGreen[i][j];
                            if(lorentz){
                                green[i][j] = (int) Math.floor(prevGreen[i][j] + 0.25 * ((double)dN * (anisotropic ? loretnzOperator(dN, sigma) : 1.0) + (double)dS * (anisotropic ? loretnzOperator(dS, sigma) : 1.0)
                                        + (double)dE * (anisotropic ? loretnzOperator(dE, sigma) : 1.0) + (double)dO * (anisotropic ? loretnzOperator(dO, sigma) : 1.0)));
                            } else {
                                green[i][j] = (int) Math.floor(prevGreen[i][j] + 0.25 * ((double)dN * (anisotropic ? lecrercOperator(dN, sigma) : 1.0) + (double)dS * (anisotropic ? lecrercOperator(dS, sigma) : 1.0)
                                        + (double)dE * (anisotropic ? lecrercOperator(dE, sigma) : 1.0) + (double)dO * (anisotropic ? lecrercOperator(dO, sigma) : 1.0)));
                            }
                            dN = prevBlue[Math.floorMod(i-1, image.getHeight())][j] - prevBlue[i][j];
                            dS = prevBlue[Math.floorMod(i+1, image.getHeight())][j] - prevBlue[i][j];
                            dE = prevBlue[i][Math.floorMod(j+1, image.getWidth())] - prevBlue[i][j];
                            dO = prevBlue[i][Math.floorMod(j-1, image.getWidth())] - prevBlue[i][j];
                            if(lorentz){
                                blue[i][j] = (int) Math.floor(prevBlue[i][j] + 0.25 * ((double)dN * (anisotropic ? loretnzOperator(dN, sigma) : 1.0) + (double)dS * (anisotropic ? loretnzOperator(dS, sigma) : 1.0)
                                        + (double)dE * (anisotropic ? loretnzOperator(dE, sigma) : 1.0) + (double)dO * (anisotropic ? loretnzOperator(dO, sigma) : 1.0)));
                            } else {
                                blue[i][j] = (int) Math.floor(prevBlue[i][j] + 0.25 * ((double)dN * (anisotropic ? lecrercOperator(dN, sigma) : 1.0) + (double)dS * (anisotropic ? lecrercOperator(dS, sigma) : 1.0)
                                        + (double)dE * (anisotropic ? lecrercOperator(dE, sigma) : 1.0) + (double)dO * (anisotropic ? lecrercOperator(dO, sigma) : 1.0)));
                            }
                        }
                }
            }
            red = clampAlways(red);
            if(!greyscale) {
                green = clampAlways(green);
                blue = clampAlways(blue);
            }
            prevRed = red;
            if(!greyscale){
                prevGreen = green;
                prevBlue = blue;
            }
            t++;
        }

        return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth())
                : new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    //para color q los 3 colores sean coherentes con lo de theta0 - theta / theta1 - theta?
    public ImageInt activeContorns(double x, double y, int sqSize, int counter, boolean fromPrev) {
        Integer[][] red;
        Integer[][] green = null;
        Integer[][] blue = null;
        Integer[][] newRed = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] newGreen = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] newBlue = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] phi = new Integer[image.getHeight()][image.getWidth()];

        ArrayList<Point> lin = new ArrayList<>();
        ArrayList<Point> lout = new ArrayList<>();
        int pixelCount = image.getHeight() * image.getWidth();
        if(greyscale) {
            red = ((ImageGrey)image).getImage();
        } else {
            red = ((ImageColor)image).getRed();
            green = ((ImageColor)image).getGreen();
            blue = ((ImageColor)image).getBlue();
        }

        int half = sqSize/2;
        double rTheta0 =  0, rTheta1 = 0;
        double gTheta0 =  0, gTheta1 = 0;
        double bTheta0 =  0, bTheta1 = 0;

        int inCounter = 0;
        //paso inicial, setea los theta y phi inicial
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                newRed[i][j] = red[i][j];
                if(!greyscale){
                    newGreen[i][j] = green[i][j];
                    newBlue[i][j] = blue[i][j];
                }
                if(!fromPrev){
                    if(i >= y - half && i < y + half && j >= x - half && j < x + half){
                        phi[i][j] = -3;
                        rTheta1 += red[i][j];
                        if(!greyscale){
                            gTheta1 += green[i][j];
                            bTheta1 += blue[i][j];
                        }
                        inCounter++;
                    } else {
                        phi[i][j] = 3;
                        rTheta0 += red[i][j];
                        if(!greyscale){
                            gTheta0 += green[i][j];
                            bTheta0 += blue[i][j];
                        }
                    }
                    if(((i == y - half || i == y + half) && (j > x - half && j < x + half))
                            || ((j == x - half || j == x + half) && (i > y - half && i < y + half)) ){
                        lout.add(new Point(i, j));
                        phi[i][j] = 1;
                    } else if(((i == y - half + 1 || i == y + half - 1) && (j > x - half && j < x + half))
                            || ((j == x - half + 1 || j == x + half - 1) && (i > y - half + 1&& i < y + half - 1)) ){
                        lin.add(new Point(i, j));
                        phi[i][j] = -1;
                    }
                } else {
                    if(classPhi[i][j] < 0){
                        rTheta1 += red[i][j];
                        if(!greyscale){
                            gTheta1 += green[i][j];
                            bTheta1 += blue[i][j];
                        }
                        inCounter++;
                    } else {
                        rTheta0 += red[i][j];
                        if(!greyscale){
                            gTheta0 += green[i][j];
                            bTheta0 += blue[i][j];
                        }
                    }
                }
            }
        }
        if(fromPrev){
            phi = classPhi;
            lin = classLin;
            lout = classLout;
            rTheta0 /= pixelCount - inCounter;
            gTheta0 /= pixelCount - inCounter;
            bTheta0 /= pixelCount - inCounter;
            rTheta1 = thetaR;
            gTheta1 = thetaG;
            bTheta1 = thetaB;
        } else {
            rTheta0 /= pixelCount - (sqSize * sqSize);
            gTheta0 /= pixelCount - (sqSize * sqSize);
            bTheta0 /= pixelCount - (sqSize * sqSize);
            rTheta1 /= sqSize * sqSize;
            gTheta1 /= sqSize * sqSize;
            bTheta1 /= sqSize * sqSize;
        }
        //tengo lin, lout, thetas, phis
        while( counter-- > 0){
            //recorro lout
            ArrayList<Point> prevLout = new ArrayList<>(lout);
            int changes = 0;
            for (Point p : prevLout){
                if(greyscale){
                    if(Math.abs(rTheta0 - red[p.x][p.y]) > Math.abs(rTheta1 - red[p.x][p.y])) { //fd(x) > 0
                        lout.remove(p);
                        lin.add(p);
                        phi[p.x][p.y] = -1;
                        if (p.x + 1 < image.getHeight() && phi[p.x + 1][p.y] == 3) {
                            lout.add(new Point(p.x + 1, p.y));
                            phi[p.x + 1][p.y] = 1;
                            changes++;
                        }
                        if (p.x > 0 && phi[p.x - 1][p.y] == 3) {
                            lout.add(new Point(p.x - 1, p.y));
                            phi[p.x - 1][p.y] = 1;
                            changes++;
                        }
                        if (p.y + 1 < image.getWidth() && phi[p.x][p.y + 1] == 3) {
                            lout.add(new Point(p.x, p.y + 1));
                            phi[p.x][p.y + 1] = 1;
                            changes++;
                        }
                        if (p.y > 0 && phi[p.x][p.y - 1] == 3) {
                            lout.add(new Point(p.x, p.y - 1));
                            phi[p.x][p.y - 1] = 1;
                            changes++;
                        }
                    }
                } else {
                    if(/*Math.abs(rTheta0 - red[p.x][p.y]) > Math.abs(rTheta1 - red[p.x][p.y]) &&
                            Math.abs(gTheta0 - green[p.x][p.y]) > Math.abs(gTheta1 - green[p.x][p.y]) &&
                            Math.abs(bTheta0 - blue[p.x][p.y]) > Math.abs(bTheta1 - blue[p.x][p.y])*/
                            Math.sqrt(Math.pow(rTheta0 - red[p.x][p.y],2) + Math.pow(gTheta0 - green[p.x][p.y],2) + Math.pow(bTheta0 - blue[p.x][p.y],2)) >
                                    Math.sqrt(Math.pow(rTheta1 - red[p.x][p.y],2) + Math.pow(gTheta1 - green[p.x][p.y],2) + Math.pow(bTheta1 - blue[p.x][p.y],2))) { //fd(x) > 0
                        lout.remove(p);
                        lin.add(p);
                        phi[p.x][p.y] = -1;
                        if (p.x + 1 < image.getHeight() && phi[p.x + 1][p.y] == 3) {
                            lout.add(new Point(p.x + 1, p.y));
                            phi[p.x + 1][p.y] = 1;
                            changes++;
                        }
                        if (p.x > 0 && phi[p.x - 1][p.y] == 3) {
                            lout.add(new Point(p.x - 1, p.y));
                            phi[p.x - 1][p.y] = 1;
                            changes++;
                        }
                        if (p.y + 1 < image.getWidth() && phi[p.x][p.y + 1] == 3) {
                            lout.add(new Point(p.x, p.y + 1));
                            phi[p.x][p.y + 1] = 1;
                            changes++;
                        }
                        if (p.y > 0 && phi[p.x][p.y - 1] == 3) {
                            lout.add(new Point(p.x, p.y - 1));
                            phi[p.x][p.y - 1] = 1;
                            changes++;
                        }
                    }
                }
            }
            if(changes == 0){
                break;
            }

            //recorro lin
            ArrayList<Point> prevLin = new ArrayList<>(lin);
            for (Point p : prevLin) {
                if(   !((p.x + 1 < image.getHeight() && phi[p.x + 1][p.y] > 0) ||
                        (p.x > 0 && phi[p.x - 1][p.y] > 0) ||
                        (p.y + 1 < image.getWidth() && phi[p.x][p.y + 1] > 0) ||
                        (p.y > 0 && phi[p.x][p.y - 1] > 0))) { //si ningun vecino es >0 => es interior
                    lin.remove(p);
                    phi[p.x][p.y] = -3;
                }
            }

            //recorro lin
            prevLin = new ArrayList<>(lin);
            for (Point p : prevLin) {
                if(greyscale){
                    if(Math.abs(rTheta0 - red[p.x][p.y]) < Math.abs(rTheta1 - red[p.x][p.y])) { //fd(x) < 0
                        lin.remove(p);
                        lout.add(p);
                        phi[p.x][p.y] = 1;
                        if(p.x + 1 < image.getHeight() && phi[p.x + 1][p.y] == -3){
                            lin.add(new Point(p.x + 1, p.y));
                            phi[p.x + 1][p.y] = -1;
                        }
                        if(p.x > 0 && phi[p.x - 1][p.y] == -3){
                            lin.add(new Point(p.x - 1, p.y));
                            phi[p.x - 1][p.y] = -1;
                        }
                        if(p.y + 1 < image.getWidth() && phi[p.x][p.y + 1] == -3){
                            lin.add(new Point(p.x, p.y + 1));
                            phi[p.x][p.y + 1] = -1;
                        }
                        if(p.y > 0 && phi[p.x][p.y - 1] == -3){
                            lin.add(new Point(p.x, p.y - 1));
                            phi[p.x][p.y - 1] = -1;
                        }
                    }
                } else {
                    if(/*Math.abs(rTheta0 - red[p.x][p.y]) + Math.abs(gTheta0 - green[p.x][p.y]) + Math.abs(bTheta0 - blue[p.x][p.y]) <
                            Math.abs(rTheta1 - red[p.x][p.y]) + Math.abs(gTheta1 - green[p.x][p.y]) + Math.abs(bTheta1 - blue[p.x][p.y])*/
                            Math.sqrt(Math.pow(rTheta0 - red[p.x][p.y],2) + Math.pow(gTheta0 - green[p.x][p.y],2) + Math.pow(bTheta0 - blue[p.x][p.y],2)) <
                                    Math.sqrt(Math.pow(rTheta1 - red[p.x][p.y],2) + Math.pow(gTheta1 - green[p.x][p.y],2) + Math.pow(bTheta1 - blue[p.x][p.y],2))) { //fd(x) < 0
                        lin.remove(p);
                        lout.add(p);
                        phi[p.x][p.y] = 1;
                        if(p.x + 1 < image.getHeight() && phi[p.x + 1][p.y] == -3){
                            lin.add(new Point(p.x + 1, p.y));
                            phi[p.x + 1][p.y] = -1;
                        }
                        if(p.x > 0 && phi[p.x - 1][p.y] == -3){
                            lin.add(new Point(p.x - 1, p.y));
                            phi[p.x - 1][p.y] = -1;
                        }
                        if(p.y + 1 < image.getWidth() && phi[p.x][p.y + 1] == -3){
                            lin.add(new Point(p.x, p.y + 1));
                            phi[p.x][p.y + 1] = -1;
                        }
                        if(p.y > 0 && phi[p.x][p.y - 1] == -3){
                            lin.add(new Point(p.x, p.y - 1));
                            phi[p.x][p.y - 1] = -1;
                        }
                    }
                }
            }

            //recorro lout
            prevLout= new ArrayList<>(lout);
            for (Point p : prevLout) {
                if(   !((p.x + 1 < image.getHeight() && phi[p.x + 1][p.y] < 0) ||
                        (p.x > 0 && phi[p.x - 1][p.y] < 0) ||
                        (p.y + 1 < image.getWidth() && phi[p.x][p.y + 1] < 0) ||
                        (p.y > 0 && phi[p.x][p.y - 1] < 0))) { //si ningun vecino es <0 => es exterior
                    lout.remove(p);
                    phi[p.x][p.y] = 3;
                }
            }

            //recalc thetas
//            rTheta0 =  0; rTheta1 = 0;
//            gTheta0 =  0; gTheta1 = 0;
//            bTheta0 =  0; bTheta1 = 0;
//            int inCounter = 0;
//            for (int i = 0; i < image.getHeight(); i++) {
//                for (int j = 0; j < image.getWidth(); j++) {
//                    if(phi[i][j] < 0){
//                        rTheta1 += red[i][j];
//                        if(!greyscale){
//                            gTheta1 += green[i][j];
//                            bTheta1 += blue[i][j];
//                        }
//                        inCounter++;
//                    } else {
//                        rTheta0 += red[i][j];
//                        if(!greyscale){
//                            gTheta0 += green[i][j];
//                            bTheta0 += blue[i][j];
//                        }
//                    }
//                }
//            }
//            rTheta0 /= pixelCount  - inCounter;
//            rTheta1 /= inCounter;
//            gTheta0 /= pixelCount  - inCounter;
//            gTheta1 /= inCounter;
//            bTheta0 /= pixelCount  - inCounter;
//            bTheta1 /= inCounter;
        }

        classLin = (ArrayList<Point>) lin.clone();
        classLout = lout;
        classPhi = phi;
        thetaR = rTheta1;
        thetaG = gTheta1;
        thetaB = bTheta1;
        lin.addAll(lout);
        for (Point p : lin) {
            newRed[p.x][p.y] = 255;
            if(!greyscale){
                newGreen[p.x][p.y] = 255;
                newBlue[p.x][p.y] = 255;
            }
        }
        return greyscale ? new ImageGrey(newRed, image.getHeight(), image.getWidth())
                : new ImageColor(newRed, newGreen, newBlue, image.getHeight(), image.getWidth());
    }

    public ImageGrey harrisMethod(int threshold) {
        double[][] wE = {{-1,0,1},
                {-2,0,2},
                {-1,0,1}};
        double[][] wS = rotate(rotate(wE));
        ImageGrey aux = (ImageGrey) this.image;
        ImageGrey gx = (ImageGrey) filter(wE,false,false);
        ImageGrey gy = (ImageGrey) filter(wS,false,false);

        Integer[][] gx2 = Arrays.stream(gx.getImage()).parallel().map(a-> Arrays.stream(a).parallel().map(p->p*p).toArray(Integer[]::new)).toArray(Integer[][]::new);
        this.image = new ImageGrey(gx2, image.getHeight(), image.getWidth(), false);
        gx2 = ((ImageGrey) gaussFilter(7, 2)).getImage();
        Integer[][] gy2 = Arrays.stream(gy.getImage()).parallel().map(a-> Arrays.stream(a).parallel().map(p->p*p).toArray(Integer[]::new)).toArray(Integer[][]::new);
        this.image = new ImageGrey(gy2, image.getHeight(), image.getWidth(), false);
        gy2 = ((ImageGrey) gaussFilter(7, 2)).getImage();

        Integer[][] gxy = new Integer[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                gxy[i][j] = gx.getImage()[i][j] * gy.getImage()[i][j];
            }
        }
        this.image = new ImageGrey(gxy, image.getHeight(), image.getWidth(), false);
        gxy = ((ImageGrey) gaussFilter(7, 2)).getImage();

        this.image = aux;

        Integer[][] cim = new Integer[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
//                cim[i][j] = (int) Math.floor((gx2[i][j]*gy2[i][j] - gxy[i][j]*gxy[i][j]) - 0.04*Math.pow(gx2[i][j] + gy2[i][j], 2)) /*> threshold? 255: 0*/;
                cim[i][j] = (int) Math.abs(Math.floor((gx2[i][j]*gy2[i][j] - Math.pow(gxy[i][j],4)) - 0.04*Math.pow(gx2[i][j] + gy2[i][j],2)));

            }

        }
        cim = clamp(cim);
//        cim = clamp(cim);
        return new ImageGrey(cim, image.getHeight(), image.getWidth());
    }

    private double loretnzOperator(double x, double sigma) {
        return 1.0 / ((x * x) / (sigma * sigma) + 1.0);
    }

    private double lecrercOperator(double x, double sigma) {
        return Math.exp(-(x * x)/(sigma * sigma));
    }

    public void setImage(ImageInt image) {
        this.image = image;
    }

    private class Point {
        private int x;
        private int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private class HoughAcumulatorElement implements Comparable<HoughAcumulatorElement>  {
        public int[] coordinates;
        public int votes;

        public HoughAcumulatorElement(int votes, int... coordinates) {
            this.coordinates = coordinates;
            this.votes = votes;
        }

        public HoughAcumulatorElement(int[] coordinates, int votes) {
            this.coordinates = coordinates;
            this.votes = votes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HoughAcumulatorElement that = (HoughAcumulatorElement) o;
            return votes == that.votes &&
                    Arrays.equals(coordinates, that.coordinates);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(votes);
            result = 31 * result + Arrays.hashCode(coordinates);
            return result;
        }

        @Override
        public int compareTo(HoughAcumulatorElement obj) {
            return obj.votes - votes;
        }
    }
}
