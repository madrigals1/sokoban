package res.objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import res.scenar.Main;
import res.constants.Values;

public class Player extends ImageView {
    public int x = 0;
    public int y = 0;

    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;

    public Player (int x, int y) {
        this.x = x;
        this.y = y;

        this.setX(y * Values.tileWidth);
        this.setY(x * Values.tileHeight);

        this.setFitHeight(Values.tileHeight);
        this.setFitWidth(Values.tileWidth);

        //this.setPreserveRatio(true);
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        this.setX(y * Values.tileWidth);
        this.setY(x * Values.tileHeight);
        this.checkDirections();
    }

    public void getPlayerImage(){
        Image image = new Image(getClass().getResourceAsStream("/res/assets/player.png"));
        this.setImage(image);
    }

    public void checkDirections () {
        if(x > 0){
            if(Main.tiles[x - 1][y] == 0 ||
               Main.tiles[x - 1][y] == 3 ||
               Main.tiles[x - 1][y] == 4){
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
               Main.tiles[x + 1][y] == 4){
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
               Main.tiles[x][y - 1] == 4){
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
               Main.tiles[x][y + 1] == 4) {
                this.right = true;
            } else {
                this.right = false;
            }
        } else {
            this.right = false;
        }
    }

    public void printDir(){
        System.out.println("up = "+ this.up);
        System.out.println("down = "+ this.down);
        System.out.println("left = "+ this.left);
        System.out.println("right = "+ this.right);
    }

    public void moveUp () {
        Main.tiles[this.x][this.y] = 0;
        this.x--;
        if(Main.tiles[this.x][this.y] == 4){
            Crate temp = Main.getCrateAt(this.x, this.y);
            if(temp.up) {
                temp.moveUp();
            } else {
                this.x++;
            }
        }
        Main.tiles[this.x][this.y] = 5;
        this.setY(this.x * Values.tileHeight);

        this.checkDirections();
        // printDir();
    }

    public void moveDown () {
        Main.tiles[this.x][this.y] = 0;
        this.x++;
        if(Main.tiles[this.x][this.y] == 4){
            Crate temp = Main.getCrateAt(this.x, this.y);
            if(temp.down) {
                temp.moveDown();
            } else {
                this.x--;
            }
        }
        Main.tiles[this.x][this.y] = 5;
        this.setY(this.x * Values.tileHeight);

        this.checkDirections();
        // printDir();
    }

    public void moveLeft () {
        Main.tiles[this.x][this.y] = 0;
        this.y--;
        if(Main.tiles[this.x][this.y] == 4){
            Crate temp = Main.getCrateAt(this.x, this.y);
            if(temp.left) {
                temp.moveLeft();
            } else {
                this.y++;
            }
        }
        Main.tiles[this.x][this.y] = 5;
        this.setX(this.y * Values.tileWidth);

        this.checkDirections();
        // printDir();
    }

    public void moveRight () {
        Main.tiles[this.x][this.y] = 0;
        this.y++;
        if(Main.tiles[this.x][this.y] == 4){
            Crate temp = Main.getCrateAt(this.x, this.y);
            if(temp.right) {
                temp.moveRight();
            } else {
                this.y--;
            }
        }
        Main.tiles[this.x][this.y] = 5;
        this.setX(this.y * Values.tileWidth);

        this.checkDirections();
        // printDir();
    }
}
