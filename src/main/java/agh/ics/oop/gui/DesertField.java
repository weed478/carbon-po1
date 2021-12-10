package agh.ics.oop.gui;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DesertField implements IDrawableElement {

    @Override
    public Node getDrawableNode(int size) {
        Rectangle r = new Rectangle();
        r.setWidth(size);
        r.setHeight(size);
        r.setFill(Color.DARKOLIVEGREEN);
        return new Group(r);
    }
}
