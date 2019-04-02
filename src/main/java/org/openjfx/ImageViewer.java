package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.scene.control.Control;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

class ImageViewerImage {
    private StackPane stackPane;
    private ImageView imageView;
    private JFXButton button;
    ImageViewerImage() {
        stackPane = new StackPane();
        imageView = new ImageView();
        button = new JFXButton();

        stackPane.getChildren().add(imageView);
        stackPane.getChildren().add(button);
    }
    StackPane GetStackPane() {
        return stackPane;
    }
    ImageView GetImageView() {
        return imageView;
    }
    JFXButton GetButton() {
        return button;
    }
}

public class ImageViewer {
    private FXMLController fxmlController;
    private JFXMasonryPane masonryPane;
    private ArrayList<ImageViewerImage> imageViewerImages = new ArrayList<>();

    public void Initialize(FXMLController fxmlController) {
        this.fxmlController = fxmlController;
        masonryPane = this.fxmlController.imageMasonryPane;
    }

    public void CreateImageElement() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("ani2.png");
        Image image = new Image(file);

        ImageViewerImage imageViewerImage = new ImageViewerImage();
        imageViewerImages.add(imageViewerImage);

        imageViewerImage.GetImageView().setImage(image);
        imageViewerImage.GetImageView().setFitWidth(256);
        imageViewerImage.GetImageView().setFitHeight(256);
        imageViewerImage.GetImageView().setPreserveRatio(true);
        imageViewerImage.GetImageView().setSmooth(true);

        imageViewerImage.GetButton().setPrefWidth(266);
        imageViewerImage.GetButton().setPrefHeight(266);
        imageViewerImage.GetButton().setText("");
        imageViewerImage.GetButton().getStyleClass().add("sharpButton");
        imageViewerImage.GetButton().setRipplerFill(Color.color(1,1,1));

        AddDropShadow(imageViewerImage.GetImageView());

        masonryPane.getChildren().add(imageViewerImage.GetStackPane());
    }

    private void AddDropShadow(ImageView element) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setWidth(20);
        dropShadow.setHeight(20);
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(2.5);
        dropShadow.setOffsetY(2.5);
        dropShadow.setColor(Color.color(0,0,0,0.5));

        element.setEffect(dropShadow);
    }
}
