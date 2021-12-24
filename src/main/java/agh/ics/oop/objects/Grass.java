package agh.ics.oop.objects;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawable;
import agh.ics.oop.map.IGrassMap;
import agh.ics.oop.map.MapDirection;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class Grass extends AbstractObservableMapElement implements IDrawable {

    private static final Image image = new Image(Objects.requireNonNull(Grass.class.getResourceAsStream("plant.png")));

    public Grass(IGrassMap map, Vector2d position) {
        super(position, MapDirection.N);
        map.registerGrass(this);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, 0, 0, 1, 1);
    }
}
