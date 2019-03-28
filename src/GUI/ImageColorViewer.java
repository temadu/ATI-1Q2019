package GUI;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;
import utils.IOManager;
import utils.ImageColorTransformer;
import utils.ImageCreator;

import java.io.File;

public class ImageColorViewer extends ImageViewer{

    ImageColor image;

    public ImageColorViewer(ImageColor image) {
        super();
        this.image = image;
//        this.addColorImageContextMenu(image);
        this.stage.setHeight(this.image.getHeight() + 80);
        this.stage.setWidth(this.image.getWidth() + 20);
        this.box.getChildren().add(image.getView());
        this.addColorMenuBars();
        stage.show();
    }

    private void addColorMenuBars(){
        MenuItem save = new MenuItem("Save image");
        save.setOnAction(event -> this.saveImage());
        mainMenu.getMenus().get(0).getItems().add(1, save);

        final Menu transformMenu = new Menu("Transform");
        MenuItem sum = new MenuItem("Sum");
        sum.setOnAction(e -> new ImageColorTransformer().sumImages(this.image));
        MenuItem substract = new MenuItem("Substract");
        substract.setOnAction(e -> new ImageColorTransformer().substractImages(this.image));
        MenuItem multiply = new MenuItem("Multiply by scalar");
        multiply.setOnAction(e -> new ImageColorTransformer().multiplyImage(this.image));
        MenuItem gamma = new MenuItem("Apply gamma function");
        gamma.setOnAction(e -> new ImageColorTransformer().gammaFunction(this.image));
        MenuItem rangeCompressor = new MenuItem("Range Compressor");
        rangeCompressor.setOnAction(e -> new ImageColorTransformer().dynamicRangeCompression(this.image));
        MenuItem negative = new MenuItem("Negate");
        negative.setOnAction(e -> new ImageColorTransformer().negative(this.image));
        transformMenu.getItems().addAll(sum,substract, multiply, gamma, rangeCompressor, negative);

        this.mainMenu.getMenus().addAll(transformMenu);
    }

    protected void saveImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.ppm","*.raw")
        );
        File file = fileChooser.showSaveDialog(stage);

        IOManager.savePPM(file.getPath(), (ImageColor) image);

    }


}
