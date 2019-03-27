package utils;

import GUI.ImageColorViewer;
import GUI.ImageGreyViewer;
import GUI.Window;
import javafx.event.ActionEvent;
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

public class ImageColorTransformer {

    ImageColor originalImage;
    ImageColor secondImage;
    ImageColor outputImage;

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
                    this.outputImage = new Functions(originalImage).imageSum(secondImage.getImage());
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
                new ImageColorViewer(outputImage);
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
                    this.outputImage = new Functions(originalImage).imageSub(secondImage.getImage());
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
                new ImageColorViewer(outputImage);
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
    public static void multiplyImage(ImageGrey original){

    }
    public static void dynamicRangeCompression(ImageGrey original){

    }


}
