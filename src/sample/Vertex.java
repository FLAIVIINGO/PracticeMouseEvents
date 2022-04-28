package sample;

import javafx.scene.control.Button;

public class Vertex extends Button {

    private static int count = 0;
    public int id;

    public Vertex(Double x, Double y) {
        setLayoutX(x);
        setLayoutY(y);
        translateXProperty().bind(widthProperty().divide(-2));
        translateYProperty().bind(heightProperty().divide(-2));
        id = count++;
        setText(id + "");
    }
}
