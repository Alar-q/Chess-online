package com.rat6.chessonline;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rat6.chessonline.framework.Font;
import com.rat6.chessonline.Screens.MenuScreen;

public class Main extends Game {
	public float WORLD_WIDTH = 451, WORLD_HEIGHT;
	public final float padding = 14f, cellSize = 52f;
	public SpriteBatch batcher;
	public Font font;

	public Assets assets;

	@Override
	public void create () {
		batcher = new SpriteBatch();
		assets = new Assets();
		font = new Font(batcher, assets.atlas, 640, 192, 16, 16, 20);
		setHeight();
		setScreen(new MenuScreen(this));//MainGameScreen(this));
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
