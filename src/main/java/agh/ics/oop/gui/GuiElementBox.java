package agh.ics.oop.gui;

import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GuiElementBox {

    public static Node create(IMapElement element) {
        Image image = ImageProvider.getForAsset(element.assetName());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        Label label = new Label(element.mapLabel());
        VBox vBox = new VBox(imageView, label);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}
