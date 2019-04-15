package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;

public class ImageViewer {
    private AnchorPane viewImageTabPane;
    private ScrollPane scrollPane;
    private JFXMasonryPane masonryPane;
    private StackPane imageViewerPane;
    private ImageView imageViewerImageView;
    private JFXButton closeImageViewerButton;
    private ArrayList<XMLImage> imageList;
    private ArrayList<ImageViewerImage> imageViewerImages = new ArrayList<>();
    private XMLImageEditor imagePaths;
    private HBox imageOptions;
    private JFXButton imageOptionsRemove;
    private ArrayList<ImageViewerImage> selectedImages = new ArrayList<>();
    private JFXButton createImagesButton;
    private JFXButton refreshImagesButton;

    ImageViewer(FXMLController fxmlController) {
        viewImageTabPane = fxmlController.viewImageTabPane;
        scrollPane = fxmlController.imagesScrollPane;
        masonryPane = fxmlController.imageMasonryPane;
        imageViewerPane = fxmlController.imageViewerPane;
        imageViewerImageView = fxmlController.imageViewerImageView;
        closeImageViewerButton = fxmlController.closeImageViewerButton;
        imageOptions = fxmlController.imageOptions;
        imageOptionsRemove = fxmlController.imageOptionsRemove;

        imageViewerPane.setVisible(false);
        closeImageViewerButton.setDisable(true);
        closeImageViewerButton.setVisible(false);
        closeImageViewerButton.setOnAction((event) -> CloseImage());

        createImagesButton = new JFXButton();
        createImagesButton.setOnAction((event) -> CreateImages());
        createImagesButton.setPrefSize(256, 256);
        createImagesButton.getStyleClass().add("primaryColor");
        createImagesButton.setText("Create Images");
        masonryPane.getChildren().add(createImagesButton);

        refreshImagesButton = new JFXButton();
        refreshImagesButton.setOnAction((event) -> LoadImages());
        refreshImagesButton.setPrefSize(256, 256);
        refreshImagesButton.getStyleClass().add("primaryColor");
        refreshImagesButton.setText("Refresh Images");
        masonryPane.getChildren().add(refreshImagesButton);

        imagePaths = new XMLImageEditor();

        imageOptionsRemove.setOnAction((event) -> RemoveImage());
    }

    private void CreateImages() {
        imagePaths.CreateXMLImage("Test", "Hello", "ani3.png");
        imagePaths.CreateXMLImage("Test", "Hello", "ani3.png");
        imagePaths.CreateXMLImage("Test", "Hello", "ani3.png");
        LoadImages();
    }

    private void LoadImages() {
        Thread imageThread = new Thread(() -> {
            imageList = imagePaths.GetXMLImages();
            Platform.runLater(() -> {
                masonryPane.getChildren().clear();
                masonryPane.getChildren().add(createImagesButton);
                masonryPane.getChildren().add(refreshImagesButton);
            });
            imageViewerImages.clear();
            for (int i = 0; i < imageList.size(); i++) {
                try {
                    ImageViewerImage imageViewerImage = CreateImageElement(imageList.get(i));
                    AnchorPane imageAnchorPane = imageViewerImage.GetAnchorPane();
                    imageViewerImages.add(imageViewerImage);
                    Platform.runLater(() -> {
                        masonryPane.getChildren().add(imageAnchorPane);
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        imageThread.setDaemon(true);
        imageThread.start();
        HideOffScreenImages();
    }

    private void HideOffScreenImages() {
        Thread hideImagesThread = new Thread(() -> {
            try {
                int i = 0;
                while (viewImageTabPane.isVisible()) {
                    if (imageViewerImages.size() > 0) {
                        AnchorPane anchorPane = imageViewerImages.get(i).GetAnchorPane();
                        ImageView imageView = imageViewerImages.get(i).GetImageView();
                        Platform.runLater(() -> {
                            Bounds nodeBounds = anchorPane.localToScene(anchorPane.getBoundsInLocal());
                            Bounds scrollBounds = scrollPane.localToScene(scrollPane.getBoundsInParent());
                            if (scrollBounds.intersects(nodeBounds)) {
                                if (!imageView.isVisible()) {
                                    imageView.setVisible(true);
                                }
                            } else {
                                if (imageView.isVisible()) {
                                    imageView.setVisible(false);
                                }
                            }
                        });
                    }
                    i++;
                    if (i > imageViewerImages.size() - 1) {
                        i = 0;
                    }
                    Thread.sleep(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Stopping Hiding");
            }
        });
        hideImagesThread.setDaemon(true);
        hideImagesThread.start();
    }

    private ImageViewerImage CreateImageElement(XMLImage xmlImage) {
        ImageViewerImage imageViewerImage = new ImageViewerImage(xmlImage);

        imageViewerImage.GetButton().setOnAction(event -> OpenImage(imageViewerImage));
        imageViewerImage.GetCheckBox().setOnAction(event -> SelectImage(imageViewerImage));

        return imageViewerImage;
    }

    private void OpenImage(ImageViewerImage imageViewerImage) {
        Image image = imageViewerImage.GetImageView().getImage();
        imageViewerImageView.setImage(image);

        imageViewerPane.setMinSize(0, 0);
        imageViewerImageView.fitWidthProperty().bind(imageViewerPane.widthProperty());
        imageViewerImageView.fitHeightProperty().bind(imageViewerPane.heightProperty());

        JavaFXHelper.AddDropShadow(imageViewerPane);

        closeImageViewerButton.setDisable(false);
        closeImageViewerButton.setVisible(true);

        JavaFXHelper.FadeIn(Duration.seconds(0.25), imageViewerPane);
    }

    private void SelectImage(ImageViewerImage imageViewerImage) {
        if (imageViewerImage.GetCheckBox().isSelected()) {
            if (selectedImages.size() < 1) {
                JavaFXHelper.FadeIn(Duration.seconds(0.1), imageOptions);
            }
            selectedImages.add(imageViewerImage);
        } else {
            selectedImages.remove(imageViewerImage);
            if (selectedImages.size() < 1) {
                JavaFXHelper.FadeOut(Duration.seconds(0.1), imageOptions);
            }
        }
    }

    private void RemoveImage() {
        Thread removeThread = new Thread(() -> {
            try {
                for (int i = 0; i < selectedImages.size(); i++) {
                    XMLImage xmlImage = selectedImages.get(i).GetXMLImage();
                    String path = xmlImage.GetPath();
                    imageList.remove(xmlImage);
                    imagePaths.RemoveXMLImage(path);
                    imageViewerImages.remove(selectedImages.get(i));
                    final int index = i;
                    Platform.runLater(() -> {
                        masonryPane.getChildren().remove(selectedImages.get(index).GetAnchorPane());
                    });
                    Thread.sleep(10);
                }
                selectedImages.clear();
                JavaFXHelper.FadeOut(Duration.seconds(0.1), imageOptions);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        removeThread.setDaemon(true);
        removeThread.start();
    }

    private void CloseImage() {
        imageViewerPane.setVisible(false);
        closeImageViewerButton.setDisable(true);
        closeImageViewerButton.setVisible(false);
    }
}
