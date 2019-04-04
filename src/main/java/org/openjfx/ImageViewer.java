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
    private FXMLController fxmlController;
    private ScrollPane scrollPane;
    private Bounds scrollBounds;
    private JFXMasonryPane masonryPane;
    private StackPane imageViewerPane;
    private ImageView imageViewerImageView;
    private JFXButton closeImageViewerButton;
    private ArrayList<ImageViewerImage> imageViewerImages = new ArrayList<>();
    private Thread hideImagesThread;

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

        hideImagesThread = new Thread();
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
                ImageViewerImage imageViewerImage = CreateImageElement(image, file.getName());
                Platform.runLater(() -> {
                    masonryPane.getChildren().add(imageViewerImage.GetAnchorPane());
                });
            }
        });
        imageThread.setDaemon(true);
        imageThread.start();
        HideOffScreenImages();
    }

    public ImageViewerImage CreateImageElement(Image image, String name) {
        ImageViewerImage imageViewerImage = new ImageViewerImage();
        imageViewerImages.add(imageViewerImage);

        imageViewerImage.GetImageView().setImage(image);
        imageViewerImage.GetImageName().setText(name);
        imageViewerImage.GetButton().setOnAction(event -> OpenImage(imageViewerImage));

        return imageViewerImage;
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
