package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

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

    public void LoadImages() {
        //SearchImages searchImages = new SearchImages();
        //ArrayList<String> imageList = searchImages.GetImages();
        //imageList.clear();
        ArrayList<String> imageList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            imageList.add("C:/Users/Trevor/Desktop/ani2.png");
        }
        for (int i = 0; i < imageList.size(); i++) {
            CreateImageElement(imageList.get(i));
        }
        //CreateImageElement("C:/Users/Trevor/Desktop/ani2.png");
    }

    public void CreateImageElement(String filePath) {
        //RESOURCES FOLDER
//        ClassLoader classLoader = getClass().getClassLoader();
//        InputStream inputStream = classLoader.getResourceAsStream("ani2.png");
//        File file = new File(classLoader.getResource("ani2.png").getFile());
//        Image image = new Image(inputStream);

        //DIRECT FILE PATH
        File file = new File(filePath);
        Image image = new Image(file.toURI().toString());

        ImageViewerImage imageViewerImage = new ImageViewerImage();
        imageViewerImages.add(imageViewerImage);

        imageViewerImage.GetImageView().setImage(image);
        imageViewerImage.GetImageName().setText(file.getName());
        imageViewerImage.GetButton().setOnAction(event -> OpenImage(imageViewerImage));

        masonryPane.getChildren().add(imageViewerImage.GetAnchorPane());
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
