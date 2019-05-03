package res.Windows;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import res.Scenar.LevelCreate;
import res.Static.Values;

public class MainMenu extends Application {
    Stage primaryStageSaved;
    AnchorPane root;
    Scene scene;
    VBox vbox;

    Button[] buttons = new Button[4];

    public void start(Stage primaryStage) {
        try {
            primaryStageSaved = primaryStage;
            root = new AnchorPane();
            scene = new Scene(root, Values.sceneWidth, Values.sceneHeight);

            createElements();

            primaryStageSaved.setTitle(Values.title);
            primaryStageSaved.setScene(scene);
            primaryStageSaved.show();

        } catch(Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    void createElements(){
        if(!Values.levelLoaded){
            Values.loadAllLevels();
            Values.levelLoaded = true;
        }

        vbox = new VBox();
        vbox.setPrefWidth(400);
        vbox.setPrefHeight(400);

        vbox.setLayoutX((Values.sceneWidth - 400) / 2);
        vbox.setLayoutY((Values.sceneHeight - 400) / 2);
        vbox.setSpacing(5);

        buttons[0] = new Button("New game");
        buttons[1] = new Button("Add level");
        buttons[2] = new Button("Settings");
        buttons[3] = new Button("Exit");

        for(int i = 0; i < 4; i++){
            buttons[i].setPrefWidth(400);
            buttons[i].setPrefHeight(95);
            buttons[i].setFont(new Font(60));
            vbox.getChildren().add(buttons[i]);
        }

        setListeners();

        root.getChildren().add(vbox);
        scene.getStylesheets().add("/res/CSS/main.css");
    }

    void setListeners(){
        buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                LevelPickMenu menu = new LevelPickMenu();
                menu.start(primaryStageSaved);
            }
        });
        buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                LevelCreate levelCreate = new LevelCreate();
                levelCreate.start(primaryStageSaved);
            }
        });
        buttons[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                SettingsMenu menu = new SettingsMenu();
                menu.start(primaryStageSaved);
            }
        });
        buttons[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Platform.exit();
            }
        });
    }
}
