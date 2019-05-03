package res.Windows;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import res.Scenar.Main;
import res.Static.Values;

public class LevelPickMenu extends Application {
    Stage primaryStageSaved;
    AnchorPane root;
    Scene scene;

    GridPane levelGrid = new GridPane();

    Button[][] levels = new Button[3][5];
    Button prev, next;
    Text label;
    public int startingPoint = 0;

    double gridWidth, gridHeight, tileWidth, tileHeight;

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

    void createElements() {
        scene.getStylesheets().add("/res/CSS/main.css");

        gridWidth = 400;
        gridHeight = 300;
        tileWidth = gridWidth / 5;
        tileHeight = gridHeight / 3 - 10;

        levelGrid.setPrefWidth(gridWidth);
        levelGrid.setPrefHeight(gridHeight);

        levelGrid.setHgap(10);
        levelGrid.setVgap(10);

        levelGrid.setLayoutX((Values.sceneWidth - gridWidth) / 2);
        levelGrid.setLayoutY((Values.sceneHeight - gridHeight) / 2);

        resetButtons();

        prev = new Button("◄");
        next = new Button("►");

        prev.setPrefWidth(tileWidth);
        prev.setPrefHeight(tileHeight);
        prev.setLayoutX(10);
        prev.setLayoutY((Values.sceneHeight - tileHeight) / 2 - 5);
        prev.setStyle("-fx-font-size: 40px");

        next.setPrefWidth(tileWidth);
        next.setPrefHeight(tileHeight);
        next.setLayoutX(Values.sceneWidth - tileWidth - 10);
        next.setLayoutY((Values.sceneHeight - tileHeight) / 2 - 5);
        next.setStyle("-fx-font-size: 40px");

        label = new Text(0, (Values.sceneHeight - gridHeight) / 2 - 50, "Levels");
        label.setWrappingWidth(Values.sceneWidth);
        label.setId("label");
        label.setFill(new Color(0.188, 0.247, 0.624, 1));
        label.setTextAlignment(TextAlignment.CENTER);

        setListeners();

        root.getChildren().add(levelGrid);
        root.getChildren().add(prev);
        root.getChildren().add(next);
        root.getChildren().add(label);
    }

    void resetButtons(){
        levelGrid.getChildren().clear();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                int index = startingPoint + i * 5 + j;

                levels[i][j] = new Button("" + (index + 1));
                levels[i][j].setPrefWidth(tileWidth);
                levels[i][j].setPrefHeight(tileHeight);
                levels[i][j].setStyle("-fx-font-size: 33px");

                if(index >= Values.levels.size()){
                    levels[i][j].setVisible(false);
                }

                levels[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        Main main = new Main();
                        main.level = index;
                        main.start(primaryStageSaved);
                    }
                });

                levelGrid.getChildren().add(levels[i][j]);
                levelGrid.setConstraints(levels[i][j], j, i);
            }
        }
    }

    void setListeners(){
        prev.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                startingPoint -= 15;
                if(startingPoint < 0) startingPoint = 0;
                resetButtons();
            }
        });
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                startingPoint += 15;
                if(startingPoint >= Values.levels.size()) startingPoint -= 15;
                resetButtons();
            }
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    MainMenu main = new MainMenu();
                    main.start(primaryStageSaved);
                    ke.consume();
                }
            }
        });
    }
}
