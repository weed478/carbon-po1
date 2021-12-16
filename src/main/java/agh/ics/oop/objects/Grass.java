package agh.ics.oop.objects;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawable;
import agh.ics.oop.map.IGrassMap;
import agh.ics.oop.map.MapDirection;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Grass extends AbstractObservableMapElement implements IDrawable {
    public Grass(IGrassMap map, Vector2d position) {
        super(position, MapDirection.N);
        map.registerGrass(this);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(0.3, 0.3, 0.4, 0.4);
    }
}
