package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d pos = new Vector2d(2, 2);

    // 3.10 statyczny zbiór wszystkich zwierząt do pilnowania kolizji

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
        Vector2d newPos = getPos();
        switch (dir) {
            case FORWARD:
                newPos = newPos.add(getDirection().toUnitVector());
                break;
            case BACKWARD:
                newPos = newPos.subtract(getDirection().toUnitVector());
                break;
            case RIGHT:
                this.direction = getDirection().next();
                return;
            case LEFT:
                this.direction = getDirection().previous();
                return;
        }
        if (newPos.follows(World.MAP_BOTTOM_LEFT) && newPos.precedes(World.MAP_TOP_RIGHT)) {
            this.pos = newPos;
        }
    }

    @Override
    public String toString() {
        return "Animal(" + direction + ", " + pos + ")";
    }
}
