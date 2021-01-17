package com.rat6.chessonline.enternet.simple_client;

import com.rat6.chessonline.Main;
import com.rat6.chessonline.MainGameScreen;

public class ClientGameScreen extends MainGameScreen {
    private SimpleClient client;
    public ClientGameScreen(Main game, SimpleClient client) {
        super(game);
        this.client = client;
    }
}
