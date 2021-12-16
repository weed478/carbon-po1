package agh.ics.oop.gui;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;

import java.util.List;

public interface IDrawableMap {

    Rect getDrawingBounds();

    List<IDrawable> getDrawablesAt(Vector2d pos);
}
