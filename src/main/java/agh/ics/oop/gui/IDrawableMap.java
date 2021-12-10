package agh.ics.oop.gui;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;

public interface IDrawableMap {

    Rect getDrawingBounds();

    IDrawableElement getDrawableElementAt(Vector2d pos);
}
