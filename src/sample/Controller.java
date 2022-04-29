package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {

    @FXML
    AnchorPane graph;
    Vertex vertexTemp;

    public void onGraphPressed(MouseEvent mouseEvent) {
        if(mouseEvent.isPrimaryButtonDown())
            createAndAddVertex(mouseEvent);
    }

    public void onGraphDragDetected(MouseEvent mouseEvent) {
        if(mouseEvent.isPrimaryButtonDown())
            vertexTemp = createAndAddVertex(mouseEvent);
    }

    public void onGraphDragged(MouseEvent mouseEvent) {
        if(vertexTemp != null) {
            vertexTemp.setLayoutX(mouseEvent.getX());
            vertexTemp.setLayoutY(mouseEvent.getY());
        }
    }

    public void onGraphReleased(MouseEvent mouseEvent) {
        vertexTemp = null;
    }

    private Vertex createAndAddVertex(MouseEvent mouseEvent) {
        Vertex vertex = new Vertex(mouseEvent.getX(), mouseEvent.getY());



        vertex.setOnDragDetected(e -> onVertexDraggedDetected(e, vertex));
        vertex.setOnMouseDragged(e -> onVertexDragged(e, vertex));
        graph.getChildren().add(vertex);
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
