package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class Controller {

    @FXML
    AnchorPane graph;

    public void onGraphPressed(MouseEvent mouseEvent) {
        graph.getChildren().add(createVertex(mouseEvent));
    }


    private Node createVertex(MouseEvent mouseEvent) {
        Vertex vertex = new Vertex(mouseEvent.getX(), mouseEvent.getY());
        vertex.setShape(new Circle(30));
        vertex.setPrefSize(50,40);



        vertex.setOnDragDetected(e -> onVertexDraggedDetected(e, vertex));
        vertex.setOnMouseDragged(e -> onVertexDragged(e, vertex));

        return vertex;
    }

    private void onVertexDraggedDetected(MouseEvent e, Node vertex) {
        vertex.toFront();
    }

    private void onVertexDragged(MouseEvent e, Node vertex) {
        vertex.setLayoutX(vertex.getLayoutX() + e.getX() + vertex.getTranslateX());
        vertex.setLayoutY(vertex.getLayoutY() + e.getY() + vertex.getTranslateY());
    }
}
