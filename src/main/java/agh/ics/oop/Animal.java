package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d pos = new Vector2d(2, 2);
    private final IWorldMap map;

    public Animal(IWorldMap map) {
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.pos = initialPosition;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public Vector2d getPos() {
        return pos;
    }

    public boolean isAt(Vector2d pos) {
        return getPos().equals(pos);
    }

    public void move(MoveDirection dir) {
        switch (dir) {
            case RIGHT:
                this.direction = getDirection().next();
                return;
            case LEFT:
                this.direction = getDirection().previous();
                return;
        }

        Vector2d newPos = getPos();

        switch (dir) {
            case FORWARD:
                newPos = newPos.add(getDirection().toUnitVector());
                break;
            case BACKWARD:
                newPos = newPos.subtract(getDirection().toUnitVector());
                break;
        }

        if (map.canMoveTo(newPos)) {
            this.pos = newPos;
        }
    }

    @Override
    public String toString() {
        switch (direction) {
            case NORTH:
                return "^";
            case SOUTH:
                return "v";
            case WEST:
                return "<";
            case EAST:
                return ">";
            default:
                throw new IllegalArgumentException("Invalid animal direction");
        }
    }
}
