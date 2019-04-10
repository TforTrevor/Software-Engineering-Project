package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageViewer {
    private ScrollPane scrollPane;
    private Bounds scrollBounds;
    private JFXMasonryPane masonryPane;
    private StackPane imageViewerPane;
    private ImageView imageViewerImageView;
    private JFXButton closeImageViewerButton;
    ArrayList<String> imageList = new ArrayList<>();
    private ArrayList<ImageViewerImage> imageViewerImages = new ArrayList<>();
    private Thread hideImagesThread;

    ImageViewer(FXMLController fxmlController) {
        scrollPane = fxmlController.imagesScrollPane;
        masonryPane = fxmlController.imageMasonryPane;
        imageViewerPane = fxmlController.imageViewerPane;
        imageViewerImageView = fxmlController.imageViewerImageView;
        closeImageViewerButton = fxmlController.closeImageViewerButton;

        imageViewerPane.setVisible(false);
        closeImageViewerButton.setDisable(true);
        closeImageViewerButton.setVisible(false);
        closeImageViewerButton.setOnAction((event) -> CloseImage());

        hideImagesThread = new Thread();

        JFXButton button = new JFXButton();
        button.setOnAction((event) -> CreateImages());
        button.setPrefSize(256, 256);
        button.getStyleClass().add("primaryColor");
        button.setText("Create 100 Images");
        masonryPane.getChildren().add(button);
    }

    private void CreateImages() {
        for (int i = 0; i < 100; i++) {
            imageList.add("C:/Users/Trevor/Desktop/ani2.png");
        }
        LoadImages();
    }

    private void LoadImages() {
        Thread imageThread = new Thread(() -> {
            for (int i = 0; i < imageList.size(); i++) {
                //RESOURCES FOLDER
                ClassLoader classLoader = getClass().getClassLoader();
                InputStream inputStream = classLoader.getResourceAsStream("ani2.png");
                File file = new File(classLoader.getResource("ani2.png").getFile());
                Image image = new Image(inputStream);
                //DIRECT FILE PATH
                //File file = new File(imageList.get(i));
                //Image image = new Image(file.toURI().toString());
                ImageViewerImage imageViewerImage = CreateImageElement(image, file.getName());
                AnchorPane imageAnchorPane = imageViewerImage.GetAnchorPane();
                imageViewerImages.add(imageViewerImage);
                Platform.runLater(() -> {
                    masonryPane.getChildren().add(imageAnchorPane);
                });
            }
        });
        imageThread.setDaemon(true);
        imageThread.start();
        HideOffScreenImages();
    }

    private void HideOffScreenImages() {
        if (!hideImagesThread.isAlive()) {
            scrollBounds = scrollPane.localToScene(scrollPane.getBoundsInParent());
            hideImagesThread = new Thread(() -> {
                double scrollProgress = scrollPane.getVvalue();
                double prevScrollProgress = scrollProgress;
                while (masonryPane.isVisible()) {
                    try {
                        prevScrollProgress = scrollProgress;
                        scrollProgress = scrollPane.getVvalue();
                        if (scrollProgress != prevScrollProgress) {
                            final int count = imageViewerImages.size();
                            for (int i = 0; i < count; i++) {
                                AnchorPane anchorPane = imageViewerImages.get(i).GetAnchorPane();
                                ImageView imageView = imageViewerImages.get(i).GetImageView();
                                Bounds nodeBounds = anchorPane.localToScene(anchorPane.getBoundsInLocal());
                                if (scrollBounds.intersects(nodeBounds)) {
                                    if (!imageView.isVisible()) {
                                        Platform.runLater(() -> {
                                            imageView.setVisible(true);
                                        });
                                        Thread.sleep(5);
                                    }
                                } else {
                                    if (imageView.isVisible()) {
                                        Platform.runLater(() -> {
                                            imageView.setVisible(false);
                                        });
                                        Thread.sleep(5);
                                    }
                                }
                            }
                        }
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            hideImagesThread.setDaemon(true);
            hideImagesThread.start();
        }
    }

    private ImageViewerImage CreateImageElement(Image image, String name) {
        ImageViewerImage imageViewerImage = new ImageViewerImage();

        imageViewerImage.GetImageView().setImage(image);
        imageViewerImage.GetImageName().setText(name);
        imageViewerImage.GetButton().setOnAction(event -> OpenImage(imageViewerImage));

        return imageViewerImage;
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

    private void CloseImage() {
        imageViewerPane.setVisible(false);
        closeImageViewerButton.setDisable(true);
        closeImageViewerButton.setVisible(false);
    }
}
