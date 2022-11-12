package hk.ust.comp3021.gui.scene.start;

import hk.ust.comp3021.gui.component.maplist.MapEvent;
import hk.ust.comp3021.gui.component.maplist.MapList;
import hk.ust.comp3021.gui.component.maplist.MapModel;
import hk.ust.comp3021.gui.utils.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control logic for {@link  StartScene}.
 */
public class StartController implements Initializable {
    @FXML
    private MapList mapList;

    @FXML
    private Button deleteButton;

    @FXML
    private Button openButton;

    /**
     * Initialize the controller.
     * Load the built-in maps to {@link this#mapList}.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        this.mapList.getController().addMap(getClass().getClassLoader().getResource("map00.map"));
        this.mapList.getController().addMap(getClass().getClassLoader().getResource("map01.map"));
        setButtons(true);
        this.mapList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setButtons(false);
        });
    }

    /**
     * Event handler for the open button.
     * Display a file chooser, load the selected map and add to {@link this#mapList}.
     * If the map is invalid or cannot be loaded, display an error message.
     *
     * @param event Event data related to clicking the button.
     */
    @FXML
    private void onLoadMapBtnClicked(ActionEvent event) {
        // TODO
        var node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        final var fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(thisStage);
        if (selectedFile != null) {
            try {
                var map = MapModel.load(selectedFile.toURI().toURL());
                if (map.gameMap().getPlayerIds().size() > 4) {
                    Message.error("Load map failed", "There can only be at most 4 players in a map.");
                    return;
                }
                mapList.getController().addMap(selectedFile.toURI().toURL());
            } catch (Exception e) {
                Message.error("Load map failed", e.getMessage());
            }
        }
    }

    /**
     * Handle the event when the delete button is clicked.
     * Delete the selected map from the {@link this#mapList}.
     */
    @FXML
    public void onDeleteMapBtnClicked() {
        // TODO
        int index = mapList.getController().getListIndex();
        mapList.getController().deleteMap(index);
        if (mapList.getController().getCount() == 0) {
            setButtons(true);
        }
    }

    private void setButtons(boolean disable) {
        openButton.setDisable(disable);
        deleteButton.setDisable(disable);
    }

    /**
     * Handle the event when the map open button is clicked.
     * Retrieve the selected map from the {@link this#mapList}.
     * Fire an {@link MapEvent} so that the {@link hk.ust.comp3021.gui.App} can handle it and switch to the game scene.
     */
    @FXML
    public void onOpenMapBtnClicked() {
        // TODO
        int index = mapList.getController().getListIndex();
        var e = new MapEvent(MapEvent.OPEN_MAP_EVENT_TYPE, mapList.getController().getMap(index));
        Node node = openButton.getScene().getRoot();
        node.fireEvent(e);
    }

    /**
     * Handle the event when a file is dragged over.
     *
     * @param event The drag event.
     * @see <a href="https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm">JavaFX Drag and Drop</a>
     */
    @FXML
    public void onDragOver(DragEvent event) {
        // TODO
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.LINK);
        }
    }

    /**
     * Handle the event when the map file is dragged to this app.
     * <p>
     * Multiple files should be supported.
     * Display error message if some dragged files are invalid.
     * All valid maps should be loaded.
     *
     * @param dragEvent The drag event.
     * @see <a href="https://docs.oracle.com/javafx/2/drag_drop/jfxpub-drag_drop.htm">JavaFX Drag and Drop</a>
     */
    @FXML
    public void onDragDropped(DragEvent dragEvent) {
        // TODO
        var f = dragEvent.getDragboard().getFiles();
        for (var file : f) {
            try {
                var map = MapModel.load(file.toURI().toURL());
                if (map.gameMap().getPlayerIds().size() > 4) {
                    Message.error("Load map failed", "There can only be at most 4 players in a map.");
                    return;
                }
                mapList.getController().addMap(file.toURI().toURL());
            } catch (Exception e) {
                Message.error("Load map failed", e.getMessage());
            }
        }
    }
}
