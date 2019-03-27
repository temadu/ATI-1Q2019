package tp1;

import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Functions {
    public ImageColor image;
    public ImageGrey imageGrey;

    public Functions(ImageColor image) {
        this.image = image;
    }

    public Functions(ImageGrey image) {
        this.imageGrey = image;
    }

    public ImageColor imageSum(int[][][] addend) {
        int[][][] sum = new int[image.getHeight()][image.getWidth()][3];
        boolean over255 = false;
        double maxPixel = 0;
        int maxColor = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                sum[i][j][0] = image.getImage()[i][j][0] + addend[i][j][0];
                sum[i][j][1] = image.getImage()[i][j][1] + addend[i][j][1];
                sum[i][j][2] = image.getImage()[i][j][2] + addend[i][j][2];
                if(sum[i][j][0] > 255 || sum[i][j][1] > 255 || sum[i][j][2] > 255) {
                    over255 = true;
                    maxPixel = Math.max(maxPixel, Math.max(sum[i][j][0], Math.max(sum[i][j][1],sum[i][j][2])));
                }
            }
        }
        if(over255) {
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    sum[i][j][0] = (int) (sum[i][j][0] * 255 / maxPixel);
                    sum[i][j][1] = (int) (sum[i][j][1] * 255 / maxPixel);
                    sum[i][j][2] = (int) (sum[i][j][2] * 255 / maxPixel);
                    maxColor = Math.max(maxColor, Math.max(sum[i][j][0], Math.max(sum[i][j][1], sum[i][j][2])));
                }
            }
        }

        return new ImageColor(sum, maxPixel > 255 ? maxColor : (int) maxPixel, image.getHeight(), image.getWidth());
    }

    public ImageColor imageSub(int[][][] substract) {
        int[][][] sub = new int[image.getHeight()][image.getWidth()][3];
        int maxColor = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                sub[i][j][0] = Math.max(0, image.getImage()[i][j][0] - substract[i][j][0]);
                sub[i][j][1] = Math.max(0, image.getImage()[i][j][1] - substract[i][j][1]);
                sub[i][j][2] = Math.max(0, image.getImage()[i][j][2] - substract[i][j][2]);
                maxColor = Math.max(maxColor, Math.max(sub[i][j][0], Math.max(sub[i][j][1], sub[i][j][2])));
            }
        }
        return new ImageColor(sub, maxColor, image.getHeight(), image.getWidth());
    }

    public ImageColor imageProd(double multiplier) {
        int[][][] prod = new int[image.getHeight()][image.getWidth()][3];
        int maxColor = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                prod[i][j][0] = (int) Math.min(255.0, (image.getImage()[i][j][0] * multiplier));
                prod[i][j][1] = (int) Math.min(255.0, (image.getImage()[i][j][1] * multiplier));
                prod[i][j][2] = (int) Math.min(255.0, (image.getImage()[i][j][2] * multiplier));
                maxColor = Math.max(maxColor, Math.max(prod[i][j][0], Math.max(prod[i][j][1], prod[i][j][2])));
            }
        }
        return new ImageColor(prod, maxColor, image.getHeight(), image.getWidth());
    }

    public ImageColor rangeCompressor() {
        int[][][] res = new int[image.getHeight()][image.getWidth()][3];
        double multiplier = 255.0 / Math.log(1 + image.getMaxColor());
        int maxColor = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                res[i][j][0] = (int) (multiplier * Math.log(1 + image.getImage()[i][j][0]));
                res[i][j][1] = (int) (multiplier * Math.log(1 + image.getImage()[i][j][1]));
                res[i][j][2] = (int) (multiplier * Math.log(1 + image.getImage()[i][j][2]));
                maxColor = Math.max(maxColor, Math.max(res[i][j][0], Math.max(res[i][j][1], res[i][j][2])));
            }
        }
        return new ImageColor(res, maxColor, image.getHeight(), image.getWidth());
    }

    public ImageColor gammaFunction(double gamma) {
        int[][][] res = new int[image.getHeight()][image.getWidth()][3];
        double multiplier = Math.pow(255.0, 1 - gamma);
        int maxColor = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                res[i][j][0] = (int) (multiplier * Math.pow(image.getImage()[i][j][0], gamma));
                res[i][j][1] = (int) (multiplier * Math.pow(image.getImage()[i][j][1], gamma));
                res[i][j][2] = (int) (multiplier * Math.pow(image.getImage()[i][j][2], gamma));
                maxColor = Math.max(maxColor, Math.max(res[i][j][0], Math.max(res[i][j][1], res[i][j][2])));
            }
        }
        return new ImageColor(res, maxColor, image.getHeight(), image.getWidth());
    }

    public ImageColor negative() {
        int[][][] res = new int[image.getHeight()][image.getWidth()][3];
        int maxColor = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                res[i][j][0] = 255 - image.getImage()[i][j][0];
                res[i][j][1] = 255 - image.getImage()[i][j][1];
                res[i][j][2] = 255 - image.getImage()[i][j][2];
                maxColor = Math.max(maxColor, Math.max(res[i][j][0], Math.max(res[i][j][1], res[i][j][2])));
            }
        }
        return new ImageColor(res, maxColor, image.getHeight(), image.getWidth());
    }


    public double[] greyHistogram() {
        double[] hist = new double[256];
        Arrays.stream(imageGrey.getImage()).forEach(a -> Arrays.stream(a).forEach(e -> hist[e]++));
        return Arrays.stream(hist).map(h -> h / (imageGrey.getHeight() * imageGrey.getWidth())).toArray();
    }

    public ImageGrey histogramEqualization() {
        double[] g = greyHistogram();
        double[] cum = new double[256];
        cum[0] = g[0];
        IntStream.range(1, cum.length).forEach(i -> cum[i] = (cum[i - 1] + g[i]));
        Arrays.setAll(cum, i -> cum[i] * 255.0);

        Integer[][] res = Arrays.stream(imageGrey.getImage()).map(a -> Arrays.stream(a).map(p -> (int) Math.floor(cum[p])).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageGrey(res, imageGrey.getMaxColor(), imageGrey.getHeight(), imageGrey.getWidth());
    }

    public ImageGrey greyContrast(double sigmaMult) {
        imageGrey.calcStd();
        int r1 = Math.max(0, (int) (imageGrey.getMean() - imageGrey.getSigma() * sigmaMult));
        int r2 = Math.max(0, (int) (imageGrey.getMean() + imageGrey.getSigma() * sigmaMult));
        Integer [][] res = new Integer[imageGrey.getHeight()][imageGrey.getWidth()];
        for (int i = 0; i < imageGrey.getHeight(); i++) {
            for (int j = 0; j < imageGrey.getWidth(); j++) {
                if(imageGrey.getImage()[i][j] < r1) {
                    res[i][j] = 0;
                } else if(imageGrey.getImage()[i][j] < r2) {
                    res[i][j] = (imageGrey.getImage()[i][j] - r1) * (255 / (r2 - r1));
                } else {
                    res[i][j] = 255;
                }
            }
        }
        return new ImageGrey(res, imageGrey.getMaxColor(), imageGrey.getHeight(), imageGrey.getWidth());
    }

    public ImageGrey thresholdization(int threshold) {
        Integer[][] res = Arrays.stream(imageGrey.getImage()).map(a -> Arrays.stream(a).map(p -> p > threshold ? 255 : 0).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageGrey(res, imageGrey.getMaxColor(), imageGrey.getHeight(), imageGrey.getWidth());
    }

    public ImageGrey addGaussianNoise(double density, double std) {
        RandomGaussianGenerator rgg = new RandomGaussianGenerator(0, std);
        Random r = new Random();
        Integer[][] res = Arrays.stream(imageGrey.getImage()).map(a-> Arrays.stream(a).map(p -> {
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
        return new ImageGrey(res, imageGrey.getMaxColor(), imageGrey.getHeight(), imageGrey.getWidth());
    }

    public ImageGrey addRayleighNoise(double density, double psi) {
        RandomRayleighGenerator rrg = new RandomRayleighGenerator(psi);
        Random r = new Random();
        Integer[][] res = Arrays.stream(imageGrey.getImage()).map(a-> Arrays.stream(a).map(p -> {
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
        return new ImageGrey(res, imageGrey.getMaxColor(), imageGrey.getHeight(), imageGrey.getWidth());
    }

    public ImageGrey addExponentialNoise(double density, double lamda) {
        RandomExpGenerator reg = new RandomExpGenerator(lamda);
        Random r = new Random();
        Integer[][] res = Arrays.stream(imageGrey.getImage()).map(a-> Arrays.stream(a).map(p -> {
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
        return new ImageGrey(res, imageGrey.getMaxColor(), imageGrey.getHeight(), imageGrey.getWidth());
    }

    public ImageGrey addSaltAndPepper(double density) {
        Random r = new Random();
        Integer[][] res = Arrays.stream(imageGrey.getImage()).map(a-> Arrays.stream(a).map(p -> {
            if(r.nextDouble() < density) {
                return 0;
            } else if (r.nextDouble() > 1 - density) {
                return 255;
            } else {
                return p;
            }
        }).toArray(Integer[]::new)).toArray(Integer[][]::new);
        return new ImageGrey(res, imageGrey.getMaxColor(), imageGrey.getHeight(), imageGrey.getWidth());
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

        Integer[][] res = new Integer[imageGrey.getHeight()][imageGrey.getWidth()];
        for (int i = half; i < imageGrey.getHeight() - half; i++) {
            for (int j = half; j < imageGrey.getHeight() - half; j++) {
                double sum = 0;
                ArrayList<Integer> med = null;
                if(median)
                    med = new ArrayList<>();
                for (int k = -half; k < n - half; k++) {
                    for (int l = -half; l < n - half; l++) {
                        if(median){
                            for (int m = 0; m < w[k + half][l + half]; m++) {
                                med.add(imageGrey.getImage()[i + k][j + l]);
                            }
                        } else {
                            sum += imageGrey.getImage()[i + k][j + l] * w[k + half][l + half];
                        }
                    }
                }
                if(median) {
                    med.sort(Integer::compareTo);
                    res[i][j] = med.get(med.size()/2);
                } else {
                    res[i][j] = (int) sum;
                }

            }
            for (int j = 0; j < half; j++) {
                res[i][j] = imageGrey.getImage()[i][j];
                res[i][imageGrey.getWidth() - j - 1] = imageGrey.getImage()[i][imageGrey.getWidth() - j - 1];
            }
        }
        for(int i = 0; i < imageGrey.getWidth(); i++) {
            for (int j = 0; j < half; j++) {
                res[j][i] = imageGrey.getImage()[j][i];
                res[imageGrey.getHeight() - j - 1][i] = imageGrey.getImage()[imageGrey.getHeight() - j - 1][i];

            }
        }

        return new ImageGrey(res, imageGrey.getMaxColor(), imageGrey.getHeight(), imageGrey.getWidth());

    }
}
