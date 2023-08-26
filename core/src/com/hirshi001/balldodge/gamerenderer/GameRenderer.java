package com.hirshi001.balldodge.gamerenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.*;
import com.hirshi001.balldodge.game.GameManager;
import com.hirshi001.balldodge.game.entity.Entity;

import java.util.function.Predicate;

public class GameRenderer implements GameManagerRender{

    GameManager manager;
    Box2DDebugRenderer renderer;
    ShapeRenderer shapeRenderer;
    Viewport topViewport;
    Viewport bottomViewport;
    Viewport overViewport;
    SpriteBatch batch;

    public GameRenderer(GameManager manager){
        this.manager = manager;
        renderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        topViewport = new ExtendViewport(50F, 30F, 0, 60F, new OrthographicCamera());
        bottomViewport = new ExtendViewport(50F, 30F, 0, 60F, new OrthographicCamera());
        overViewport = new StretchViewport(100F, 100F);

        bottomViewport.getCamera().rotateAround(topViewport.getCamera().position, new Vector3(1F, 0F, 0F), 180);

    }

    @Override
    public void render(){

        draw(topViewport, (entity)-> entity.draw>=0);
        draw(bottomViewport, (entity)-> entity.draw<=0);
;
        overViewport.apply();
        shapeRenderer.setProjectionMatrix(overViewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(-50, -1, 100F, 2F);
        shapeRenderer.end();


    }

    private void draw(Viewport viewport, Predicate<Entity> entityPredicate){
        viewport.getCamera().position.set(manager.entity.body.getPosition(), 0F);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        for(Entity entity:manager.entityList){
            if(entityPredicate.test(entity)){
                entity.render(batch);
            }
        }
        batch.end();
    }

    public void resize(int width, int height){
        topViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2, false);

        bottomViewport.setWorldSize(topViewport.getWorldWidth(), topViewport.getWorldHeight());
        bottomViewport.getCamera().position.set(topViewport.getCamera().position);
        bottomViewport.setScreenBounds(0, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2);

        overViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


/*
        viewport2.setScreenBounds(0, 0, width, height/2);
        viewport2.update(width, height/2);

 */
    }


}
