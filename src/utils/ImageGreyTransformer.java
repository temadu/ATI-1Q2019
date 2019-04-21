package utils;

import GUI.ATIApp;
import GUI.ImageGreyViewer;
import GUI.ImageGreyViewer;
import com.sun.org.apache.xpath.internal.functions.Function2Args;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ImageGrey;
import models.ImageGrey;
import models.ImageInt;
import tp1.Functions;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class ImageGreyTransformer {

    ImageInt originalImage;
    ImageView originalImageView;
    ImageInt secondImage;
    ImageView secondImageView;
    ImageInt outputImage;
    ImageView outputImageView;
    int windowIndex;

    public ImageGreyTransformer(int windowIndex) {
        this.windowIndex = windowIndex;
    }

    public void sumImages(ImageGrey originalImage){
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
                    new FileChooser.ExtensionFilter("Image files", "*.pgm; *.raw")
            );
            File file = fileChooser.showOpenDialog(stage);
            String extension = file.getName();

            int i = extension.lastIndexOf('.');
            if (i > 0) {
                extension = extension.substring(i+1);
            }
            try {
                if(this.secondImage != null)
                    grid.getChildren().remove(secondImage.getView());
                System.out.println(extension);
                if(extension.toLowerCase().equals("raw")) {
                    this.secondImage = IOManager.loadRAW(file.getPath());
                } else {
                    this.secondImage = IOManager.loadPGM(file.getPath());
                }
                grid.add(this.secondImage.getView(),1,3);
                if(this.originalImage.getHeight() >= this.secondImage.getHeight() &&
                        this.originalImage.getWidth() >= this.secondImage.getWidth()){
                    if(this.outputImage != null)
                        grid.getChildren().remove(outputImage.getView());
                    this.outputImage = (ImageGrey) new Functions(originalImage).imageSum(secondImage);
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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
    public void substractImages(ImageGrey originalImage){
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
                    new FileChooser.ExtensionFilter("Image files", "*.pgm; *.raw")
            );
            File file = fileChooser.showOpenDialog(stage);
            String extension = file.getName();

            int i = extension.lastIndexOf('.');
            if (i > 0) {
                extension = extension.substring(i+1);
            }
            try {
                if(this.secondImage != null)
                    grid.getChildren().remove(secondImage.getView());
                if(extension.toLowerCase().equals("raw")) {
                    this.secondImage = IOManager.loadRAW(file.getPath());
                } else {
                    this.secondImage = IOManager.loadPGM(file.getPath());
                }
                grid.add(this.secondImage.getView(),1,3);
                if(this.originalImage.getHeight() >= this.secondImage.getHeight() &&
                        this.originalImage.getWidth() >= this.secondImage.getWidth()){
                    if(this.outputImage != null)
                        grid.getChildren().remove(outputImage.getView());
                    this.outputImage = (ImageGrey) new Functions(originalImage).imageSub(secondImage);
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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
    public void multiplyImage(ImageGrey originalImage){
        this.originalImage = originalImage;
        this.outputImage = (ImageGrey) new Functions(this.originalImage).imageProd(1);

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
                    this.outputImage = (ImageGrey) new Functions(this.originalImage).imageProd(multiplier);
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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
    public void dynamicRangeCompression(ImageGrey originalImage){
        ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)new Functions(originalImage).rangeCompressor(), windowIndex));
    }
    public void gammaFunction(ImageGrey originalImage){
        this.originalImage = originalImage;
        this.outputImage = (ImageGrey) new Functions(this.originalImage).imageProd(1);

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
                    this.outputImage = (ImageGrey) new Functions(this.originalImage).gammaFunction(multiplier);
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void negative(ImageGrey originalImage){
        ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)new Functions(originalImage).negative(),windowIndex));
    }

    public void prewitt(ImageGrey originalImage){
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage,windowIndex));
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

    public void sobel(ImageGrey originalImage){
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage,windowIndex));
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

    public void kirsh(ImageGrey originalImage){
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage,windowIndex));
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

    public void mask5a(ImageGrey originalImage){
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage,windowIndex));
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

    public void bilateralFilter(ImageGrey originalImage) {
        ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)new Functions(originalImage).bilateralFilter(7,2,30),windowIndex));
    }

    public void histogramEqualization(ImageGrey originalImage){
        ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer(new Functions(originalImage).histogramEqualization(),windowIndex));
    }

    public void globalThreshold(ImageGrey originalImage) {
        ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer(new Functions(originalImage).globalThresholdization(),windowIndex));
    }

    public void threshold(ImageGrey originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).thresholdization(128);

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

        grid.add(new Label("Threshold:"), 0, 1);
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(255);
        slider.setValue(128);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(128);
        slider.setMinorTickCount(32);
        slider.setBlockIncrement(1);
//        TextField multField = new TextField();
//        multField.setMaxWidth(60);
//        grid.add(multField, 1, 1);
        grid.add(slider, 1, 1);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).thresholdization(newValue.intValue());
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void greyContrast(ImageGrey originalImage){
        this.originalImage = originalImage;
        Functions f = new Functions(this.originalImage);
        this.outputImage = f.greyContrast(1);

        Stage stage = new Stage();
        stage.setTitle("Apply contrast");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Apply contrast");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Sigma:"), 0, 1);
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(4);
        slider.setValue(1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(0.5);
        slider.setBlockIncrement(0.1);
//        TextField multField = new TextField();
//        multField.setMaxWidth(60);
//        grid.add(multField, 1, 1);
        grid.add(slider, 1, 1);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = f.greyContrast(newValue.doubleValue());
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void addGaussianNoise(ImageGrey originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(originalImage).addGaussianNoise(0.2, 10);

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
        sigmaSlider.setValue(0);
        sigmaSlider.setShowTickLabels(true);
        sigmaSlider.setShowTickMarks(true);
        sigmaSlider.setMajorTickUnit(25);
        sigmaSlider.setMinorTickCount(5);
        sigmaSlider.setBlockIncrement(1);
        grid.add(sigmaSlider, 1, 2);

        densitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).addGaussianNoise(newValue.doubleValue(), sigmaSlider.getValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });
        sigmaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).addGaussianNoise(densitySlider.getValue(), newValue.doubleValue());
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void addRayleighNoise(ImageGrey originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(originalImage).addRayleighNoise(0.2, 2);

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
            this.outputImage = new Functions(this.originalImage).addRayleighNoise(newValue.doubleValue(), psiSlider.getValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });
        psiSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).addRayleighNoise(densitySlider.getValue(), newValue.doubleValue());
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void addExponentialNoise(ImageGrey originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(originalImage).addExponentialNoise(0.2, 1);

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
            this.outputImage = new Functions(this.originalImage).addExponentialNoise(newValue.doubleValue(), lambdaSlider.getValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });
        lambdaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).addExponentialNoise(densitySlider.getValue(), newValue.doubleValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });

//        grid.add(new Label("Density:"), 0, 1);
//        TextField densityField = new TextField();
//        densityField.setMaxWidth(60);
//        densityField.setText("0.2");
//        grid.add(densityField, 1, 1);
//
//
//        grid.add(new Label("lambda:"), 0, 2);
//        TextField lambdaField = new TextField();
//        lambdaField.setMaxWidth(60);
//        lambdaField.setText("1");
//        grid.add(lambdaField, 1, 2);
//        densityField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
//                densityField.setText(oldValue);
//            } else {
//                float density = 0;
//                float lambda = 0;
//                try {
//                    density = Float.parseFloat(densityField.getText());
//                    lambda = Float.parseFloat(lambdaField.getText());
//                } catch (NumberFormatException | NullPointerException nfe) {
//                    return;
//                }
//                if(density >= 0 && lambda >= 0){
//                    grid.getChildren().remove(outputImage.getView());
//                    this.outputImage = new Functions(this.originalImage).addExponentialNoise(density,lambda);
//                    grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
//
//                }
//            }
//        });
//        lambdaField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
//                lambdaField.setText(oldValue);
//            } else {
//                float density = 0;
//                float lambda = 0;
//                try {
//                    density = Float.parseFloat(densityField.getText());
//                    lambda = Float.parseFloat(densityField.getText());
//                } catch (NumberFormatException | NullPointerException nfe) {
//                    return;
//                }
//                if(density >= 0 && lambda >= 0){
//                    grid.getChildren().remove(outputImage.getView());
//                    this.outputImage = new Functions(this.originalImage).addExponentialNoise(density,lambda);
//                    grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
//
//                }
//            }
//        });

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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void addSaltAndPepper(ImageGrey originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).addSaltAndPepper(0);

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
            this.outputImage = new Functions(this.originalImage).addSaltAndPepper(newValue.floatValue());
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void meanFilter(ImageGrey originalImage){
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void medianFilter(ImageGrey originalImage){
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void weightedMedianFilter(ImageGrey originalImage){
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void highpassFilter(ImageGrey originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).highpassFilter(3);

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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void gaussFilter(ImageGrey originalImage){
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

//        grid.add(new Label("Sigma:"), 0, 2);
//        Slider sigmaSlider = new Slider();
//        sigmaSlider.setMin(0);
//        sigmaSlider.setMax(10);
//        sigmaSlider.setValue(0.1);
//        sigmaSlider.setShowTickLabels(true);
//        sigmaSlider.setShowTickMarks(true);
//        sigmaSlider.setMajorTickUnit(5);
//        sigmaSlider.setMinorTickCount(1);
//        sigmaSlider.setBlockIncrement(1);
//        grid.add(sigmaSlider, 1, 2);
//
//        sigmaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
//            int multiplier = 0;
//            try {
//                multiplier = Integer.parseInt(multField.getText());
//            } catch (NumberFormatException | NullPointerException nfe) {
//                return;
//            }
//            grid.getChildren().remove(outputImage.getView());
//            this.outputImage = new Functions(this.originalImage).gaussFilter(multiplier, newValue.doubleValue());
//            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
//        });
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    public void cutImage(ImageGrey originalImage){
        this.originalImage = originalImage;
        this.outputImage = new ImageGrey(originalImage.getImage().clone(), originalImage.getHeight(), originalImage.getWidth());
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
            System.out.println("X1: " + cutRegion.x1 + ", Y1: " + cutRegion.y1 + ", Color: " + originalImage.getImage()[cutRegion.y1][cutRegion.x1]);

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
            Integer[][] cutImage = new Integer[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    cutImage[i][j] = originalImage.getPixel(minX+j,minY+i);
                }
            }
            this.outputImage = new ImageGrey(cutImage, height, width);
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
                stage.close();
            }

        });

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(grid);

        Scene scene = new Scene(scroller);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.setTitle("Cut image");
        stage.show();
    }


    public void painter(ImageGrey originalImage){
        this.outputImage = new ImageGrey(originalImage.getImage(), originalImage.getHeight(), originalImage.getWidth());
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

//        Label firstImageLabel = new Label("First Image:");
//        firstImageLabel.setAlignment(Pos.CENTER);
//        grid.add(firstImageLabel, 0, 2);
//        ImageView input = new ImageView(originalImage.getRenderer());
//        grid.add(input, 0, 3);

        TextField colorField = new TextField();
        colorField.setText("" + cutRegion.paintColor);
        colorField.setMaxWidth(60);
        colorField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                colorField.setText(oldValue);
            } else {
                int color = 0;
                try {
                    color = Integer.parseInt(colorField.getText());
                } catch (NumberFormatException | NullPointerException nfe) {
                    return;
                }
                if(color >= 0 && color <= 255){
                    cutRegion.paintColor = color;
                }
            }
        });

        Button paintBtn = new Button("Get Color");
        grid.add(paintBtn, 0, 2);
        paintBtn.setOnAction((e) -> {
            if(cutRegion.painterState == 0){
                paintBtn.setText("Paint");
                cutRegion.painterState = 1;
                grid.add(colorField, 1, 2);
            }else{
                paintBtn.setText("Get Color");
                cutRegion.painterState = 0;
                grid.getChildren().remove(colorField);
            }
        });



        grid.add(new Label("Output Image:"), 0, 3);
        grid.add(outputImage.getView(), 0, 4);


        EventHandler<MouseEvent> mousePress = e -> {
            cutRegion.x1 = (int) e.getX();
            cutRegion.y1 = (int) e.getY();
            if(cutRegion.painterState == 0){
                text.setText("X: " + cutRegion.x1 + ", Y: " + cutRegion.y1 + ", Color: " + originalImage.getImage()[cutRegion.y1][cutRegion.x1]);
            }else{
                ((ImageGrey)outputImage).setPixel(cutRegion.x1,cutRegion.y1,cutRegion.paintColor);
            }
        };
        EventHandler<MouseEvent> mouseDrag = e -> {
            cutRegion.x1 = (int) e.getX();
            cutRegion.y1 = (int) e.getY();
            if(cutRegion.painterState == 1){
                ((ImageGrey)outputImage).setPixel(cutRegion.x1,cutRegion.y1,cutRegion.paintColor);
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
                ATIApp.WINDOWS.get(windowIndex).addImageViewer(new ImageGreyViewer((ImageGrey)outputImage, windowIndex));
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

    class Region{
        public int x1;
        public int y1;
        public int x2;
        public int y2;
        public int painterState = 0;
        public int paintColor = 0;
    }


}
