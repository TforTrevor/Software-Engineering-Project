package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.ArrayList;

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
    private StackPane imageViewerPane;
    private ImageView imageViewerImageView;
    private ArrayList<ImageViewerImage> imageViewerImages = new ArrayList<>();

    public void Initialize(FXMLController fxmlController) {
        this.fxmlController = fxmlController;
        masonryPane = this.fxmlController.imageMasonryPane;
        imageViewerPane = this.fxmlController.imageViewerPane;
        imageViewerImageView = this.fxmlController.imageViewerImageView;
        imageViewerPane.setVisible(false);
    }

    private int counter = 0;

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
        imageViewerImage.GetButton().setText(Integer.toString(counter++));
        imageViewerImage.GetButton().getStyleClass().add("sharpButton");
        imageViewerImage.GetButton().setRipplerFill(Color.color(1,1,1));
        imageViewerImage.GetButton().setOnAction(event -> OpenImage(imageViewerImage));

        AddDropShadow(imageViewerImage.GetImageView());

        masonryPane.getChildren().add(imageViewerImage.GetStackPane());
    }

    private void OpenImage(ImageViewerImage imageViewerImage) {
        JFXButton button = imageViewerImage.GetButton();
        fxmlController.WriteToConsole(button.getText());

        Image image = imageViewerImage.GetImageView().getImage();
        imageViewerImageView.setImage(image);

        imageViewerPane.setMinSize(0, 0);
        imageViewerImageView.fitWidthProperty().bind(imageViewerPane.widthProperty());
        imageViewerImageView.fitHeightProperty().bind(imageViewerPane.heightProperty());
        fxmlController.WriteToConsole(Double.toString(imageViewerImageView.getFitWidth()));
        fxmlController.WriteToConsole(Double.toString(imageViewerImageView.getFitHeight()));

        AddDropShadow(imageViewerPane);

        imageViewerPane.setVisible(true);
    }

    private void AddDropShadow(Node element) {
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
