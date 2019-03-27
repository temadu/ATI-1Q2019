package GUI;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Histogram {

    public static BarChart<String, Number> createHistogram(String[] labels, double[] data){
        final BarChart<String, Number> chartHistogram = new BarChart<>(new CategoryAxis(), new NumberAxis());
//        chartHistogram.setCreateSymbols(false);
        chartHistogram.getData().clear();
        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < labels.length; i++) {
            series.getData().add(new XYChart.Data(labels[i], data[i]));
        }
        chartHistogram.getData().add(series);
        return chartHistogram;
    }
}
