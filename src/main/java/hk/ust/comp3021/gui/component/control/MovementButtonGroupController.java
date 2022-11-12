package hk.ust.comp3021.gui.component.control;

import hk.ust.comp3021.actions.Action;
import hk.ust.comp3021.actions.Move;
import hk.ust.comp3021.entities.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;

/**
 * Control logic for {@link MovementButtonGroup}.
 */
public class MovementButtonGroupController implements Initializable {
    @FXML
    private GridPane playerControl;

    @FXML
    private ImageView playerImage;

    private Player player = null;
    static BlockingQueue<Action> queue = null;

    /**
     * Sets the player controller by the button group.
     *
     * @param player The player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * The URL to the profile image of the player.
     *
     * @param url The URL.
     */
    public void setPlayerImage(URL url) {
        // TODO
        playerImage.setImage(new Image(url.toString()));
    }

    @FXML
    private void moveUp() {
        // TODO
        queue.add(new Move.Up(player.getId()));
    }

    @FXML
    private void moveDown() {
        // TODO
        queue.add(new Move.Down(player.getId()));
    }

    @FXML
    private void moveLeft() {
        // TODO
        queue.add(new Move.Left(player.getId()));
    }

    @FXML
    private void moveRight() {
        // TODO
        queue.add(new Move.Right(player.getId()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO

    }

    public static void setQueue(BlockingQueue<Action> bQueue) {
        queue = bQueue;
    }
}
