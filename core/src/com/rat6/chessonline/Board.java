package com.rat6.chessonline;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.rat6.chessonline.chessLogic.FigureAdapter;

public class Board {

    private Main game;
    public float boardSize, boardLeftY;

    private com.rat6.chessonline.chessLogic.Figure[][] board;

    private com.rat6.chessonline.chessLogic.Castling castling;
    private com.rat6.chessonline.chessLogic.PawnTransf pawnTransf;
    private com.rat6.chessonline.chessLogic.PawnInterception pawnInterception;

    private com.rat6.chessonline.chessLogic.Check checkW;
    private com.rat6.chessonline.chessLogic.Check checkB;

    private com.rat6.chessonline.chessLogic.History history;

    public com.rat6.chessonline.chessLogic.PieceEnum turn = com.rat6.chessonline.chessLogic.PieceEnum.white;

    public Board(Main game){
        this.game = game;

        boardSize = game.WORLD_WIDTH;
        boardLeftY = game.WORLD_HEIGHT/2-(boardSize/2);

        board = new com.rat6.chessonline.chessLogic.Figure[8][8];

        castling = new com.rat6.chessonline.chessLogic.Castling(this);
        pawnTransf = new com.rat6.chessonline.chessLogic.PawnTransf(game, this);
        pawnInterception = new com.rat6.chessonline.chessLogic.PawnInterception(this);

        history = new com.rat6.chessonline.chessLogic.History(this);

        clear();
    }



    public void present(){
        for(int y=0; y<8; y++) {//y-row, x-col
            for (int x = 0; x < 8; x++) {
                com.rat6.chessonline.chessLogic.Figure f = board[y][x];
                if(f.team == com.rat6.chessonline.chessLogic.PieceEnum.empty || !f.visible) continue;
                TextureRegion tr = game.assets.getCharactersTextureR(f.piece);
                drawCharacter(y, x, tr);
            }
        }


    }

    public void highlight_check(){
        checkW.present();
        checkB.present();
    }


    public void clear(){
        for(int y=0; y<8; y++) { //y-row, x-col
            for (int x = 0; x < 8; x++) {
                board[y][x] = createEmpty(y, x);
            }
        }
        //pawns
        for(int i=0; i<8; i++)
            board[1][i] = new com.rat6.chessonline.chessLogic.Pawn(this, com.rat6.chessonline.chessLogic.PieceEnum.white, new Vector2(i, 1));
        for(int i=0; i<8; i++)
            board[6][i] = new com.rat6.chessonline.chessLogic.Pawn(this, com.rat6.chessonline.chessLogic.PieceEnum.black, new Vector2(i, 6));

        board[0][0] = new com.rat6.chessonline.chessLogic.Rook(this, com.rat6.chessonline.chessLogic.PieceEnum.white, new Vector2(0, 0));
        board[0][1] = new com.rat6.chessonline.chessLogic.Knight(this, com.rat6.chessonline.chessLogic.PieceEnum.white, new Vector2(1, 0));
        board[0][2] = new com.rat6.chessonline.chessLogic.Bishop(this, com.rat6.chessonline.chessLogic.PieceEnum.white, new Vector2(2, 0));
        board[0][3] = new com.rat6.chessonline.chessLogic.Queen(this, com.rat6.chessonline.chessLogic.PieceEnum.white, new Vector2(3, 0));

        com.rat6.chessonline.chessLogic.King kingW = new com.rat6.chessonline.chessLogic.King(this, com.rat6.chessonline.chessLogic.PieceEnum.white, new Vector2(4, 0));
        board[0][4] = kingW;
        if(checkW==null)
            checkW = new com.rat6.chessonline.chessLogic.Check(this, kingW, game.assets.red);
        else
            checkW.setKing(kingW);

        board[0][5] = new com.rat6.chessonline.chessLogic.Bishop(this, com.rat6.chessonline.chessLogic.PieceEnum.white, new Vector2(5, 0));
        board[0][6] = new com.rat6.chessonline.chessLogic.Knight(this, com.rat6.chessonline.chessLogic.PieceEnum.white, new Vector2(6, 0));
        board[0][7] = new com.rat6.chessonline.chessLogic.Rook(this, com.rat6.chessonline.chessLogic.PieceEnum.white, new Vector2(7, 0));

        board[7][0] = new com.rat6.chessonline.chessLogic.Rook(this, com.rat6.chessonline.chessLogic.PieceEnum.black, new Vector2(0, 7));
        board[7][1] = new com.rat6.chessonline.chessLogic.Knight(this, com.rat6.chessonline.chessLogic.PieceEnum.black, new Vector2(1, 7));
        board[7][2] = new com.rat6.chessonline.chessLogic.Bishop(this, com.rat6.chessonline.chessLogic.PieceEnum.black, new Vector2(2, 7));
        board[7][3] = new com.rat6.chessonline.chessLogic.Queen(this, com.rat6.chessonline.chessLogic.PieceEnum.black, new Vector2(3, 7));

        com.rat6.chessonline.chessLogic.King kingB = new com.rat6.chessonline.chessLogic.King(this, com.rat6.chessonline.chessLogic.PieceEnum.black, new Vector2(4, 7));
        board[7][4] = kingB;
        if(checkB==null)
            checkB = new com.rat6.chessonline.chessLogic.Check(this, kingB, game.assets.red);
        else
            checkB.setKing(kingB);

        board[7][5] = new com.rat6.chessonline.chessLogic.Bishop(this, com.rat6.chessonline.chessLogic.PieceEnum.black, new Vector2(5, 7));
        board[7][6] = new com.rat6.chessonline.chessLogic.Knight(this, com.rat6.chessonline.chessLogic.PieceEnum.black, new Vector2(6, 7));
        board[7][7] = new com.rat6.chessonline.chessLogic.Rook(this, com.rat6.chessonline.chessLogic.PieceEnum.black, new Vector2(7, 7));
    }



    public void drawCharacter(int row, int col, TextureRegion tr){
        float chx = game.padding + (game.cellSize * col) + col; //+col - это промежутки
        float chy = boardLeftY + game.padding + (game.cellSize * row) + row;

        game.batcher.draw(tr, chx, chy, game.cellSize, game.cellSize);
    }
    public void drawCharacter(Vector2 pos, TextureRegion tr){
        drawCharacter((int)pos.y, (int)pos.x, tr);
    }


    //В этот метод поступаю только те ходы которые реально будут
    public void move(int row, int col, int rowTo, int colTo){

        history.move(row, col, rowTo, colTo);

        com.rat6.chessonline.chessLogic.Figure fFrom = get(row, col);
        com.rat6.chessonline.chessLogic.Figure fTo = get(rowTo, colTo);

        deleteCharacter(row, col, fTo); //удаляем фигуру, которая стояла на старой позиции
        set(rowTo, colTo, fFrom); //Поставили взятую рукой фигуру

        //Можно не вписывать где был, куда пошел, а юзать position, lastPosition фигуры

        if(pawnInterception.ifInterception_removeEnemyPawn(row, col, rowTo, colTo, fFrom)){ //Взятие на проходе
            //Удаляем пешку противника
        }
        else if(pawnInterception.fixPawn2Jump(row, col, rowTo, colTo, fFrom)){
            //Просто фиксирует был ли двойной прыжок пешки на первом ходе
            //Обязательно должен быть после первой проверки, потому что меняется значение был ли двойной прыжок до этого
        }
        else if(pawnTransf.fixPawn_Reached_The_End(rowTo, colTo, fFrom)){
            //Фиксируем если пешка дошла до конца. Если да, то мы рисуем фигуры на выбор и не разрешаем больше ничего делать
        }
        else if(castling.ifCastling_SwapRook(fFrom, fTo, row, col, rowTo, colTo)){
            //Переставляет ладью при рокировке
        }


        checkW.fixCheck();
        checkB.fixCheck();

        if(turn== com.rat6.chessonline.chessLogic.PieceEnum.white) turn = com.rat6.chessonline.chessLogic.PieceEnum.black;
        else turn = com.rat6.chessonline.chessLogic.PieceEnum.white;
    }
    public void move(Vector2 newPos, Vector2 toPos){
        move((int)newPos.y, (int)newPos.x, (int)toPos.y, (int)toPos.x);
    }


    public boolean isPosUnderAttack(int row, int col, com.rat6.chessonline.chessLogic.PieceEnum team){
        Vector2 pos = new Vector2(col, row);

        //Проходим по всем клеткам,
        for(int y=0; y<8; y++){
            for(int x=0; x<8; x++){
                // находим фигуры вражеской команды и проверяет бьют ли они клетку
                com.rat6.chessonline.chessLogic.Figure en = get(y, x);
                if(en.team!=team && en.canMove(pos))
                    return true;
            }
        }
        return false;
    }
    public boolean isPosUnderAttack(Vector2 pos, com.rat6.chessonline.chessLogic.PieceEnum team){
        return isPosUnderAttack((int)pos.y, (int)pos.x, team);
    }



    public com.rat6.chessonline.chessLogic.PieceEnum getChessPiece(int row, int col){
        if(iS_WITHIN_BOARD(row, col))
            return board[row][col].piece;
        return com.rat6.chessonline.chessLogic.PieceEnum.empty;
    }
    public com.rat6.chessonline.chessLogic.PieceEnum getChessPiece(Vector2 pos){
        return getChessPiece((int)pos.y, (int)pos.x);
    }



    public com.rat6.chessonline.chessLogic.Figure get(int row, int col){
        if(iS_WITHIN_BOARD(row, col))
            return board[row][col];
        return createEmpty(row, col);
    }
    public com.rat6.chessonline.chessLogic.Figure get(Vector2 to) {
        return get((int) to.y, (int) to.x);
    }



    public void set(int row, int col, com.rat6.chessonline.chessLogic.Figure f){
        f.setPosition(row, col);
        board[row][col] = f;
    }
    public void set(Vector2 pos, com.rat6.chessonline.chessLogic.Figure f) {
        set((int) pos.y, (int) pos.x, f);
    }
    public void set_unchanged(int row, int col, com.rat6.chessonline.chessLogic.Figure f){
        board[row][col] = f;
    }



    public com.rat6.chessonline.chessLogic.Figure createEmpty(int row, int col){
        return new FigureAdapter(this, com.rat6.chessonline.chessLogic.PieceEnum.empty, new Vector2(col, row));
    }



    public void deleteCharacter(int row, int col, com.rat6.chessonline.chessLogic.Figure to){
        if(iS_WITHIN_BOARD(row, col)) {
            if(to.piece== com.rat6.chessonline.chessLogic.PieceEnum.empty) //Можно убрать, разницы не будет. Сделан чтобы не создавать новый пустой объект
                set(row, col, to); //swap with empty
            else
                board[row][col] = createEmpty(row, col);
        }
    }
    public void deleteCharacter(int row, int col){
        if(iS_WITHIN_BOARD(row, col)) {
            board[row][col] = createEmpty(row, col);
        }
    }
    public void deleteCharacter(Vector2 v){
        deleteCharacter((int)v.y, (int)v.x);
    }


    public com.rat6.chessonline.chessLogic.Castling getCastling(){
        return castling;
    }

    public com.rat6.chessonline.chessLogic.PawnTransf getPawnTransformation(){
        return pawnTransf;
    }

    public com.rat6.chessonline.chessLogic.Check getCheck(com.rat6.chessonline.chessLogic.PieceEnum team){
        if(team == com.rat6.chessonline.chessLogic.PieceEnum.white)
            return checkW;
        else return checkB;
    }

    public com.rat6.chessonline.chessLogic.PawnInterception getPawnInterceptionLogic(){
        return pawnInterception;
    }

    public static boolean iS_WITHIN_BOARD(int row, int col){
        if(row>=0 && row<8 && col>=0 && col<8)
            return true;
        return false;
    }
    public static boolean iS_WITHIN_BOARD(Vector2 pos){
        return iS_WITHIN_BOARD((int)pos.y, (int)pos.x);
    }

}
