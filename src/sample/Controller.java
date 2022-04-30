package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {

    @FXML
    AnchorPane graph;
    Vertex vertexTemp;
    Vertex vertexDelete;

    public void onGraphPressed(MouseEvent mouseEvent) {
        if(mouseEvent.isPrimaryButtonDown())
            createAndAddVertex(mouseEvent.getX(), mouseEvent.getY());
    }

    public void onGraphDragDetected(MouseEvent mouseEvent) {
        if(mouseEvent.isPrimaryButtonDown())
            vertexTemp = createAndAddVertex(mouseEvent.getX(), mouseEvent.getY());
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

    private Vertex createAndAddVertex(Double x, Double y) {
        Vertex vertex = new Vertex(x, y);


        vertex.setOnMousePressed(mouseEvent -> onVertexPressed(mouseEvent, vertex));
        vertex.setOnDragDetected(e -> onVertexDraggedDetected(e, vertex));
        vertex.setOnMouseDragged(e -> onVertexDragged(e, vertex));
        vertex.setOnMouseReleased(e -> onVertexReleased(e, vertex));
        graph.getChildren().add(vertex);
        return vertex;
    }

    private void onVertexPressed(MouseEvent mouseEvent, Vertex vertex) {
        vertexDelete = vertex;
    }

    private void onVertexReleased(MouseEvent e, Vertex vertex) {
        if(vertexDelete != null) {
            graph.getChildren().remove(vertexDelete);
        }
        vertexTemp = null;
        vertexDelete = null;
    }

    private void onVertexDraggedDetected(MouseEvent e, Node vertex) {
        vertex.toFront();
        vertexDelete = null;
        if(e.isPrimaryButtonDown()) {
            vertexTemp = createAndAddVertex(vertex.getLayoutX() + e.getX() + vertex.getTranslateX(),
                                            vertex.getLayoutY() + e.getY() + vertex.getTranslateY());
        }
    }

    private void onVertexDragged(MouseEvent e, Node vertex) {
        if(vertexTemp != null) {
            vertexTemp.setLayoutX(vertex.getLayoutX() + e.getX() + vertex.getTranslateX());
            vertexTemp.setLayoutY(vertex.getLayoutY() + e.getY() + vertex.getTranslateY());
        }
        if(e.isSecondaryButtonDown()) {
            vertex.setLayoutX(vertex.getLayoutX() + e.getX() + vertex.getTranslateX());
            vertex.setLayoutY(vertex.getLayoutY() + e.getY() + vertex.getTranslateY());
        }
    }
}
