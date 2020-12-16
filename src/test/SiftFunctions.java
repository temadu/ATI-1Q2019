package test;
import models.ImageColor;
import models.ImageGrey;
import models.ImageInt;
import org.opencv.core.Mat;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.highgui.Highgui;

public class SiftFunctions {

    public ImageInt image;
    private boolean greyscale;


    public SiftFunctions(ImageInt image) {
        this.image = image;
        if(image instanceof ImageGrey)
            this.greyscale = true;
        else{
            this.greyscale = false;
        }
    }

    public static void main(String[] args) {

        File lib = null;
        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            if (System.getProperty("sun.arch.data.model").endsWith("64")) {
                lib = new File("lib//opencv//x64//" + System.mapLibraryName("opencv_java2413"));
            } else {
                lib = new File("lib//opencv//x86//" + System.mapLibraryName("opencv_java2413"));
            }
        }
        System.load(lib.getAbsolutePath());


        String objLoc = "images//TP4//elcaminante.jpg";
        String sceneLoc = "images//TP4//elcaminante1.jpg";

        System.out.println("Started....");
        System.out.println("Loading images...");

        Mat o = new Mat(5,6, CvType.CV_8UC3);

        Mat objectImage = Highgui.imread(objLoc, Highgui.CV_LOAD_IMAGE_COLOR);
        Mat sceneImage = Highgui.imread(sceneLoc, Highgui.CV_LOAD_IMAGE_COLOR);
        Scalar keypointsColor = new Scalar(0, 0, 255); //red

        FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.SIFT);
        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);

        System.out.print("Object key points and descriptors...");
        MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
        MatOfKeyPoint objectDescriptors = new MatOfKeyPoint();
        featureDetector.detect(objectImage, objectKeyPoints);
        descriptorExtractor.compute(objectImage, objectKeyPoints, objectDescriptors);
//        System.out.println(objectKeyPoints.toArray().length);
//        System.out.println(objectDescriptors.toArray().length);
        // Create the matrix for output image.
        System.out.println("Drawing key points on object image...");
        Mat keypointsImage = new Mat(objectImage.rows(), objectImage.cols(), Highgui.CV_LOAD_IMAGE_COLOR);
        Features2d.drawKeypoints(objectImage, objectKeyPoints, keypointsImage, keypointsColor,Features2d.DRAW_RICH_KEYPOINTS);

        // Match object image with the scene image
        System.out.print("Scene key points and descriptors...");
        MatOfKeyPoint sceneKeyPoints = new MatOfKeyPoint();
        MatOfKeyPoint sceneDescriptors = new MatOfKeyPoint();
        featureDetector.detect(sceneImage, sceneKeyPoints);
        descriptorExtractor.compute(sceneImage, sceneKeyPoints, sceneDescriptors);
//        System.out.println(sceneKeyPoints.toArray().length);
//        System.out.println(sceneDescriptors.toArray().length);

        Mat matchoutput = new Mat(sceneImage.rows() * 2, sceneImage.cols() * 2, Highgui.CV_LOAD_IMAGE_COLOR);
        Scalar matchestColor = new Scalar(0, 255, 0);

        List<MatOfDMatch> matches = new LinkedList<MatOfDMatch>();
        DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
        System.out.println("Matching object and scene images...");
        descriptorMatcher.knnMatch(objectDescriptors, sceneDescriptors, matches, 2);

        System.out.println("Calculating good match list...");
        LinkedList<DMatch> goodMatchesList = new LinkedList<DMatch>();

        float nndrRatio = 0.7f;

        for (int i = 0; i < matches.size(); i++) {
            MatOfDMatch matofDMatch = matches.get(i);
            DMatch[] dmatcharray = matofDMatch.toArray();
            DMatch m1 = dmatcharray[0];
            DMatch m2 = dmatcharray[1];

            if (m1.distance <= m2.distance * nndrRatio) {
                goodMatchesList.addLast(m1);
            }
        }

        System.out.println("Matches: " + goodMatchesList.size());
        if (goodMatchesList.size() >= 7) {
            System.out.println("Object Found!!!");

            List<KeyPoint> objKeypointlist = objectKeyPoints.toList();
            List<KeyPoint> scnKeypointlist = sceneKeyPoints.toList();

            LinkedList<Point> objectPoints = new LinkedList<>();
            LinkedList<Point> scenePoints = new LinkedList<>();

            for (int i = 0; i < goodMatchesList.size(); i++) {
                objectPoints.addLast(objKeypointlist.get(goodMatchesList.get(i).queryIdx).pt);
                scenePoints.addLast(scnKeypointlist.get(goodMatchesList.get(i).trainIdx).pt);
            }

            MatOfPoint2f objMatOfPoint2f = new MatOfPoint2f();
            objMatOfPoint2f.fromList(objectPoints);
            MatOfPoint2f scnMatOfPoint2f = new MatOfPoint2f();
            scnMatOfPoint2f.fromList(scenePoints);

            Mat homography = Calib3d.findHomography(objMatOfPoint2f, scnMatOfPoint2f, Calib3d.RANSAC, 3);

            Mat obj_corners = new Mat(4, 1, CvType.CV_32FC2);
            Mat scene_corners = new Mat(4, 1, CvType.CV_32FC2);

            obj_corners.put(0, 0, new double[]{0, 0});
            obj_corners.put(1, 0, new double[]{objectImage.cols(), 0});
            obj_corners.put(2, 0, new double[]{objectImage.cols(), objectImage.rows()});
            obj_corners.put(3, 0, new double[]{0, objectImage.rows()});

            System.out.println("Transforming object corners to scene corners...");
            Core.perspectiveTransform(obj_corners, scene_corners, homography);
            Mat recognitionImage = Highgui.imread(sceneLoc, Highgui.CV_LOAD_IMAGE_COLOR);
            Core.line(recognitionImage, new Point(scene_corners.get(0, 0)), new Point(scene_corners.get(1, 0)), new Scalar(0, 255, 0), 4);
            Core.line(recognitionImage, new Point(scene_corners.get(1, 0)), new Point(scene_corners.get(2, 0)), new Scalar(0, 255, 0), 4);
            Core.line(recognitionImage, new Point(scene_corners.get(2, 0)), new Point(scene_corners.get(3, 0)), new Scalar(0, 255, 0), 4);
            Core.line(recognitionImage, new Point(scene_corners.get(3, 0)), new Point(scene_corners.get(0, 0)), new Scalar(0, 255, 0), 4);

            System.out.println("Drawing matches image...");
            MatOfDMatch goodMatches = new MatOfDMatch();
            goodMatches.fromList(goodMatchesList);
            Features2d.drawMatches(objectImage, objectKeyPoints, sceneImage, sceneKeyPoints, goodMatches, matchoutput, matchestColor, keypointsColor, new MatOfByte(), 2);

            Highgui.imwrite(objLoc.substring(0,objLoc.length()-4)+"_keypoints"+".jpg", keypointsImage);
            Highgui.imwrite(objLoc.substring(0,objLoc.length()-4)+"_matchlines"+".jpg", matchoutput);
            Highgui.imwrite(objLoc.substring(0,objLoc.length()-4)+"_recognition"+".jpg", recognitionImage);
        } else {
            Highgui.imwrite("images//outputImage.jpg", keypointsImage);
            System.out.println("Object Not Found");
        }

        System.out.println("Ended....");
    }

    public List<ImageInt> siftDetector(ImageInt sceneImage, float threshold){

        List<ImageInt> ret = new ArrayList<>();

        Mat objectMat = SiftFunctions.ImageIntToMat(this.image);
        Mat sceneMat = SiftFunctions.ImageIntToMat(sceneImage);

        Scalar keypointsColor = new Scalar(0, 255, 0); //red
        Scalar matchestColor = new Scalar(0, 255, 0); //green


        FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.SIFT);
        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);

        System.out.println("Object key points and descriptors...");
        MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
        MatOfKeyPoint objectDescriptors = new MatOfKeyPoint();
        featureDetector.detect(objectMat, objectKeyPoints);
        descriptorExtractor.compute(objectMat, objectKeyPoints, objectDescriptors);

        // Create the matrix for output image.
        System.out.println("Drawing key points on object image...");
        Mat keypointsImage = new Mat(objectMat.rows(), objectMat.cols(), Highgui.CV_LOAD_IMAGE_COLOR);
        Features2d.drawKeypoints(objectMat, objectKeyPoints, keypointsImage, keypointsColor,Features2d.DRAW_RICH_KEYPOINTS);
        ret.add(MatToImageInt(keypointsImage));
//        Highgui.imwrite("images//posta.jpg", keypointsImage);

        // Match object image with the scene image
        System.out.println("Scene key points and descriptors...");
        MatOfKeyPoint sceneKeyPoints = new MatOfKeyPoint();
        MatOfKeyPoint sceneDescriptors = new MatOfKeyPoint();
        featureDetector.detect(sceneMat, sceneKeyPoints);
        descriptorExtractor.compute(sceneMat, sceneKeyPoints, sceneDescriptors);

        // Create the matrix for output image.
        System.out.println("Drawing key points on scene image...");
        Mat scenekeypointsImage = new Mat(sceneMat.rows(), sceneMat.cols(), Highgui.CV_LOAD_IMAGE_COLOR);
        Features2d.drawKeypoints(sceneMat, sceneKeyPoints, scenekeypointsImage, keypointsColor,Features2d.DRAW_RICH_KEYPOINTS);
        ret.add(MatToImageInt(scenekeypointsImage));

        List<MatOfDMatch> matches = new LinkedList<MatOfDMatch>();
        DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
        System.out.println("Matching object and scene images...");
        descriptorMatcher.knnMatch(objectDescriptors, sceneDescriptors, matches, 2);

        System.out.println("Calculating good match list...");
        LinkedList<DMatch> goodMatchesList = new LinkedList<DMatch>();

        float nndrRatio = threshold;

        for (int i = 0; i < matches.size(); i++) {
            MatOfDMatch matofDMatch = matches.get(i);
            DMatch[] dmatcharray = matofDMatch.toArray();
            DMatch m1 = dmatcharray[0];
            DMatch m2 = dmatcharray[1];

            if (m1.distance <= m2.distance * nndrRatio) {
                goodMatchesList.addLast(m1);
            }
        }

        System.out.println("Matches: " + goodMatchesList.size());
        if (goodMatchesList.size() >= 7) {
            System.out.println("Object Found!!!");

            List<KeyPoint> objKeypointlist = objectKeyPoints.toList();
            List<KeyPoint> scnKeypointlist = sceneKeyPoints.toList();

            LinkedList<Point> objectPoints = new LinkedList<>();
            LinkedList<Point> scenePoints = new LinkedList<>();
            for (int i = 0; i < goodMatchesList.size(); i++) {
                objectPoints.addLast(objKeypointlist.get(goodMatchesList.get(i).queryIdx).pt);
                scenePoints.addLast(scnKeypointlist.get(goodMatchesList.get(i).trainIdx).pt);
            }

            System.out.println("Drawing matches image...");
            MatOfDMatch goodMatches = new MatOfDMatch();
            goodMatches.fromList(goodMatchesList);

            Mat matchesMat = new Mat(sceneMat.rows() * 2, sceneMat.cols() * 2, Highgui.CV_LOAD_IMAGE_COLOR);
            Features2d.drawMatches(objectMat, objectKeyPoints, sceneMat, sceneKeyPoints, goodMatches, matchesMat, matchestColor, keypointsColor, new MatOfByte(), 2);
            ret.add(MatToImageInt(matchesMat));


            System.out.println("Transforming object corners to scene corners...");
            MatOfPoint2f objMatOfPoint2f = new MatOfPoint2f();
            objMatOfPoint2f.fromList(objectPoints);
            MatOfPoint2f scnMatOfPoint2f = new MatOfPoint2f();
            scnMatOfPoint2f.fromList(scenePoints);

            Mat homography = Calib3d.findHomography(objMatOfPoint2f, scnMatOfPoint2f, Calib3d.RANSAC, 3);

            Mat scene_corners = new Mat(4, 1, CvType.CV_32FC2);
            Mat obj_corners = new Mat(4, 1, CvType.CV_32FC2);
            obj_corners.put(0, 0, new double[]{0, 0});
            obj_corners.put(1, 0, new double[]{objectMat.cols(), 0});
            obj_corners.put(2, 0, new double[]{objectMat.cols(), objectMat.rows()});
            obj_corners.put(3, 0, new double[]{0, objectMat.rows()});

            Core.perspectiveTransform(obj_corners, scene_corners, homography);
            Mat recognitionMat = ImageIntToMat(sceneImage);
            Core.line(recognitionMat, new Point(scene_corners.get(0, 0)), new Point(scene_corners.get(1, 0)), new Scalar(0, 255, 0), 4);
            Core.line(recognitionMat, new Point(scene_corners.get(1, 0)), new Point(scene_corners.get(2, 0)), new Scalar(0, 255, 0), 4);
            Core.line(recognitionMat, new Point(scene_corners.get(2, 0)), new Point(scene_corners.get(3, 0)), new Scalar(0, 255, 0), 4);
            Core.line(recognitionMat, new Point(scene_corners.get(3, 0)), new Point(scene_corners.get(0, 0)), new Scalar(0, 255, 0), 4);

            ret.add(MatToImageInt(recognitionMat));
            System.out.println("ENDED");

        } else {
            Highgui.imwrite("images//outputImage.jpg", keypointsImage);
            System.out.println("Object Not Found");
        }
        return ret;
    }

    public static Mat ImageIntToMat(ImageInt img){
        Mat ret;
        if(img instanceof ImageGrey){
            ImageGrey imgG = (ImageGrey) img;
            ret = new Mat(img.getHeight(),img.getWidth(), CvType.CV_8UC1);
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    ret.put(i,j, imgG.getPixel(j,i));
                }
            }
        }else{
            ImageColor imgC = (ImageColor) img;
            ret = new Mat(img.getHeight(),img.getWidth(), CvType.CV_8UC3);
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    ret.put(i,j, SiftFunctions.toBytePrimitive(imgC.getPixel(j,i)));
                }
            }

        }
        return ret;
    }

    public static ImageInt MatToImageInt(Mat mat){
        ImageInt ret;
        if(mat.type() == CvType.CV_8UC1){
            ret = ImageGrey.blankImage(mat.height(),mat.width());
            for (int i = 0; i < ret.getHeight(); i++) {
                for (int j = 0; j < ret.getWidth(); j++) {
                    byte[] color = new byte[1];
                    mat.get(j,i,color);
                    ((ImageGrey) ret).setPixel(j,i, (int)color[0]<0?(256+color[0]):color[0]);
                }
            }
        }else{
            ret = ImageColor.blankImage(mat.height(),mat.width());
            for (int i = 0; i < ret.getHeight(); i++) {
                for (int j = 0; j < ret.getWidth(); j++) {
                    byte[] color = new byte[3];
                    mat.get(i,j,color);
                    Integer[] pix =  {(int)color[2]<0?(256+color[2]):color[2],(int)color[1]<0?(256+color[1]):color[1],(int)color[0]<0?(256+color[0]):color[0]};
                    ((ImageColor) ret).setPixel(j,i, pix);
                }
            }

        }
        return ret;
    }

    public static byte[] toBytePrimitive(Integer[] IntegerArray) {

        byte[] result = new byte[IntegerArray.length];
        for (int i = 0; i < IntegerArray.length; i++) {
            result[(IntegerArray.length-1)-i] = IntegerArray[i].byteValue();
        }
        return result;
    }

}