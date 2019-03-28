package GUI;

import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;
import tp1.Functions;
import utils.IOManager;
import utils.ImageColorTransformer;
import utils.ImageGreyTransformer;

import java.io.File;

public class ImageGreyViewer extends ImageViewer {

    ImageGrey image;

    public ImageGreyViewer(ImageGrey image) {
        super();
        this.image = image;
//        this.addGreyImageContextMenu(image);
        this.stage.setHeight(image.getHeight() + 80);
        this.stage.setWidth(this.image.getWidth() + 20);
        this.box.getChildren().add(image.getView());
        this.addGreyMenuBars();
        stage.show();
    }

    private void addGreyMenuBars(){
        MenuItem save = new MenuItem("Save image");
        save.setOnAction(event -> this.saveImage());
        mainMenu.getMenus().get(0).getItems().add(1, save);

        final Menu transformMenu = new Menu("Transform");
        MenuItem cutter = new MenuItem("Cut Image");
        cutter.setOnAction(e -> new ImageGreyTransformer().cutImage(this.image));
        MenuItem painter = new MenuItem("Get and Set Colors");
        painter.setOnAction(e -> new ImageGreyTransformer().painter(this.image));
        MenuItem sum = new MenuItem("Sum");
        sum.setOnAction(e -> new ImageGreyTransformer().sumImages(this.image));
        MenuItem substract = new MenuItem("Substract");
        substract.setOnAction(e -> new ImageGreyTransformer().substractImages(this.image));
        MenuItem multiply = new MenuItem("Multiply by scalar");
        multiply.setOnAction(e -> new ImageGreyTransformer().multiplyImage(this.image));
        MenuItem gamma = new MenuItem("Apply gamma function");
        gamma.setOnAction(e -> new ImageGreyTransformer().gammaFunction(this.image));
        MenuItem rangeCompressor = new MenuItem("Range Compressor");
        rangeCompressor.setOnAction(e -> new ImageGreyTransformer().dynamicRangeCompression(this.image));
        MenuItem negative = new MenuItem("Negate");
        negative.setOnAction(e -> new ImageGreyTransformer().negative(this.image));

        MenuItem histogramEqualization = new MenuItem("Histogram Equalization");
        MenuItem contrast = new MenuItem("Contrast Improvement");
        MenuItem threshold = new MenuItem("Thresholding");
        MenuItem gaussNoise = new MenuItem("Add Gauss Noise");
        MenuItem rayleighNoise = new MenuItem("Add Rayleigh Noise");
        MenuItem expNoise = new MenuItem("Add Exponential Noise");
        MenuItem saltAndPepper = new MenuItem("Add Salt and Pepper");
        MenuItem meanFilter = new MenuItem("Add Mean Filter");
        MenuItem medianFilter = new MenuItem("Add Median Filter");
        MenuItem weightedMedianFilter = new MenuItem("Add Weighted Median Filter");
        MenuItem laplacianFilter = new MenuItem("Add Laplacian Filter");
        MenuItem gaussFilter = new MenuItem("Add Gauss Filter");
        histogramEqualization.setOnAction(e -> new ImageGreyTransformer().histogramEqualization(this.image));
        contrast.setOnAction(e -> new ImageGreyTransformer().greyContrast(this.image));
        threshold.setOnAction(e -> new ImageGreyTransformer().threshold(this.image));
        gaussNoise.setOnAction(e -> new ImageGreyTransformer().addGaussianNoise(this.image));
        rayleighNoise.setOnAction(e -> new ImageGreyTransformer().addRayleighNoise(this.image));
        expNoise.setOnAction(e -> new ImageGreyTransformer().addExponentialNoise(this.image));
        saltAndPepper.setOnAction(e -> new ImageGreyTransformer().addSaltAndPepper(this.image));
        meanFilter.setOnAction(e -> new ImageGreyTransformer().meanFilter(this.image));
        medianFilter.setOnAction(e -> new ImageGreyTransformer().medianFilter(this.image));
        weightedMedianFilter.setOnAction(e -> new ImageGreyTransformer().weightedMedianFilter(this.image));
        laplacianFilter.setOnAction(e -> new ImageGreyTransformer().laplacianFilter(this.image));
        gaussFilter.setOnAction(e -> new ImageGreyTransformer().gaussFilter(this.image));
//        MenuItem histogramEqualization = new MenuItem("Histogram Equalization");
//        histogramEqualization.setOnAction(e -> new ImageGreyTransformer().histogramEqualization(this.image));

        transformMenu.getItems().addAll( painter, cutter, sum, substract, multiply, gamma, rangeCompressor, negative,
                histogramEqualization, contrast, threshold,gaussNoise,rayleighNoise,expNoise,
                saltAndPepper, meanFilter, medianFilter, weightedMedianFilter, laplacianFilter, gaussFilter);


        final Menu showMenu = new Menu("Show");
        MenuItem showHistogram = new MenuItem("Histogram");
        showHistogram.setOnAction(event -> this.showHistogram());
        showMenu.getItems().add(showHistogram);

        this.mainMenu.getMenus().addAll(transformMenu, showMenu);
    }

    private void showHistogram(){
        if(this.box.getChildren().size()==1){
            double [] data = new Functions(this.image).greyHistogram();
            String[] labels = new String[256];
            for (int i = 0; i < 256; i++) {
                labels[i] = String.valueOf(i);
            }
            BarChart chart = Histogram.createHistogram(labels,data);
            chart.getYAxis().setLabel("");
            chart.getXAxis().setLabel("Intensity");
            this.box.getChildren().add(chart);
//            this.stage.setHeight(image.getHeight() + 80);
            this.stage.setWidth(this.stage.getWidth() + 500);
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

    protected void saveImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.pgm","*.raw")
        );
        File file = fileChooser.showSaveDialog(stage);

        IOManager.savePGM(file.getPath(), image);
    }
}
