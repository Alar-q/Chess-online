package com.rat6.chessonline.menuScreens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame implements ApplicationListener {

    //Создаём SpriteBatch для отрисовки текстур и прочего на экране
    private SpriteBatch batch;

    //Создаём BitmapFont для создания текста
    private BitmapFont FontRed1;
    private BitmapFont FontBlue2;
    private BitmapFont FontMaroon3;
    private BitmapFont FontMaroonMulti;

    @Override
    public void create() {
        batch = new SpriteBatch();

        FontRed1 = new BitmapFont();
        FontRed1.setColor(Color.RED); //Красный
        //FontRed1.sc

        FontBlue2 = new BitmapFont();
        FontBlue2.setColor(Color.BLUE); //Голубой
        //FontBlue2.setScale(2);

        FontMaroon3 = new BitmapFont();
        FontMaroon3.setColor(Color.MAROON); // Коричневый
       // FontMaroon3.setScale(3);

        FontMaroonMulti = new BitmapFont();
        FontMaroonMulti.setColor(Color.MAROON); // Коричневый
       // FontMaroonMulti.setScale(7,5);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        FontRed1.draw(batch, "Hello World !", 10, 20);
        FontBlue2.draw(batch, "Hello World !", 10, 60);
        FontMaroon3.draw(batch, "Hello World !", 10, 110);
        FontMaroonMulti.draw(batch, "Hello World !", 10, 200);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
