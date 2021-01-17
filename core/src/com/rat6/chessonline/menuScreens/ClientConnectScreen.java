package com.rat6.chessonline.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.enternet.simple_client.ClientGameScreen;
import com.rat6.chessonline.enternet.simple_client.SimpleClient;
import com.rat6.chessonline.utils.Keyboard;


/**
 * Нужно реализовать легкий ввод с клавиатуры IP адреса
 * и подключение к серверу
 */

public class ClientConnectScreen extends ScreenAdapter {

    private Main game;
    private final float MENU_WIDTH = 10, MENU_HEIGHT = 15;
    private OrthographicCamera camera;

    private Keyboard keyboard;

    private SimpleClient client;

    public ClientConnectScreen(Main game){
        this.game = game;
        camera = new OrthographicCamera(MENU_WIDTH, MENU_HEIGHT);
        camera.position.set(MENU_WIDTH / 2f, MENU_HEIGHT / 2f, 0);

        keyboard = new Keyboard(game, camera);
    }

    @Override
    public void render(float deltaTime){
        update();
        present();
    }


    private boolean connected = false;

    private void update(){
        if(!connected && keyboard.update()){
            System.out.println(keyboard.getEnteredText());
            if(keyboard.lastPressedKey == keyboard.connect){
                connected = true;
                connect();
            }
        }
        if(client.isConnected()){
            game.setScreen(new ClientGameScreen(game, client));
        }else{
            connected = false;
        }
    }

    private void connect(){
        String ip = keyboard.getEnteredText();
        if(isIPV4(ip)) { // Нужно проверить является ли введенный текст IP адрессом
            //client = new SimpleClient(keyboard.getEnteredText(), 1);
            System.out.println("client trying to connect");
            client = new SimpleClient(ip);
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

        keyboard.present();

        //game.font.drawText();

        game.batcher.end();

        keyboard.drawLine(); //Нужно заменить на просто растягивание черного пикселя
    }

    @Override
    public void pause(){
        client.release();
    }
    @Override
    public void dispose(){
        client.release();
    }
}
