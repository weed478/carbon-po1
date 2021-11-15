package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap {

    private final Rect bounds;

    public RectangularMap(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException("Width/height must be >0");
        }
        bounds = new Rect(0, 0, width - 1, height - 1);
    }

    @Override
    protected Rect getDrawingBounds() {
        return bounds;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return bounds.contains(position) &&
               super.canMoveTo(position);
    }
}
