package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;

public class FlappyBird extends ApplicationAdapter {

	public static final int WIDTH = 480;
	public static final int HEIGTH = 800;

	public static final String TITLE = "Joc whatxy";

	private GameStateManager gsm;
	private Music music;
	//private SpriteBatch batch;

	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		//img = new Texture("badlogic.jpg");
		//Musica joc
	/*	music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();*/

		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		//music.dispose();
	}
}
