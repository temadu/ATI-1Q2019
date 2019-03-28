package tp1;

import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;

import java.util.ArrayList;
import java.util.Arrays;
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
        Integer[][][] sum = new Integer[image.getHeight()][image.getWidth()][3];
        Integer[][] greySum = new Integer[image.getHeight()][image.getWidth()];
        boolean over255 = false;
        double maxPixel = 0;
        int maxColor = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(greyscale){
                    greySum[i][j] = ((ImageGrey) image).getImage()[i][j] + ((ImageGrey) addend).getImage()[i][j];
                    if(greySum[i][i] > 255) {
                        over255 = true;
                        maxPixel = Math.max(maxPixel, greySum[i][j]);
                    }
                } else {
                    sum[i][j][0] = ((ImageColor) image).getImage()[i][j][0] + ((ImageColor) addend).getImage()[i][j][0];
                    sum[i][j][1] = ((ImageColor) image).getImage()[i][j][1] + ((ImageColor) addend).getImage()[i][j][1];
                    sum[i][j][2] = ((ImageColor) image).getImage()[i][j][2] + ((ImageColor) addend).getImage()[i][j][2];
                    if(sum[i][j][0] > 255 || sum[i][j][1] > 255 || sum[i][j][2] > 255) {
                        over255 = true;
                        maxPixel = Math.max(maxPixel, Math.max(sum[i][j][0], Math.max(sum[i][j][1],sum[i][j][2])));
                    }
                }
            }
        }
        if(over255) {
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    if(greyscale){
                        greySum[i][j] = (int) (greySum[i][j] * 255 / maxPixel);

                    } else {
                        sum[i][j][0] = (int) (sum[i][j][0] * 255 / maxPixel);
                        sum[i][j][1] = (int) (sum[i][j][1] * 255 / maxPixel);
                        sum[i][j][2] = (int) (sum[i][j][2] * 255 / maxPixel);
                        maxColor = Math.max(maxColor, Math.max(sum[i][j][0], Math.max(sum[i][j][1], sum[i][j][2])));
                    }
                }
            }
        }

        return greyscale ? new ImageGrey(greySum, maxPixel > 255 ? maxColor : (int) maxPixel, image.getHeight(), image.getWidth())
                : new ImageColor(sum, maxPixel > 255 ? maxColor : (int) maxPixel, image.getHeight(), image.getWidth());


    }

    public ImageInt imageSub(ImageInt substract) {
        Integer[][][] sub = new Integer[image.getHeight()][image.getWidth()][3];
        Integer[][] greySub = new Integer[image.getHeight()][image.getWidth()];
        int maxColor = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (greyscale) {
                    greySub[i][j] = Math.max(0, ((ImageGrey)image).getImage()[i][j] - ((ImageGrey)substract).getImage()[i][j]);
                    maxColor = Math.max(maxColor, greySub[i][j]);
                } else {
                    sub[i][j][0] = Math.max(0, ((ImageColor)image).getImage()[i][j][0] - ((ImageColor)substract).getImage()[i][j][0]);
                    sub[i][j][1] = Math.max(0, ((ImageColor)image).getImage()[i][j][1] - ((ImageColor)substract).getImage()[i][j][1]);
                    sub[i][j][2] = Math.max(0, ((ImageColor)image).getImage()[i][j][2] - ((ImageColor)substract).getImage()[i][j][2]);
                    maxColor = Math.max(maxColor, Math.max(sub[i][j][0], Math.max(sub[i][j][1], sub[i][j][2])));
                }
            }
        }
        return greyscale ? new ImageGrey(greySub, maxColor, image.getHeight(), image.getWidth())
                : new ImageColor(sub, maxColor, image.getHeight(), image.getWidth());
    }

    public ImageInt imageProd(double multiplier) {
        Integer[][][] prod = new Integer[image.getHeight()][image.getWidth()][3];
        Integer[][] greyProd = new Integer[image.getHeight()][image.getWidth()];
        int maxColor = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (greyscale) {
                    greyProd[i][j] = (int) Math.min(255, ((ImageGrey)image).getImage()[i][j] * multiplier);
                    maxColor = Math.max(maxColor, greyProd[i][j]);

                } else {
                    prod[i][j][0] = (int) Math.min(255, ((ImageColor)image).getImage()[i][j][0] * multiplier);
                    prod[i][j][1] = (int) Math.min(255, ((ImageColor)image).getImage()[i][j][1] * multiplier);
                    prod[i][j][2] = (int) Math.min(255, ((ImageColor)image).getImage()[i][j][2] * multiplier);
                    maxColor = Math.max(maxColor, Math.max(prod[i][j][0], Math.max(prod[i][j][1], prod[i][j][2])));
                }
            }
        }
        return greyscale ? new ImageGrey(greyProd, maxColor, image.getHeight(), image.getWidth())
                : new ImageColor(prod, maxColor, image.getHeight(), image.getWidth());
    }

    public ImageInt rangeCompressor() {
        Integer[][][] res = new Integer[image.getHeight()][image.getWidth()][3];
        Integer[][] greyRes = new Integer[image.getHeight()][image.getWidth()];
        double multiplier = 255.0 / Math.log(1 + image.getMaxColor());
        int maxColor = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(greyscale) {
                    greyRes[i][j] = (int) (multiplier * Math.log(1 + ((ImageGrey)image).getImage()[i][j]));
                    maxColor = Math.max(maxColor, greyRes[i][j]);

                } else {
                    res[i][j][0] = (int) (multiplier * Math.log(1 + ((ImageColor)image).getImage()[i][j][0]));
                    res[i][j][1] = (int) (multiplier * Math.log(1 + ((ImageColor)image).getImage()[i][j][1]));
                    res[i][j][2] = (int) (multiplier * Math.log(1 + ((ImageColor)image).getImage()[i][j][2]));
                    maxColor = Math.max(maxColor, Math.max(res[i][j][0], Math.max(res[i][j][1], res[i][j][2])));
                }
            }
        }
        return greyscale ? new ImageGrey(greyRes, maxColor, image.getHeight(), image.getWidth())
                : new ImageColor(res, maxColor, image.getHeight(), image.getWidth());
    }

    public ImageInt gammaFunction(double gamma) {
        Integer[][][] res = new Integer[image.getHeight()][image.getWidth()][3];
        Integer[][] greyRes = new Integer[image.getHeight()][image.getWidth()];
        double multiplier = Math.pow(255.0, 1 - gamma);
        int maxColor = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(greyscale){
                    greyRes[i][j] = (int) (multiplier * Math.pow(((ImageGrey)image).getImage()[i][j], gamma));
                    maxColor = Math.max(maxColor, greyRes[i][j]);
                } else {
                    res[i][j][0] = (int) (multiplier * Math.pow(((ImageColor)image).getImage()[i][j][0], gamma));
                    res[i][j][1] = (int) (multiplier * Math.pow(((ImageColor)image).getImage()[i][j][1], gamma));
                    res[i][j][2] = (int) (multiplier * Math.pow(((ImageColor)image).getImage()[i][j][2], gamma));
                    maxColor = Math.max(maxColor, Math.max(res[i][j][0], Math.max(res[i][j][1], res[i][j][2])));
                }
            }
        }
        return greyscale ? new ImageGrey(greyRes, maxColor, image.getHeight(), image.getWidth())
                : new ImageColor(res, maxColor, image.getHeight(), image.getWidth());
    }

    public ImageInt negative() {
        Integer[][][] res = null;
        Integer[][] greyRes = null;
        int maxColor = 0;
        if(greyscale){
            greyRes = Arrays.stream(((ImageGrey) image).getImage()).map(a -> Arrays.stream(a).map(e -> 255 - e).toArray(Integer[]::new)).toArray(Integer[][]::new);
        } else {
            res = Arrays.stream(((ImageColor)image).getImage()).map(a -> Arrays.stream(a).map(b -> Arrays.stream(b).map(e -> 255 - e).toArray(Integer[]::new)).toArray(Integer[][]::new)).toArray(Integer[][][]::new);
        }
        return greyscale ?  new ImageGrey(greyRes, maxColor, image.getHeight(), image.getWidth())
                :  new ImageColor(res, maxColor, image.getHeight(), image.getWidth());
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
        return new ImageGrey(res, image.getMaxColor(), image.getHeight(), image.getWidth());
    }

    public ImageGrey greyContrast(double sigmaMult) {
        ((ImageGrey)image).calcStd();
        int r1 = Math.max(0, (int) (((ImageGrey)image).getMean() - ((ImageGrey)image).getSigma() * sigmaMult));
        int r2 = Math.min(255, (int) (((ImageGrey)image).getMean() + ((ImageGrey)image).getSigma() * sigmaMult));
        Integer [][] res = new Integer[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if(((ImageGrey)image).getImage()[i][j] < r1) {
                    res[i][j] = 0;
                } else if(((ImageGrey)image).getImage()[i][j] < r2) {
                    res[i][j] = (int) ((((ImageGrey)image).getImage()[i][j] - r1) * (255.0 / (r2 - r1)));
                } else {
                    res[i][j] = 255;
                }
            }
        }
        return new ImageGrey(res, image.getMaxColor(), image.getHeight(), image.getWidth());
    }

    public ImageGrey thresholdization(int threshold) {
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a -> Arrays.stream(a).map(p -> p > threshold ? 255 : 0).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageGrey(res, image.getMaxColor(), image.getHeight(), image.getWidth());
    }

    public ImageColor thresholdizationColor(int r, int g, int b) {
        Integer[][][] res = new Integer[image.getHeight()][image.getHeight()][3];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                res[i][j][0] = ((ImageColor)image).getImage()[i][j][0] > r ? 255 : 0;
                res[i][j][1] = ((ImageColor)image).getImage()[i][j][1] > g ? 255 : 0;
                res[i][j][2] = ((ImageColor)image).getImage()[i][j][2] > b ? 255 : 0;
            }
        }
        return new ImageColor(res, 255, image.getHeight(), image.getWidth());
    }

    public ImageGrey addGaussianNoise(double density, double std) {
        RandomGaussianGenerator rgg = new RandomGaussianGenerator(0, std);
        Random r = new Random();
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a-> Arrays.stream(a).map(p -> {
            double rnd = rgg.nextRandom();
            if(r.nextDouble() > density) {
                return p;
            }
            if(p + rnd > 255) {
                return 255;
            } else if (p + rnd < 0) {
                return 0;
            } else {
                return (int) Math.floor(p + rnd);
            }
        }).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageGrey(res, image.getMaxColor(), image.getHeight(), image.getWidth());
    }

    public ImageGrey addRayleighNoise(double density, double psi) {
        RandomRayleighGenerator rrg = new RandomRayleighGenerator(psi);
        Random r = new Random();
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a-> Arrays.stream(a).map(p -> {
            double rnd = rrg.nextRandom();
            if(r.nextDouble() > density) {
                return p;
            }
            if(p * rnd > 255) {
                return 255;
            } else if (p * rnd < 0) {
                return 0;
            } else {
                return (int) Math.floor(p * rnd);
            }
        }).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageGrey(res, image.getMaxColor(), image.getHeight(), image.getWidth());
    }

    public ImageGrey addExponentialNoise(double density, double lamda) {
        RandomExpGenerator reg = new RandomExpGenerator(lamda);
        Random r = new Random();
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a-> Arrays.stream(a).map(p -> {
            double rnd = reg.nextRandom();
            if(r.nextDouble() > density) {
                return p;
            }
            if(p * rnd > 255) {
                return 255;
            } else if (p * rnd < 0) {
                return 0;
            } else {
                return (int) Math.floor(p * rnd);
            }
        }).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageGrey(res, image.getMaxColor(), image.getHeight(), image.getWidth());
    }

    public ImageGrey addSaltAndPepper(double density) {
        Random r = new Random();
        Integer[][] res = Arrays.stream(((ImageGrey)image).getImage()).map(a-> Arrays.stream(a).map(p -> {
            if(r.nextDouble() < density) {
                return 0;
            } else if (r.nextDouble() > 1 - density) {
                return 255;
            } else {
                return p;
            }
        }).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageGrey(res, image.getMaxColor(), image.getHeight(), image.getWidth());
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

        Integer[][] res = new Integer[image.getHeight()][image.getWidth()];
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
                                    med.add(((ImageColor)image).getImage()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())][0]);
                                    med2.add(((ImageColor)image).getImage()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())][1]);
                                    med3.add(((ImageColor)image).getImage()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())][2]);
                                }
                            }
                        } else {
                            if(greyscale) {
                                sum += (double) ((ImageGrey) image).getImage()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())] * w[k + half][l + half];
                            } else {
                                sum += (double) ((ImageColor) image).getImage()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())][0] * w[k + half][l + half];
                                sum2 += (double) ((ImageColor) image).getImage()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())][1] * w[k + half][l + half];
                                sum3 += (double) ((ImageColor) image).getImage()[Math.floorMod(i + k, image.getHeight())][Math.floorMod(j + l, image.getWidth())][2] * w[k + half][l + half];
                            }
                        }
                    }
                }
                if(median) {
                    if(greyscale){
                        med.sort(Integer::compareTo);
                        res[i][j] = med.get(med.size()/2);
                    } else {
                        med.sort(Integer::compareTo);
                        res2[i][j][0] = med.get(med.size()/2);
                        med2.sort(Integer::compareTo);
                        res2[i][j][1] = med2.get(med2.size()/2);
                        med3.sort(Integer::compareTo);
                        res2[i][j][2] = med3.get(med3.size()/2);
                    }
                } else {
                    if(greyscale) {
                        res[i][j] = (int) sum < 0 ? 0 : (int) sum;
                    } else {
                        res2[i][j][0] = (int) sum < 0 ? 0 : (int) sum;
                        res2[i][j][1] = (int) sum2 < 0 ? 0 : (int) sum2;
                        res2[i][j][2] = (int) sum3 < 0 ? 0 : (int) sum3;
                    }
                }

            }
        }
        return greyscale ? new ImageGrey(res, image.getMaxColor(), image.getHeight(), image.getWidth())
                : new ImageColor(res2, image.getMaxColor(), image.getHeight(), image.getWidth());

    }
}
