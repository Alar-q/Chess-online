package com.rat6.chessonline;

import com.badlogic.gdx.Input;
import com.rat6.chessonline.Main;
import com.rat6.chessonline.MainGameScreen;
import com.rat6.chessonline.chessLogic.PieceEnum;
import com.rat6.chessonline.enternet.ConnectIO;

public class OnlineGameScreen extends MainGameScreen {

    private ConnectIO connectIO;
    private PieceEnum team;
    private Gamepad gamepad;

    public OnlineGameScreen(Main game, ConnectIO connectIO, PieceEnum team) {
        super(game);
        this.connectIO = connectIO;
        this.team = team;
        gamepad = getGamepad(team);
    }

    @Override
    public void updateGamepad(Input in){
        if(board.turn==team)
            gamepad.update(in);
    }

    @Override
    public void drawGamepad(){
        gamepad.present(); //one captured figure under finger
    }

    @Override
    public void drawGamepad_Highlight(){
        gamepad.highlight_available();
    }

    @Override
    public void dispose(){
        connectIO.release();
    }
}
