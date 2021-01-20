package com.rat6.chessonline.utils;

import com.badlogic.gdx.math.Rectangle;
import com.rat6.chessonline.Main;

public class StandardBackButton {
    private Main game;
    private float WIDTH, HEIGHT;
    private Rectangle backBRect;
    public StandardBackButton(Main game, float WIDTH, float HEIGHT){
        this.game = game;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        float wh = WIDTH / 5f;
        backBRect = new Rectangle(0, HEIGHT-wh, wh, wh);
    }
    public void draw(){
        game.batcher.draw(game.assets.blue, backBRect.x, backBRect.y, backBRect.width, backBRect.height);
    }
    public Rectangle bounds(){
        return backBRect;
    }
}
