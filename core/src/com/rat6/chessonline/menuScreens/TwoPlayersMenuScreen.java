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
public class TwoPlayersMenuScreen extends ScreenAdapter {

    private final static int WIDTH = 10, HEIGHT = 15;

    private Main game;
    private OrthographicCamera camera;
    private Vector3 touchPoint;

    private final int one_player=4, two_player=3, settings=2, help=1, exit=0;
    private Rectangle[] buttons;
    private String[] bNames;
    private final float width = 8, height = 1.5f, distance = 0.25f, startHeight = 2.5f;
    private final float glyphWidth = 0.6f, glyphHeight = 0.6f;


    private Font font;

    public TwoPlayersMenuScreen(Main game){
        this.game = game;

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH/2, HEIGHT/2, 0);

        touchPoint = new Vector3();

        buttons = new Rectangle[2];
        for(int i=0; i<buttons.length; i++) {
            buttons[i] = new Rectangle((WIDTH - width) / 2, startHeight + (height * i) + (distance * i), width, height);
        }

        bNames = new String[]{"exit", "help", "settings", "two player", "one player"};

        font = game.assets.font;
    }

    @Override
    public void render(float deltaTime){
        update();
        present();
    }

    private void update(){
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



    private void present(){
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
            font.drawText(game.batcher, bNames[i], r.x+(r.width/2)-((bNames[i].length()*glyphWidth)/2), r.y+r.height/2-glyphHeight/2, glyphWidth, glyphHeight);
        }

        game.batcher.end();
    }
}
