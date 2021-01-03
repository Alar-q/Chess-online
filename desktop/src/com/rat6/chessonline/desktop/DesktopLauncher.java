package com.rat6.chessonline.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.rat6.chessonline.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowSizeLimits(720, 720, 720, 720);
		new Lwjgl3Application(new Main(){
			@Override
			public void setHeight(){
				WORLD_WIDTH = 451;
				WORLD_HEIGHT = 550;
			}
		}, config);
	}
}
