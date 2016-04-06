package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.FlappyBird;

/**
 * Created by jnd0 on 31/10/2015.
 */
public class MenuState extends State {

    private Texture background;
    private Texture playBtn;
    private TextButton makePhoto;
    TextButton.TextButtonStyle textButtonStyle;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyBird.WIDTH / 2, FlappyBird.HEIGTH / 2);

        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        //textButtonStyle = new TextButton.TextButtonStyle();
       // makePhoto = new TextButton("photo", textButtonStyle);


    }

    @Override
    public void handleInput() {

        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);

        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playBtn,cam.position.x - playBtn.getWidth() /2, cam.position.y);
       // sb.draw(makePhoto );
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
       // System.out.println("menu state dispose");

    }
}
