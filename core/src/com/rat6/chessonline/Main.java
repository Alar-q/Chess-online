package com.rat6.chessonline;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rat6.chessonline.ChessLogic.Logic;

public class Main extends Game {
	public float WORLD_WIDTH = 451, WORLD_HEIGHT;
	public final float padding = 14f, cellSize = 52f;
	public SpriteBatch batcher;
	public Assets assets;

	@Override
	public void create () {
		batcher = new SpriteBatch();
		assets = new Assets();
		setHeight();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batcher.dispose();
		assets.dispose();
	}

	public void setHeight(){
		WORLD_HEIGHT = 902;
	}
}
