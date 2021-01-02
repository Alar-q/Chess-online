package com.rat6.chessonline;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public Texture atlas;
    public TextureRegion board;
    public TextureRegion kingB;
    public TextureRegion kingW;
    public TextureRegion queenB;
    public TextureRegion queenW;
    public TextureRegion rookB;
    public TextureRegion rookW;
    public TextureRegion bishopB;
    public TextureRegion bishopW;
    public TextureRegion knightB;
    public TextureRegion knightW;
    public TextureRegion pawnB;
    public TextureRegion pawnW;


    public Texture loadTexture(String fileName) {
        return new Texture(Gdx.files.internal(fileName));
    }

    public Assets() {
        atlas = loadTexture("atlas.png");
        board = new TextureRegion(atlas, 0, 0, 451, 451);

        bishopB = new TextureRegion(atlas, 512, 64, 64, 64);
        bishopW = new TextureRegion(atlas, 576, 64, 64, 64);

        kingB = new TextureRegion(atlas, 640, 64, 64, 64);
        kingW = new TextureRegion(atlas, 704, 64, 64, 64);

        knightB = new TextureRegion(atlas, 768, 64, 64, 64);
        knightW = new TextureRegion(atlas, 832, 64, 64, 64);

        queenB = new TextureRegion(atlas, 512, 128, 64, 64);
        queenW = new TextureRegion(atlas, 576, 128, 64, 64);

        rookB = new TextureRegion(atlas, 640, 128, 64, 64);
        rookW = new TextureRegion(atlas, 704, 128, 64, 64);

        pawnB = new TextureRegion(atlas, 768, 128, 64, 64);
        pawnW = new TextureRegion(atlas, 832, 128, 64, 64);

    }

    public void dispose(){
        atlas.dispose();
    }
}
