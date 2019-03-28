package utils;

import GUI.ImageColorViewer;
import GUI.ImageGreyViewer;
import GUI.Window;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;
import tp1.Functions;

import java.io.File;
import java.io.IOException;

public class ImageColorTransformer {

    ImageInt originalImage;
    ImageInt secondImage;
    ImageInt outputImage;

    public void sumImages(ImageColor originalImage){
        this.originalImage = originalImage;

        Stage stage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Add Images");
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
                if(this.originalImage.getHeight() == this.secondImage.getHeight() &&
                        this.originalImage.getWidth() == this.secondImage.getWidth()){
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
                new ImageColorViewer((ImageColor)outputImage);
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

        Text scenetitle = new Text("Add Images");
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
                if(this.originalImage.getHeight() == this.secondImage.getHeight() &&
                        this.originalImage.getWidth() == this.secondImage.getWidth()){
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
                new ImageColorViewer((ImageColor)outputImage);
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

        Text scenetitle = new Text("Add Images");
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
                new ImageColorViewer((ImageColor)outputImage);
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
        new ImageColorViewer((ImageColor)new Functions(originalImage).rangeCompressor());
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

        Text scenetitle = new Text("Add Images");
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
                new ImageColorViewer((ImageColor)outputImage);
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
        new ImageColorViewer((ImageColor)new Functions(originalImage).negative());
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
                new ImageColorViewer((ImageColor) outputImage);
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
                new ImageColorViewer((ImageColor) outputImage);
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
                new ImageColorViewer((ImageColor) outputImage);
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

    public void laplacianFilter(ImageColor originalImage){
        this.originalImage = originalImage;
        this.outputImage = new Functions(this.originalImage).laplacianFilter(1);

        Stage stage = new Stage();
        stage.setTitle("Apply laplacian filter");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text scenetitle = new Text("Apply laplacian filter");
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
                    this.outputImage = new Functions(this.originalImage).laplacianFilter(multiplier);
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
                new ImageColorViewer((ImageColor) outputImage);
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

        grid.add(new Label("Sigma:"), 0, 2);
        Slider sigmaSlider = new Slider();
        sigmaSlider.setMin(0);
        sigmaSlider.setMax(10);
        sigmaSlider.setValue(0.1);
        sigmaSlider.setShowTickLabels(true);
        sigmaSlider.setShowTickMarks(true);
        sigmaSlider.setMajorTickUnit(5);
        sigmaSlider.setMinorTickCount(1);
        sigmaSlider.setBlockIncrement(1);
        grid.add(sigmaSlider, 1, 2);

        sigmaSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int multiplier = 0;
            try {
                multiplier = Integer.parseInt(multField.getText());
            } catch (NumberFormatException | NullPointerException nfe) {
                return;
            }
            grid.getChildren().remove(outputImage.getView());
            this.outputImage = new Functions(this.originalImage).gaussFilter(multiplier, newValue.doubleValue());
            grid.add(new ImageView(outputImage.getRenderer()), 1, 4);
        });
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
                    this.outputImage = new Functions(this.originalImage).gaussFilter(multiplier, sigmaSlider.getValue());
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
                new ImageColorViewer((ImageColor) outputImage);
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
}
