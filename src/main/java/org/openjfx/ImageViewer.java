package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

class ImageViewerImage {
    private StackPane stackPane;
    private VBox vBox;
    private ImageView imageView;
    private Label imageName;
    private JFXButton button;
    ImageViewerImage() {
        stackPane = new StackPane();
        vBox = new VBox();
        imageView = new ImageView();
        imageName = new Label();
        button = new JFXButton();

        stackPane.getChildren().add(vBox);
        vBox.getChildren().add(imageView);
        vBox.getChildren().add(imageName);
        stackPane.getChildren().add(button);

        vBox.setAlignment(Pos.CENTER);
        imageView.setFitWidth(225);
        imageView.setFitHeight(225);
        imageName.setPadding(new Insets(5, 0, 0, 0));
        button.setPrefSize(256, 256);

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        button.getStyleClass().add("sharpButton");
        button.setRipplerFill(Color.color(1,1,1));
        JavaFXHelper.AddDropShadow(imageView);
    }
    StackPane GetStackPane() {
        return stackPane;
    }
    ImageView GetImageView() {
        return imageView;
    }
    Label GetImageName() {
        return imageName;
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
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("ani2.png");
        File file = new File(classLoader.getResource("ani2.png").getFile());
        Image image = new Image(inputStream);

        ImageViewerImage imageViewerImage = new ImageViewerImage();
        imageViewerImages.add(imageViewerImage);

        imageViewerImage.GetImageView().setImage(image);
        imageViewerImage.GetImageName().setText(file.getName());
        imageViewerImage.GetButton().setOnAction(event -> OpenImage(imageViewerImage));

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

        JavaFXHelper.FadeIn(Duration.seconds(0.25), imageViewerPane);
    }

    private void CloseImage(ActionEvent event) {
        imageViewerPane.setVisible(false);
        closeImageViewerButton.setDisable(true);
        closeImageViewerButton.setVisible(false);
    }
}
