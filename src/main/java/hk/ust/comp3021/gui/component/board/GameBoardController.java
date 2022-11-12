package hk.ust.comp3021.gui.component.board;

import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.game.Position;
import hk.ust.comp3021.game.RenderingEngine;
import hk.ust.comp3021.gui.utils.Message;
import hk.ust.comp3021.gui.utils.Resource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;
import hk.ust.comp3021.entities.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control logic for a {@link GameBoard}.
 * <p>
 * GameBoardController serves the {@link RenderingEngine} which draws the current game map.
 */
public class GameBoardController implements RenderingEngine, Initializable {
    @FXML
    private GridPane map;

    @FXML
    private Label undoQuota;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Draw the game map in the {@link #map} GridPane.
     *
     * @param state The current game state.
     */
    @Override
    public void render(@NotNull GameState state) {
        // TODO
        int column = state.getMapMaxWidth();
        int row = state.getMapMaxHeight();
        final URL wall = Resource.getWallImageURL();
        final URL empty = Resource.getEmptyImageURL();
        final URL destination = Resource.getDestinationImageURL();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < row; ++i) {
                        for (int j = 0; j < column; ++j) {
                            Cell cell = new Cell();
                            URL imageURL = null;
                            Position pos = new Position(j, i);
                            boolean isDestination = isDestination(pos,state);
                            switch (state.getEntity(pos)) {
                                case null:
                                    continue;
                                case Box b:
                                    imageURL = Resource.getBoxImageURL(b.getPlayerId());
                                    if (isDestination) {
                                        cell.getController().markAtDestination();
                                    }
                                    break;
                                case Empty e:
                                    imageURL = isDestination? destination : empty;
                                    break;
                                case Player p:
                                    imageURL = Resource.getPlayerImageURL(p.getId());
                                    break;
                                case Wall w:
                                    imageURL = wall;
                            }
                            cell.getController().setImage(imageURL);
                            map.add(cell, j, i);
                        }
                    }
                    String undoText = "Undo Quota: ";
                    undoText += state.getUndoQuota().isPresent()? state.getUndoQuota().get().toString() : "unlimited";
                    undoQuota.setText(undoText);

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }
        });
    }

    private boolean isDestination(Position p, GameState state) {
        for (var entry : state.getDestinations()) {
            if (entry.x()==p.x() && entry.y()==p.y()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Display a message via a dialog.
     *
     * @param content The message
     */
    @Override
    public void message(@NotNull String content) {
        Platform.runLater(() -> Message.info("Sokoban", content));
    }
}
