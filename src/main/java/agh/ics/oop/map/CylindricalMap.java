package agh.ics.oop.map;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;

public class CylindricalMap extends AbstractJungleMap {

    public CylindricalMap(Rect mapArea, Rect jungleArea) {
        super(mapArea, jungleArea);
    }

    @Override
    public Vector2d moveFrom(Vector2d position, MapDirection direction) {
        if (!mapArea.contains(position)) {
            throw new IllegalArgumentException("Position not on map: " + position);
        }
        Vector2d moveVector = direction.toUnitVector();
        Vector2d newPos = position.add(moveVector);
        newPos = new Vector2d(
                newPos.x,
                Math.max(mapArea.bottom(), Math.min(newPos.y, mapArea.top() - 1))
        );
        if (newPos.x >= mapArea.right()) {
            newPos = new Vector2d(
                    mapArea.left(),
                    newPos.y
            );
        }
        else if (newPos.x < mapArea.left()) {
            newPos = new Vector2d(
                    mapArea.right() - 1,
                    newPos.y
            );
        }
        return newPos;
    }
}
