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
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImageViewer {
    private FXMLController fxmlController;
    private ScrollPane scrollPane;
    private JFXMasonryPane masonryPane;
    private StackPane imageViewerPane;
    private ImageView imageViewerImageView;
    private JFXButton closeImageViewerButton;
    private ArrayList<ImageViewerImage> imageViewerImages = new ArrayList<>();
    private boolean showImages = true;

    public void Initialize(FXMLController fxmlController) {
        this.fxmlController = fxmlController;
        scrollPane = this.fxmlController.imagesScrollPane;
        masonryPane = this.fxmlController.imageMasonryPane;
        imageViewerPane = this.fxmlController.imageViewerPane;
        imageViewerImageView = this.fxmlController.imageViewerImageView;
        closeImageViewerButton = this.fxmlController.closeImageViewerButton;

        imageViewerPane.setVisible(false);
        closeImageViewerButton.setDisable(true);
        closeImageViewerButton.setVisible(false);
        closeImageViewerButton.setOnAction(this::CloseImage);

        JFXButton button = new JFXButton();
        button.getStyleClass().addAll("sharpButton", "primaryColor");
        masonryPane.getChildren().addAll(button);
        button.setOnAction(this::ToggleHideImages);
    }

    private void ToggleHideImages(ActionEvent event) {
        showImages = !showImages;
//        for (int i = 0; i < imageViewerImages.size(); i++) {
//            ImageView image = imageViewerImages.get(i).GetImageView();
//            image.setVisible(showImages);
//        }
        fxmlController.WriteToConsole(Boolean.toString(showImages));
        Thread hideImagesThread = new Thread(() -> {
            while (masonryPane.isVisible() && !showImages) {
                for (int i = 0; i < imageViewerImages.size(); i++) {
                    final int num = i;
                    AnchorPane anchorPane = imageViewerImages.get(i).GetAnchorPane();
                    ImageView imageView = imageViewerImages.get(i).GetImageView();
                    Bounds scrollBounds = scrollPane.localToScene(scrollPane.getBoundsInParent());
                    Bounds nodeBounds = anchorPane.localToScene(anchorPane.getBoundsInLocal());
                    if (scrollBounds.intersects(nodeBounds)) {
                        if (!imageView.isVisible()) {
                            Platform.runLater(() -> {
                                imageView.setVisible(true);
                                fxmlController.WriteToConsole("Showing image " + num);
                            });

                        }
                    }
                    else {
                        if (imageView.isVisible()) {
                            Platform.runLater(() -> {
                                imageView.setVisible(false);
                            });
                        }
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hideImagesThread.setDaemon(true);
        hideImagesThread.start();
    }

    public void LoadImages() {
        //SearchImages searchImages = new SearchImages();
        //ArrayList<String> imageList = searchImages.GetImages();
        //imageList.clear();
        ArrayList<String> imageList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            imageList.add("C:/Users/Trevor/Desktop/ani2.png");
        }
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
                Platform.runLater(() -> {
                    CreateImageElement(image, file.getName());
                });
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        imageThread.setDaemon(true);
        imageThread.start();
    }

    public void CreateImageElement(Image image, String name) {
        ImageViewerImage imageViewerImage = new ImageViewerImage();
        imageViewerImages.add(imageViewerImage);

        imageViewerImage.GetImageView().setImage(image);
        imageViewerImage.GetImageName().setText(name);
        imageViewerImage.GetButton().setOnAction(event -> OpenImage(imageViewerImage));

        masonryPane.getChildren().add(imageViewerImage.GetAnchorPane());

        //fxmlController.WriteToConsole(Boolean.toString(imageViewerImage.GetImageView().isCache()));
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
