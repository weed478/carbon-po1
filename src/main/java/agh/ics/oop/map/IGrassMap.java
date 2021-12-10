package agh.ics.oop.map;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.objects.Grass;

public interface IGrassMap {

    Grass getGrassAt(Vector2d pos);

    void growGrass();
}
