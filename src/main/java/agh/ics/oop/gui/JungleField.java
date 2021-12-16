package agh.ics.oop.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class JungleField implements IDrawable {

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.FORESTGREEN);
        gc.fillRect(0.01, 0.01, 0.98, 0.98);
    }
}
