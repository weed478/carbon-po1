package agh.ics.oop.core;

import agh.ics.oop.map.MapDirection;
import agh.ics.oop.objects.IMapElement;

public interface IMapElementObserver {

    void mapElementMoved(IMapElement object, Vector2d oldPosition);

    void mapElementRotated(IMapElement object, MapDirection oldDirection);

    void mapElementRemoved(IMapElement object);
}
