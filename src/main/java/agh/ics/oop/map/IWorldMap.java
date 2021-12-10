package agh.ics.oop.map;

import agh.ics.oop.core.Vector2d;

public interface IWorldMap {

    Vector2d moveFrom(Vector2d position, MapDirection direction);
}
