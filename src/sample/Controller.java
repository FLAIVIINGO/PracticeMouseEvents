package sample;

import controls.Arrow;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {

    @FXML
    AnchorPane graph;
    Vertex vertex1;
    Vertex vertex2;
    Arrow arrow;
    Vertex vertexDelete;

    public void onGraphPressed(MouseEvent mouseEvent) {
        if(mouseEvent.isPrimaryButtonDown())
            vertex1 = createAndAddVertex(mouseEvent.getX(), mouseEvent.getY());
    }

    public void onGraphDragDetected(MouseEvent mouseEvent) {
        if(mouseEvent.isPrimaryButtonDown()) {
            vertex2 = createAndAddVertex(mouseEvent.getX(), mouseEvent.getY());
            arrow = createAndAddArrow(vertex1, vertex2);
            vertex2.getStyleClass().add("dragged");
            arrow.getStyleClass().add("dragged");
        }
    }

    public void onGraphDragged(MouseEvent mouseEvent) {
        if(vertex2 != null) {
            vertex2.setLayoutX(mouseEvent.getX());
            vertex2.setLayoutY(mouseEvent.getY());
        }
    }

    public void onGraphReleased(MouseEvent mouseEvent) {
        if (vertex2 != null) {
            vertex2.getStyleClass().remove("dragged");
            arrow.getStyleClass().remove("dragged");
        }
        vertex2 = null;
    }

    private Vertex createAndAddVertex(Double x, Double y) {
        Vertex vertex = new Vertex(x, y);

        vertex.setOnAction(e -> {
            for(Arrow a : vertex.edges) {
                a.setHeadAVisible(!a.isHeadAVisible());
                a.setHeadBVisible(!a.isHeadBVisible());
            }
        });
        vertex.setOnMousePressed(mouseEvent -> onVertexPressed(mouseEvent, vertex));
        vertex.setOnDragDetected(e -> onVertexDraggedDetected(e, vertex));
        vertex.setOnMouseDragged(e -> onVertexDragged(e, vertex));
        vertex.setOnMouseReleased(e -> onVertexReleased(e, vertex));
        graph.getChildren().add(vertex);
        return vertex;
    }

    private Arrow createAndAddArrow(Vertex v1, Vertex v2) {
        Arrow arrow = new Arrow(v1.getLayoutX(), v1.getLayoutY(), v2.getLayoutX(), v2.getLayoutY());
        arrow.x1Property().bind(v1.layoutXProperty());
        arrow.y1Property().bind(v1.layoutYProperty());
        arrow.x2Property().bind(v2.layoutXProperty());
        arrow.y2Property().bind(v2.layoutYProperty());

        v1.edges.add(arrow);
        v2.edges.add(arrow);
        graph.getChildren().add(arrow);
        return arrow;
    }

    private void onVertexPressed(MouseEvent mouseEvent, Vertex vertex) {
        if(mouseEvent.isPrimaryButtonDown()) {
            vertex1 = vertex;
        } else if(mouseEvent.isSecondaryButtonDown()) {
            vertexDelete = vertex;
        }
    }

    private void onVertexReleased(MouseEvent e, Vertex vertex) {
        vertex.getStyleClass().remove("dragged");
        for(Arrow a : vertex.edges) {
            a.getStyleClass().remove("dragged");
        }
        if(vertex2 != null) {
            vertex2.getStyleClass().remove("dragged");
        }

        if(vertexDelete != null) {
            graph.getChildren().remove(vertexDelete);
            for(Arrow a : vertexDelete.edges) {
                graph.getChildren().remove(a);
            }
        }
        vertex2 = null;
        vertexDelete = null;
    }

    private void onVertexDraggedDetected(MouseEvent e, Vertex vertex) {
        vertex.toFront();
        if(e.isPrimaryButtonDown()) {
            vertex2 = createAndAddVertex(vertex.getLayoutX() + e.getX() + vertex.getTranslateX(),
                                            vertex.getLayoutY() + e.getY() + vertex.getTranslateY());
            arrow = createAndAddArrow(vertex1, vertex2);
            vertex2.getStyleClass().add("dragged");
            arrow.getStyleClass().add("dragged");
        } else if(e.isSecondaryButtonDown()) {
            vertexDelete = null;
            vertex.getStyleClass().add("dragged");
            for(Arrow a : vertex.edges) {
                a.getStyleClass().add("dragged");
            }
        }
    }

    private void onVertexDragged(MouseEvent e, Node vertex) {
        if(vertex2 != null) {
            vertex2.setLayoutX(vertex.getLayoutX() + e.getX() + vertex.getTranslateX());
            vertex2.setLayoutY(vertex.getLayoutY() + e.getY() + vertex.getTranslateY());
        }
        if(e.isSecondaryButtonDown()) {
            vertex.setLayoutX(vertex.getLayoutX() + e.getX() + vertex.getTranslateX());
            vertex.setLayoutY(vertex.getLayoutY() + e.getY() + vertex.getTranslateY());
        }
    }
}
