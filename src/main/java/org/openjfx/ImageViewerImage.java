package org.openjfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.File;

class ImageViewerImage {
    private AnchorPane anchorPane;
    private VBox vBox;
    private StackPane stackPane;
    private XMLImage xmlImage;
    private ImageView imageView;
    private Label imageName;
    private JFXButton button;
    private JFXCheckBox checkBox;
    private File file;
    ImageViewerImage(XMLImage xmlImage) {
        anchorPane = new AnchorPane();
        vBox = new VBox();
        stackPane = new StackPane();
        imageView = new ImageView();
        imageName = new Label();
        button = new JFXButton();
        checkBox = new JFXCheckBox();

        anchorPane.getChildren().add(vBox);
        vBox.getChildren().addAll(stackPane, imageName);
        stackPane.getChildren().add(imageView);
        anchorPane.getChildren().addAll(button, checkBox);

        anchorPane.setCache(true);
        vBox.setCache(true);
        stackPane.setCache(true);
        imageView.setCache(true);
        imageName.setCache(true);
        button.setCache(true);
        checkBox.setCache(true);

        vBox.setAlignment(Pos.CENTER);
        stackPane.setPrefWidth(225);
        stackPane.setPrefHeight(225);
        imageView.setFitWidth(225);
        imageView.setFitHeight(225);
        imageName.setTextOverrun(OverrunStyle.ELLIPSIS);
        imageName.setMaxWidth(256);
        imageName.setAlignment(Pos.CENTER);
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

        this.xmlImage = xmlImage;
        file = new File(xmlImage.GetPath());
        Image scaledImage = new Image(file.toURI().toString(), 225, 225, true, true);
        imageView.setImage(scaledImage);
        imageName.setText(xmlImage.GetName());
    }
    AnchorPane GetAnchorPane() {
        return anchorPane;
    }
    ImageView GetImageView() {
        return imageView;
    }
    Label GetImageLabel() {
        return imageName;
    }
    String GetImageName() { return imageName.getText(); }
    JFXButton GetButton() {
        return button;
    }
    JFXCheckBox GetCheckBox() {
        return checkBox;
    }
    XMLImage GetXMLImage() { return xmlImage; }
    File GetFile() { return file; }
}
