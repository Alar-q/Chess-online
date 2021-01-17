package com.rat6.chessonline.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.rat6.chessonline.Assets;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.framework.Font;

public class Keyboard {
    private Main game;

    private Rectangle[] keys;
    private String[] kNames;

    private final int rows = 4, cols = 3;

    private final float glyphWidth = 0.5f, glyphHeight = 0.5f;

    public Keyboard(Main game, float w_width, float w_height){
        this.game = game;

        initRects(w_width, w_height);
        initkNames();
    }

    private void initRects(float w_width, float w_height){
        keys = new Rectangle[cols * rows];

        float b_width = w_width/cols, b_height = (w_height/2)/rows;
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                float x = c * b_width, y = r * b_height;
                keys[r*cols+c] = new Rectangle(x, y, b_width, b_height);
            }
        }
    }

    private void initkNames(){
        kNames = new String[rows*cols];
        for(int i=0, k=0; i<kNames.length; i++){
            if(i==0){
                kNames[i] = ".";
            }
            else if(i==2){
                kNames[i] = "del";
            }
            else {
                kNames[i] = String.valueOf(k);
                k++;
            }
        }
    }

    public void update(Input input){
        //if(input.is){}
    }

    public void present(){
        for(int i=0; i<keys.length; i++){
            Rectangle r = keys[i];
            game.batcher.draw(game.assets.buttonUnTouch, r.x, r.y, r.width, r.height);
            game.assets.font.drawText(game.batcher, kNames[i], r, glyphWidth, glyphHeight);
        }
    }

}
