package res.scenar;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import res.constants.Values;
import res.windows.LevelPickMenu;
import res.windows.MainMenu;

import java.io.*;

public class LevelCreate extends Application {

    // Panes for elements
    AnchorPane root, tilesPane, imagePane, buttonPane;
    FlowPane tileset;

    // Scene settings
    Stage primaryStageSaved;
    Scene scene;
    int sceneWidthLocal = 0;
    int sceneHeightLocal = 0;

    Button[][] buttons = new Button[10][10];
    Button[] buttonTiles = new Button[7];
    Button save, back, reset;

    ImageView thisTile;
    ImageView[][] ivs = new ImageView[10][10];
    Image[] tilesetImages = new Image[7];

    String title = "Sokoban";

    String[] tileNames = new String[]{
            "null.png",
            "tree.png",
            "brick.png",
            "bomb.png",
            "crate.png",
            "player.png",
            "cratebomb"
    };

    public static int[][] tiles = new int[][]{
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0}
    };

    int pickedTile;

    public void start(Stage primaryStage) {
        try {
            primaryStageSaved = primaryStage;
            root = new AnchorPane();

            sceneWidthLocal = Values.sceneWidth + 200;
            sceneHeightLocal = Values.sceneHeight;

            scene = new Scene(root, sceneWidthLocal, sceneHeightLocal);

            firstRun();

            primaryStageSaved.setTitle(title);
            primaryStageSaved.setScene(scene);
            primaryStageSaved.show();

        } catch(Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    void firstRun() {

        Values.getTileSize();

        tilesPane = new AnchorPane();
        tileset = new FlowPane();
        imagePane = new AnchorPane();
        buttonPane = new AnchorPane();

        createElements();
        setListeners();

        root.getChildren().add(tilesPane);
        root.getChildren().add(buttonPane);
        root.getChildren().add(tileset);
        root.getChildren().add(imagePane);
    }

    public void createElements () {
        scene.getStylesheets().add("/res/css/second.css");

        tilesPane.setLayoutX(0);
        tilesPane.setLayoutY(0);
        tilesPane.setPrefWidth(Values.sceneWidth);
        tilesPane.setPrefHeight(Values.sceneHeight);

        buttonPane.setLayoutX(Values.sceneWidth);
        buttonPane.setLayoutY(Values.sceneHeight - 200);
        buttonPane.setPrefWidth(200);
        buttonPane.setPrefHeight(200);
        buttonPane.setStyle("-fx-background-color: gray");

        imagePane.setLayoutX(Values.sceneWidth);
        imagePane.setLayoutY(0);
        imagePane.setPrefWidth(200);
        imagePane.setPrefHeight(200);
        imagePane.setStyle("-fx-background-color: gray");

        tileset.setLayoutX(Values.sceneWidth);
        tileset.setLayoutY(200);
        tileset.setPrefWidth(200);
        tileset.setPrefHeight(Values.sceneHeight-400);
        tileset.setStyle("-fx-background-color: gray; -fx-padding: 10px");
        tileset.setHgap(10);
        tileset.setVgap(10);
        tileset.setAlignment(Pos.CENTER);

        {
            Image image = new Image(getClass().getResourceAsStream("/res/assets/grass.png"));
            ImageView iv = new ImageView(image);

            iv.setLayoutX(0);
            iv.setLayoutY(0);

            iv.setFitWidth(Values.sceneWidth);
            iv.setFitHeight(Values.sceneHeight);

            root.getChildren().add(iv);
        }

        {
            save = new Button("Save");
            back = new Button("Back");
            reset = new Button("Reset");

            save = new Button("Save");
            save.setLayoutX(50);
            save.setLayoutY(15);
            save.setPrefSize(100, 40);
            save.setId("btn");
            save.setStyle("-fx-font-size: 20px");

            reset = new Button("Reset");
            reset.setLayoutX(50);
            reset.setLayoutY(70);
            reset.setPrefSize(100, 40);
            reset.setId("btn");
            reset.setStyle("-fx-font-size: 20px");

            back = new Button("Back");
            back.setLayoutX(50);
            back.setLayoutY(125);
            back.setPrefSize(100, 40);
            back.setId("btn");
            back.setStyle("-fx-font-size: 20px");

            buttonPane.getChildren().add(back);
            buttonPane.getChildren().add(save);
            buttonPane.getChildren().add(reset);
        }

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                ivs[i][j] = new ImageView();

                ivs[i][j].setX(0);
                ivs[i][j].setY(0);

                ivs[i][j].setFitWidth(Values.tileWidth);
                ivs[i][j].setFitHeight(Values.tileHeight);

                buttons[i][j] = new Button("", ivs[i][j]);
                buttons[i][j].setStyle("-fx-padding: 0;" +
                        " -fx-background-color: rgba(0,0,0,0);" +
                        " -fx-border-color: black; -fx-border-width: 1px;");
                buttons[i][j].setLayoutX(j * Values.tileWidth);
                buttons[i][j].setLayoutY(i * Values.tileHeight);
                buttons[i][j].setPrefWidth(Values.tileWidth);
                buttons[i][j].setPrefHeight(Values.tileHeight);

                int temp_i = i;
                int temp_j = j;

                buttons[i][j].setOnAction(e -> setTile(temp_i, temp_j));

                tilesPane.getChildren().add(buttons[i][j]);
            }
        }

        {
           Image image = new Image(getClass().getResourceAsStream("/res/assets/null.png"));
           thisTile = new ImageView(image);

           thisTile.setLayoutX(50);
           thisTile.setLayoutY(50);

           thisTile.setFitWidth(100);
           thisTile.setFitHeight(100);

           thisTile.setPreserveRatio(true);

           imagePane.getChildren().add(thisTile);
        }

        imagePane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        tileset.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        buttonPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        for(int i = 0; i < 7; i++){
            Image image;
            ImageView iv;

            if(tileNames[i].equals("cratebomb")) {
                image = new Image(getClass().getResourceAsStream("/res/assets/crate.png"));
                iv = new ImageView(image);
                iv.setEffect(new Glow(0.8));
            } else {
                image = new Image(getClass().getResourceAsStream("/res/assets/" + tileNames[i]));
                iv = new ImageView(image);
            }

            tilesetImages[i] = image;

            buttonTiles[i] = new Button("", iv);
            buttonTiles[i].setStyle("-fx-padding: 0px;");
            buttonTiles[i].setPrefHeight(52);
            buttonTiles[i].setPrefWidth(52);

            iv.setFitWidth(52);
            iv.setFitHeight(52);

            int tempi = i;

            buttonTiles[i].setOnAction(e -> changePickedTile(tempi));

            tileset.getChildren().add(buttonTiles[i]);
        }
    }

    void changePickedTile (int i){
        pickedTile = i;
        thisTile.setImage(tilesetImages[i]);
        if(i == 6){
            thisTile.setEffect(new Glow(0.8));
        } else {
            thisTile.setEffect(null);
        }
    }

    void setTile (int i, int j){
        if(pickedTile == 0){
            ivs[i][j].setImage(null);
        } else if(pickedTile == 6) {
            ivs[i][j].setImage(tilesetImages[pickedTile]);
            ivs[i][j].setEffect(new Glow(0.8));
        } else {
            ivs[i][j].setImage(tilesetImages[pickedTile]);
            ivs[i][j].setEffect(null);
        }

        tiles[i][j] = pickedTile;
    }

    void setListeners(){
        save.setOnAction(event -> {
            Values.addLevel(tiles);
            saveLevel();
            int x = (Values.levels.size() - 1) / 15;
            LevelPickMenu menu = new LevelPickMenu();
            menu.startingPoint = x * 15;
            menu.start(primaryStageSaved);
        });

        reset.setOnAction(event -> clearLevel());

        back.setOnAction(event -> {
            MainMenu menu = new MainMenu();
            menu.start(primaryStageSaved);
        });
    }

    void clearLevel(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                tiles[i][j] = 0;
                ivs[i][j].setImage(null);
            }
        }
    }

    public static void saveLevel(){
        if(checkBomb()){
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    print("" + tiles[i][j]);
                    if(i != 9 || j != 9) print(",");
                }
            }
            println("#");
            tiles = new int[10][10];
        } else {
            System.out.println("Error, bomb amount not same with crate amount!");
        }
    }

    static boolean checkBomb (){
        int bombAmount = 0, crateAmount = 0, playerAmount = 0;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(tiles[i][j] == 3) bombAmount++;
                if(tiles[i][j] == 4) crateAmount++;
                if(tiles[i][j] == 5) playerAmount++;
            }
        }

        return bombAmount != 0 && bombAmount == crateAmount && playerAmount == 1;
    }

    static void print(String s){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Values.levelsPath, true))) {
            bw.write(s);
        } catch (IOException e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    static void println(String s){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Values.levelsPath, true))) {
            bw.write(s);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            Platform.exit();
        }
    }
}