package utils;

import GUI.Window;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ImageGrey;

import java.util.ArrayList;

public class ImageCreator {

    public static void createSquare(){
        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Create Square");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Image Width:"), 0, 1);
        TextField widthField = new TextField();
        widthField.setMaxWidth(60);
        grid.add(widthField, 1, 1);

        grid.add(new Label("Image Height:"), 2, 1);
        TextField heightField = new TextField();
        grid.add(heightField, 3, 1);
        heightField.setMaxWidth(60);

        grid.add(new Label("Rectangle Width:"), 0, 2);
        TextField rectWidthField = new TextField();
        rectWidthField.setMaxWidth(60);
        grid.add(rectWidthField, 1, 2);

        grid.add(new Label("Rectangle Height:"), 2, 2);
        TextField rectHeightField = new TextField();
        grid.add(rectHeightField, 3, 2);
        rectHeightField.setMaxWidth(60);


        Button btn = new Button("Generate");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 3, 4);

        btn.setOnAction((e) -> {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            int rectWidth = Integer.parseInt(rectWidthField.getText());
            int rectHeight = Integer.parseInt(rectHeightField.getText());

            Integer[][] square = drawRectangle(new int[height][width],
                    (width/2) - (rectWidth/2), (height/2) - (rectHeight/2),
                    (width/2) + (rectWidth/2), (height/2) + (rectHeight/2),255);
            ImageGrey img = new ImageGrey(square, 255, width, height);

            Window w = new Window();
            w.addRow(img.getView());
            w.addGreyImageContextMenu(img);
            stage.close();
        });

        Scene scene = new Scene(grid, 600, 275);
        stage.setScene(scene);

        stage.setTitle("Create Square");
        stage.show();

    }

    public static void createCircle(){
        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Create Square");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Image Width:"), 0, 1);
        TextField widthField = new TextField();
        widthField.setMaxWidth(60);
        grid.add(widthField, 1, 1);

        grid.add(new Label("Image Height:"), 2, 1);
        TextField heightField = new TextField();
        grid.add(heightField, 3, 1);
        heightField.setMaxWidth(60);

        grid.add(new Label("Radius:"), 0, 2);
        TextField radiusField = new TextField();
        radiusField.setMaxWidth(60);
        grid.add(radiusField, 1, 2);

        Button btn = new Button("Generate");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 3, 4);

        btn.setOnAction((e) -> {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            float radius = Float.parseFloat(radiusField.getText());

            Integer[][] circle = drawCircle(new int[height][width],
                    width/2, height/2, radius, 255);
            ImageGrey img = new ImageGrey(circle, 255, width, height);

            Window w = new Window();
            w.addRow(img.getView());
            w.addGreyImageContextMenu(img);
            stage.close();
        });

        Scene scene = new Scene(grid, 600, 275);
        stage.setScene(scene);

        stage.setTitle("Create Square");

        stage.show();
    }


    public static Integer[][] drawCircle( int[][] circle, float cx, float cy, float radius, int color){
        Integer[][] retCircle =  new Integer[circle.length][circle[0].length];
        for(int row = 0; row < circle.length; row++) {
            for(int col = 0; col < circle[0].length; col++) {
                double d = Math.sqrt((cy-row)*(cy-row)+(cx-col)*(cx-col));
                if(d <= radius) {
                    retCircle[row][col] = color;
                } else {
                    retCircle[row][col] = circle[row][col];
                }
            }
        }
        return retCircle;
    }

    public static Integer[][] drawRectangle( int[][] image, int x1, int y1, int x2, int y2, int color){
        Integer[][] retImage =  new Integer[image.length][image[0].length];
        int minX = Math.min(x1,x2);
        int maxX = Math.max(x1,x2);
        int minY = Math.min(y1,y2);
        int maxY = Math.max(y1,y2);
        for(int row = 0; row < image.length; row++) {
            for(int col = 0; col < image[0].length; col++) {
                if(minX <= col && col <= maxX && minY <= row && row <= maxY) {
                    retImage[row][col] = color;
                } else {
                    retImage[row][col] = image[row][col];
                }
            }
        }
        return retImage;
    }
}
