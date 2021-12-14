package agh.ics.oop;

/**
 * Animal with AI.
 */
public class HungryBot extends Animal {

    private static final int SEARCH_RADIUS = 5;

    public HungryBot(IWorldMap map, Vector2d pos) {
        super(map, pos);
    }

    /**
     * Find path to closest grass field by taxi distance.
     * Considers only fields within SEARCH_RADIUS.
     * If no grass is found move to map center.
     * First moves horizontally to match x then vertically.
     * @return next move
     */
    public MoveDirection decideNextMove() {
        Vector2d goal = null;
        Vector2d pos = getPos();

        for (int x = getPos().x - SEARCH_RADIUS; x <= getPos().x + SEARCH_RADIUS; x++) {
            for (int y = getPos().y - SEARCH_RADIUS; y <= getPos().y + SEARCH_RADIUS; y++) {
                Vector2d vec = new Vector2d(x, y);

                if (map.objectAt(vec) instanceof Grass) {
                    if (goal == null || pos.taxiDistance(vec) < pos.taxiDistance(goal)) {
                        goal = vec;
                    }
                }
            }
        }

        if (goal == null) {
            goal = new Vector2d(0, 0);
        }

        if (pos.x < goal.x) {
            switch (getDirection()) {
                case EAST:
                    return MoveDirection.FORWARD;
                case SOUTH:
                    return MoveDirection.LEFT;
                default:
                    return MoveDirection.RIGHT;
            }
        }
        else if (goal.x < pos.x) {
            switch (getDirection()) {
                case WEST:
                    return MoveDirection.FORWARD;
                case NORTH:
                    return MoveDirection.LEFT;
                default:
                    return MoveDirection.RIGHT;
            }
        }
        else if (goal.y > pos.y) {
            switch (getDirection()) {
                case NORTH:
                    return MoveDirection.FORWARD;
                case EAST:
                    return MoveDirection.LEFT;
                default:
                    return MoveDirection.RIGHT;
            }
        }
        else {
            switch (getDirection()) {
                case SOUTH:
                    return MoveDirection.FORWARD;
                case WEST:
                    return MoveDirection.LEFT;
                default:
                    return MoveDirection.RIGHT;
            }
        }
    }
}
