package hk.ust.comp3021.gui;

import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.gui.component.maplist.MapEvent;
import hk.ust.comp3021.gui.scene.game.ExitEvent;
import hk.ust.comp3021.gui.scene.game.GameScene;
import hk.ust.comp3021.gui.scene.start.StartScene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The JavaFX application that launches the game.
 */
public class App extends Application {
    private Stage primaryStage;
    private StartScene start;
    private GameScene game;

    /**
     * Set up the primary stage and show the {@link StartScene}.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Sokoban Game - COMP3021 2022Fall");
        // TODO
        this.start = new StartScene();
        primaryStage.setScene(start);
        primaryStage.show();
        primaryStage.addEventHandler(MapEvent.OPEN_MAP_EVENT_TYPE, this::onOpenMap);
        primaryStage.addEventHandler(ExitEvent.EVENT_TYPE, this::onExitGame);
    }

    /**
     * Event handler for opening a map.
     * Swith to the {@link GameScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to the map being opened.
     */
    public void onOpenMap(MapEvent event) {
        // TODO
        try {
            this.game = new GameScene(new GameState(event.getModel().gameMap()));
            primaryStage.setScene(this.game);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for exiting the game.
     * Switch to the {@link StartScene} in the {@link this#primaryStage}.
     *
     * @param event The event data related to exiting the game.
     */
    public void onExitGame(ExitEvent event) {
        // TODO
        primaryStage.setScene(start);
    }
}
