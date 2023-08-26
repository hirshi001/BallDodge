package com.hirshi001.balldodge.gamerenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hirshi001.balldodge.game.GameManager;
import com.hirshi001.balldodge.game.entity.Entity;

public class BabyGameRenderer implements GameManagerRender{

    GameManager manager;
    Box2DDebugRenderer renderer;
    Viewport viewport;
    SpriteBatch batch;

    public BabyGameRenderer(GameManager manager){
        this.manager = manager;
        renderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        viewport = new ExtendViewport(50F, 30F, new OrthographicCamera());

    }

    @Override
    public void render(){
        viewport.getCamera().position.set(manager.entity.body.getPosition(), 0F);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        for(Entity entity:manager.entityList){
            entity.render(batch);

        }
        batch.end();
    }


    public void resize(int width, int height){
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);


    }
}
