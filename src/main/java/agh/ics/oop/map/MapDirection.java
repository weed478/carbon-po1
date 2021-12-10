package agh.ics.oop.map;

import agh.ics.oop.core.Vector2d;

public enum MapDirection {
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW
    ;

    public double angle() {
        switch (this) {
            case N:
                return 0 * 45;
            case NE:
                return 1 * 45;
            case E:
                return 2 * 45;
            case SE:
                return 3 * 45;
            case S:
                return 4 * 45;
            case SW:
                return 5 * 45;
            case W:
                return 6 * 45;
            case NW:
                return 7 * 45;
            default:
                throw new IllegalArgumentException("Invalid direction: " + this);
        }
    }

    public MapDirection turn(int turn) {
        MapDirection dir = this;
        if (turn >= 0) {
            for (int i = 0; i < turn; i++) {
                dir = dir.next();
            }
        }
        else {
            for (int i = 0; i < -turn; i++) {
                dir = dir.previous();
            }
        }
        return dir;
    }

    public MapDirection next() {
        switch (this) {
            case N:
                return NE;
            case NE:
                return E;
            case E:
                return SE;
            case SE:
                return S;
            case S:
                return SW;
            case SW:
                return W;
            case W:
                return NW;
            case NW:
                return N;
            default:
                throw new IllegalArgumentException();
        }
    }

    public MapDirection previous() {
        switch (this) {
            case N:
                return NW;
            case NE:
                return N;
            case E:
                return NE;
            case SE:
                return E;
            case S:
                return SE;
            case SW:
                return S;
            case W:
                return SW;
            case NW:
                return W;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Vector2d toUnitVector() {
        switch (this) {
            case N:
                return new Vector2d(0, 1);
            case NE:
                return new Vector2d(1, 1);
            case E:
                return new Vector2d(1, 0);
            case SE:
                return new Vector2d(1, -1);
            case S:
                return new Vector2d(0, -1);
            case SW:
                return new Vector2d(-1, -1);
            case W:
                return new Vector2d(-1, 0);
            case NW:
                return new Vector2d(-1, 1);
            default:
                throw new IllegalArgumentException();
        }
    }
}
