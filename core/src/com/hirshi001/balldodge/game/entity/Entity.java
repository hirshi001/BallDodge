package com.hirshi001.balldodge.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.hirshi001.balldodge.game.GameManager;

import java.util.Random;

public abstract class Entity implements ContactListener {

    public boolean collidesWithPlayer = true;
    public GameManager manager;
    public Body body;
    public int draw = Math.random()<0.5?-1:1;

    public Entity(GameManager manager){
        this.manager = manager;
    }

    public Entity(GameManager manager, Body body){
        this.manager = manager;
        this.body = body;
        draw = MathUtils.randomSign();
        body.setUserData(this);
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void act(float delta){
        body.setUserData(this);
    }

    public void render(SpriteBatch batch){

    }
}
