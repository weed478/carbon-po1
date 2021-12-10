package agh.ics.oop.objects;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.map.MapDirection;

public interface IMapElement {

    Vector2d getPosition();

    MapDirection getDirection();
}
