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
import javafx.scene.control.TextField;
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
    public static void dynamicRangeCompression(ImageGrey original){

    }


}
