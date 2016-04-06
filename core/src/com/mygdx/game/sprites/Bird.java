package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.FlappyBird;
import com.mygdx.game.states.PlayState;

import org.omg.PortableServer.POA;

import java.util.Random;

import javax.xml.soap.Text;

/**
 * Created by jnd0 on 31/10/2015.
 */
public class Bird {

    private static final int GRAVITY = -10;
    private static final int MOVEMENT = 100;

    private Vector3 position;
    private Vector3 velocity;

    private Rectangle bounds;


    private Texture bird;
    private Animation birdAnimation;
    private Texture texture;
    private Sound flap;
    private Sound stube;


    public Bird(int x, int y) {
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        bird = new Texture("goku.gif");


        //bird animation
//         //texture = new Texture("birdAnimation.png");
//      birdAnimation = new Animation(new TextureRegion(bird),0,0.5f);
//
       bounds = new Rectangle(x,y,bird.getWidth(),bird.getHeight());

        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float dt){

        //birdAnimation.update(dt);

        if(position.y>0){
            velocity.add(0,GRAVITY,0);

        }

        velocity.add(0,GRAVITY,0);
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);

        if(position.y < 0){
            position.y=0;
        }

        velocity.scl(1/dt);

        bounds.setPosition(position.x, position.y);

    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Texture getBird() {
        return bird;
    }

    public void setBird(Texture bird) {
        this.bird = bird;
    }

    public void jump(){
        velocity.y = 250;
        flap.play(0.4f);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void generateRndSound(){

        Random rand = new Random();
        int r = rand.nextInt(( 4 - 1 )+1) + 1;

        stube = Gdx.audio.newSound(Gdx.files.internal("GOKU_"+r+".wav"));

        stube.play(0.4f);

    }

    public void sucesfullTube(){
        generateRndSound();
        stube.play();

    }



    public void dispose() {

        bird.dispose();
        flap.dispose();
    }
}
