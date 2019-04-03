package org.openjfx;

import javafx.animation.FadeTransition;
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

    static public void FadeOpacity(Duration time, Node element) {
        FadeTransition fadeTransition = new FadeTransition(time, element);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}
