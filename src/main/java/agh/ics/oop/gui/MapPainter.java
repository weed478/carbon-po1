package agh.ics.oop.gui;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.IWorldMap;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class MapPainter implements IDrawable {

    private final IWorldMap map;

    public MapPainter(IWorldMap map) {
        this.map = map;
    }

    @Override
    public void draw(GraphicsContext gc) {
        synchronized (map) {

            Rect bounds = map.getMapArea();

            double gridW = 1. / bounds.width();
            double gridH = 1. / bounds.height();

            for (int mx = bounds.getBL().x, cx = 0; mx < bounds.getTR().x; mx++, cx++) {
                for (int my = bounds.getBL().y, cy = bounds.height() - 1; my < bounds.getTR().y; my++, cy--) {
                    List<IDrawable> drawables = map.getDrawablesAt(new Vector2d(mx, my));

                    for (IDrawable drawable : drawables) {
                        gc.save();
                        gc.translate(gridW * cx, gridH * cy);
                        gc.scale(gridW * 0.98, gridH * 0.98);
                        drawable.draw(gc);
                        gc.restore();
                    }
                }
            }

        }
    }
}
