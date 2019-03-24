package models;

import javafx.scene.image.Image;

import java.io.IOException;

public interface ImageInt {
    void parse(String filePath) throws IOException;
    Image matrixToColorImage(boolean red, boolean green, boolean blue);
}
