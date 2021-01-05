package com.rat6.chessonline;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.ChessLogic.*;

public class Board {

    private Main game;
    public float boardSize, boardLeftY;

    private Figure[][] board;

    private CastlingLogic castlingLogic;
    private PawnTransfLogic pawnTransfLogic;
    private PawnInterceptionLogic pawnInterceptionLogic;

    private Check checkW;
    private Check checkB;

    public Board(Main game){
        this.game = game;

        boardSize = game.WORLD_WIDTH;
        boardLeftY = game.WORLD_HEIGHT/2-(boardSize/2);

        board = new Figure[8][8];

        castlingLogic = new CastlingLogic(this);
        pawnTransfLogic = new PawnTransfLogic(game, this);
        pawnInterceptionLogic = new PawnInterceptionLogic(this);

        clear();
    }



    public void present(){
        for(int y=0; y<8; y++) {//y-row, x-col
            for (int x = 0; x < 8; x++) {
                Figure f = board[y][x];
                if(f.team == PieceEnum.empty || !f.visible) continue;
                TextureRegion tr = game.assets.getCharactersTextureR(f.piece);
                drawCharacter(y, x, tr);
            }
        }

        if(pawnTransfLogic.isTransNow) {
            pawnTransfLogic.present();
        }
    }

    public void update(){
        checkW.update();
        checkB.update();
    }

    public void highlight(){
        checkW.present();
        checkB.present();
    }


    public void clear(){
        for(int y=2; y<=6; y++) { //y-row, x-col
            for (int x = 0; x < 8; x++) {
                board[y][x] = createEmpty(y, x);
            }
        }
        //pawns
        for(int i=0; i<8; i++)
            board[1][i] = new Pawn(this, PieceEnum.white, new Vector2(i, 1));
        for(int i=0; i<8; i++)
            board[6][i] = new Pawn(this, PieceEnum.black, new Vector2(i, 6));

        board[0][0] = new Rook(this, PieceEnum.white, new Vector2(0, 0));
        board[0][1] = new Knight(this, PieceEnum.white, new Vector2(1, 0));
        board[0][2] = new Bishop(this, PieceEnum.white, new Vector2(2, 0));
        board[0][3] = new Queen(this, PieceEnum.white, new Vector2(3, 0));
        board[0][4] = new King(this, PieceEnum.white, new Vector2(4, 0));
        checkW = new Check(this, board[0][3], game.assets.red);
        board[0][5] = new Bishop(this, PieceEnum.white, new Vector2(5, 0));
        board[0][6] = new Knight(this, PieceEnum.white, new Vector2(6, 0));
        board[0][7] = new Rook(this, PieceEnum.white, new Vector2(7, 0));

        board[7][0] = new Rook(this, PieceEnum.black, new Vector2(0, 7));
        board[7][1] = new Knight(this, PieceEnum.black, new Vector2(1, 7));
        board[7][2] = new Bishop(this, PieceEnum.black, new Vector2(2, 7));
        board[7][3] = new Queen(this, PieceEnum.black, new Vector2(3, 7));
        board[7][4] = new King(this, PieceEnum.black, new Vector2(4, 7));
        checkB = new Check(this, board[7][3], game.assets.red);
        board[7][5] = new Bishop(this, PieceEnum.black, new Vector2(5, 7));
        board[7][6] = new Knight(this, PieceEnum.black, new Vector2(6, 7));
        board[7][7] = new Rook(this, PieceEnum.black, new Vector2(7, 7));
    }



    public void drawCharacter(int row, int col, TextureRegion tr){
        float chx = game.padding + (game.cellSize * col) + col; //+col - это промежутки
        float chy = boardLeftY + game.padding + (game.cellSize * row) + row;

        game.batcher.draw(tr, chx, chy, game.cellSize, game.cellSize);
    }
    public void drawCharacter(Vector2 pos, TextureRegion tr){
        drawCharacter((int)pos.y, (int)pos.x, tr);
    }



    public void move(int row, int col, int rowTo, int colTo){
        Figure to = get(rowTo, colTo);
        Figure from = get(row, col);

        if(pawnInterceptionLogic.ifInterception_removeEnemyPawn(row, col, rowTo, colTo)){}
        else if(pawnInterceptionLogic.fixPawnJump(row, col, rowTo, colTo)){}
        else if(castlingLogic.ifCastling_SwapRook(from, row, col, rowTo, colTo)){}

        if(to.piece==PieceEnum.empty){ //Можно убрать, разницы не будет. Сделан чтобы не создавать новый пустой объект
            set(row, col, to); //swap with empty
        }else deleteCharacter(row, col); //take (съел)

        set(rowTo, colTo, from); //Поставили взятую рукой фигуру

        pawnTransfLogic.pawn_Reached_The_End(rowTo, colTo);

    }
    public void move(Vector2 newPos, Vector2 toPos){
        move((int)newPos.y, (int)newPos.x, (int)toPos.y, (int)toPos.x);
    }



    public PieceEnum getChessPiece(int row, int col){
        if(isWithinBoard(row, col))
            return board[row][col].piece;
        return PieceEnum.empty;
    }
    public PieceEnum getChessPiece(Vector2 pos){
        return getChessPiece((int)pos.y, (int)pos.x);
    }



    public Figure get(int row, int col){
        if(isWithinBoard(row, col))
            return board[row][col];
        return createEmpty(row, col);
    }
    public Figure get(Vector2 to) {
        return get((int) to.y, (int) to.x);
    }



    public void set(int row, int col, Figure f){
        f.setPosition(row, col);
        board[row][col] = f;
    }
    public void set(Vector2 pos, Figure f) {
        set((int) pos.y, (int) pos.x, f);
    }



    public Figure createEmpty(int row, int col){
        return new Figure(this, PieceEnum.empty, new Vector2(col, row)) {
            @Override
            public boolean canMove(Vector2 to) {
                return false;
            }
        };
    }



    public void deleteCharacter(int row, int col){
        if(isWithinBoard(row, col)) {
            board[row][col] = createEmpty(row, col);
        }
    }
    public void deleteCharacter(Vector2 v){
        deleteCharacter((int)v.y, (int)v.x);
    }

    public CastlingLogic getCastling(){
        return castlingLogic;
    }
    public PawnTransfLogic getPawnTransformation(){
        return pawnTransfLogic;
    }
    public Check getCheck(PieceEnum team){
        if(team == PieceEnum.white)
            return checkW;
        else return checkB;
    }
    public PawnInterceptionLogic getPawnInterceptionLogic(){
        return pawnInterceptionLogic;
    }

    public static boolean isWithinBoard(int row, int col){
        if(row>=0 && row<8 && col>=0 && col<8)
            return true;
        return false;
    }
    public static boolean isWithinBoard(Vector2 pos){ 
        return isWithinBoard((int)pos.y, (int)pos.x); 
    }

}
