package com.rat6.chessonline.enternet;

import com.rat6.chessonline.Main;
import com.rat6.chessonline.MainGameScreen;
import com.rat6.chessonline.enternet.ConnectIO;

public class OnlineGameScreen extends MainGameScreen {
    private ConnectIO connectIO;
    public OnlineGameScreen(Main game, ConnectIO connectIO) {
        super(game);
        this.connectIO = connectIO;
    }
}
