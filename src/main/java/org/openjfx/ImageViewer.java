package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
    private JFXButton closeImageViewerButton;
    private ArrayList<ImageViewerImage> imageViewerImages = new ArrayList<>();

    public void Initialize(FXMLController fxmlController) {
        this.fxmlController = fxmlController;
        masonryPane = this.fxmlController.imageMasonryPane;
        imageViewerPane = this.fxmlController.imageViewerPane;
        imageViewerImageView = this.fxmlController.imageViewerImageView;
        closeImageViewerButton = this.fxmlController.closeImageViewerButton;

        imageViewerPane.setVisible(false);
        closeImageViewerButton.setDisable(true);
        closeImageViewerButton.setVisible(false);
        closeImageViewerButton.setOnAction(this::CloseImage);
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
        imageViewerImage.GetButton().getStyleClass().add("sharpButton");
        imageViewerImage.GetButton().setRipplerFill(Color.color(1,1,1));
        imageViewerImage.GetButton().setOnAction(event -> OpenImage(imageViewerImage));

        JavaFXHelper.AddDropShadow(imageViewerImage.GetImageView());

        masonryPane.getChildren().add(imageViewerImage.GetStackPane());
    }

    private void OpenImage(ImageViewerImage imageViewerImage) {
        Image image = imageViewerImage.GetImageView().getImage();
        imageViewerImageView.setImage(image);

        imageViewerPane.setMinSize(0, 0);
        imageViewerImageView.fitWidthProperty().bind(imageViewerPane.widthProperty());
        imageViewerImageView.fitHeightProperty().bind(imageViewerPane.heightProperty());

        JavaFXHelper.AddDropShadow(imageViewerPane);

        imageViewerPane.setVisible(true);
        closeImageViewerButton.setDisable(false);
        closeImageViewerButton.setVisible(true);

        JavaFXHelper.FadeOpacity(Duration.seconds(0.25), imageViewerPane);
    }

    private void CloseImage(ActionEvent event) {
        imageViewerPane.setVisible(false);
        closeImageViewerButton.setDisable(true);
        closeImageViewerButton.setVisible(false);
    }
}
