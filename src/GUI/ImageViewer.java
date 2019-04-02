package GUI;

import javafx.scene.control.ContextMenu;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class ImageViewer {
    public VBox pane;
    public ContextMenu menu;
    public ImageView imageView;
    public int windowIndex;

    public ImageViewer(int windowIndex) {
        this.windowIndex = windowIndex;
        this.pane = new VBox();
    }

    public VBox getPane() {
        return pane;
    }
}
