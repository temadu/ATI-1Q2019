package GUI;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import models.ImageGrey;
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
        contrast.setOnAction(e -> new ImageGreyTransformer().greyContrast(this.image)); //el 1 deberia ser un slider
        threshold.setOnAction(e -> new ImageGreyTransformer().threshold(this.image));
        gaussNoise.setOnAction(e -> new ImageGreyTransformer().addGaussianNoise(this.image));
        rayleighNoise.setOnAction(e -> new ImageGreyTransformer().addRayleighNoise(this.image));
        expNoise.setOnAction(e -> new ImageGreyTransformer().addExponentialNoise(this.image));
//        MenuItem histogramEqualization = new MenuItem("Histogram Equalization");
//        histogramEqualization.setOnAction(e -> new ImageGreyTransformer().histogramEqualization(this.image));

        transformMenu.getItems().addAll(suma,histogramEqualization,contrast, threshold,gaussNoise,rayleighNoise,expNoise);

        this.mainMenu.getMenus().addAll(transformMenu);
    }
}
