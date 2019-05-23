package GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;
import tp1.Functions;
import utils.IOManager;
import utils.ImageCreator;
import utils.ImageGreyTransformer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VideoViewer {

    public Stage stage;
//    public MenuBar bar;
    public HBox mediaButtons;
    public HBox imageViews;
    public List<ImageViewer> images;

    public String videoDir;
    public boolean play = false;
    public boolean loop = false;
    public int currentFrame = 0;


    private ScheduledExecutorService playThread;

    public VideoViewer(String videoDir) {
        this.stage = new Stage();
        this.imageViews = new HBox();
        this.images = new ArrayList<>();
        
        this.videoDir = videoDir;
        try (Stream<Path> walk = Files.walk(Paths.get(videoDir))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            Collections.sort(result);
            result.forEach(s -> openImageFile(new File(s)));

        } catch (IOException e) {
            e.printStackTrace();
        }


        ScrollPane scroller = new ScrollPane();
//        scroller.setPrefSize(512, 512);
//        scroller.setFitToHeight(true);
        scroller.setFitToWidth(true);
        scroller.setContent(imageViews);

        VBox root = new VBox();
        root.getChildren().addAll(generateMediaBtns(stage), scroller);

        Scene scene = new Scene(root, 512,256);

        stage.setTitle("ATI 1Q2019");

        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            if(this.playThread!=null)
                this.playThread.shutdown();
        });
    }

    private HBox generateMediaBtns(Stage stage){
        Button openItem = new Button("Open...");
        openItem.setOnAction(e -> {
            this.openImageFile();
        });


        Button playBtn = new Button("►");
        playBtn.setOnAction(e -> {
            this.play = !this.play;
            if(this.play){
                playBtn.setText("❚❚");
                this.playThread = Executors.newSingleThreadScheduledExecutor();
                this.playThread.scheduleAtFixedRate(() -> {
                    if(this.play){
                        Platform.runLater(() -> this.nextFrame());
                    }
                }, 0, 1000, TimeUnit.MILLISECONDS);
            }else{
                playBtn.setText("►");
                this.playThread.shutdown();
            }
        });


        Button nextBtn = new Button("❚►");
        nextBtn.setOnAction(e -> {
            this.nextFrame();
        });
        Button prevBtn = new Button("<❚");
        prevBtn.setOnAction(e -> {
            this.previousFrame();
        });
        Button loopBtn = new Button("Loop");
        loopBtn.setOnAction(e -> this.loop = !this.loop);
        Button analyzeBtn = new Button("Analyze frame");
        analyzeBtn.setOnAction(e -> {
            if(this.images.get(this.currentFrame) instanceof ImageGreyViewer){
                this.showImage(new ImageGreyViewer((ImageGrey)new Functions(((ImageGreyViewer) this.images.get(this.currentFrame)).image)
                        .activeContorns(80, 64, 12,500), -1));
            }else{
                this.showImage(new ImageColorViewer((ImageColor)new Functions(((ImageColorViewer) this.images.get(this.currentFrame)).image)
                        .activeContorns(80, 64, 12,500), -1));
            }
        });





        this.mediaButtons = new HBox(openItem,prevBtn,playBtn,nextBtn, loopBtn, analyzeBtn);

        return this.mediaButtons;
    }
    private void openImageFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./images"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.pgm","*.ppm","*.raw")
        );
        File file = fileChooser.showOpenDialog(stage);

        if(file != null){
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
                this.addImageViewer(new ImageColorViewer(openedImage, -1));

//            this.stage.close();
            }else if(extension.toLowerCase().equals("pgm")){
                ImageGrey openedImage;
                try {
                    openedImage = IOManager.loadPGM(file.getPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return;
                }
                this.addImageViewer(new ImageGreyViewer(openedImage, -1));

//            this.stage.close();
            }else if(extension.toLowerCase().equals("raw")) {
                ImageGrey openedImage;

                try {
                    openedImage = IOManager.loadRAW(file.getPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return;
                }
                this.addImageViewer(new ImageGreyViewer(openedImage, -1));
//            this.stage.close();
            }
        }
    }

    private void openImageFile(File file){

        if(file != null){
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
                this.addImageViewer(new ImageColorViewer(openedImage, -1));

//            this.stage.close();
            }else if(extension.toLowerCase().equals("pgm")){
                ImageGrey openedImage;
                try {
                    openedImage = IOManager.loadPGM(file.getPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return;
                }
                this.addImageViewer(new ImageGreyViewer(openedImage, -1));

//            this.stage.close();
            }else if(extension.toLowerCase().equals("raw")) {
                ImageGrey openedImage;

                try {
                    openedImage = IOManager.loadRAW(file.getPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                    return;
                }
                this.addImageViewer(new ImageGreyViewer(openedImage, -1));
//            this.stage.close();
            }
        }
    }

    private void openVideo(){
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File("./images"));
        File directory = dirChooser.showDialog(stage);

        if(directory != null){
            System.out.println(directory.getAbsolutePath());


        }
    }

    public void addImageViewer(ImageViewer imageViewer){
        this.images.add(imageViewer);
        if(this.imageViews.getChildren().size() == 0)
            this.imageViews.getChildren().add(imageViewer.imageView);
        System.out.println(this.images.size());
    }

    public synchronized void nextFrame(){
        if(this.images.size()>0){
            if(currentFrame < this.images.size()-1){
                this.imageViews.getChildren().clear();
                this.currentFrame++;
                this.imageViews.getChildren().add(this.images.get(this.currentFrame).imageView);
            }else if(loop){
                this.currentFrame = 0;
                this.imageViews.getChildren().clear();
                this.imageViews.getChildren().add(this.images.get(this.currentFrame).imageView);
            }
        }
    }
    public synchronized void previousFrame(){
        if(this.images.size()>0){
            if(currentFrame > 0){
                this.imageViews.getChildren().clear();
                this.currentFrame--;
                this.imageViews.getChildren().add(this.images.get(this.currentFrame).imageView);
            }else if(loop){
                this.currentFrame = this.images.size()-1;
                this.imageViews.getChildren().clear();
                this.imageViews.getChildren().add(this.images.get(this.currentFrame).imageView);
            }
        }
    }
    public void showImage(ImageViewer imageViewer){
        this.imageViews.getChildren().clear();
        this.imageViews.getChildren().add(imageViewer.imageView);
    }

}
