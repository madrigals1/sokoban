package res.objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import res.constants.Values;

public class Bomb extends ImageView {
    public int x = 0;
    public int y = 0;

    public boolean closed;

    public Bomb (int x, int y) {
        this.x = x;
        this.y = y;

        this.setX(y * Values.tileWidth);
        this.setY(x * Values.tileHeight);
        this.setFitHeight(Values.tileHeight);
        this.setFitWidth(Values.tileWidth);
    }

    public void getBombImage(){
        Image image = new Image(getClass().getResourceAsStream("/res/assets/bomb.png"));
        this.setImage(image);
    }

    public void close(){
        closed = true;
    }

    public void open(){
        closed = false;
    }
}
