package com.rat6.chessonline.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.framework.OverlapTester;

public class StandardButton {
    private Main game;
    private OrthographicCamera camera;
    private float WIDTH, HEIGHT;
    private Rectangle boundRect;

    private TextureRegion textureRegion;

    public StandardButton(Main game, OrthographicCamera camera, TextureRegion textureRegion){
        loadMain(game, camera, textureRegion);
        float wh = WIDTH / 5f;
        boundRect = new Rectangle(0, HEIGHT-wh, wh, wh);
    }
    public StandardButton(Main game, OrthographicCamera camera, TextureRegion textureRegion, float x, float y, float width, float height){
        loadMain(game, camera, textureRegion);
        boundRect = new Rectangle(x, y, width, height);
    }

    public void loadMain(Main game, OrthographicCamera camera, TextureRegion textureRegion) {
        this.game = game;
        this.camera = camera;
        this.textureRegion = textureRegion;
        WIDTH = camera.viewportWidth;
        HEIGHT = camera.viewportHeight;
    }


    public void draw(){
        game.batcher.draw(textureRegion, boundRect.x, boundRect.y, boundRect.width, boundRect.height);
    }
    public void draw(TextureRegion iconOver){
        game.batcher.draw(textureRegion, boundRect.x, boundRect.y, boundRect.width, boundRect.height);
        game.batcher.draw(iconOver, boundRect.x, boundRect.y, boundRect.width, boundRect.height);
    }

    public Rectangle bounds(){
        return boundRect;
    }
    public boolean touched(Vector3 touchPoint){
        if(OverlapTester.pointInRectangle(boundRect, touchPoint)){
            return true;
        }
        return false;
    }
}
