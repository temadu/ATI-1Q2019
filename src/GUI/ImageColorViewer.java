package GUI;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;
import utils.IOManager;
import utils.ImageColorTransformer;
import utils.ImageCreator;
import utils.ImageGreyTransformer;

import java.io.File;

public class ImageColorViewer extends ImageViewer{

    ImageColor image;

    public ImageColorViewer(ImageColor image, int windowIndex) {
        super(windowIndex);
        this.image = image;
        this.imageView = new ImageView(image.getRenderer());
        this.pane.getChildren().add(this.imageView);
        this.addColorContextMenu();
    }

    private void addColorContextMenu(){
        MenuItem save = new MenuItem("Save image");
        save.setOnAction(event -> this.saveImage());
        MenuItem remove = new MenuItem("Remove");
        remove.setOnAction(event -> ATIApp.WINDOWS.get(this.windowIndex).imageViews.getChildren().remove(this.getPane()));


        final Menu transformMenu = new Menu("Transform");
        MenuItem painter = new MenuItem("Get and Set Colors");
        painter.setOnAction(e -> new ImageColorTransformer(windowIndex).painter(this.image));
        MenuItem cutter = new MenuItem("Cut Image");
        cutter.setOnAction(e -> new ImageColorTransformer(windowIndex).cutImage(this.image));
        MenuItem sum = new MenuItem("Sum");
        sum.setOnAction(e -> new ImageColorTransformer(windowIndex).sumImages(this.image));
        MenuItem substract = new MenuItem("Substract");
        substract.setOnAction(e -> new ImageColorTransformer(windowIndex).substractImages(this.image));
        MenuItem multiply = new MenuItem("Multiply by scalar");
        multiply.setOnAction(e -> new ImageColorTransformer(windowIndex).multiplyImage(this.image));
        MenuItem gamma = new MenuItem("Apply gamma function");
        gamma.setOnAction(e -> new ImageColorTransformer(windowIndex).gammaFunction(this.image));
        MenuItem rangeCompressor = new MenuItem("Range Compressor");
        rangeCompressor.setOnAction(e -> new ImageColorTransformer(windowIndex).dynamicRangeCompression(this.image));
        MenuItem negative = new MenuItem("Negate");
        negative.setOnAction(e -> new ImageColorTransformer(windowIndex).negative(this.image));
        MenuItem threshold = new MenuItem("Thresholding");
        threshold.setOnAction(e -> new ImageColorTransformer(windowIndex).thresholding(this.image));
        MenuItem prewitt = new MenuItem("Prewitt");
        prewitt.setOnAction(e -> new ImageColorTransformer(this.windowIndex).prewitt(this.image));

        MenuItem gaussNoise = new MenuItem("Add Gauss Noise");
        MenuItem rayleighNoise = new MenuItem("Add Rayleigh Noise");
        MenuItem expNoise = new MenuItem("Add Exponential Noise");
        MenuItem saltAndPepper = new MenuItem("Add Salt and Pepper");

        MenuItem meanFilter = new MenuItem("Add Mean Filter");
        MenuItem medianFilter = new MenuItem("Add Median Filter");
        MenuItem weightedMedianFilter = new MenuItem("Add Weighted Median Filter");
        MenuItem laplacianFilter = new MenuItem("Add Laplacian Filter");
        MenuItem gaussFilter = new MenuItem("Add Gauss Filter");

        gaussNoise.setOnAction(e -> new ImageColorTransformer(windowIndex).addGaussianNoise(this.image));
        rayleighNoise.setOnAction(e -> new ImageColorTransformer(windowIndex).addRayleighNoise(this.image));
        expNoise.setOnAction(e -> new ImageColorTransformer(windowIndex).addExponentialNoise(this.image));
        saltAndPepper.setOnAction(e -> new ImageColorTransformer(windowIndex).addSaltAndPepper(this.image));

        meanFilter.setOnAction(e -> new ImageColorTransformer(windowIndex).meanFilter(this.image));
        medianFilter.setOnAction(e -> new ImageColorTransformer(windowIndex).medianFilter(this.image));
        weightedMedianFilter.setOnAction(e -> new ImageColorTransformer(windowIndex).weightedMedianFilter(this.image));
        laplacianFilter.setOnAction(e -> new ImageColorTransformer(windowIndex).laplacianFilter(this.image));
        gaussFilter.setOnAction(e -> new ImageColorTransformer(windowIndex).gaussFilter(this.image));

        transformMenu.getItems().addAll(painter, cutter, sum,substract, multiply, gamma, rangeCompressor, negative, threshold,
                gaussNoise, rayleighNoise, expNoise, saltAndPepper,
                meanFilter, medianFilter, weightedMedianFilter, laplacianFilter, gaussFilter, prewitt);
        this.menu = new ContextMenu(save, remove, transformMenu);
        this.imageView.setOnContextMenuRequested(event -> this.menu.show(this.imageView, event.getScreenX(), event.getScreenY()));
    }

    protected void saveImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.ppm","*.raw")
        );
        File file = fileChooser.showSaveDialog(ATIApp.WINDOWS.get(windowIndex).stage);

        IOManager.savePPM(file.getPath(), (ImageColor) image);

    }


}
