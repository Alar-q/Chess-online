package com.rat6.chessonline.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.rat6.chessonline.Assets;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.framework.Font;
import com.rat6.chessonline.framework.OverlapTester;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {
    private Main game;
    private OrthographicCamera camera;
    private Vector3 touchPoint;

    private float w_width, w_height;

    private Rectangle[] keys;
    private String[] kNames;

    private final int rows = 4, cols = 3;

    //15-макс число символов в ipv4. 15*0.6=9, 9-длина линии
    private final float glyphWidth = 0.6f, glyphHeight = 0.6f;
    private float indents = 0.5f, keysTop;
    public int dot = 0, del = 2, connect = rows*cols;

    private String entered_text;

    public Keyboard(Main game, OrthographicCamera camera){
        this.game = game;
        this.camera = camera;
        touchPoint = new Vector3();

        w_width = camera.viewportWidth;
        w_height = camera.viewportHeight;

        initRects(w_width, w_height);
        keysTop = keys[0].height * rows + keys[connect].height + indents*2;

        initkNames();

        entered_text = "";
    }

    private void initRects(float w_width, float w_height){
        keys = new Rectangle[(cols * rows) + 1];

        float b_width = w_width/cols, b_height = (w_height/2)/rows;
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                float x = c * b_width, y = r * b_height;
                keys[r*cols+c] = new Rectangle(x, y, b_width, b_height);
            }
        }

        float y = keys[0].height * rows + indents;
        keys[connect]  = new Rectangle(0, y, w_width, b_height);
    }

    private void initkNames(){
        kNames = new String[(rows*cols) + 1];
        for(int i=0, k=0; i<kNames.length; i++){
            if(i==dot){
                kNames[i] = ".";
            }
            else if(i==del){
                kNames[i] = "del";
            }
            else if(i==connect){
                kNames[connect] = "connect";
            }
            else {
                kNames[i] = String.valueOf(k);
                k++;
            }
        }
    }

    public int lastPressedKey;

    public boolean update(){
        Input in = Gdx.input;
        if(in.justTouched()) {
            camera.unproject(touchPoint.set(in.getX(), in.getY(), 0));

            for (int i = 0; i < keys.length; i++) {

                Rectangle r = keys[i];

                if (OverlapTester.pointInRectangle(r, touchPoint)) {

                    lastPressedKey = i;

                    if(i==del)
                        delLastChar();
                    else if(i==connect){
                        System.out.println("Connect");
                    }
                    else if(entered_text.length()<15)
                        entered_text += kNames[i];

                    return true;
                }
            }

        }
        return false;
    }


    public void present(){
        //Рисуем клавиатуру
        for(int i=0; i<keys.length; i++){
            Rectangle r = keys[i];
            game.batcher.draw(game.assets.buttonUnTouch, r.x, r.y, r.width, r.height);
            game.assets.font.drawText(game.batcher, kNames[i], r, glyphWidth, glyphHeight);
        }
        //Рисуем ipv4
        game.assets.font.drawText(game.batcher, entered_text, indents, keysTop, glyphWidth, glyphHeight);
    }


    public void drawLine(){
        drawLine(indents, keysTop,w_width-indents, keysTop);
    }

    private static ShapeRenderer debugRenderer = new ShapeRenderer();
    private void drawLine(float x0, float y0, float x1, float y1) {
        Gdx.gl.glLineWidth(1);
        debugRenderer.setProjectionMatrix(camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.BLACK);
        debugRenderer.line(x0, y0, x1, y1);
        debugRenderer.end();
    }

    private void delLastChar(){
        int to = Math.max(entered_text.length() - 1, 0);
        entered_text = entered_text.substring(0, to);
    }

    public String getEnteredText(){
        return entered_text;
    }
}
