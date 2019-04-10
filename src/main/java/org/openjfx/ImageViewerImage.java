package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

class ImageViewerImage {
    private AnchorPane anchorPane;
    private VBox vBox;
    private ImageView imageView;
    private Label imageName;
    private JFXButton button;
    private JFXCheckBox checkBox;
    ImageViewerImage() {
        anchorPane = new AnchorPane();
        vBox = new VBox();
        imageView = new ImageView();
        imageName = new Label();
        button = new JFXButton();
        checkBox = new JFXCheckBox();

        anchorPane.getChildren().add(vBox);
        vBox.getChildren().addAll(imageView, imageName);
        anchorPane.getChildren().addAll(button, checkBox);

        anchorPane.setCache(true);
        vBox.setCache(true);
        imageView.setCache(true);
        imageName.setCache(true);
        button.setCache(true);
        checkBox.setCache(true);

        vBox.setAlignment(Pos.CENTER);
        imageView.setFitWidth(225);
        imageView.setFitHeight(225);
        imageName.setPadding(new Insets(5, 0, 0, 0));
        button.setPrefSize(256, 256);

        AnchorPane.setTopAnchor(vBox, 0.0);
        AnchorPane.setBottomAnchor(vBox, 0.0);
        AnchorPane.setLeftAnchor(vBox, 0.0);
        AnchorPane.setRightAnchor(vBox, 0.0);

        AnchorPane.setTopAnchor(button, 0.0);
        AnchorPane.setBottomAnchor(button, 0.0);
        AnchorPane.setLeftAnchor(button, 0.0);
        AnchorPane.setRightAnchor(button, 0.0);

        AnchorPane.setTopAnchor(checkBox, 5.0);
        AnchorPane.setLeftAnchor(checkBox, 5.0);

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setVisible(true);
        button.getStyleClass().add("sharpButton");
        button.setRipplerFill(Color.color(1,1,1));
        JavaFXHelper.AddDropShadow(imageView);
    }
    AnchorPane GetAnchorPane() {
        return anchorPane;
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
    JFXCheckBox GetCheckBox() {
        return checkBox;
    }
}
