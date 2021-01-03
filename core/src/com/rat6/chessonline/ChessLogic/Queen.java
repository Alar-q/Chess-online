package com.rat6.chessonline.ChessLogic;

import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.Board;

import java.util.List;

public class Queen extends FigureLogic {
    @Override
    public boolean canMove(Board board, PieceEnum piece, Vector2 position, Vector2 to) {
        return false;
    }

    @Override
    public List<Vector2> getAvailableCells(Board board, PieceEnum piece, Vector2 position) {
        return null;
    }
}
