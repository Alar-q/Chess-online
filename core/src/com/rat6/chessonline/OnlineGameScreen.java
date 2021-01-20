package com.rat6.chessonline;

import com.badlogic.gdx.Input;
import com.rat6.chessonline.chessLogic.PieceEnum;
import com.rat6.chessonline.enternet.InetIO;

public class OnlineGameScreen extends MainGameScreen {

    private InetIO inetIO;
    private PieceEnum team;
    private Gamepad gamepad;

    public OnlineGameScreen(Main game, InetIO inetIO, PieceEnum team) {
        super(game);
        this.inetIO = inetIO;
        this.team = team;
        gamepad = getGamepad(team);
    }

    @Override
    public void updateGamepad(Input in){
        if(board.turn==team)
            gamepad.update(in);
        else{
            /*
            * Отправляю последнюю строку хистори и ожидаю ответа, если не получил ответ, то отправляю снова
            * (Можно добавить, что если не получил ответ, например, 100 раз, то отключать игру)
            * */
        }
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
        inetIO.release();
    }
}
