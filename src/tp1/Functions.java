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
        this.greyscale = false;
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
            res = Arrays.stream(((ImageColor)image).getImage()).map(a -> Arrays.stream(a).map(e -> {
                int c1 = 255 - e[0];
                int c2 = 255 - e[1];
                int c3 = 255 - e[2];
                return new int[]{c1, c2,c3};
            }).toArray(Integer[][]::new)).toArray(Integer[][][]::new);
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

    public ImageGrey meanFilter(int n) {
        double[][] w = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                w[i][j] = 1.0/(double)(n*n);
            }
        }
        return filter(w, false);
    }

    public ImageGrey medianFilter(int n) {
        double[][] w = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                w[i][j] = 1;
            }
        }
        return filter(w, true);
    }

    public ImageGrey weightedMedianFilter(int n) {
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

    public ImageGrey laplacianFilter(int n) {
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

    public ImageGrey gaussFilter(int n, double sigma) {
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

    private ImageGrey filter(double[][] w, boolean median) {
        int n = w.length;
        int half = (int) Math.floor(n/2);

        Integer[][] res = new Integer[image.getHeight()][image.getWidth()];
        for (int i = half; i < image.getHeight() - half; i++) {
            for (int j = half; j < image.getHeight() - half; j++) {
                double sum = 0;
                ArrayList<Integer> med = null;
                if(median)
                    med = new ArrayList<>();
                for (int k = -half; k < n - half; k++) {
                    for (int l = -half; l < n - half; l++) {
                        if(median){
                            for (int m = 0; m < w[k + half][l + half]; m++) {
                                med.add(((ImageGrey)image).getImage()[i + k][j + l]);
                            }
                        } else {
                            sum += (double) ((ImageGrey)image).getImage()[i + k][j + l] * w[k + half][l + half];
                        }
                    }
                }
                if(median) {
                    med.sort(Integer::compareTo);
                    res[i][j] = med.get(med.size()/2);
                } else {
                    res[i][j] = (int) sum < 0 ? 0 : (int) sum;
                }

            }
            for (int j = 0; j < half; j++) {
                res[i][j] = ((ImageGrey)image).getImage()[i][j];
                res[i][image.getWidth() - j - 1] = ((ImageGrey)image).getImage()[i][image.getWidth() - j - 1];
            }
        }
        for(int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < half; j++) {
                res[j][i] = ((ImageGrey)image).getImage()[j][i];
                res[image.getHeight() - j - 1][i] = ((ImageGrey)image).getImage()[image.getHeight() - j - 1][i];

            }
        }

        return new ImageGrey(res, image.getMaxColor(), image.getHeight(), image.getWidth());

    }
}
