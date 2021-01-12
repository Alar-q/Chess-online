package com.rat6.chessonline.chessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

public class FigureAdapter extends com.rat6.chessonline.chessLogic.Figure {
    public FigureAdapter(Board board, PieceEnum team, Vector2 position) {
        super(board, team, position);
    }

    @Override
    public Figure clone() {
        return new FigureAdapter(board, team, position);
    }

    @Override
    public boolean canMove(Vector2 to) {
        return false;
    }
}
