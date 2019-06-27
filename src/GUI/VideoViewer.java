package GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
    private Functions f;

    private double[] originCoords = {100.,100.};

    private ScheduledExecutorService playThread;

    public VideoViewer(String videoDir) {
        this.stage = new Stage();
        this.imageViews = new HBox();
        this.images = new ArrayList<>();

        this.videoDir = videoDir;
        try (Stream<Path> walk = Files.walk(Paths.get(videoDir))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).sorted(Comparator.comparingLong(a -> Long.parseLong(a.replaceAll("\\D+", "")))).collect(Collectors.toList());
            result.forEach(s -> openImageFile(new File(s)));

        } catch (IOException e) {
            e.printStackTrace();
        }


        ScrollPane scroller = new ScrollPane();
//        scroller.setPrefSize(512, 512);
//        scroller.setFitToHeight(true);
        scroller.setFitToWidth(true);
        scroller.setContent(imageViews);
        int w = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getWidth();
        int h = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getHeight();

        f = new Functions((((ImageColorViewer) this.images.get(this.currentFrame)).image));
        f.activeContorns(226,172,20,100,false);
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

        Label fpsLabel = new Label("FPS:");
        TextField multField = new TextField();
        multField.setMaxWidth(60);
        multField.setText("1");
        multField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                multField.setText(oldValue);
            }
        });


        Button playBtn = new Button("\u23F5");
        playBtn.setOnAction(e -> {
            this.play = !this.play;
            if(this.play){
                int fps = Integer.parseInt(multField.getText());
                if(fps <= 0){
                    fps = 1;
                }
                playBtn.setText("\u23F8");
                this.playThread = Executors.newSingleThreadScheduledExecutor();
                this.playThread.scheduleAtFixedRate(() -> {
                    if(this.play){
                        Platform.runLater(() -> this.nextFrame());
                    }
                }, 0, 1000/fps, TimeUnit.MILLISECONDS);
            }else{
                playBtn.setText("\u23F5");
                this.playThread.shutdown();
            }
        });


        Button nextBtn = new Button("\u23F8\u23F5");
        nextBtn.setOnAction(e -> {
            this.nextFrame();
        });
        Button prevBtn = new Button("\u23F4\u23F8");
        prevBtn.setOnAction(e -> {
            this.previousFrame();
        });
        Button loopBtn = new Button("Loop ⟳");
        loopBtn.setOnAction(e -> this.loop = !this.loop);
        Button analyzeBtn = new Button("Analyze frame");
        analyzeBtn.setOnAction(e -> {
            if(this.images.get(this.currentFrame) instanceof ImageGreyViewer){
                int w = ((ImageGreyViewer) this.images.get(this.currentFrame)).image.getWidth();
                int h = ((ImageGreyViewer) this.images.get(this.currentFrame)).image.getHeight();
                this.showImage(new ImageGreyViewer((ImageGrey)f                        .activeContorns(originCoords[0], originCoords[1], Math.min(w,h)/8,500, false), -1));
            }else{
                int w = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getWidth();
                int h = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getHeight();
                this.showImage(new ImageColorViewer((ImageColor)f
                        .activeContorns(originCoords[0], originCoords[1], Math.min(w,h)/8,500, false), -1));
            }
        });

        Button analyzeOneStepBtn = new Button("Analyze one step");
        analyzeOneStepBtn.setOnAction(e -> {
            if(this.images.get(this.currentFrame) instanceof ImageGreyViewer){
                int w = ((ImageGreyViewer) this.images.get(this.currentFrame)).image.getWidth();
                int h = ((ImageGreyViewer) this.images.get(this.currentFrame)).image.getHeight();
                f.setImage((((ImageGreyViewer) this.images.get(this.currentFrame)).image));
                this.showImage(new ImageGreyViewer((ImageGrey) f.activeContorns(originCoords[0], originCoords[1], 20,1,true), -1));
//                this.showImage(new ImageGreyViewer((ImageGrey)new Functions(((ImageGreyViewer) this.images.get(this.currentFrame)).image)
//                        .activeContorns(originCoords[0], originCoords[1], Math.min(w,h)/8,1,true), -1));
            }else{
                int w = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getWidth();
                int h = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getHeight();
                f.setImage((((ImageColorViewer) this.images.get(this.currentFrame)).image));
                this.showImage(new ImageColorViewer((ImageColor) f.activeContorns(originCoords[0], originCoords[1], 20,1,true), -1));
//                this.showImage(new ImageColorViewer((ImageColor)new Functions(((ImageColorViewer) this.images.get(this.currentFrame)).image)
//                        .activeContorns(originCoords[0], originCoords[1], Math.min(w,h)/8,1,true), -1));
            }
        });








        this.mediaButtons = new HBox(openItem,prevBtn,playBtn,nextBtn, loopBtn, analyzeOneStepBtn, analyzeBtn,fpsLabel,multField);

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
            }else if(extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("jpeg")){
                ImageColor openedImage;
                try {
                    openedImage = IOManager.loadJPG(file.getPath());
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
    }

    public void addImageViewer(ImageViewer imageViewer){
        this.images.add(imageViewer);
        if(this.imageViews.getChildren().size() == 0){
            this.imageViews.getChildren().add(imageViewer.imageView);
            imageViewer.imageView.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePress);
        }
    }

    public synchronized void nextFrame(){
        int w = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getWidth();
        int h = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getHeight();
        if(this.images.size()>0){
            if(currentFrame < this.images.size()-1){
                this.imageViews.getChildren().clear();
                this.currentFrame++;
                f.setImage((((ImageColorViewer) this.images.get(this.currentFrame)).image));
                this.showImage(new ImageColorViewer((ImageColor) f.activeContorns(w/2, h/2, 20,100,true), -1));
//                this.imageViews.getChildren().add(this.images.get(this.currentFrame).imageView);
            }else if(loop){
                this.currentFrame = 0;
                this.imageViews.getChildren().clear();
                f.setImage((((ImageColorViewer) this.images.get(this.currentFrame)).image));
                this.showImage(new ImageColorViewer((ImageColor) f.activeContorns(w/2, h/2, 20,100,true), -1));
            }
        }
    }
    public synchronized void previousFrame(){
        int w = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getWidth();
        int h = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getHeight();

        if(this.images.size()>0){
            if(currentFrame > 0){
                this.imageViews.getChildren().clear();
                this.currentFrame--;
                f.setImage((((ImageColorViewer) this.images.get(this.currentFrame)).image));
                this.showImage(new ImageColorViewer((ImageColor) f.activeContorns(w/2, h/2, 20,100,true), -1));
            }else if(loop){
                this.currentFrame = this.images.size()-1;
                this.imageViews.getChildren().clear();
                f.setImage((((ImageColorViewer) this.images.get(this.currentFrame)).image));
                this.showImage(new ImageColorViewer((ImageColor) f.activeContorns(w/2, h/2, 20,100,true), -1));
            }
        }
    }

    EventHandler<MouseEvent> mousePress = e -> {
        originCoords[0] = e.getX();
        originCoords[1] = e.getY();
        if(this.images.get(this.currentFrame) instanceof ImageGreyViewer){
            int w = ((ImageGreyViewer) this.images.get(this.currentFrame)).image.getWidth();
            int h = ((ImageGreyViewer) this.images.get(this.currentFrame)).image.getHeight();
            this.showImage(new ImageGreyViewer((ImageGrey) f.activeContorns(e.getX(), e.getY(), 20,1, false), -1));
        }else{
            int w = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getWidth();
            int h = ((ImageColorViewer) this.images.get(this.currentFrame)).image.getHeight();
            this.showImage(new ImageColorViewer((ImageColor)f.activeContorns(e.getX(), e.getY(), 20,1, false), -1));
        }
    };

    public void showImage(ImageViewer imageViewer){
        this.imageViews.getChildren().clear();
        this.imageViews.getChildren().add(imageViewer.imageView);
        imageViewer.imageView.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePress);
    }

}
