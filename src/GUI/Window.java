package GUI;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;
import tp1.Functions;
import utils.IOManager;
import utils.ImageCreator;
import utils.ImageTransformer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Window {

    public Stage stage;
    public VBox box;
    public List<HBox> rows;

    public Window() {
        this.stage = new Stage();
        this.rows = new ArrayList<>();
        this.box = new VBox();

        ScrollPane s1 = new ScrollPane();
        s1.setFitToHeight(true);
        s1.setContent(box);

        VBox root = new VBox();
        root.getChildren().addAll(generateMenuBar(stage), s1);

        Scene scene = new Scene(root);

        stage.setTitle("ATI 1Q2019");

        stage.setScene(scene);
        stage.show();
    }

    private MenuBar generateMenuBar(Stage stage){
        final Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open...");
        openItem.setOnAction(e -> openImage());

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e-> stage.close());

        fileMenu.getItems().addAll(openItem,exit);


        final Menu generateMenu = new Menu("Generate");
        MenuItem generateSquare = new MenuItem("Square");
        generateSquare.setOnAction(e -> ImageCreator.createSquare());
        MenuItem generateCircle = new MenuItem("Circle");
        generateCircle.setOnAction(e-> ImageCreator.createCircle());
        generateMenu.getItems().addAll(generateSquare,generateCircle);


        final Menu transformMenu = new Menu("Transform");
        MenuItem suma = new MenuItem("Sum");
        suma.setOnAction(e -> ImageTransformer.sumImages());
        transformMenu.getItems().addAll(suma);


        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, generateMenu, transformMenu);

        return menuBar;
    }
    private void openImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.pgm","*.ppm","*.raw")
        );
        File file = fileChooser.showOpenDialog(stage);

        String extension = file.getName();

        int i = extension.lastIndexOf('.');
        if (i > 0) {
            extension = extension.substring(i+1);
        }

        if(extension.toLowerCase().equals("ppm")){
            ImageColor openedImage;
            try {
                openedImage = IOManager.loadPPM(file.getPath());
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
            addColorImageContextMenu(openedImage);
            new Window().addRow(openedImage.getView());
        }else if(extension.toLowerCase().equals("pgm")){
            ImageGrey openedImage;
            try {
                openedImage = IOManager.loadPGM(file.getPath());
            } catch (IOException e1) {
                e1.printStackTrace();
                return;
            }
            addGreyImageContextMenu(openedImage);
            new Window().addRow(openedImage.getView());
        }



//            WritableImage wim = this.matrixToBWImage(openedImage);
//            this.imageView.setImage(wim);
//
//            imageView.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
//                wim.getPixelWriter().setColor((int) mouseEvent.getX(), (int) mouseEvent.getY(),Color.rgb(128,128,128));
//            });
    }

    public void addRow(Node... nodes){
        HBox h = new HBox();
        h.getChildren().addAll(nodes);
        rows.add(h);
        this.box.getChildren().add(h);
    }

    public void addColorImageContextMenu(ImageColor image){
        ImageView view = image.getView();
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Save");
        item1.setOnAction((ActionEvent event) -> {
            saveImage(image);
        });
        contextMenu.getItems().addAll(item1);
        view.setOnContextMenuRequested((ContextMenuEvent event) ->
                contextMenu.show(view, event.getScreenX(), event.getScreenY())
        );
        System.out.println("Paso");
    }

    public void addGreyImageContextMenu(ImageGrey image){
        ImageView view = image.getView();
        ContextMenu contextMenu = new ContextMenu();

        MenuItem save = new MenuItem("Save");
        save.setOnAction((ActionEvent event) -> {
            saveImage(image);
        });
        contextMenu.getItems().addAll(save);
        view.setOnContextMenuRequested((ContextMenuEvent event) ->
                contextMenu.show(view, event.getScreenX(), event.getScreenY())
        );
    }

    private void saveImage(ImageInt image){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.pgm","*.ppm","*.raw")
        );
        File file = fileChooser.showSaveDialog(stage);

        if(image instanceof ImageColor){
            IOManager.savePPM(file.getPath(), (ImageColor) image);
        }else if(image instanceof  ImageGrey){
            IOManager.savePGM(file.getPath(), (ImageGrey) image);
        }
    }


}
