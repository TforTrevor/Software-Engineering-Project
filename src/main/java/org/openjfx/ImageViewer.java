package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

public class ImageViewer {
    private AnchorPane viewImageTabPane;
    private JFXTextField searchField;
    private ScrollPane scrollPane;
    private JFXMasonryPane masonryPane;
    private int loadAmount = 30;

    private StackPane imageViewerPane;
    private ImageView imageViewerImageView;
    private ImageViewerImage openedImage;
    private JFXButton closeImageViewerButton;
    private JFXButton openOriginal;

    private HBox imageOptions;
    private JFXButton imageOptionsRemove;

    private ArrayList<XMLImage> imageList = new ArrayList<>();
    private ArrayList<ImageViewerImage> imageViewerImages = new ArrayList<>();
    private ArrayList<ImageViewerImage> selectedImages = new ArrayList<>();

    private Thread hideImagesThread;
    private Thread loadImagesThread;
    private Thread scrollCheck;

    ImageViewer(FXMLController fxmlController) {
        viewImageTabPane = fxmlController.viewImageTabPane;
        scrollPane = fxmlController.imagesScrollPane;
        masonryPane = fxmlController.imageMasonryPane;
        imageViewerPane = fxmlController.imageViewerPane;
        imageViewerImageView = fxmlController.imageViewerImageView;
        closeImageViewerButton = fxmlController.closeImageViewerButton;
        imageOptions = fxmlController.imageOptions;
        imageOptionsRemove = fxmlController.imageOptionsRemove;
        searchField = fxmlController.imageViewerSearch;
        openOriginal = fxmlController.imageViewerOpenButton;

        searchField.setOnAction((event) -> SearchImages());
        imageOptionsRemove.setOnAction((event) -> RemoveImage());
        imageViewerPane.setVisible(false);
        closeImageViewerButton.setDisable(true);
        closeImageViewerButton.setVisible(false);
        closeImageViewerButton.setOnAction((event) -> CloseImage());
        openOriginal.setOnAction((event) -> OpenOriginalImage());

        hideImagesThread = new Thread();
        loadImagesThread = new Thread();
        scrollCheck = new Thread();
    }

    private void SearchImages() {
        Thread searchImagesThread = new Thread(() -> {
            String input = searchField.getText().toLowerCase();
            if (input.equals("")) {
                Platform.runLater(() -> masonryPane.getChildren().clear());
                ClearImages();
                GetXMLImages();
                LoadImages();
                return;
            }
            Platform.runLater(() -> scrollPane.setVvalue(scrollPane.getVmin()));
            for (int i = 0; i < imageViewerImages.size(); i++) {
                ImageViewerImage image = imageViewerImages.get(i);
                String imageName = image.GetImageName().toLowerCase();
                if (imageName.contains(input)) {
                    if (!masonryPane.getChildren().contains(image.GetAnchorPane())) {
                        Platform.runLater(() -> masonryPane.getChildren().add(image.GetAnchorPane()));
                    }
                } else {
                    if (masonryPane.getChildren().contains(image.GetAnchorPane())) {
                        Platform.runLater(() -> masonryPane.getChildren().remove(image.GetAnchorPane()));
                    }
                }
            }
        });
        searchImagesThread.setDaemon(true);
        searchImagesThread.start();
    }

    void ClearImages() {
        imageViewerImages.clear();
        masonryPane.getChildren().clear();
    }

    void GetXMLImages() {
        XMLImageEditor xmlImageEditor = new XMLImageEditor();
        imageList = xmlImageEditor.GetXMLImages();
    }

    void ScrollCheck() {
        scrollCheck = new Thread(() -> {
            while (masonryPane.isVisible()) {
                try {
                    if (scrollPane.getVvalue() >= (0.9 * scrollPane.getVmax()) || masonryPane.getChildren().size() == 0) {
                        LoadImages();
                    }
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        scrollCheck.setDaemon(true);
        scrollCheck.start();
    }

    private void LoadImages() {
        if (!loadImagesThread.isAlive()) {
            loadImagesThread = new Thread(() -> {
                int count = 0;
                for (int i = imageViewerImages.size(); i < imageList.size(); i++) {
                    if (count++ >= loadAmount) {
                        break;
                    }
                    ImageViewerImage imageViewerImage = CreateImageElement(imageList.get(i));
                    AnchorPane imageAnchorPane = imageViewerImage.GetAnchorPane();
                    imageViewerImages.add(imageViewerImage);
                    Platform.runLater(() -> masonryPane.getChildren().add(imageAnchorPane));
                }
            });
            loadImagesThread.setDaemon(true);
            loadImagesThread.start();
        }
    }

    void HideOffScreenImages() {
        if (!hideImagesThread.isAlive()) {
            hideImagesThread = new Thread(() -> {
                try {
                    System.out.println("Starting Hiding Images");
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
                        Thread.sleep(5);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Stopping Hiding Images");
                }
            });
            hideImagesThread.setDaemon(true);
            hideImagesThread.start();
        }
    }

    private ImageViewerImage CreateImageElement(XMLImage xmlImage) {
        ImageViewerImage imageViewerImage = new ImageViewerImage(xmlImage);

        imageViewerImage.GetButton().setOnAction(event -> OpenImage(imageViewerImage));
        imageViewerImage.GetCheckBox().setOnAction(event -> SelectImage(imageViewerImage));

        return imageViewerImage;
    }

    private void OpenImage(ImageViewerImage imageViewerImage) {
        openedImage = imageViewerImage;
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

    private void CloseImage() {
        imageViewerPane.setVisible(false);
        closeImageViewerButton.setDisable(true);
        closeImageViewerButton.setVisible(false);
    }

    private void OpenOriginalImage() {
        try {
            if (openedImage.GetFile().exists()) {
                Desktop.getDesktop().open(openedImage.GetFile());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                XMLImageEditor xmlImageEditor = new XMLImageEditor();
                for (int i = 0; i < selectedImages.size(); i++) {
                    XMLImage xmlImage = selectedImages.get(i).GetXMLImage();
                    String path = xmlImage.GetPath();
                    xmlImageEditor.RemoveXMLImage(path);
                    imageList.remove(xmlImage);
                    imageViewerImages.remove(selectedImages.get(i));
                    final int index = i;
                    Platform.runLater(() -> masonryPane.getChildren().remove(selectedImages.get(index).GetAnchorPane()));
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

    ArrayList<ImageViewerImage> GetSelectedImages() {
        return (ArrayList<ImageViewerImage>) selectedImages.clone();
    }
}
