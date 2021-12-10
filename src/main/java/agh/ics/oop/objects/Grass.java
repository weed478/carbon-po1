package agh.ics.oop.objects;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.gui.IDrawableElement;
import agh.ics.oop.map.IGrassMap;
import agh.ics.oop.map.MapDirection;

public class Grass extends AbstractObservableMapElement implements IDrawableElement {
    public Grass(IGrassMap map, Vector2d position) {
        super(position, MapDirection.NORTH);
        map.registerGrass(this);
    }

    @Override
    public String toString() {
        return "*";
    }
}
