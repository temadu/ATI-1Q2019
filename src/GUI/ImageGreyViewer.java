package GUI;

import javafx.scene.chart.BarChart;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import models.ImageGrey;
import tp1.Functions;
import utils.ImageColorTransformer;
import utils.ImageGreyTransformer;

public class ImageGreyViewer extends ImageViewer {

    ImageGrey image;

    public ImageGreyViewer(ImageGrey image) {
        super();
        this.image = image;
        this.addGreyImageContextMenu(image);
        this.stage.setHeight(image.getHeight() + 80);
        this.stage.setWidth(this.image.getWidth() + 20);
        this.box.getChildren().add(image.getView());
        this.addGreyMenuBars();
        stage.show();
    }

    private void addGreyMenuBars(){
        final Menu transformMenu = new Menu("Transform");
        MenuItem suma = new MenuItem("Sum");
        MenuItem histogramEqualization = new MenuItem("Histogram Equalization");
        MenuItem contrast = new MenuItem("Contrast Improvement");
        MenuItem threshold = new MenuItem("Thresholding");
        MenuItem gaussNoise = new MenuItem("Add Gauss Noise");
        MenuItem rayleighNoise = new MenuItem("Add Rayleigh Noise");
        MenuItem expNoise = new MenuItem("Add Exponential Noise");
        suma.setOnAction(e -> new ImageGreyTransformer().sumImages(this.image));
        histogramEqualization.setOnAction(e -> new ImageGreyTransformer().histogramEqualization(this.image));
        contrast.setOnAction(e -> new ImageGreyTransformer().greyContrast(this.image));
        threshold.setOnAction(e -> new ImageGreyTransformer().threshold(this.image));
        gaussNoise.setOnAction(e -> new ImageGreyTransformer().addGaussianNoise(this.image));
        rayleighNoise.setOnAction(e -> new ImageGreyTransformer().addRayleighNoise(this.image));
        expNoise.setOnAction(e -> new ImageGreyTransformer().addExponentialNoise(this.image));
//        MenuItem histogramEqualization = new MenuItem("Histogram Equalization");
//        histogramEqualization.setOnAction(e -> new ImageGreyTransformer().histogramEqualization(this.image));

        transformMenu.getItems().addAll(suma,histogramEqualization,contrast, threshold,gaussNoise,rayleighNoise,expNoise);


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
}
