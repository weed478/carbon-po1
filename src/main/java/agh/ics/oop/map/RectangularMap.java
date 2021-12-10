package agh.ics.oop.map;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;

public class RectangularMap extends AbstractAnimalAndGrassDrawableBoundedJungleMap {

    public RectangularMap(Rect mapArea, Rect jungleArea) {
        super(mapArea, jungleArea);
    }

    @Override
    public Vector2d moveFrom(Vector2d position, MapDirection direction) {
        if (!mapArea.contains(position)) {
            throw new IllegalArgumentException("Position not on map: " + position);
        }
        Vector2d moveVector = direction.toUnitVector();
        Vector2d newPos = position.add(moveVector);
        return new Vector2d(
                Math.max(mapArea.left(), Math.min(newPos.x, mapArea.right() - 1)),
                Math.max(mapArea.bottom(), Math.min(newPos.y, mapArea.top() - 1))
        );
    }
}
