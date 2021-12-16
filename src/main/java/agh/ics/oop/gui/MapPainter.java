package agh.ics.oop.gui;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class MapPainter implements IDrawable {

    private final IDrawableMap map;

    public MapPainter(IDrawableMap map) {
        this.map = map;
    }

    @Override
    public void draw(GraphicsContext gc) {
        synchronized (map) {

            Rect bounds = map.getDrawingBounds();

            double gridW = 1. / bounds.width();
            double gridH = 1. / bounds.height();

            for (int mx = bounds.getBL().x, cx = 0; mx < bounds.getTR().x; mx++, cx++) {
                for (int my = bounds.getBL().y, cy = bounds.height() - 1; my < bounds.getTR().y; my++, cy--) {
                    List<IDrawable> drawables = map.getDrawablesAt(new Vector2d(mx, my));

                    for (IDrawable drawable : drawables) {
                        gc.save();
                        gc.translate(gridW * cx, gridH * cy);
                        gc.scale(gridW, gridH);
                        drawable.draw(gc);
                        gc.restore();
                    }
                }
            }

        }
    }
}
