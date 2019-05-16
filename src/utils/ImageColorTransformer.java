package utils;

import GUI.ATIApp;
import GUI.ImageColorViewer;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ImageColor;
import models.ImageInt;
import tp1.Functions;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageColorTransformer {

    ImageInt originalImage;
    ImageInt secondImage;
    ImageInt outputImage;
    int windowIndex;

    public ImageColorTransformer(int windowIndex) {
        this.windowIndex = windowIndex;
    }

    public void sumImages(ImageColor originalImage){
        this.originalImage = originalImage;

        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Sum Images");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Button chooseImageBtn = new Button("Choose Image");
        chooseImageBtn.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("./images"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image files", "*.ppm")
            );
            File file = fileChooser.showOpenDialog(stage);

            try {
                if(this.secondImage != null)
                    grid.getChildren().remove(secondImage.getView());
                this.secondImage = IOManager.loadPPM(file.getPath());
                grid.add(this.secondImage.getView(),1,3);
                if(this.originalImage.getHeight() >= this.secondImage.getHeight() &&
                        this.originalImage.getWidth() >= this.secondImage.getWidth()){
                    if(this.outputImage != null)
                        grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(originalImage).imageSum(secondImage);
                    grid.add(this.outputImage.getView(),2,3);

                } else{
                    this.outputImage = null;
                    grid.add(new Label("NOT COMPATIBLE"),2,3);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
        });
        grid.add(chooseImageBtn, 0, 1);

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 3);
        grid.add(new Label("Second Image:"), 1, 2);
        grid.add(new Label("Output Image:"), 2, 2);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 2, 4);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor)outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.setTitle("Sum Images");
        stage.show();

    }
    public void substractImages(ImageColor originalImage){
        this.originalImage = originalImage;

        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Substract Images");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Button chooseImageBtn = new Button("Choose Image");
        chooseImageBtn.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("./images"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image files", "*.ppm")
            );
            File file = fileChooser.showOpenDialog(stage);

            try {
                if(this.secondImage != null)
                    grid.getChildren().remove(secondImage.getView());
                this.secondImage = IOManager.loadPPM(file.getPath());
                grid.add(this.secondImage.getView(),1,3);
                if(this.originalImage.getHeight() >= this.secondImage.getHeight() &&
                        this.originalImage.getWidth() >= this.secondImage.getWidth()){
                    if(this.outputImage != null)
                        grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(originalImage).imageSub(secondImage);
                    grid.add(this.outputImage.getView(),2,3);

                } else{
                    this.outputImage = null;
                    grid.add(new Label("NOT COMPATIBLE"),2,3);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
        });
        grid.add(chooseImageBtn, 0, 1);

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 3);
        grid.add(new Label("Second Image:"), 1, 2);
        grid.add(new Label("Output Image:"), 2, 2);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 2, 4);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor)outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.setTitle("Substract Images");
        stage.show();


    }
    public void multiplyImage(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).imageProd(1);

        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Multiply image by scalar");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Multiplier:"), 0, 1);
        TextField multField = new TextField();
        multField.setMaxWidth(60);
        grid.add(multField, 1, 1);
        multField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                multField.setText(oldValue);
            } else {
                float multiplier = 0;
                try {
                    multiplier = Float.parseFloat(multField.getText());
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(multiplier >= 0){
                    grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(this.originalImage).imageProd(multiplier);
                    grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

                }
            }
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 3);
        grid.add(new Label("Output Image:"), 1, 2);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 4);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor)outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.setTitle("Multiply image by scalar");
        stage.show();
    }

    public void dynamicRangeCompression(ImageColor originalImage){
        ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor)new Functions(originalImage).rangeCompressor(),windowIndex));
    }

    public void prewitt(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).prewittOperator(false,false,true,true,false,false,false,false);
        Stage stage = new Stage();
        stage.setTitle("Apply Prewitt");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Apply Prewitt");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Directions:"), 0, 2, 1,3);

        CheckBox n = new CheckBox();

        n.setSelected(false);
        CheckBox nw = new CheckBox();
        nw.setSelected(false);
        CheckBox ne = new CheckBox();
        ne.setSelected(false);
        CheckBox s = new CheckBox();
        s.setSelected(true);
        CheckBox sw = new CheckBox();
        sw.setSelected(false);
        CheckBox se = new CheckBox();
        se.setSelected(false);
        CheckBox e = new CheckBox();
        e.setSelected(true);
        CheckBox w = new CheckBox();
        w.setSelected(false);
        grid.add(n, 3,3);
        grid.add(nw, 2,3);
        grid.add(ne, 4,3);
        grid.add(s, 3,5);
        grid.add(sw, 2,5);
        grid.add(se, 4,5);
        grid.add(e, 4,4);
        grid.add(w, 2,4);
        n.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).prewittOperator(newValue, w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        ne.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).prewittOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), newValue, sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1, 4);
        }));
        nw.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).prewittOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), newValue, ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        s.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).prewittOperator(n.isSelected(), w.isSelected(), newValue, e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        se.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).prewittOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), newValue);
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        sw.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).prewittOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), newValue, se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        e.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).prewittOperator(n.isSelected(), w.isSelected(), s.isSelected(), newValue, nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        w.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).prewittOperator(n.isSelected(), newValue, s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 5, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 5, 3,1,4);
        grid.add(new Label("Output Image:"), 6, 2);
        grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 6, 8);

        outputBtn.setOnAction((ev) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void sobel(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).sobelOperator(false,false,true,true,false,false,false,false);
        Stage stage = new Stage();
        stage.setTitle("Apply Sobel");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Apply Sobel");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Directions:"), 0, 2, 1,3);

        CheckBox n = new CheckBox();

        n.setSelected(false);
        CheckBox nw = new CheckBox();
        nw.setSelected(false);
        CheckBox ne = new CheckBox();
        ne.setSelected(false);
        CheckBox s = new CheckBox();
        s.setSelected(true);
        CheckBox sw = new CheckBox();
        sw.setSelected(false);
        CheckBox se = new CheckBox();
        se.setSelected(false);
        CheckBox e = new CheckBox();
        e.setSelected(true);
        CheckBox w = new CheckBox();
        w.setSelected(false);
        grid.add(n, 3,3);
        grid.add(nw, 2,3);
        grid.add(ne, 4,3);
        grid.add(s, 3,5);
        grid.add(sw, 2,5);
        grid.add(se, 4,5);
        grid.add(e, 4,4);
        grid.add(w, 2,4);
        n.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).sobelOperator(newValue, w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        ne.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).sobelOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), newValue, sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1, 4);
        }));
        nw.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).sobelOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), newValue, ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        s.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).sobelOperator(n.isSelected(), w.isSelected(), newValue, e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        se.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).sobelOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), newValue);
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        sw.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).sobelOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), newValue, se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        e.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).sobelOperator(n.isSelected(), w.isSelected(), s.isSelected(), newValue, nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        w.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).sobelOperator(n.isSelected(), newValue, s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 5, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 5, 3,1,4);
        grid.add(new Label("Output Image:"), 6, 2);
        grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 6, 8);

        outputBtn.setOnAction((ev) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void kirsh(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).kirshOperator(false,false,true,true,false,false,false,false);
        Stage stage = new Stage();
        stage.setTitle("Apply Kirsh");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Apply Kirsh");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Directions:"), 0, 2, 1,3);

        CheckBox n = new CheckBox();

        n.setSelected(false);
        CheckBox nw = new CheckBox();
        nw.setSelected(false);
        CheckBox ne = new CheckBox();
        ne.setSelected(false);
        CheckBox s = new CheckBox();
        s.setSelected(true);
        CheckBox sw = new CheckBox();
        sw.setSelected(false);
        CheckBox se = new CheckBox();
        se.setSelected(false);
        CheckBox e = new CheckBox();
        e.setSelected(true);
        CheckBox w = new CheckBox();
        w.setSelected(false);
        grid.add(n, 3,3);
        grid.add(nw, 2,3);
        grid.add(ne, 4,3);
        grid.add(s, 3,5);
        grid.add(sw, 2,5);
        grid.add(se, 4,5);
        grid.add(e, 4,4);
        grid.add(w, 2,4);
        n.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).kirshOperator(newValue, w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        ne.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).kirshOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), newValue, sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1, 4);
        }));
        nw.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).kirshOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), newValue, ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        s.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).kirshOperator(n.isSelected(), w.isSelected(), newValue, e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        se.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).kirshOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), newValue);
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        sw.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).kirshOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), newValue, se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        e.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).kirshOperator(n.isSelected(), w.isSelected(), s.isSelected(), newValue, nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        w.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).kirshOperator(n.isSelected(), newValue, s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 5, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 5, 3,1,4);
        grid.add(new Label("Output Image:"), 6, 2);
        grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 6, 8);

        outputBtn.setOnAction((ev) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void mask5a(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).mask5aOperator(false,false,true,true,false,false,false,false);
        Stage stage = new Stage();
        stage.setTitle("Apply Mask 5a");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Apply Mask 5a");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Directions:"), 0, 2, 1,3);

        CheckBox n = new CheckBox();

        n.setSelected(false);
        CheckBox nw = new CheckBox();
        nw.setSelected(false);
        CheckBox ne = new CheckBox();
        ne.setSelected(false);
        CheckBox s = new CheckBox();
        s.setSelected(true);
        CheckBox sw = new CheckBox();
        sw.setSelected(false);
        CheckBox se = new CheckBox();
        se.setSelected(false);
        CheckBox e = new CheckBox();
        e.setSelected(true);
        CheckBox w = new CheckBox();
        w.setSelected(false);
        grid.add(n, 3,3);
        grid.add(nw, 2,3);
        grid.add(ne, 4,3);
        grid.add(s, 3,5);
        grid.add(sw, 2,5);
        grid.add(se, 4,5);
        grid.add(e, 4,4);
        grid.add(w, 2,4);
        n.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).mask5aOperator(newValue, w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        ne.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).mask5aOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), newValue, sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1, 4);
        }));
        nw.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).mask5aOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), newValue, ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        s.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).mask5aOperator(n.isSelected(), w.isSelected(), newValue, e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3, 1,4);
        }));
        se.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).mask5aOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), newValue);
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        sw.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).mask5aOperator(n.isSelected(), w.isSelected(), s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), newValue, se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        e.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).mask5aOperator(n.isSelected(), w.isSelected(), s.isSelected(), newValue, nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));
        w.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).mask5aOperator(n.isSelected(), newValue, s.isSelected(), e.isSelected(), nw.isSelected(), ne.isSelected(), sw.isSelected(), se.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);
        }));

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 5, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 5, 3,1,4);
        grid.add(new Label("Output Image:"), 6, 2);
        grid.add(new ImageView(outputImage.getRenderer()), 6, 3,1,4);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 6, 8);

        outputBtn.setOnAction((ev) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void bilateralFilter(ImageColor originalImage) {
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).bilateralFilter(3,1,1);

        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Apply bilateral filter");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Mask size:"), 0, 1);
        TextField nField = new TextField();
        nField.setMaxWidth(60);
        grid.add(nField, 1, 1);

        grid.add(new Label("Sigma S:"), 0, 2);
        TextField ssField = new TextField();
        ssField.setMaxWidth(60);
        grid.add(ssField, 1, 2);


        grid.add(new Label("Sigma R:"), 0, 3);
        TextField srField = new TextField();
        srField.setMaxWidth(60);
        grid.add(srField, 1, 3);

        nField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}")) {
                nField.setText(oldValue);
            } else {
                int n = 0;
                double sigmaS = 0;
                double sigmaR = 0;
                try {
                    n = Integer.parseInt(newValue);
                    sigmaS = Float.parseFloat(ssField.getText());
                    sigmaR = Float.parseFloat(srField.getText());
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(n >= 0){
                    grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(this.originalImage).bilateralFilter(n, sigmaS, sigmaR);
                    grid.add(new ImageView(outputImage.getRenderer()), 2, 5);

                }
            }
        });
        ssField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}")) {
                ssField.setText(oldValue);
            } else {
                int n = 0;
                double sigmaS = 0;
                double sigmaR = 0;
                try {
                    n = Integer.parseInt(nField.getText());
                    sigmaS = Float.parseFloat(newValue);
                    sigmaR = Float.parseFloat(srField.getText());
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(n >= 0){
                    grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(this.originalImage).bilateralFilter(n, sigmaS, sigmaR);
                    grid.add(new ImageView(outputImage.getRenderer()), 2, 5);

                }
            }
        });

        srField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}")) {
                srField.setText(oldValue);
            } else {
                int n = 0;
                double sigmaS = 0;
                double sigmaR = 0;
                try {
                    n = Integer.parseInt(nField.getText());
                    sigmaS = Float.parseFloat(ssField.getText());
                    sigmaR = Float.parseFloat(newValue);
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(n >= 0){
                    grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(this.originalImage).bilateralFilter(n, sigmaS, sigmaR);
                    grid.add(new ImageView(outputImage.getRenderer()), 2, 5);

                }
            }
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 4);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 5, 2, 1);
        grid.add(new Label("Output Image:"), 2, 4);
        grid.add(new ImageView(outputImage.getRenderer()), 2, 5);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 2, 6);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor)outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.setTitle("Apply gamma function");
        stage.show();
    }


    public void gammaFunction(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).imageProd(1);

        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Apply gamma function");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Gamma:"), 0, 1);
        TextField multField = new TextField();
        multField.setMaxWidth(60);
        grid.add(multField, 1, 1);
        multField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                multField.setText(oldValue);
            } else {
                float multiplier = 0;
                try {
                    multiplier = Float.parseFloat(multField.getText());
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(multiplier >= 0){
                    grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(this.originalImage).gammaFunction(multiplier);
                    grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

                }
            }
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 3);
        grid.add(new Label("Output Image:"), 1, 2);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 4);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor)outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.setTitle("Apply gamma function");
        stage.show();
    }

    public void negative(ImageColor originalImage){
        ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor)new Functions(originalImage).negative(),windowIndex));
    }

    public void meanFilter(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).meanFilter(0);

        Stage stage = new Stage();
        stage.setTitle("Apply mean filter");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text scenetitle = new Text("Apply mean filter");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Mask Size:"), 0, 1);
        TextField multField = new TextField();
        multField.setMaxWidth(60);
        grid.add(multField, 1, 1);
        multField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                multField.setText(oldValue);
            } else {
                int multiplier = 0;
                try {
                    multiplier = Integer.parseInt(multField.getText());
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(multiplier >= 0){
                    grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(this.originalImage).meanFilter(multiplier);
                    grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

                }
            }
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 3);
        grid.add(new Label("Output Image:"), 1, 2);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 4);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }


    public void medianFilter(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).medianFilter(1);

        Stage stage = new Stage();
        stage.setTitle("Apply median filter");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text scenetitle = new Text("Apply median filter");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Mask size:"), 0, 1);
        TextField multField = new TextField();
        multField.setMaxWidth(60);
        multField.setText("1");
        grid.add(multField, 1, 1);
        multField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                multField.setText(oldValue);
            } else {
                int multiplier = 0;
                try {
                    multiplier = Integer.parseInt(multField.getText());
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(multiplier >= 0){
                    grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(this.originalImage).medianFilter(multiplier);
                    grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

                }
            }
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 3);
        grid.add(new Label("Output Image:"), 1, 2);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 4);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void weightedMedianFilter(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).weightedMedianFilter(1);

        Stage stage = new Stage();
        stage.setTitle("Apply weighted median filter");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text scenetitle = new Text("Apply weighted median filter");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Mask Size:"), 0, 1);
        TextField multField = new TextField();
        multField.setText("1");
        multField.setMaxWidth(60);
        grid.add(multField, 1, 1);
        multField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                multField.setText(oldValue);
            } else {
                int multiplier = 0;
                try {
                    multiplier = Integer.parseInt(multField.getText());
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(multiplier >= 0){
                    grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(this.originalImage).weightedMedianFilter(multiplier);
                    grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

                }
            }
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 3);
        grid.add(new Label("Output Image:"), 1, 2);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 4);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void highpassFilter(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).highpassFilter(1);

        Stage stage = new Stage();
        stage.setTitle("Apply highpass filter");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text scenetitle = new Text("Apply highpass filter");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Mask size:"), 0, 1);
        TextField multField = new TextField();
        multField.setMaxWidth(60);
        multField.setText("1");
        grid.add(multField, 1, 1);
        multField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                multField.setText(oldValue);
            } else {
                int multiplier = 0;
                try {
                    multiplier = Integer.parseInt(multField.getText());
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(multiplier >= 0){
                    grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(this.originalImage).highpassFilter(multiplier);
                    grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

                }
            }
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 3);
        grid.add(new Label("Output Image:"), 1, 2);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 4);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void gaussFilter(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).gaussFilter(1,0);

        Stage stage = new Stage();
        stage.setTitle("Apply gaussian filter");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text scenetitle = new Text("Apply gaussian filter");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Mask Size:"), 0, 1);
        TextField multField = new TextField();
        multField.setMaxWidth(60);
        multField.setText("1");
        grid.add(multField, 1, 1);

        multField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                multField.setText(oldValue);
            } else {
                int multiplier = 0;
                try {
                    multiplier = Integer.parseInt(multField.getText());
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(multiplier >= 0){
                    grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(this.originalImage).gaussFilter(multiplier, (multiplier-1)/2);
                    grid.add(new ImageView(outputImage.getRenderer()), 1, 4);

                }
            }
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 3);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 4);
        grid.add(new Label("Output Image:"), 1, 3);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 4);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 5);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void globalThresholding(ImageColor originalImage) {
        ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer(new Functions(originalImage).globalThresholdizationColor(),windowIndex));
    }

    public void otsuThresholding(ImageColor originalImage) {
        ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer(new Functions(originalImage).otsuThresholdColor(),windowIndex));
    }

    public void contourTracing(ImageColor originalImage) {
        ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor)new Functions(originalImage).activeContorns(280,280,10,1000),windowIndex));
    }

    public void anisotropic(ImageColor originalImage) {
        this.originalImage = originalImage;
        this.outputImage = originalImage;
        Stage stage = new Stage();
        stage.setTitle("Anisotropic Diffusion");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Anisotropic Diffusion");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Edge detector:"), 1, 3);
        ToggleGroup tg = new ToggleGroup();
        RadioButton lor = new RadioButton();
        RadioButton lec = new RadioButton();
        lor.setText("Lorentz");
        lec.setText("Leclerc");
        lor.setToggleGroup(tg);
        lec.setToggleGroup(tg);
        lor.setSelected(true);

        grid.add(lor, 2,3);
        grid.add(lec, 2,4);

        grid.add(new Label("Sigma:"), 1, 1);
        TextField sigmaField = new TextField();
        sigmaField.setMaxWidth(60);
        grid.add(sigmaField, 2, 1);
        sigmaField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                sigmaField.setText(oldValue);
            }
        });

        grid.add(new Label("Iterations:"), 1, 2);
        TextField iterField = new TextField();
        iterField.setMaxWidth(60);
        grid.add(iterField, 2, 2);
        iterField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                iterField.setText(oldValue);
            }
        });

        Button preOutputBtn = new Button("Apply");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(preOutputBtn);
        grid.add(hbBtn1, 2, 6);

        preOutputBtn.setOnAction((e) -> {
            double sigma = 10;
            int iter = 10;
            try {
                    iter = Integer.parseInt(iterField.getText());
                    sigma = Float.parseFloat(sigmaField.getText());
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).diffusion(sigma, iter, true, ((RadioButton)tg.getSelectedToggle()).getText().equals("Lorentz"));
            grid.add(new ImageView(outputImage.getRenderer()), 2, 5, 2, 1);
        });

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 3, 6);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });


        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();

    }

    public void isotropic(ImageColor originalImage) {
        this.originalImage = originalImage;
        this.outputImage = originalImage;
        Stage stage = new Stage();
        stage.setTitle("Isotropic Diffusion");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Isotropic Diffusion");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Edge detector:"), 1, 3);
        ToggleGroup tg = new ToggleGroup();
        RadioButton lor = new RadioButton();
        RadioButton lec = new RadioButton();
        lor.setText("Lorentz");
        lec.setText("Leclerc");
        lor.setToggleGroup(tg);
        lec.setToggleGroup(tg);
        lor.setSelected(true);

        grid.add(lor, 2,3);
        grid.add(lec, 2,4);

        grid.add(new Label("Sigma:"), 1, 1);
        TextField sigmaField = new TextField();
        sigmaField.setMaxWidth(60);
        grid.add(sigmaField, 2, 1);
        sigmaField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                sigmaField.setText(oldValue);
            }
        });

        grid.add(new Label("Iterations:"), 1, 2);
        TextField iterField = new TextField();
        iterField.setMaxWidth(60);
        grid.add(iterField, 2, 2);
        iterField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                iterField.setText(oldValue);
            }
        });

        Button preOutputBtn = new Button("Apply");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(preOutputBtn);
        grid.add(hbBtn1, 2, 6);

        preOutputBtn.setOnAction((e) -> {
            double sigma = 10;
            int iter = 10;
            try {
                iter = Integer.parseInt(iterField.getText());
                sigma = Float.parseFloat(sigmaField.getText());
            } catch (NumberFormatException | NullPointerException nfe) {
                return;
            }
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).diffusion(sigma, iter, false, ((RadioButton)tg.getSelectedToggle()).getText().equals("Lorentz"));
            grid.add(new ImageView(outputImage.getRenderer()), 2, 5, 2, 1);
        });

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 3, 6);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });


        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void laplaceEvaluated(ImageColor originalImage) {
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).laplaceEvaluated(128, true);

        Stage stage = new Stage();
        stage.setTitle("Apply threshold");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Apply Laplace with threshold");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Threshold:"), 0, 1);
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(255);
        slider.setValue(128);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(64);
        slider.setMinorTickCount(8);
        slider.setBlockIncrement(1);
//        TextField multField = new TextField();
//        multField.setMaxWidth(60);
//        grid.add(multField, 1, 1);
        grid.add(slider, 1, 1);

        CheckBox eval = new CheckBox();
        eval.setSelected(true);
        grid.add(new Label("Slope Eval:"), 0, 2);
        grid.add(eval, 1, 2);
        eval.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).laplaceEvaluated((int) Math.floor(slider.getValue()), newValue);
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        }));

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).laplaceEvaluated(newValue.intValue(), eval.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 3);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 4);
        grid.add(new Label("Output Image:"), 1, 3);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 4);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 5);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage, windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }


    public void laplacianOfGaussianEvaluated(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(originalImage).laplacianOfGaussianEvaluated(7, 1.0, 128, true);

        Stage stage = new Stage();
        stage.setTitle("Apply laplacian of gaussian filter");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Apply laplacian of gaussian filter");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Mask Size:"), 0, 1);
        TextField multField = new TextField();
        multField.setMaxWidth(60);
        multField.setText("7");
        grid.add(multField, 1, 1);

        grid.add(new Label("Sigma:"), 0, 2);
        TextField sigmaField = new TextField();
        sigmaField.setMaxWidth(60);
        sigmaField.setText("1");
        grid.add(sigmaField, 1, 2);

        grid.add(new Label("Threshold:"), 0, 3);
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(255);
        slider.setValue(128);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(64);
        slider.setMinorTickCount(8);
        slider.setBlockIncrement(1);
        grid.add(slider, 1, 3);

        CheckBox eval = new CheckBox();
        eval.setSelected(true);
        grid.add(new Label("Slope Eval:"), 0, 4);
        grid.add(eval, 1, 4);
        eval.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).laplacianOfGaussianEvaluated(Integer.parseInt(multField.getText()),
                    Double.parseDouble(sigmaField.getText()), (int) Math.floor(slider.getValue()), newValue);
            grid.add(new ImageView(outputImage.getRenderer()), 1, 6);
        }));

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).laplacianOfGaussianEvaluated(
                    Integer.parseInt(multField.getText()), Double.parseDouble(sigmaField.getText()),newValue.intValue(), eval.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 6);
        });

        multField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                multField.setText(oldValue);
            } else {
                int size = 0;
                try {
                    size = Integer.parseInt(multField.getText());
                    if(size<7){
                        size = 7;
                    }
                    if(size>30){
                        size = 30;
                    }
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(size >= 0){
                    grid.getChildren().remove(outputImage.getView());
                    this.outputImage = new Functions(this.originalImage)
                            .laplacianOfGaussianEvaluated(size, Double.parseDouble(sigmaField.getText()), (int) Math.round(slider.getValue()), eval.isSelected());
                    grid.add(new ImageView(outputImage.getRenderer()), 1, 6);
                }
            }
        });

        sigmaField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            double sigma;
            try {
                sigma = Double.parseDouble(sigmaField.getText());
            } catch (NumberFormatException | NullPointerException nfe) {
                return;
            }
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage)
                    .laplacianOfGaussianEvaluated(Integer.parseInt(multField.getText()), sigma, (int) Math.round(slider.getValue()), eval.isSelected());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 6);
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 5);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 6);
        grid.add(new Label("Output Image:"), 1, 5);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 6);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 7);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage, windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }


    public void thresholding(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).thresholdizationColor(128,128,128);

        Stage stage = new Stage();
        stage.setTitle("Apply threshold");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Apply threshold");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Red:"), 0, 1);
        Slider sliderR = new Slider();
        sliderR.setMin(0);
        sliderR.setMax(255);
        sliderR.setValue(128);
        sliderR.setShowTickLabels(true);
        sliderR.setShowTickMarks(true);
        sliderR.setMajorTickUnit(128);
        sliderR.setMinorTickCount(32);
        sliderR.setBlockIncrement(1);
        grid.add(new Label("Green:"), 0, 2);
        Slider sliderG = new Slider();
        sliderG.setMin(0);
        sliderG.setMax(255);
        sliderG.setValue(128);
        sliderG.setShowTickLabels(true);
        sliderG.setShowTickMarks(true);
        sliderG.setMajorTickUnit(128);
        sliderG.setMinorTickCount(32);
        sliderG.setBlockIncrement(1);
        grid.add(new Label("Blue:"), 0, 3);
        Slider sliderB = new Slider();
        sliderB.setMin(0);
        sliderB.setMax(255);
        sliderB.setValue(128);
        sliderB.setShowTickLabels(true);
        sliderB.setShowTickMarks(true);
        sliderB.setMajorTickUnit(128);
        sliderB.setMinorTickCount(32);
        sliderB.setBlockIncrement(1);
//        TextField multField = new TextField();
//        multField.setMaxWidth(60);
//        grid.add(multField, 1, 1);
        grid.add(sliderR, 1, 1);
        sliderR.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).thresholdizationColor(newValue.intValue(), (int) sliderG.getValue(), (int) sliderB.getValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 5);
        });
        grid.add(sliderG, 1, 2);
        sliderG.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).thresholdizationColor((int) sliderR.getValue(), newValue.intValue(), (int)sliderB.getValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 5);
        });
        grid.add(sliderB, 1, 3);
        sliderB.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).thresholdizationColor((int) sliderR.getValue(), (int) sliderG.getValue(), newValue.intValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 5);
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 4);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 5);
        grid.add(new Label("Output Image:"), 1, 4);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 5);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 6);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void addGaussianNoise(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(originalImage).addGaussianNoiseColor(0, 0.1);

        Stage stage = new Stage();
        stage.setTitle("Add gaussian noise");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Add gaussian noise");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Density:"), 0, 1);
        Slider densitySlider = new Slider();
        densitySlider.setMin(0);
        densitySlider.setMax(1);
        densitySlider.setValue(0);
        densitySlider.setShowTickLabels(true);
        densitySlider.setShowTickMarks(true);
        densitySlider.setMajorTickUnit(0.25);
        densitySlider.setBlockIncrement(0.05);
        grid.add(densitySlider, 1, 1);

        grid.add(new Label("Sigma:"), 0, 2);
        Slider sigmaSlider = new Slider();
        sigmaSlider.setMin(0);
        sigmaSlider.setMax(50);
        sigmaSlider.setValue(0.1);
        sigmaSlider.setShowTickLabels(true);
        sigmaSlider.setShowTickMarks(true);
        sigmaSlider.setMajorTickUnit(25);
        sigmaSlider.setMinorTickCount(5);
        sigmaSlider.setBlockIncrement(1);
        grid.add(sigmaSlider, 1, 2);

        densitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).addGaussianNoiseColor(newValue.doubleValue(), sigmaSlider.getValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });
        sigmaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).addGaussianNoiseColor(densitySlider.getValue(), newValue.doubleValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 3);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 4);
        grid.add(new Label("Output Image:"), 1, 3);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 4);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 5);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void addRayleighNoise(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(originalImage).addRayleighNoiseColor(0, 0);

        Stage stage = new Stage();
        stage.setTitle("Add rayleigh noise");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Add rayleigh noise");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Density:"), 0, 1);
        Slider densitySlider = new Slider();
        densitySlider.setMin(0);
        densitySlider.setMax(1);
        densitySlider.setValue(0);
        densitySlider.setShowTickLabels(true);
        densitySlider.setShowTickMarks(true);
        densitySlider.setMajorTickUnit(0.25);
        densitySlider.setBlockIncrement(0.05);
        grid.add(densitySlider, 1, 1);

        grid.add(new Label("Psi:"), 0, 2);
        Slider psiSlider = new Slider();
        psiSlider.setMin(0);
        psiSlider.setMax(2);
        psiSlider.setValue(0);
        psiSlider.setShowTickLabels(true);
        psiSlider.setShowTickMarks(true);
        psiSlider.setMajorTickUnit(1);
        psiSlider.setBlockIncrement(0.05);
        grid.add(psiSlider, 1, 2);

        densitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).addRayleighNoiseColor(newValue.doubleValue(), psiSlider.getValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });
        psiSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).addRayleighNoiseColor(densitySlider.getValue(), newValue.doubleValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 3);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 4);
        grid.add(new Label("Output Image:"), 1, 3);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 4);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 5);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void addExponentialNoise(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(originalImage).addExponentialNoiseColor(0, 0);

        Stage stage = new Stage();
        stage.setTitle("Add exponential noise");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Add exponential noise");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Density:"), 0, 1);
        Slider densitySlider = new Slider();
        densitySlider.setMin(0);
        densitySlider.setMax(1);
        densitySlider.setValue(0);
        densitySlider.setShowTickLabels(true);
        densitySlider.setShowTickMarks(true);
        densitySlider.setMajorTickUnit(0.25);
        densitySlider.setBlockIncrement(0.05);
        grid.add(densitySlider, 1, 1);

        grid.add(new Label("Lambda:"), 0, 2);
        Slider lambdaSlider = new Slider();
        lambdaSlider.setMin(0);
        lambdaSlider.setMax(4);
        lambdaSlider.setValue(0);
        lambdaSlider.setShowTickLabels(true);
        lambdaSlider.setShowTickMarks(true);
        lambdaSlider.setMajorTickUnit(2);
        lambdaSlider.setMinorTickCount(1);
        lambdaSlider.setBlockIncrement(0.1);
        grid.add(lambdaSlider, 1, 2);

        densitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).addExponentialNoiseColor(newValue.doubleValue(), lambdaSlider.getValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });
        lambdaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).addExponentialNoiseColor(densitySlider.getValue(), newValue.doubleValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });


        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 3);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 4);
        grid.add(new Label("Output Image:"), 1, 3);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 4);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 5);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }


    public void addSaltAndPepper(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).addSaltAndPepperColor(0);

        Stage stage = new Stage();
        stage.setTitle("Apply salt and pepper");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Add salt and pepper noise");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Density:"), 0, 1);
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(1);
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.25);
        slider.setBlockIncrement(0.05);
//        TextField multField = new TextField();
//        multField.setMaxWidth(60);
//        grid.add(multField, 1, 1);
        grid.add(slider, 1, 1);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).addSaltAndPepperColor(newValue.floatValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 3);
        });

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 2);
        grid.add(new ImageView(originalImage.getRenderer()), 0, 3);
        grid.add(new Label("Output Image:"), 1, 2);
        grid.add(new ImageView(outputImage.getRenderer()), 1, 3);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 4);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor) outputImage,windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    public void painter(ImageColor originalImage){
        this.outputImage = new ImageColor(originalImage.getRed(), originalImage.getGreen(), originalImage.getBlue(), originalImage.getHeight(), originalImage.getWidth());
        Region cutRegion = new Region();
        cutRegion.x1 = 0;
        cutRegion.y1 = 0;
        cutRegion.x2 = originalImage.getWidth()-1;
        cutRegion.y2 = originalImage.getHeight()-1;


        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Painter");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Text text = new Text("");
        grid.add(text, 0, 1, 2, 1);

        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(event -> {
            cutRegion.paintColor[0] = (int) Math.round(colorPicker.getValue().getRed() * 255);
            cutRegion.paintColor[1] = (int) Math.round(colorPicker.getValue().getGreen() * 255);
            cutRegion.paintColor[2] = (int) Math.round(colorPicker.getValue().getBlue() * 255);
        });

        Button paintBtn = new Button("Get Color");
        grid.add(paintBtn, 0, 2);
        paintBtn.setOnAction((e) -> {
            if(cutRegion.painterState == 0){
                paintBtn.setText("Paint");
                cutRegion.painterState = 1;
                grid.add(colorPicker, 1, 2);
//                grid.add(greenField, 2, 2);
//                grid.add(blueField, 3, 2);
            }else{
                paintBtn.setText("Get Color");
                cutRegion.painterState = 0;
                grid.getChildren().remove(colorPicker);
//                grid.getChildren().remove(greenField);
//                grid.getChildren().remove(blueField);
            }
        });



        grid.add(new Label("Output Image:"), 0, 3);
        grid.add(outputImage.getView(), 0, 4);


        EventHandler<MouseEvent> mousePress = e -> {
            cutRegion.x1 = (int) e.getX();
            cutRegion.y1 = (int) e.getY();
            if(cutRegion.painterState == 0){
                text.setText("X: " + cutRegion.x1 + ", Y: " + cutRegion.y1 + ", Color: " + Arrays.toString(originalImage.getPixel(cutRegion.x1,cutRegion.y1)));
            }else{
                System.out.println("SHOULD PAINT");
                ((ImageColor)outputImage).setPixel(cutRegion.x1,cutRegion.y1, new Integer[]{cutRegion.paintColor[0],cutRegion.paintColor[1],cutRegion.paintColor[2]});
            }
        };
        EventHandler<MouseEvent> mouseDrag = e -> {
            cutRegion.x1 = (int) e.getX();
            cutRegion.y1 = (int) e.getY();
            if(cutRegion.painterState == 1){
                System.out.println("DRAGGING");
                ((ImageColor)outputImage).setPixel(cutRegion.x1,cutRegion.y1, new Integer[]{cutRegion.paintColor[0],cutRegion.paintColor[1],cutRegion.paintColor[2]});
            }
        };
        outputImage.getView().addEventFilter(MouseEvent.MOUSE_PRESSED, mousePress);
        outputImage.getView().addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseDrag);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 5);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor)outputImage, windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.setTitle("Painter");
        System.out.println("Paint");
        stage.show();
    }

    public void cutImage(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new ImageColor(originalImage.getRed().clone(),
                                            originalImage.getGreen().clone(),
                                            originalImage.getBlue().clone(),
                                            originalImage.getHeight(),
                                            originalImage.getWidth());
        Region cutRegion = new Region();
        cutRegion.x1 = 0;
        cutRegion.y1 = 0;
        cutRegion.x2 = originalImage.getWidth()-1;
        cutRegion.y2 = originalImage.getHeight()-1;


        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Cut image");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Text text = new Text("");
        grid.add(text, 0, 1, 2, 1);

        Label firstImageLabel = new Label("First Image:");
        firstImageLabel.setAlignment(Pos.CENTER);
        grid.add(firstImageLabel, 0, 2);
        ImageView input = new ImageView(originalImage.getRenderer());

        grid.add(input, 0, 3);
        grid.add(new Label("Output Image:"), 1, 2);
        grid.add(outputImage.getView(), 1, 3);

        EventHandler<MouseEvent> mousePress = e -> {
            cutRegion.x1 = (int) e.getX();
            cutRegion.y1 = (int) e.getY();
//            text.setText("X: " + cutRegion.x1 + ", Y: " + cutRegion.y1 + ", Color: " + originalImage.getImage()[cutRegion.y1][cutRegion.x1]);
            System.out.println("X1: " + cutRegion.x1 + ", Y1: " + cutRegion.y1 + ", Color: " + originalImage.getPixel(cutRegion.x1,cutRegion.y1));

        };
        EventHandler<MouseEvent> mouseRelease = e -> {
            cutRegion.x2 = (int) e.getX();
            cutRegion.y2 = (int) e.getY();
            System.out.println("X2: " + cutRegion.x2 + ", Y2: " + cutRegion.y2);

            int minX = Math.max(Math.min(cutRegion.x1,cutRegion.x2),0);
            int maxX = Math.min(Math.max(cutRegion.x1,cutRegion.x2),originalImage.getWidth()-1);
            int minY = Math.max(Math.min(cutRegion.y1,cutRegion.y2),0);
            int maxY = Math.min(Math.max(cutRegion.y1,cutRegion.y2),originalImage.getHeight()-1);
            System.out.println("X: [" + minX + ", " + maxX + "]");
            System.out.println("Y: [" + minY + ", " + maxY + "]");

            grid.getChildren().remove(outputImage.getView());
            int width = (maxX-minX)+1;
            int height = (maxY-minY)+1;
            System.out.println("Width:" + width + ", Height:" + height);
            Integer[][] cutImageR = new Integer[height][width];
            Integer[][] cutImageG = new Integer[height][width];
            Integer[][] cutImageB = new Integer[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    cutImageR[i][j] = originalImage.getPixel(minX+j,minY+i)[0];
                    cutImageG[i][j] = originalImage.getPixel(minX+j,minY+i)[1];
                    cutImageB[i][j] = originalImage.getPixel(minX+j,minY+i)[2];
                }
            }
            this.outputImage = new ImageColor(cutImageR, cutImageG, cutImageB, height, width);
            grid.add(outputImage.getView(), 1, 3);
        };
        //Registering the event filter
        input.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePress);
        input.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseRelease);

        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 1, 4);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null){
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageColorViewer((ImageColor)outputImage, windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.setTitle("Multiply image by scalar");
        stage.show();
    }



    class Region{
        public int x1;
        public int y1;
        public int x2;
        public int y2;
        public int painterState = 0;
        public int[] paintColor = new int[3];
    }
}
