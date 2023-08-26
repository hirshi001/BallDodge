package com.hirshi001.balldodge.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hirshi001.balldodge.game.GameManager;
import com.hirshi001.balldodge.util.Assets;

public class PlayerEntity extends Entity{


    public static final int FILTER_BIT_MASK = 0b1;

    Sprite sprite;
    public float radius;
    public boolean canJump = false;
    public Vector2 jumpAngle = new Vector2();

    public PlayerEntity(GameManager manager, float radius){
        super(manager);
        this.radius = radius;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.linearDamping = 0.3F;
        def.angularDamping = 0.2F;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 1F;
        fixtureDef.restitution = 0.05F;
        fixtureDef.density = 3F;

        Shape shape = new CircleShape();
        shape.setRadius(radius);
        fixtureDef.shape = shape;

        body = manager.world.createBody(def);
        body.createFixture(fixtureDef).setUserData(this);

        sprite = new Sprite(Assets.getTextureRegion("Player"));
        sprite.setSize(radius*4, radius*4);
        draw = 0;
    }

    public PlayerEntity(GameManager manager) {
        this(manager, 0.5F);
    }

    public void act(float delta){
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        super.preSolve(contact, oldManifold);
        Vector2 contactPoint = new Vector2();
        WorldManifold manifold = contact.getWorldManifold();
        for(int i=0; i<manifold.getNumberOfContactPoints(); i++){
            contactPoint.add(manifold.getPoints()[i]);
        }
        contactPoint.scl(1F/manifold.getNumberOfContactPoints());
        jumpAngle.set(body.getPosition()).sub(contactPoint).scl(1F);
        //jumpAngle.scl(-1F);
        //if(angle>45F && angle<135F){
            canJump = true;
        //}
    }

    public void render(SpriteBatch batch){
        sprite.setPosition(body.getPosition().x-sprite.getWidth()/2F, body.getPosition().y-sprite.getHeight()/2F);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        sprite.setOrigin(sprite.getWidth()/2F, sprite.getHeight()/2F);
        sprite.draw(batch);
    }
}
