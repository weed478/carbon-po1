package agh.ics.oop.gui;

import agh.ics.oop.core.Rect;
import agh.ics.oop.core.Vector2d;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

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

        for (int i = 0; i < bounds.width() + 1; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(GRID_SIZE));
        }

        for (int i = 0; i < bounds.height() + 1; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(GRID_SIZE));
        }

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

                IDrawableElement mapElement = map.getDrawableElementAt(mapPos);

                if (mapElement != null) {
                    Node field = mapElement.getDrawableNode(GRID_SIZE);
                    GridPane.setHalignment(field, HPos.CENTER);
                    gridPane.add(field, gridPos.x, gridPos.y);
                }
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
        GridPane.setHalignment(field, HPos.CENTER);
        return field;
    }
}
