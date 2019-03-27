package GUI;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import models.ImageColor;
import utils.ImageColorTransformer;
import utils.ImageCreator;

public class ImageColorViewer extends ImageViewer{

    ImageColor image;

    public ImageColorViewer(ImageColor image) {
        super();
        this.image = image;
        this.addColorImageContextMenu(image);
        this.stage.setHeight(this.image.getHeight() + 80);
        this.stage.setWidth(this.image.getWidth() + 20);
        this.box.getChildren().add(image.getView());
        this.addColorMenuBars();
        stage.show();
    }

    private void addColorMenuBars(){
        final Menu transformMenu = new Menu("Transform");
        MenuItem sum = new MenuItem("Sum");
        sum.setOnAction(e -> new ImageColorTransformer().sumImages(this.image));
        MenuItem substract = new MenuItem("Substract");
        substract.setOnAction(e -> new ImageColorTransformer().substractImages(this.image));
        transformMenu.getItems().addAll(sum,substract);

        this.mainMenu.getMenus().addAll(transformMenu);
    }

}
