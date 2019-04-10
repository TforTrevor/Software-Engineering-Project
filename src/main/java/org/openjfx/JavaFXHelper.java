package org.openjfx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class JavaFXHelper {
    static public void AddDropShadow(Node element) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setWidth(20);
        dropShadow.setHeight(20);
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(2.5);
        dropShadow.setOffsetY(2.5);
        dropShadow.setColor(Color.color(0,0,0,0.5));

        element.setEffect(dropShadow);
    }

//    static public void FadeIn(Duration time, Node element) {
//        Timeline timeline = new Timeline();
//        KeyValue startOpacity = new KeyValue(element.opacityProperty(), 0);
//        KeyValue endOpacity = new KeyValue(element.opacityProperty(), 1);
//
//        KeyFrame start = new KeyFrame(Duration.ZERO, startOpacity);
//        KeyFrame end = new KeyFrame(time, endOpacity);
//
//        timeline.getKeyFrames().addAll(start, end);
//        timeline.play();
//    }

//    static public void FadeOut(Duration time, Node element) {
//        Timeline timeline = new Timeline();
//        KeyValue startOpacity = new KeyValue(element.opacityProperty(), 1);
//        KeyValue endOpacity = new KeyValue(element.opacityProperty(), 0);
//
//        KeyFrame start = new KeyFrame(Duration.ZERO, startOpacity);
//        KeyFrame end = new KeyFrame(time, endOpacity);
//
//        timeline.getKeyFrames().addAll(start, end);
//        timeline.play();
//    }
}
