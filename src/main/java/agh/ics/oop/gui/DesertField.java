package agh.ics.oop.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DesertField implements IDrawable {

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.DARKOLIVEGREEN);
        gc.fillRect(0, 0, 1, 1);
    }
}
