package com.rat6.chessonline.menuScreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.MainGameScreen;
import com.rat6.chessonline.framework.Font;
import com.rat6.chessonline.framework.OverlapTester;

/*
Можно играть вдвоем на одном устройстве и на двух разных
 */
public class TwoPlayersMenuScreen extends MenuScreen {

    private final int one_device = 0, online = 1;

    public TwoPlayersMenuScreen(Main game) {
        super(game);
    }

    @Override
    protected void initButtons(){
        buttons = new Rectangle[2];
    }

    @Override
    protected void initBNames(){
        bNames = new String[]{"one device", "online"};
    }

    @Override
    protected void update(){
        Input in = Gdx.input;
        if(in.justTouched()) {
            camera.unproject(touchPoint.set(in.getX(), in.getY(), 0));
            for(int i=0; i<buttons.length; i++) {
                Rectangle r = buttons[i];
                if (OverlapTester.pointInRectangle(r, touchPoint)) {
                    switch (i){
                        case online:
                            System.out.println("online");
                            game.setScreen(new ServerOrClientScreen(game));
                            break;
                        case one_device:
                            game.setScreen(new MainGameScreen(game));
                            break;

                    }
                }
            }
        }
    }
}
