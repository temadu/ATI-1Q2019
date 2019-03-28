package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.io.IOException;

public interface ImageInt {
    void parse(String filePath) throws IOException;
    int getHeight();
    int getWidth();
    int getMaxColor();
    ImageView getView();
    WritableImage getRenderer();
    void updateRenderer();
}
