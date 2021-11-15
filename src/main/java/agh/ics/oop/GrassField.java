package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GrassField extends AbstractWorldMap {

    private final Map<Vector2d, Grass> grassFields = new HashMap<>();

    public GrassField(int numGrass) {
        Random r = new Random();
        int grassBound = (int) Math.ceil(Math.sqrt(10 * numGrass));
        for (int i = 0; i < numGrass; i++) {
            Vector2d p;
            do {
                p = new Vector2d(
                        r.nextInt(grassBound),
                        r.nextInt(grassBound)
                );
            } while (grassFields.containsKey(p));
            grassFields.put(p, new Grass(p));
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
}