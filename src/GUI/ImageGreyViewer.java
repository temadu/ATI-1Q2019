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
        suma.setOnAction(e -> new ImageGreyTransformer().sumImages(this.image));
        transformMenu.getItems().addAll(suma);

        this.mainMenu.getMenus().addAll(transformMenu);
    }


}
