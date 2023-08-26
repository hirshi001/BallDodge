package com.hirshi001.balldodge.game.entity.platforms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.hirshi001.balldodge.game.GameManager;
import com.hirshi001.balldodge.util.Assets;

public class RotatingPlatform extends RectangularPlatform{

    boolean normalEndPoint;
    TextureRegion region = Assets.getTextureRegion("badlogic");


    public static RotatingPlatform fromStartPosition(GameManager manager, float startX, float startY, float width, float height, float angle){
        return create(manager, startX+width/2F, startY-height/2F, width, height, angle);
        //return new Ground(manager, , bodyDef, fixtureDef);
    }

    public static RotatingPlatform create(GameManager manager, float x, float y, float width, float height, float angle){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.1F;
        fixtureDef.restitution = 0.3F;
        return new RotatingPlatform(manager, x, y, width, height, angle, bodyDef, fixtureDef);
    }



    public RotatingPlatform(GameManager manager, float x, float y, float width, float height, float angle, BodyDef bodyDef, FixtureDef fixtureDef) {
        super(manager, x, y, width, height, angle, bodyDef, fixtureDef);
        normalEndPoint = MathUtils.randomBoolean();
    }

    public RotatingPlatform(GameManager manager, Body body) {
        super(manager, body);
        normalEndPoint = MathUtils.randomBoolean();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        RectangularPlatform.renderPlatform(region, width, height, body, batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setAngularVelocity(-2F);
        //body.setTransform(body.getPosition(), body.getAngle()-0.05F);
    }

    @Override
    public Vector2 getStartPoint(Vector2 vector2) {
        vector2.set(body.getPosition()).add(-width/2F, height/2F);
        return vector2;
    }

    @Override
    public Vector2 getEndPoint(Vector2 vector2) {
        vector2.set(body.getPosition()).add(width/2F, height/2F);
        vector2.add(0F, width/2F * (normalEndPoint ?1:-1));
        return vector2;
    }

}
