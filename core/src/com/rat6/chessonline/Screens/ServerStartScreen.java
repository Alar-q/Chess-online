package com.rat6.chessonline.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.OnlineGameScreen;
import com.rat6.chessonline.enternet.simple_server.SimpleServer;
import com.rat6.chessonline.utils.StandardButton;

/**
 * Нужно запустить сервер и вывести на экран IP
 */

public class ServerStartScreen extends ScreenAdapter {

    private Main game;
    private final float MENU_WIDTH = 10, MENU_HEIGHT = 15;
    private OrthographicCamera camera;
    private Vector3 touchPoint;

    private final float glyphWidth = 0.6f, glyphHeight = 0.6f;

    private SimpleServer server;

    private Rectangle windowRec = new Rectangle(0, 0, MENU_WIDTH, MENU_HEIGHT);
    private StandardButton backButton;


    public ServerStartScreen(Main game){
        this.game = game;
        camera = new OrthographicCamera(MENU_WIDTH, MENU_HEIGHT);
        camera.position.set(MENU_WIDTH / 2f, MENU_HEIGHT / 2f, 0);
        touchPoint = new Vector3();

        server = new SimpleServer();

        backButton = new StandardButton(game, camera, game.assets.buttonUnTouch);
    }

    @Override
    public void render(float deltaTime){
        update();
        present();
    }

    private void update() {
        Input in = Gdx.input;
        if(in.justTouched()){
            camera.unproject(touchPoint.set(in.getX(), in.getY(), 0));
            if(backButton.touched(touchPoint)){
                server.release();
                game.setScreen(new ServerOrClientScreen(game));
            }
        }
        if(server.isConnected()){
            game.setScreen(new OnlineGameScreen(game, server));
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

        backButton.draw(game.assets.next_icon);

        game.font.drawText(server.getIp(), windowRec, glyphWidth, glyphHeight);
        game.font.drawText("Waiting for the client...", windowRec, 0, -glyphHeight-0.2f, glyphWidth-0.2f, glyphHeight-0.2f);

        game.batcher.end();

    }

    @Override
    public void dispose(){
        if(!server.isConnected())
            server.release();
    }
}
