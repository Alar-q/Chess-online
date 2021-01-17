package com.rat6.chessonline.menuScreens;

import com.badlogic.gdx.ScreenAdapter;
import com.rat6.chessonline.Main;

/**
 * Нужно запустить сервер и вывести на экран IP
 */

public class ServerStartScreen extends ScreenAdapter {
    Main game;
    public ServerStartScreen(Main game){
        this.game = game;

    }

    @Override
    public void render(float deltaTime){
        update();
        present();
    }

    private void update(){

    }
    private void present(){

    }
}
