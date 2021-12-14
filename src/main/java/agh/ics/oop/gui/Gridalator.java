package agh.ics.oop.gui;

import agh.ics.oop.IDrawableMap;
import agh.ics.oop.IMapElement;
import agh.ics.oop.Rect;
import agh.ics.oop.Vector2d;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;

/**
 * IDrawableMap to JavaFX GridPane converter.
 */
public class Gridalator {

    private static final int GRID_SIZE = 50;

    private final IDrawableMap map;

    /**
     * Create a Gridalator instance for the given map.
     */
    public Gridalator(IDrawableMap map) {
        this.map = map;
    }

    /**
     * Create a GridPane representation of the map.
     */
    public GridPane makeGrid() {
        Rect bounds = map.getDrawingBounds();

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        // size constraints
        for (int i = 0; i <= bounds.width(); i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(GRID_SIZE));
        }
        for (int i = 0; i <= bounds.height(); i++) {
            gridPane.getRowConstraints().add(new RowConstraints(GRID_SIZE));
        }

        // axis labels
        gridPane.add(new Label("y\\x"), 0, 0);
        for (int x = bounds.getBL().x; x <= bounds.getTR().x; x++) {
            gridPane.add(makeField(String.valueOf(x)), x - bounds.getBL().x + 1, 0);
        }
        for (int y = bounds.getBL().y; y <= bounds.getTR().y; y++) {
            gridPane.add(makeField(String.valueOf(y)), 0, bounds.getTR().y - y + 1);
        }

        // populate grid
        for (int x = bounds.getBL().x; x <= bounds.getTR().x; x++) {
            for (int y = bounds.getBL().y; y <= bounds.getTR().y; y++) {
                Vector2d mapPos = new Vector2d(x, y);
                Vector2d gridPos = mapToGrid(mapPos, bounds).add(new Vector2d(1, 1));

                IMapElement o = map.objectAt(mapPos);

                if (o != null) {
                    Node field = GuiElementBox.create(o);
                    gridPane.add(field, gridPos.x, gridPos.y);
                }
            }
        }

        return gridPane;
    }

    /**
     * Convert map coords to grid indices.
     * @param v map coords
     * @param bounds viewport size (in map coords)
     * @return grid indices
     */
    private static Vector2d mapToGrid(Vector2d v, Rect bounds) {
        v = v.subtract(bounds.getBL());
        v = new Vector2d(v.x, bounds.getTR().y - bounds.getBL().y - v.y);
        return v;
    }

    /**
     * Create a centered grid label.
     * @param text label text
     * @return label
     */
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
