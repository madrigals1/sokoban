package res.Scenar;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import res.Objects.Bomb;
import res.Objects.Crate;
import res.Objects.Player;
import res.Static.Values;
import res.Windows.MainMenu;

public class Main extends Application {

    AnchorPane root;  // Root Pane
    AnchorPane bombLayer, cratesLayer, playerLayer, arrowLayer; // Layers

    // Scene parameters
    Stage primaryStageSaved;
    Scene scene;

    // Image places 10x10
    ImageView[][] images;

    // Crates and bombs hoders
    public static List<Crate> crates;
    List<Bomb> bombs;

    // Player 1 instance only
    Player player;

    // Level as 10x10 int
    public static int[][] tiles;

    // Level number
    public int level = 0;
    int levelMax = 0;

    public boolean rerun = true;

    // Tile names to find them from src/res/Assets/
    String[] tileNames = new String[]{
            "null.png",
            "tree.png",
            "brick.png",
            "bomb.png",
            "crate.png",
            "player.png"
    };


    // JavaFX standard main method
    public static void main(String[] args) {
        launch(args);
    }

    // JavaFX standard start method with 2 voids which i run
    public void start(Stage primaryStage) {
        try {
            primaryStageSaved = primaryStage;
            root = new AnchorPane();
            scene = new Scene(root, Values.sceneWidth, Values.sceneHeight);

            firstRun();
            restart();

            primaryStageSaved.setTitle(Values.title);
            primaryStageSaved.setScene(scene);
            primaryStageSaved.show();

        } catch (Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    // Only runs once at the beginning of program
    public void firstRun(){

        Values.getTileSize();

        player = new Player(2,2);
        images = new ImageView[10][10];

        Image imgnull = new Image(getClass().getResourceAsStream("/res/Assets/null.png"));

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                images[i][j] = new ImageView(imgnull);

                images[i][j].setFitHeight(Values.tileHeight);
                images[i][j].setFitWidth(Values.tileWidth);

                images[i][j].setX(j*Values.tileWidth);
                images[i][j].setY(i*Values.tileHeight);
            }
        }

        crates = new ArrayList<>();
        bombs = new ArrayList<>();

        playerLayer = new AnchorPane();
        playerLayer.getChildren().add(player);

        setListeners();
    }

    // Runs each time when new level and reset
    public void restart(){
        tiles = copyLevel(Values.levels.get(level));
        levelMax = Values.levels.size() - 1;

        bombLayer = new AnchorPane();
        cratesLayer = new AnchorPane();
        arrowLayer = new AnchorPane();

        crates.clear();
        bombs.clear();

        root.getChildren().clear();

        createElements();

        root.getChildren().add(bombLayer);
        root.getChildren().add(cratesLayer);
        root.getChildren().add(playerLayer);
        root.getChildren().add(arrowLayer);

        getImages();
        checkBomb();
    }

    // Copies level from static script values to this script (Not changing static var)
    int[][] copyLevel(int[][] lvlins){
        int[][] lvlcopy = new int[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                lvlcopy[i][j] = lvlins[i][j];
            }
        }
        return lvlcopy;
    }

    // Managing all elements
    void createElements() {
        if(rerun) root.getChildren().clear();

        getGrassImage();
        manageAllTiles();

        rerun = false;
    }

    // Getting background image (Grass)
    public void getGrassImage(){
        Image image = new Image(getClass().getResourceAsStream("/res/Assets/grass.png"));
        ImageView imageView = new ImageView(image);

        imageView.setX(0);
        imageView.setY(0);

        imageView.setFitHeight(Values.sceneHeight);
        imageView.setFitWidth(Values.sceneWidth);

        root.getChildren().add(imageView);
    }

    // Getting all tile images and tile objects
    public void manageAllTiles(){
        for(int j = 0; j < 10; j++){
            for(int i = 0; i < 10; i++){
                if(tiles[i][j] == 0) continue;
                if(tiles[i][j] == 4) {
                    Crate tempCrate = new Crate(i,j);
                    crates.add(tempCrate);
                    cratesLayer.getChildren().add(tempCrate);
                    if(Values.arrowEnabled){
                        for(int k = 0; k < 4; k++){
                            arrowLayer.getChildren().add(tempCrate.arrow[k]);
                        }
                    }
                    continue;
                }
                if(tiles[i][j] == 5) {
                    player.setPosition(i,j);
                    continue;
                }
                if(tiles[i][j] == 3) {
                    Bomb tempBomb = new Bomb(i,j);
                    bombs.add(tempBomb);
                    bombLayer.getChildren().add(tempBomb);
                    continue;
                }
                if(tiles[i][j] == 6) {
                    Bomb tempBomb = new Bomb(i,j);
                    bombs.add(tempBomb);
                    bombLayer.getChildren().add(tempBomb);

                    Crate tempCrate = new Crate(i,j);
                    crates.add(tempCrate);
                    cratesLayer.getChildren().add(tempCrate);

                    tiles[i][j] = 4;
                    continue;
                }

                Image image = new Image(getClass().getResourceAsStream("/res/Assets/" + tileNames[tiles[i][j]]));

                images[i][j].setImage(image);

                if(rerun) {
                    root.getChildren().add(images[i][j]);
                }
            }
        }
    }

    // Getting crate by position (for Crate script)
    public static Crate getCrateAt(int x, int y) {

        for(int i = 0; i < crates.size(); i++){
            if(crates.get(i).x == x && crates.get(i).y == y){
                return(crates.get(i));
            }
        }

        return null;
    }

    // Setting movement (WASD), reset (R), prev-next level (B,N) buttons
    void setListeners(){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.W) {
                    move(0);
                    ke.consume();
                }
                if (ke.getCode() == KeyCode.A) {
                    move(1);
                    ke.consume();
                }
                if (ke.getCode() == KeyCode.S) {
                    move(2);
                    ke.consume();
                }
                if (ke.getCode() == KeyCode.D) {
                    move(3);
                    ke.consume();
                }
                if (ke.getCode() == KeyCode.B) {
                    levelChange(-1);
                    ke.consume();
                }
                if (ke.getCode() == KeyCode.N) {
                    levelChange(1);
                    ke.consume();
                }
                if (ke.getCode() == KeyCode.R) {
                    levelChange(0);
                    ke.consume();
                }
                if (ke.getCode() == KeyCode.ESCAPE) {
                    MainMenu menu = new MainMenu();
                    menu.start(primaryStageSaved);
                    ke.consume();
                }
            }
        });
    }

    // Moving player
    void move(int i){
        if(i == 0 && player.up){
            player.moveUp();
        }
        if(i == 1 && player.left){
            player.moveLeft();
        }
        if(i == 2 && player.down){
            player.moveDown();
        }
        if(i == 3 && player.right){
            player.moveRight();
        }
        checkAllCrateDirections();
        checkBomb();
    }

    // Getting image of each tile object
    void getImages(){
        for(int i = 0; i < bombs.size(); i++){
            bombs.get(i).getBombImage();
        }
        for(int i = 0; i < crates.size(); i++){
            crates.get(i).getCrateImage();
        }
        player.getPlayerImage();
    }

    // Checking all bombs if they are closed
    void checkBomb(){
        int bombClosedTemp = 0;

        for(int i = 0; i < bombs.size(); i++){
            for(int j = 0; j < crates.size(); j++){
                if(crates.get(i).x == bombs.get(j).x && crates.get(i).y == bombs.get(j).y){
                    crates.get(i).setEffect(new Glow(0.8));
                    bombClosedTemp++;
                    break;
                } else {
                    crates.get(i).setEffect(null);
                }
            }
        }

        if(bombClosedTemp == bombs.size()){
            levelChange(1);
        }
    }

    // Changing level prev, next, reset
    void levelChange(int i){
        rerun = true;
        level += i;

        if(level > levelMax) level = 0;
        if(level < 0) level = levelMax;

        restart();
    }

    // Check all crate movement directions
    public static void checkAllCrateDirections(){
        for(int i = 0; i < crates.size(); i++){
            crates.get(i).checkDirections();
        }
    }
}
