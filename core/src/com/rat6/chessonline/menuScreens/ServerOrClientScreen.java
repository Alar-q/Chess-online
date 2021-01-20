package com.rat6.chessonline.menuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.enternet.simple_client.ClientConnectScreen;
import com.rat6.chessonline.enternet.simple_server.ServerStartScreen;
import com.rat6.chessonline.framework.OverlapTester;


public class ServerOrClientScreen extends MenuScreen {

    private final int client = 0, server = 1;

    public ServerOrClientScreen(Main game) {
        super(game);
    }

    @Override
    protected void initButtons(){
        buttons = new Rectangle[2];
    }

    @Override
    protected void initBNames(){
        bNames = new String[]{"client", "server"};
    }

    @Override
    protected void update(){
        Input in = Gdx.input;
        if(in.justTouched()) {
            camera.unproject(touchPoint.set(in.getX(), in.getY(), 0));
            for (int i = 0; i < buttons.length; i++) {
                Rectangle r = buttons[i];
                if (OverlapTester.pointInRectangle(r, touchPoint)) {
                    switch (i) {
                        case server:
                            System.out.println("Server");
                            game.setScreen(new ServerStartScreen(game));
                            break;
                        case client:
                            game.setScreen(new ClientConnectScreen(game));
                            break;

                    }
                }
            }
        }
    }
}