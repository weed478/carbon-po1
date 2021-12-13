package agh.ics.oop;

public class Grass implements IMapElement {
    private final Vector2d pos;

    public Grass(Vector2d where) {
        pos = where;
    }

    public Vector2d getPosition() {
        return pos;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public String assetName() {
        return "src/main/resources/plant.png";
    }

    @Override
    public String mapLabel() {
        return "Grass";
    }
}
