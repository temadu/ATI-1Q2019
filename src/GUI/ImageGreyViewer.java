package GUI;

import javafx.scene.chart.BarChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.ImageGrey;
import tp1.Functions;
import utils.IOManager;
import utils.ImageGreyTransformer;

import java.io.File;

public class ImageGreyViewer extends ImageViewer {

    ImageGrey image;

    public ImageGreyViewer(ImageGrey image, int windowIndex) {
        super(windowIndex);
        this.image = image;
        this.imageView = new ImageView(image.getRenderer());
        this.pane.getChildren().add(this.imageView);
        this.pane.getStylesheets().add("GUI/black_histogram.css");
        if(windowIndex >= 0)
            this.addGreyContextMenu();
    }

    private void addGreyContextMenu(){
        Menu save = new Menu("Save image");
        MenuItem savePGM = new MenuItem("As PGM");
        savePGM.setOnAction(event -> this.saveImagePGM());
        MenuItem saveRAW = new MenuItem("As RAW");
        saveRAW.setOnAction(event -> this.saveImageRAW());
        save.getItems().addAll(savePGM,saveRAW);

        MenuItem remove = new MenuItem("Remove");
        remove.setOnAction(event -> ATIApp.WINDOWS.get(this.windowIndex).imageViews.getChildren().remove(this.getPane()));

        final Menu transformMenu = new Menu("Transform");

        final Menu basicOps = new Menu("Basic");
        MenuItem cutter = new MenuItem("Cut Image");
        cutter.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).cutImage(this.image));
        MenuItem drawCircle = new MenuItem("Draw Circle");
        drawCircle.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).drawCircle(this.image));
        MenuItem drawSquare = new MenuItem("Draw Square");
        drawSquare.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).drawSquare(this.image));
        MenuItem painter = new MenuItem("Get and Set Colors");
        painter.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).painter(this.image));
        MenuItem sum = new MenuItem("Sum");
        sum.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).sumImages(this.image));
        MenuItem substract = new MenuItem("Substract");
        substract.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).substractImages(this.image));
        MenuItem multiply = new MenuItem("Multiply by scalar");
        multiply.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).multiplyImage(this.image));
        MenuItem gamma = new MenuItem("Apply gamma function");
        gamma.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).gammaFunction(this.image));
        MenuItem rangeCompressor = new MenuItem("Range Compressor");
        rangeCompressor.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).dynamicRangeCompression(this.image));
        MenuItem negative = new MenuItem("Negate");
        negative.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).negative(this.image));
        MenuItem histogramEqualization = new MenuItem("Histogram Equalization");
        histogramEqualization.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).histogramEqualization(this.image));
        MenuItem contrast = new MenuItem("Contrast Improvement");
        contrast.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).greyContrast(this.image));
        basicOps.getItems().addAll( painter, cutter, drawSquare, drawCircle, sum, substract, multiply, gamma, rangeCompressor, negative, histogramEqualization, contrast);

        final Menu edgeDetectionOps = new Menu("Edge detection");
        MenuItem prewitt = new MenuItem("Prewitt");
        prewitt.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).prewitt(this.image));
        MenuItem sobel = new MenuItem("Sobel");
        sobel.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).sobel(this.image));
        MenuItem kirsh = new MenuItem("Kirsh");
        kirsh.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).kirsh(this.image));
        MenuItem mask5a = new MenuItem("Mask 5a");
        mask5a.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).mask5a(this.image));


        MenuItem laplaceEvaluated = new MenuItem("Laplace Filter");
        laplaceEvaluated.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).laplaceEvaluated(this.image));
        MenuItem laplacianOfGaussianMaskEvaluated = new MenuItem("Laplacian Of Gaussian Filter");
        laplacianOfGaussianMaskEvaluated.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).laplacianOfGaussianEvaluated(this.image));
        edgeDetectionOps.getItems().addAll(prewitt, sobel, kirsh, mask5a, laplaceEvaluated, laplacianOfGaussianMaskEvaluated);


        final Menu thresholdOps = new Menu("Thresholds");
        MenuItem threshold = new MenuItem("Basic Thresholding");
        MenuItem globalThreshold = new MenuItem("Global Thresholding");
        MenuItem otsuThreshold = new MenuItem("Otsu Thresholding");
        threshold.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).threshold(this.image));
        globalThreshold.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).globalThreshold(this.image));
        otsuThreshold.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).otsuThreshold(this.image));
        thresholdOps.getItems().addAll(threshold,globalThreshold,otsuThreshold);

        final Menu noiseOps = new Menu("Noise");
        MenuItem gaussNoise = new MenuItem("Add Gauss Noise");
        MenuItem rayleighNoise = new MenuItem("Add Rayleigh Noise");
        MenuItem expNoise = new MenuItem("Add Exponential Noise");
        MenuItem saltAndPepper = new MenuItem("Add Salt and Pepper");
        gaussNoise.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).addGaussianNoise(this.image));
        rayleighNoise.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).addRayleighNoise(this.image));
        expNoise.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).addExponentialNoise(this.image));
        saltAndPepper.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).addSaltAndPepper(this.image));
        noiseOps.getItems().addAll(gaussNoise,rayleighNoise,expNoise, saltAndPepper);


        final Menu filterOps = new Menu("Filters");
        MenuItem meanFilter = new MenuItem("Add Mean Filter");
        MenuItem medianFilter = new MenuItem("Add Median Filter");
        MenuItem weightedMedianFilter = new MenuItem("Add Weighted Median Filter");
        MenuItem laplacianFilter = new MenuItem("Add Highpass Filter");
        MenuItem gaussFilter = new MenuItem("Add Gauss Filter");
        MenuItem bilateralFilter = new MenuItem("Add Bilateral Filter");
        meanFilter.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).meanFilter(this.image));
        medianFilter.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).medianFilter(this.image));
        weightedMedianFilter.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).weightedMedianFilter(this.image));
        laplacianFilter.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).highpassFilter(this.image));
        gaussFilter.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).gaussFilter(this.image));
        bilateralFilter.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).bilateralFilter(this.image));
        filterOps.getItems().addAll(meanFilter,medianFilter,weightedMedianFilter,laplacianFilter,gaussFilter,bilateralFilter);

        MenuItem anisotropic = new MenuItem("Anisotropic Diffusion");
        anisotropic.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).anisotropic(this.image));
        MenuItem isotropic = new MenuItem("Isotropic Diffusion");
        isotropic.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).isotropic(this.image));

        MenuItem canny = new MenuItem("Canny");
        canny.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).cannyEdgeDetector(this.image));

        MenuItem susan = new MenuItem("Susan");
        susan.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).susanEdgeCornerDetector(this.image));

        MenuItem houghLines = new MenuItem("Hough Lines");
        houghLines.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).houghLineDetector(this.image));

        MenuItem houghCircles = new MenuItem("Hough Circles");
        houghCircles.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).houghCircleDetector(this.image));

        MenuItem contour = new MenuItem("Contour Tracing");
        contour.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).contourTracing(this.image));

        MenuItem harris = new MenuItem("Harris");
        harris.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).harrisMethod(this.image));
        MenuItem siftDetector = new MenuItem("SIFT Detector");
        siftDetector.setOnAction(e -> new ImageGreyTransformer(this.windowIndex).siftDetector(this.image));

        transformMenu.getItems().addAll(basicOps, thresholdOps, noiseOps, filterOps, edgeDetectionOps,
                isotropic, anisotropic, canny,susan, houghLines, houghCircles,contour, harris, siftDetector);


//        final Menu showMenu = new Menu("Show");
        MenuItem showHistogram = new MenuItem("Show Histogram");
        showHistogram.setOnAction(event -> this.showHistogram());
//        showMenu.getItems().add(showHistogram);
        this.menu = new ContextMenu(save,remove,transformMenu, showHistogram);
        this.imageView.setOnContextMenuRequested(event -> this.menu.show(this.imageView, event.getScreenX(), event.getScreenY()));
    }

    private void showHistogram(){
        if(this.pane.getChildren().size()==1){
            double [] data = new Functions(this.image).greyHistogram();
            String[] labels = new String[256];
            for (int i = 0; i < 256; i++) {
                labels[i] = String.valueOf(i);
            }
            BarChart chart = Histogram.createHistogram(labels,data);
            chart.getYAxis().setLabel("");
            chart.getXAxis().setLabel("Intensity");
            this.pane.getChildren().add(chart);
//            this.stage.setHeight(image.getHeight() + 80);
//            this.stage.setWidth(this.stage.getWidth() + 500);
        }
    }

//    public void addGreyImageContextMenu(ImageGrey image){
//        ImageView view = image.getView();
//        ContextMenu contextMenu = new ContextMenu();
//
//        MenuItem save = new MenuItem("Save");
//        save.setOnAction((ActionEvent event) -> {
//            saveImage(image);
//        });
//        contextMenu.getItems().addAll(save);
//        view.setOnContextMenuRequested((ContextMenuEvent event) ->
//                contextMenu.show(view, event.getScreenX(), event.getScreenY())
//        );
//    }

    protected void saveImagePGM(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.pgm")
        );
        File file = fileChooser.showSaveDialog(ATIApp.WINDOWS.get(windowIndex).stage);

        IOManager.savePGM(file.getPath(), image);
    }

    protected void saveImageRAW(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.raw")
        );
        File file = fileChooser.showSaveDialog(ATIApp.WINDOWS.get(windowIndex).stage);

        IOManager.saveRAW(file.getPath(), file.getName(),image);
    }
}
