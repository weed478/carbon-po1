package agh.ics.oop.gui;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class GridBuilder {

    private static final int GRID_SIZE = 20;
    private final IDrawableMap map;

    public GridBuilder(IDrawableMap map) {
        this.map = map;
    }

    public GridPane buildGrid() {
        Rect bounds = map.getDrawingBounds();

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        gridPane.add(new Label("y\\x"), 0, 0);
        for (int x = bounds.getBL().x, xGrid = 1; x < bounds.getTR().x; x++, xGrid++) {
            gridPane.add(makeField(String.valueOf(x)), xGrid, 0);
        }
        for (int y = bounds.getTR().y - 1, yGrid = 1; y >= bounds.getBL().y; y--, yGrid++) {
            gridPane.add(makeField(String.valueOf(y)), 0, yGrid);
        }

        for (int x = bounds.getBL().x; x < bounds.getTR().x; x++) {
            for (int y = bounds.getBL().y; y < bounds.getTR().y; y++) {
                Vector2d mapPos = new Vector2d(x, y);
                Vector2d gridPos = mapToGrid(mapPos).add(new Vector2d(1, 1));

                IDrawableElement o = map.getDrawableElementAt(mapPos);

                Label field;
                if (o != null) {
                    field = makeField(o.toString());
                }
                else {
                    field = makeField("");
                }

                gridPane.add(field, gridPos.x, gridPos.y);
            }
        }

        return gridPane;
    }

    private Vector2d flipY(Vector2d v) {
        return new Vector2d(v.x, map.getDrawingBounds().height() - 1 - v.y);
    }

    private Vector2d mapToGrid(Vector2d v) {
        v = v.subtract(map.getDrawingBounds().getBL());
        v = flipY(v);
        return v;
    }

    private static Label makeField(String text) {
        Label field = new Label(text);
        field.setPrefSize(GRID_SIZE, GRID_SIZE);
        field.setMinSize(GRID_SIZE, GRID_SIZE);
        field.setMaxSize(GRID_SIZE, GRID_SIZE);
        field.setTextAlignment(TextAlignment.CENTER);
        field.setAlignment(Pos.CENTER);
        return field;
    }
}
