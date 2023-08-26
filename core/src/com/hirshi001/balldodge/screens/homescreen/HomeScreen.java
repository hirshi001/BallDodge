package com.hirshi001.balldodge.screens.homescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hirshi001.balldodge.BallDodge;
import com.hirshi001.balldodge.game.Difficulty;
import com.hirshi001.balldodge.screens.gamescreen.GameScreen;
import com.hirshi001.balldodge.util.Assets;

public class HomeScreen implements Screen {

    Image imageLogo;
    Stage stage;
    Button startButton;

    public HomeScreen() {
        super();
        TextureRegion gameName = Assets.getTextureRegion("BallDodge");
        stage = new Stage(new FitViewport(100, 100));

        imageLogo = new Image(gameName);
        imageLogo.setSize(60F, imageLogo.getHeight()*60F/imageLogo.getWidth());
        imageLogo.setPosition(50F - imageLogo.getWidth()/2F, 30F);
        stage.addActor(imageLogo);

        startButton = new StartButton("EASY");
        startButton.setScale(25F/startButton.getWidth());
        startButton.sizeBy(5F);
        startButton.setPosition(50F - startButton.getWidth()/2F, 60F);
        startButton.debug();
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.postRunnable(()->{
                    BallDodge.INSTANCE.setScreen(new GameScreen(Difficulty.HARD));
                });
            }
        });
        stage.addActor(startButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
