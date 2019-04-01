package tp1;

import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;

public class Functions {
    public ImageInt image;
    private boolean greyscale;

    public Functions(ImageInt image) {
        this.image = image;
        if(image instanceof ImageGrey)
            this.greyscale = true;
        else{
            this.greyscale = false;
        }
    }


    public ImageInt imageSum(ImageInt addend) {
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];
//        Integer[][] greySum = new Integer[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(greyscale){
                    red[i][j] = ((ImageGrey) image).getImage()[i][j] + ((ImageGrey) addend).getImage()[i][j];
                } else {
                    red[i][j] = ((ImageColor) image).getRed()[i][j] + ((ImageColor) addend).getRed()[i][j];
                    green[i][j] = ((ImageColor) image).getGreen()[i][j] + ((ImageColor) addend).getGreen()[i][j];
                    blue[i][j] = ((ImageColor) image).getBlue()[i][j] + ((ImageColor) addend).getBlue()[i][j];
                }
            }
        }
//        clamp
        return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth())
                : new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageInt imageSub(ImageInt substract) {
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (greyscale) {
                    red[i][j] = ((ImageGrey)image).getImage()[i][j] - ((ImageGrey)substract).getImage()[i][j];
                } else {
                    red[i][j] = ((ImageColor)image).getRed()[i][j] - ((ImageColor)substract).getRed()[i][j];
                    green[i][j] = ((ImageColor)image).getGreen()[i][j] - ((ImageColor)substract).getGreen()[i][j];
                    blue[i][j] = ((ImageColor)image).getBlue()[i][j] - ((ImageColor)substract).getBlue()[i][j];
                }
            }
        }
//        clamp
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
//        clamp
        return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth())
                : new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageInt rangeCompressor() {
        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];
//        double multiplier = 255.0 / Math.log(1 + image.getMaxColor());
        int maxRed = 0;
        if(greyscale){
            maxRed = ((ImageGrey)image).getMaxGrey();
        } else {
            maxRed = ((ImageColor)image).getMaxRed();
        }
        int maxGreen = ((ImageColor)image).getMaxGreen();
        int maxBlue = ((ImageColor)image).getMaxBlue();
        double multiplierR = 255.0 / Math.log(1 + maxRed);
        double multiplierG = 255.0 / Math.log(1 + maxGreen);
        double multiplierB = 255.0 / Math.log(1 + maxBlue);

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(greyscale) {
                    red[i][j] = (int) (multiplierR * Math.log(1 + ((ImageGrey)image).getImage()[i][j]));
                } else {
                    red[i][j] = (int) (multiplierR * Math.log(1 + ((ImageColor)image).getRed()[i][j]));
                    green[i][j] = (int) (multiplierG * Math.log(1 + ((ImageColor)image).getBlue()[i][j]));
                    blue[i][j] = (int) (multiplierB * Math.log(1 + ((ImageColor)image).getGreen()[i][j]));
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
        int maxColor = 0;
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
        Integer [][] res = new Integer[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(((ImageGrey)image).getImage()[i][j] < r1) {
                    //calcular bien la pendiende de 0 -> s1
                    res[i][j] = (int) ((((ImageGrey)image).getImage()[i][j] - r1) * (255.0 / (r2 - r1)));
                } else if(((ImageGrey)image).getImage()[i][j] < r2) {
                    res[i][j] = (int) ((((ImageGrey)image).getImage()[i][j] - r1) * (255.0 / (r2 - r1)));
                } else {
                    //calcular bien la pendiend de s2-> 255
                    res[i][j] = (int) ((((ImageGrey)image).getImage()[i][j] - r1) * (255.0 / (r2 - r1)));
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

    public ImageGrey addGaussianNoise(double density, double std) {
        RandomGaussianGenerator rgg = new RandomGaussianGenerator(0, std);
        Random r = new Random();
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a-> Arrays.stream(a).map(p -> {
            double rnd = rgg.nextRandom();
            if(r.nextDouble() > density) {
                return p;
            }
//            if(p + rnd > 255) {
//                return 255;
//            } else if (p + rnd < 0) {
//                return 0;
//            } else {
                return (int) Math.floor(p + rnd);
//            }
        }).toArray(Integer[]::new)).toArray(Integer[][]::new);

        //clamp
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
        //clamp
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

        return new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }

    public ImageGrey addRayleighNoise(double density, double psi) {
        RandomRayleighGenerator rrg = new RandomRayleighGenerator(psi);
        Random r = new Random();
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a-> Arrays.stream(a).map(p ->
            r.nextDouble() > density ? p : (int) Math.floor(p * rrg.nextRandom())
        ).toArray(Integer[]::new)).toArray(Integer[][]::new);
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

        return new ImageColor(red, green, blue, image.getHeight(), image.getWidth());
    }
    public ImageGrey addExponentialNoise(double density, double lamda) {
        RandomExpGenerator reg = new RandomExpGenerator(lamda);
        Random r = new Random();
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a-> Arrays.stream(a).map(p ->
            r.nextDouble() > density ? p : (int) Math.floor(p * reg.nextRandom())
        ).toArray(Integer[]::new)).toArray(Integer[][]::new);
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
        return filter(w, false);
    }

    public ImageInt medianFilter(int n) {
        double[][] w = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                w[i][j] = 1;
            }
        }
        return filter(w, true);
    }

    public ImageInt weightedMedianFilter(int n) {
        double[][] w = new double[n][n];
        int half = (int) Math.floor(n/2);
        for (int i = 0; i < half + 1; i++) {
            for (int j = 0; j < half + 1; j++) {
                w[i][j] = 1 + i + j;
                w[n - i - 1][n - j - 1] = 1 + i + j;
                w[i][n - j - 1] = 1 + i + j;
                w[n - i - 1][j] = 1 + i + j;
            }
        }
        return filter(w, true);
    }

    public ImageInt laplacianFilter(int n) {
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
        return filter(w, false);
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
        return filter(w, false);
    }

    private ImageInt filter(double[][] w, boolean median) {
        int n = w.length;
        int half = (int) Math.floor(n/2);

        Integer[][] red = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] green = new Integer[image.getHeight()][image.getWidth()];
        Integer[][] blue = new Integer[image.getHeight()][image.getWidth()];
        Integer[][][] res2 = new Integer[image.getHeight()][image.getWidth()][3];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
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

        //clamp
        return greyscale ? new ImageGrey(red, image.getHeight(), image.getWidth())
                : new ImageColor(red, green, blue, image.getHeight(), image.getWidth());

    }
}
