package sample;

import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class Vertex extends Button {

    private static int count = 0;
    public int id;

    public Vertex(Double x, Double y) {
        setLayoutX(x);
        setLayoutY(y);
        setShape(new Circle(30));
        setPrefSize(50,40);
        translateXProperty().bind(widthProperty().divide(-2));
        translateYProperty().bind(heightProperty().divide(-2));
        id = count++;
        setText(id + "");
    }
}
