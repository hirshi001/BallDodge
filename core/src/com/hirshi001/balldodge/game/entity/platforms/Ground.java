package com.hirshi001.balldodge.game.entity.platforms;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hirshi001.balldodge.game.GameManager;
import com.hirshi001.balldodge.game.entity.Entity;
import com.hirshi001.balldodge.gamerenderer.Sprites;
import com.hirshi001.balldodge.util.Assets;


public class Ground extends RectangularPlatform {

    TextureRegion region = Assets.getTextureRegion("badlogic");

    public static Ground fromStartPosition(GameManager manager, float startX, float startY, float width, float height, float angle){
        return create(manager, startX+width/2F, startY-height/2F, width, height, angle);
        //return new Ground(manager, , bodyDef, fixtureDef);
    }

    public static Ground create(GameManager manager, float x, float y, float width, float height, float angle){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.2F;
        fixtureDef.density = 0.3F;
        return new Ground(manager, x, y, width, height, angle, bodyDef, fixtureDef);
    }

    public Ground(GameManager manager, float x, float y, float width, float height, float angle, BodyDef bodyDef, FixtureDef fixtureDef) {
        super(manager, x, y, width, height, angle, bodyDef, fixtureDef);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        RectangularPlatform.renderPlatform(region, width, height, body, batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public Vector2 getStartPoint(Vector2 vector2) {
        vector2.set(body.getPosition()).add(-width/2F, height/2F);
        return vector2;
    }

    @Override
    public Vector2 getEndPoint(Vector2 vector2) {
        vector2.set(body.getPosition()).add(width/2F, height/2F);
        return vector2;
    }
}
