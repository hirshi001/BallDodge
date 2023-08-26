package com.hirshi001.balldodge.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.hirshi001.balldodge.game.Difficulty;
import com.hirshi001.balldodge.game.GameManager;
import com.hirshi001.balldodge.util.Assets;

public class SpikyBallEntity extends Entity{

    Entity entityToFollow;
    TextureRegion region = Assets.getTextureRegion("Player");

    int index = 0;
    float speedScale;

    Sprite sprite;

    public SpikyBallEntity(GameManager manager, Entity entityToFollow) {
        super(manager);
        this.entityToFollow = entityToFollow;

        collidesWithPlayer = false;


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.1F;
        fixtureDef.restitution = 0.3F;
        fixtureDef.shape = new CircleShape();
        fixtureDef.shape.setRadius(1F);

        body = manager.world.createBody(bodyDef);


        sprite = new Sprite(region);
        sprite.setSize(2F, 2F);
        draw = 0;

        if(manager.difficulty== Difficulty.HARD){
             speedScale = MathUtils.random(0.3F, 0.6F);
        }else{
            speedScale = 0.4F;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        speedScale+=0.001F;
        Vector2 vel = entityToFollow.body.getPosition().cpy().sub(body.getPosition()).scl(speedScale);
        if(vel.len()<5F){
            vel.setLength(5F);
        }
        body.setLinearVelocity(vel);
        System.out.println(body.getLinearVelocity());
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        sprite.setPosition(body.getPosition().x-sprite.getWidth()/2F, body.getPosition().y-sprite.getHeight()/2F);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        sprite.setOrigin(sprite.getWidth()/2F, sprite.getHeight()/2F);
        sprite.draw(batch);
    }
}
