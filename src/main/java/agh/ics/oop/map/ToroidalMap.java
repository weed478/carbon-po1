package agh.ics.oop.map;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;

public class ToroidalMap extends AbstractAnimalAndGrassDrawableBoundedJungleMap {

    public ToroidalMap(Rect mapArea, Rect jungleArea) {
        super(mapArea, jungleArea);
    }

    @Override
    public Vector2d moveFrom(Vector2d position, MapDirection direction) {
        if (!mapArea.contains(position)) {
            throw new IllegalArgumentException("Position not on map: " + position);
        }

        Vector2d moveVector = direction.toUnitVector();
        Vector2d newPos = position.add(moveVector);

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

        if (newPos.y >= mapArea.top()) {
            newPos = new Vector2d(
                    newPos.x,
                    mapArea.bottom()
            );
        }
        else if (newPos.y < mapArea.bottom()) {
            newPos = new Vector2d(
                    newPos.x,
                    mapArea.top() - 1
            );
        }

        return newPos;
    }
}
