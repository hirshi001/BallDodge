package com.hirshi001.balldodge.screens.gamescreen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.hirshi001.balldodge.game.Difficulty;
import com.hirshi001.balldodge.game.GameManager;
import com.hirshi001.balldodge.gamerenderer.BabyGameRenderer;
import com.hirshi001.balldodge.gamerenderer.GameManagerRender;
import com.hirshi001.balldodge.gamerenderer.GameRenderer;

public class GameScreen implements Screen {
    GameManager manager;
    GameManagerRender renderer;

    public GameScreen(Difficulty difficulty) {
        super();
        this.manager = new GameManager(difficulty);
        if(difficulty==Difficulty.EASY){
            this.renderer = new BabyGameRenderer(manager);
        }else if(difficulty==Difficulty.MIRRORED || difficulty == Difficulty.HARD){
            this.renderer = new GameRenderer(manager);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0F,0F,1F, 1F);
        manager.act(delta);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
