package com.rat6.chessonline.menuScreens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.MainGameScreen;
import com.rat6.chessonline.framework.Font;
import com.rat6.chessonline.framework.OverlapTester;

public class MenuScreen extends ScreenAdapter {

    protected final int MENU_WIDTH = 10, MENU_HEIGHT = 15;

    protected Main game;
    protected OrthographicCamera camera;
    protected Vector3 touchPoint;

    protected Rectangle[] buttons;
    protected String[] bNames;
    protected float width = 8, height = 1.5f, distance = 0.25f, startHeight;
    protected float glyphWidth = 0.6f, glyphHeight = 0.6f;

    protected Font font;

    private final int one_player=4, two_player=3, settings=2, help=1, exit=0;


    public MenuScreen(Main game){
        this.game = game;

        camera = new OrthographicCamera(MENU_WIDTH, MENU_HEIGHT);
        camera.position.set(MENU_WIDTH /2, MENU_HEIGHT /2, 0);

        touchPoint = new Vector3();

        initButtons();
        startHeight = MENU_HEIGHT/2 - (((height+distance)*buttons.length) / 2);
        for(int i=0; i<buttons.length; i++) {
            buttons[i] = new Rectangle((MENU_WIDTH - width) / 2, startHeight + (height * i) + (distance * i), width, height);
        }
        initBNames();

        font = game.assets.font;
    }


    protected void initButtons(){
        buttons = new Rectangle[5];
    }

    protected void initBNames(){
        bNames = new String[]{"exit", "help", "settings", "two player", "one player"};
    }



    @Override
    public void render(float deltaTime){
        update();
        present();
    }



    protected void update(){
        Input in = Gdx.input;
        if(in.justTouched()) {
            camera.unproject(touchPoint.set(in.getX(), in.getY(), 0));
            for(int i=0; i<buttons.length; i++) {
                Rectangle r = buttons[i];
                if (OverlapTester.pointInRectangle(r, touchPoint)) {
                    switch (i){
                        case one_player:
                            game.setScreen(new MainGameScreen(game));
                            break;
                        case two_player:
                            game.setScreen(new TwoPlayersMenuScreen(game));
                            break;
                        case settings:
                            game.setScreen(new MenuScreen(game));
                            break;
                        case help:
                            game.setScreen(new MenuScreen(game));
                            break;
                        case exit:
                            game.setScreen(new MenuScreen(game));
                            break;
                    }
                }
            }
        }
    }



    protected void present(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batcher.setProjectionMatrix(camera.combined);

        game.batcher.enableBlending();
        game.batcher.begin();

        for(int i=0; i<buttons.length; i++){
            Rectangle r = buttons[i];
            game.batcher.draw(game.assets.buttonUnTouch, r.x, r.y, r.width, r.height);
            font.drawText(game.batcher, bNames[i], r, glyphWidth, glyphHeight);
        }

        game.batcher.end();
    }

}
