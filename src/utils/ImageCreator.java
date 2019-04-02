package utils;

import GUI.ATIApp;
import GUI.ImageColorViewer;
import GUI.ImageGreyViewer;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.ImageColor;
import models.ImageGrey;
import tp1.Functions;

import java.util.ArrayList;

public class ImageCreator {

    public static void createSquare(int windowIndex){
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
        widthField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        grid.add(new Label("Image Height:"), 2, 1);
        TextField heightField = new TextField();
        grid.add(heightField, 3, 1);
        heightField.setMaxWidth(60);
        heightField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        grid.add(new Label("Rectangle Width:"), 0, 2);
        TextField rectWidthField = new TextField();
        rectWidthField.setMaxWidth(60);
        grid.add(rectWidthField, 1, 2);
        rectWidthField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        grid.add(new Label("Rectangle Height:"), 2, 2);
        TextField rectHeightField = new TextField();
        grid.add(rectHeightField, 3, 2);
        rectHeightField.setMaxWidth(60);
        rectHeightField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });


        Button btn = new Button("Generate");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 3, 4);

        btn.setOnAction((e) -> {

            int width = 0;
            int height = 0;
            int rectWidth = 0;
            int rectHeight = 0;
            try {
                width = Integer.parseInt(widthField.getText());
                height = Integer.parseInt(heightField.getText());
                rectWidth = Integer.parseInt(rectWidthField.getText());
                rectHeight = Integer.parseInt(rectHeightField.getText());
            } catch (NumberFormatException | NullPointerException nfe) {
                return;
            }

            Integer[][] square = drawRectangle(new int[height][width],
                    (width/2) - (rectWidth/2), (height/2) - (rectHeight/2),
                    (width/2) + (rectWidth/2), (height/2) + (rectHeight/2),255);
            ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer(new ImageGrey(square, height, width),windowIndex));

            stage.close();
        });

        Scene scene = new Scene(grid, 600, 275);
        stage.setScene(scene);

        stage.setTitle("Create Square");
        stage.show();

    }

    public static void createCircle(int windowIndex){
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
        widthField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        grid.add(new Label("Image Height:"), 2, 1);
        TextField heightField = new TextField();
        grid.add(heightField, 3, 1);
        heightField.setMaxWidth(60);
        heightField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        grid.add(new Label("Radius:"), 0, 2);
        TextField radiusField = new TextField();
        radiusField.setMaxWidth(60);
        grid.add(radiusField, 1, 2);
        heightField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                widthField.setText(oldValue);
            }
        });


        Button btn = new Button("Generate");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 3, 4);

        btn.setOnAction((e) -> {
            int width = 0;
            int height = 0;
            float radius = 0;
            try {
                width = Integer.parseInt(widthField.getText());
                height = Integer.parseInt(heightField.getText());
                radius = Float.parseFloat(radiusField.getText());
            } catch (NumberFormatException | NullPointerException nfe) {
                return;
            }

            Integer[][] circle = drawCircle(new int[height][width],
                    width/2, height/2, radius, 255);
            ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer(new ImageGrey(circle, height, width),windowIndex));

            stage.close();
        });

        Scene scene = new Scene(grid, 600, 275);
        stage.setScene(scene);

        stage.setTitle("Create Square");

        stage.show();
    }

    public static void createGreyGradient(int windowIndex){
        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Create Grey Gradient");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Image Width:"), 0, 1);
        TextField widthField = new TextField();
        widthField.setMaxWidth(60);
        grid.add(widthField, 1, 1);
        widthField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        grid.add(new Label("Image Height:"), 2, 1);
        TextField heightField = new TextField();
        grid.add(heightField, 3, 1);
        heightField.setMaxWidth(60);
        heightField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        Button btn = new Button("Generate");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 3, 4);

        btn.setOnAction((e) -> {

            int width = 0;
            int height = 0;
            try {
                width = Integer.parseInt(widthField.getText());
                height = Integer.parseInt(heightField.getText());
            } catch (NumberFormatException | NullPointerException nfe) {
                return;
            }

            Integer[][] square = generateBWGradient(width, height);
            ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer(new ImageGrey(square, height, width),windowIndex));
            stage.close();
        });

        Scene scene = new Scene(grid, 600, 275);
        stage.setScene(scene);

        stage.setTitle("Create Square");
        stage.show();

    }

    public static void createBaseImageGray(int windowIndex){
        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Create Grey Gradient");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Image Width:"), 0, 1);
        TextField widthField = new TextField();
        widthField.setMaxWidth(60);
        grid.add(widthField, 1, 1);
        widthField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        grid.add(new Label("Image Height:"), 2, 1);
        TextField heightField = new TextField();
        grid.add(heightField, 3, 1);
        heightField.setMaxWidth(60);
        heightField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        grid.add(new Label("Color:"), 4, 1);
        TextField colorField = new TextField();
        grid.add(colorField, 5, 1);
        colorField.setMaxWidth(60);
        colorField.setText("0");
        colorField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        Button btn = new Button("Generate");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 3, 4);

        btn.setOnAction((e) -> {

            int width = 0;
            int height = 0;
            int color = 0;
            try {
                width = Integer.parseInt(widthField.getText());
                height = Integer.parseInt(heightField.getText());
                color = Integer.parseInt(colorField.getText());
            } catch (NumberFormatException | NullPointerException nfe) {
                return;
            }

            if(color> 255) color = 255;

            Integer[][] square = generateBaseColor(width, height, color);
            ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer(new ImageGrey(square, height, width),windowIndex));
            stage.close();
        });

        Scene scene = new Scene(grid, 600, 275);
        stage.setScene(scene);

        stage.setTitle("Create Base Image");
        stage.show();

    }

    public static void createColorGradient(int windowIndex){
        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Create Grey Gradient");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Image Width:"), 0, 1);
        TextField widthField = new TextField();
        widthField.setMaxWidth(60);
        grid.add(widthField, 1, 1);
        widthField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        grid.add(new Label("Image Height:"), 2, 1);
        TextField heightField = new TextField();
        grid.add(heightField, 3, 1);
        heightField.setMaxWidth(60);
        heightField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthField.setText(oldValue);
            }
        });

        Button btn = new Button("Generate");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 3, 4);

        btn.setOnAction((e) -> {

            int width = 0;
            int height = 0;
            try {
                width = Integer.parseInt(widthField.getText());
                height = Integer.parseInt(heightField.getText());
            } catch (NumberFormatException | NullPointerException nfe) {
                return;
            }

            Integer[][][] square = generateColorGradient(width, height);
            ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer(new ImageColor(square[0], square[1], square[2],  height, width),windowIndex));
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

    public static Integer[][] generateBWGradient(int length, int height){
        Integer[][] retImage =  new Integer[height][length];
        for (int x=0; x<length; x++)
            for (int y=0; y<height; y++)
                retImage[y][x] = x*255/length;

        return retImage;
    }

    public static Integer[][] generateBaseColor(int length, int height, int color){
        Integer[][] retImage =  new Integer[height][length];
        for (int x=0; x<length; x++)
            for (int y=0; y<height; y++)
                retImage[y][x] = color;

        return retImage;
    }

    public static Integer[][][] generateColorGradient(int length, int height){
        Integer[][][] ret = new Integer[3][][];
        Integer[][] retImageR =  new Integer[height][length];
        Integer[][] retImageG =  new Integer[height][length];
        Integer[][] retImageB =  new Integer[height][length];

        int lengthCounter = 0;
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImageR[y][lengthCounter] = x*255*6/length;
                retImageG[y][lengthCounter] = 255;
                retImageB[y][lengthCounter] = 0;
            }
            lengthCounter++;
        }
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImageR[y][lengthCounter] = 255;
                retImageG[y][lengthCounter] = 255 - x*255*6/length;
                retImageB[y][lengthCounter] = 0;
            }
            lengthCounter++;
        }
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImageR[y][lengthCounter] = 255;
                retImageG[y][lengthCounter] = 0;
                retImageB[y][lengthCounter] = x*255*6/length;
            }
            lengthCounter++;
        }
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImageR[y][lengthCounter] = 255 - x*255*6/length;
                retImageG[y][lengthCounter] = 0;
                retImageB[y][lengthCounter] = 255;
            }
            lengthCounter++;
        }
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImageR[y][lengthCounter] = 0;
                retImageG[y][lengthCounter] = x*255*6/length;
                retImageB[y][lengthCounter] = 255;
            }
            lengthCounter++;
        }
        for (int x=0; x<(length/6); x++){
            for (int y=0; y<height; y++){
                retImageR[y][lengthCounter] = 0;
                retImageG[y][lengthCounter] = 255;
                retImageB[y][lengthCounter] = 255 - x*255*6/length;
            }
            lengthCounter++;
        }
        for (int x=lengthCounter; x<length; x++){
            for (int y=0; y<height; y++){
                retImageR[y][x] = 0;
                retImageG[y][x] = 255;
                retImageB[y][x] = 0;
            }
        }
        ret[0] = retImageR;
        ret[1] = retImageG;
        ret[2] = retImageB;
        return ret;
    }
}
