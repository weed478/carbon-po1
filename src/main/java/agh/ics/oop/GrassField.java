package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GrassField extends AbstractWorldMap {

    private final Map<Vector2d, Grass> grassFields = new HashMap<>();
    private final int grassBound;

    public GrassField(int numGrass) {
        grassBound = (int) Math.ceil(Math.sqrt(10 * numGrass));
        for (int i = 0; i < numGrass; i++) {
            growGrass();
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return grassFields.containsKey(position) ||
               super.isOccupied(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object o = super.objectAt(position);
        if (o == null) {
            o = grassFields.getOrDefault(position, null);
        }
        return o;
    }

    public void eatGrass(Vector2d pos) {
        grassFields.remove(pos);
        growGrass();
    }

    public void growGrass() {
        Random r = new Random();
        Vector2d p;
        do {
            p = new Vector2d(
                    r.nextInt(grassBound),
                    r.nextInt(grassBound)
            );
        } while (isOccupied(p));
        grassFields.put(p, new Grass(p));
    }
}
