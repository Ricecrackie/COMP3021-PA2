package hk.ust.comp3021.gui.component.maplist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller logic for {@link  MapList}.
 */
public class MapListController implements Initializable {
    @FXML
    private ListView<MapModel> list;
    private ObservableList<MapModel> mapModelObservableList = FXCollections.observableArrayList();

    /**
     * Initialize the controller.
     * You should customize the items in the list view here.
     * Set the item in the {@link MapList} to be {@link MapListCell}.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     * @see <a href="https://docs.oracle.com/javafx/2/ui_controls/list-view.htm">JavaFX ListView</a>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        list.setItems(mapModelObservableList);
        list.setCellFactory(mapListView -> new MapListCell());
    }

    public void addMap(URL path) {
        //URL path = getClass().getClassLoader().getResource(name);
        try {
            var map = MapModel.load(path);
            for (var entry : mapModelObservableList) {
                if (entry.file().compareTo(map.file()) == 0) {
                    mapModelObservableList.remove(entry);
                    break;
                }
            }
            mapModelObservableList.add(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getListIndex() {
        return list.getSelectionModel().getSelectedIndex();
    }
    public void deleteMap(int index) {
        mapModelObservableList.remove(index);
    }
    public int getCount() {
        return mapModelObservableList.size();
    }
    public MapModel getMap(int index) {
        return list.getItems().get(index);
    }
}
