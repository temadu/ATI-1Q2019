package utils;

import GUI.ImageColorViewer;
import GUI.ImageGreyViewer;
import GUI.Window;
import com.sun.org.apache.xpath.internal.functions.Function2Args;
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
import tp1.Functions;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class ImageGreyTransformer {

    ImageGrey originalImage;
    ImageGrey secondImage;
    ImageGrey outputImage;



    public void sumImages(ImageGrey originalImage){
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
                    new FileChooser.ExtensionFilter("Image files", "*.pgm")
            );
            File file = fileChooser.showOpenDialog(stage);

            try {
                this.secondImage = IOManager.loadPGM(file.getPath());
                grid.add(this.secondImage.getView(),2,2);
                if(this.originalImage.getHeight() == this.secondImage.getHeight() &&
                this.originalImage.getWidth() == this.secondImage.getWidth()){

                }
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
        });
        grid.add(chooseImageBtn, 0, 1);


        grid.add(new Label("First Image:"), 0, 1);
        grid.add(originalImage.getView(), 0, 2);

        grid.add(new Label("+"), 1, 1);

        grid.add(new Label("Second Image:"), 2, 1);

        grid.add(new Label("="), 3, 1);

        grid.add(new Label("Output Image:"), 4, 1);



        Button outputBtn = new Button("Output Image");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(outputBtn);
        grid.add(hbBtn, 3, 4);

        outputBtn.setOnAction((e) -> {
            if(outputImage != null)
                new ImageGreyViewer(outputImage);
        });

        Scene scene = new Scene(grid, originalImage.getWidth()*3, originalImage.getHeight() + 100);
        stage.setScene(scene);

        stage.setTitle("Sum Images");
        stage.show();

    }
    public static void substractImages(ImageGrey original){

    }
    public static void multiplyImage(ImageGrey original){

    }

    public void histogramEqualization(ImageGrey originalImage){
        new ImageGreyViewer(new Functions(originalImage).histogramEqualization());
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

        grid.add(new Label("Gamma:"), 0, 1);
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
                new ImageGreyViewer(outputImage);
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
                new ImageGreyViewer(outputImage);
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
                new ImageGreyViewer(outputImage);
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
                new ImageGreyViewer(outputImage);
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
                new ImageGreyViewer(outputImage);
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
