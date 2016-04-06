package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FlappyBird;
import com.mygdx.game.Tools.GifDecoderOptimized;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;


/**
 * Created by jnd0 on 31/10/2015.
 */
public class PlayState extends State  {

    private static final int TUBE_SPACIONG = 125;
    private static final int TUBE_COUNT = 4;
    public static final int GROUND_Y_OFSET = -50 ;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private SpriteBatch counter;
    private Vector2 groundPosition1, groundPosition2;
    private Animation anim;
    public static int score = 0;
    public BitmapFont font, shadow;



    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        //50 300
        bird = new Bird(50,300);

        FileHandle from = Gdx.files.external("goku.gif");

        //Animate the bird beta not implemented
        /*byte[] data = from.readBytes();
        Animation anim = GifDecoderOptimized.loadGIFAnimation(Animation.PlayMode.LOOP, data);*/

        cam.setToOrtho(false, FlappyBird.WIDTH/2, FlappyBird.HEIGTH/2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        counter = new SpriteBatch();


        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.25f,.25f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(.25f,.25f);


        groundPosition1 = new Vector2(cam.position.x  - cam.viewportWidth /2,GROUND_Y_OFSET);
        groundPosition2 = new Vector2((cam.position.x  - cam.viewportWidth /2)+ground.getWidth(),GROUND_Y_OFSET);


        tubes = new Array<Tube>();

        for(int i =1;i<=TUBE_COUNT;i++){
            tubes.add(new Tube(i*(TUBE_SPACIONG + Tube.TUBE_WIDTH)));
        }


    }

    @Override
    protected void handleInput() {

        if(Gdx.input.justTouched()){
            bird.jump();
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for (int i =0; i<tubes.size;i++){
            Tube tube = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth/2)>tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                bird.generateRndSound();
                System.out.printf("I entered");
                score++;
                tube.rePosition(tube.getPosTopTube().x + (Tube.TUBE_WIDTH + TUBE_SPACIONG)*TUBE_COUNT);


            }

            if(tube.collides(bird.getBounds())){
                gsm.set(new MenuState(gsm));
            }
        }


        /**
         * increase the score
         */


        if ( (cam.position.x == bird.getPosition().x) || (cam.position.y == bird.getPosition().y) ){

            System.out.printf("I entered");
            score++;

        }

        /**
         * kill bird when touches the ground
         */
        if(bird.getPosition().y <=ground.getHeight() + GROUND_Y_OFSET){
            gsm.set(new MenuState(gsm));
        }

        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        //sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);




        for(Tube tube : tubes){
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }


    /*    g.setFont(new Font("comicsans", Font.BOLD, 40));
        g.drawString("" + score, WIDTH / 2 - 20, 700);
        g.drawString(deathMessage, 200, 200);		*/		//paints "" if the player has not just died, paints "you died, try again" if the user just died


        sb.draw(ground, groundPosition1.x, groundPosition1.y);
        sb.draw(ground, groundPosition2.x, groundPosition2.y);
        sb.end();

        if (score > 1) {

            sb.begin();
            font.draw(sb,"FPS",10,30);
            sb.end();

        /*    String strsc = String.valueOf(score);
            sb.draw(strsc,0,0);
            System.out.print(score);
            shadow.draw(sb,strsc, (136 / 2) - (3 * strsc.length()), 12);
            font.draw(sb, strsc, (136 / 2) - (3 * strsc.length() - 1), 11);

            //sb.draw();

            //font.draw(sb, "Hello World", 200, 200);
            //font.draw(sb, Integer.toString(score), 0, 0);*/
        }
    }

    @Override
    public void dispose() {

        font.dispose();
        shadow.dispose();
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube:tubes){
            tube.dispose();
        }

        //System.out.println("play state dispose");

    }

    public void updateGround(){
        if(cam.position.x - (cam.viewportWidth / 2)> groundPosition1.x + ground.getWidth()){
            groundPosition1.add(ground.getWidth() * 2, 0);
        }

        if(cam.position.x - (cam.viewportWidth / 2)> groundPosition2.x + ground.getWidth()){
            groundPosition2.add(ground.getWidth() * 2, 0);
        }
    }
}
