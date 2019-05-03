package res.Objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import res.Scenar.Main;
import res.Static.Values;

public class Crate extends ImageView {
    public int x;
    public int y;

    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;

    public ImageView[] arrow = new ImageView[4];

    public Crate (int x, int y) {

        this.x = x;
        this.y = y;

        this.setX(y * Values.tileWidth);
        this.setY(x * Values.tileHeight);
        this.setFitHeight(Values.tileHeight);
        this.setFitWidth(Values.tileWidth);

        if(Values.arrowEnabled) manageArrows();

        this.checkDirections();
    }

    public void getCrateImage(){
        Image image = new Image(getClass().getResourceAsStream("/res/Assets/crate.png"));
        this.setImage(image);
    }

    public void printDir(){
        System.out.println("up = "+ this.up);
        System.out.println("down = "+ this.down);
        System.out.println("left = "+ this.left);
        System.out.println("right = "+ this.right);
    }

    public void manageArrows () {
        Image image = new Image(getClass().getResourceAsStream("/res/Assets/arrow.png"));

        double arrowWidth, arrowHeight;
        arrowWidth = Values.tileWidth/3;
        arrowHeight = Values.tileHeight/3;

        for(int i = 0; i < 4; i++){
            arrow[i] = new ImageView(image);
            arrow[i].setFitWidth(arrowWidth);
            arrow[i].setFitHeight(arrowHeight);
            //arrow[i].setPreserveRatio(true);
        }

        arrow[0].setX(this.getX() + arrowWidth);
        arrow[0].setY(this.getY() - arrowHeight/2);
        arrow[1].setX(this.getX() - arrowWidth/2);
        arrow[1].setY(this.getY() + arrowHeight);
        arrow[2].setX(this.getX() + arrowWidth);
        arrow[2].setY(this.getY() + Values.tileHeight/2 + arrowHeight);
        arrow[3].setX(this.getX() + Values.tileWidth/2 + arrowWidth);
        arrow[3].setY(this.getY() + arrowHeight);

        arrow[0].setRotate(270);
        arrow[1].setRotate(180);
        arrow[2].setRotate(90);
        arrow[3].setRotate(0);
    }

    public void setArrows () {
        if(this.up){
            arrow[0].setVisible(true);
        } else {
            arrow[0].setVisible(false);
        }

        if(this.left){
            arrow[1].setVisible(true);
        } else {
            arrow[1].setVisible(false);
        }

        if(this.down){
            arrow[2].setVisible(true);
        } else {
            arrow[2].setVisible(false);
        }

        if(this.right){
            arrow[3].setVisible(true);
        } else {
            arrow[3].setVisible(false);
        }
    }

    public void moveArrows(int i) {
        if(i == 0){
            for(int j = 0; j < 4; j++){
                arrow[j].setY(arrow[j].getY() - Values.tileHeight);
            }
        }
        if(i == 1){
            for(int j = 0; j < 4; j++){
                arrow[j].setX(arrow[j].getX() - Values.tileWidth);
            }
        }
        if(i == 2){
            for(int j = 0; j < 4; j++) {
                arrow[j].setY(arrow[j].getY() + Values.tileHeight);
            }
        }
        if(i == 3){
            for(int j = 0; j < 4; j++) {
                arrow[j].setX(arrow[j].getX() + Values.tileWidth);
            }
        }
    }

    public void checkDirections () {
        if(x > 0){
            if(Main.tiles[x - 1][y] == 0 ||
               Main.tiles[x - 1][y] == 3 ||
               Main.tiles[x - 1][y] == 5){
                this.up = true;
            } else {
                this.up = false;
            }
        } else {
            this.up = false;
        }
        if(x < 9){
            if(Main.tiles[x + 1][y] == 0 ||
               Main.tiles[x + 1][y] == 3 ||
               Main.tiles[x + 1][y] == 5){
                this.down = true;
            } else {
                this.down = false;
            }
        } else {
            this.down = false;
        }

        if(y > 0){
            if(Main.tiles[x][y - 1] == 0 ||
               Main.tiles[x][y - 1] == 3 ||
               Main.tiles[x][y - 1] == 5){
                this.left = true;
            } else {
                this.left = false;
            }
        } else {
            this.left = false;
        }

        if(y < 9){
            if(Main.tiles[x][y + 1] == 0 ||
               Main.tiles[x][y + 1] == 3 ||
               Main.tiles[x][y + 1] == 5) {
                this.right = true;
            } else {
                this.right = false;
            }
        } else {
            this.right = false;
        }

        if(Values.arrowEnabled) setArrows();
    }

    public void moveUp () {
        Main.tiles[this.x][this.y] = 0;
        this.x--;
        Main.tiles[this.x][this.y] = 4;
        this.setY(this.x * Values.tileHeight);

        if(Values.arrowEnabled) moveArrows(0);
    }

    public void moveDown () {
        Main.tiles[this.x][this.y] = 0;
        this.x++;
        Main.tiles[this.x][this.y] = 4;
        this.setY(this.x * Values.tileHeight);

        if(Values.arrowEnabled) moveArrows(2);
    }

    public void moveLeft () {
        Main.tiles[this.x][this.y] = 0;
        this.y--;
        Main.tiles[this.x][this.y] = 4;
        this.setX(this.y * Values.tileWidth);

        if(Values.arrowEnabled) moveArrows(1);
    }

    public void moveRight () {
        Main.tiles[this.x][this.y] = 0;
        this.y++;
        Main.tiles[this.x][this.y] = 4;
        this.setX(this.y * Values.tileWidth);

        if(Values.arrowEnabled) moveArrows(3);
    }
}
