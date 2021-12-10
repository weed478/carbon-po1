package agh.ics.oop.objects;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawableElement;
import agh.ics.oop.map.IGrassMap;
import agh.ics.oop.map.MapDirection;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grass extends AbstractObservableMapElement implements IDrawableElement {
    public Grass(IGrassMap map, Vector2d position) {
        super(position, MapDirection.NORTH);
        map.registerGrass(this);
    }

    @Override
    public Node getDrawableNode(int size) {
        Rectangle r = new Rectangle();
        r.setWidth(0.7 * size);
        r.setHeight(0.7 * size);
        r.setFill(Color.GREENYELLOW);
        return new Group(r);
    }
}
