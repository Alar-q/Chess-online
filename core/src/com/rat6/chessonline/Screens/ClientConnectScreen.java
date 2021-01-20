package com.rat6.chessonline.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.OnlineGameScreen;
import com.rat6.chessonline.chessLogic.PieceEnum;
import com.rat6.chessonline.enternet.simple_client.SimpleClient;
import com.rat6.chessonline.utils.Keyboard;
import com.rat6.chessonline.utils.StandardButton;


/**
 * Нужно реализовать легкий ввод с клавиатуры IP адреса
 * и подключение к серверу
 */

public class ClientConnectScreen extends ScreenAdapter {

    private Main game;
    private final float MENU_WIDTH = 10, MENU_HEIGHT = 15;
    private OrthographicCamera camera;
    private Vector3 touchPoint;

    private Keyboard keyboard;

    private SimpleClient client;

    private StandardButton backButton;

    private PieceEnum team = PieceEnum.white;

    public ClientConnectScreen(Main game){
        this.game = game;
        camera = new OrthographicCamera(MENU_WIDTH, MENU_HEIGHT);
        camera.position.set(MENU_WIDTH / 2f, MENU_HEIGHT / 2f, 0);
        touchPoint = new Vector3();

        client = new SimpleClient();

        keyboard = new Keyboard(game, camera);

        backButton = new StandardButton(game, camera, game.assets.buttonUnTouch);
    }

    @Override
    public void render(float deltaTime){
        update();
        present();
    }


    private boolean connected = false;

    private void update(){
        Input in = Gdx.input;
        if(in.justTouched()){
            camera.unproject(touchPoint.set(in.getX(), in.getY(), 0));
            if(backButton.touched(touchPoint)){
                client.release();
                game.setScreen(new ServerOrClientScreen(game));
            }
        }
        if(!connected && keyboard.update()){
            System.out.println(keyboard.getEnteredText());
            if(keyboard.lastPressedKey == keyboard.connect){
                connect();
            }
        }
        if(connected && client.isConnected){
            game.setScreen(new OnlineGameScreen(game, client, team));
        }else{
            connected = false;
        }
    }

    private void connect(){
        String ip = keyboard.getEnteredText();
        if(isIPV4(ip)) { // Нужно проверить является ли введенный текст IP адрессом
            //client = new SimpleClient(keyboard.getEnteredText(), 1);
            System.out.println("client trying to connect");
            connected = client.connect(ip);
        }else{
            connected = false;
        }
    }

    private boolean isIPV4(String str){
        String[] s = str.split("\\.");
        if(s.length==4){
            return true;
        }
        return false;
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

        keyboard.present();

        game.batcher.end();

        keyboard.drawLine(); //Нужно заменить на просто растягивание черного пикселя!!!!
    }

    @Override
    public void dispose(){
        client.release();
    }
}
