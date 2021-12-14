package agh.ics.oop;

/**
 * Map that has defined drawing bounds.
 */
public interface IDrawableMap extends IWorldMap {

    /**
     * Get map area which should be drawn.
     * @return drawing area Rect
     */
    Rect getDrawingBounds();
}
