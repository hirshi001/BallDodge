package com.hirshi001.balldodge.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.hirshi001.balldodge.game.entity.Entity;
import com.hirshi001.balldodge.game.entity.SpikyBallEntity;
import com.hirshi001.balldodge.game.entity.platforms.Ground;
import com.hirshi001.balldodge.game.entity.PlayerEntity;
import com.hirshi001.balldodge.game.entity.platforms.Platform;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class GameManager {

    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;


    public final World world;
    public PlayerEntity entity;
    private float scoreStep = 0F;
    private float highScore = 0F;

    public List<Entity> entityList = new LinkedList<>();

    private final ContactListener contactListener;

    public Platform rightMostGenerated;

    public Difficulty difficulty;


    public GameManager(Difficulty difficulty){
        this.difficulty = difficulty;
        world = new World(new Vector2(0F, -20F), true);
        contactListener = new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Object o = contact.getFixtureA().getUserData();
                if(o instanceof ContactListener){
                    ((ContactListener) o).beginContact(contact);
                }
                o = contact.getFixtureB().getUserData();
                if(o instanceof ContactListener){
                    ((ContactListener) o).beginContact(contact);
                }
            }

            @Override
            public void endContact(Contact contact) {
                Object o = contact.getFixtureA().getUserData();
                if(o instanceof ContactListener){
                    ((ContactListener) o).endContact(contact);
                }
                o = contact.getFixtureB().getUserData();
                if(o instanceof ContactListener){
                    ((ContactListener) o).endContact(contact);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                Object o = contact.getFixtureA().getUserData();
                if(o instanceof ContactListener){
                    ((ContactListener) o).preSolve(contact, oldManifold);
                }
                o = contact.getFixtureB().getUserData();
                if(o instanceof ContactListener){
                    ((ContactListener) o).preSolve(contact, oldManifold);
                }
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                Object o = contact.getFixtureA().getUserData();
                if(o instanceof ContactListener){
                    ((ContactListener) o).postSolve(contact, impulse);
                }
                o = contact.getFixtureB().getUserData();
                if(o instanceof ContactListener){
                    ((ContactListener) o).postSolve(contact, impulse);
                }
            }
        };

        world.setContactListener(contactListener);
        world.setContactFilter(new ContactFilter() {
            @Override
            public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
                if(fixtureA.getUserData() instanceof SpikyBallEntity && fixtureB.getUserData() instanceof SpikyBallEntity) return true;

                if(fixtureA.getUserData() instanceof Entity && fixtureB.getUserData() instanceof Entity){
                    Entity a = (Entity) fixtureA.getUserData();
                    Entity b = (Entity) fixtureB.getUserData();
                    return !((a.collidesWithPlayer && b==entity) || (b.collidesWithPlayer && a==entity));
                }
                return true;
            }
        });
        setup();
    }

    public void setup(){
        //Create player
        entity = new PlayerEntity(this);
        addEntity(entity);

        //Create Ground
        rightMostGenerated = Ground.create(this, -16, -16, 32, 1, 0F);
        addEntity(rightMostGenerated);
        addEntity(new SpikyBallEntity(this, entity));
        if(Difficulty.HARD == difficulty){
            addEntity(new SpikyBallEntity(this, entity));
            while(Math.random()<0.6){
                addEntity(new SpikyBallEntity(this, entity));
            }
        }


    }


    public void addEntity(Entity entity){
        entityList.add(entity);
    }


    Array<Body> bodyArray = new Array<>();
    private Vector2 rightMostPoint = new Vector2();
    int jumpTicks = 0;
    public void act(float delta){


        Vector2 forceVector = new Vector2(0F, 0F);

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            forceVector.x+=50;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) forceVector.x-=50;

        if(entity.body.getLinearVelocity().x<0 && forceVector.x>0){
            forceVector.x+=10F;
        }else if(entity.body.getLinearVelocity().x>0&& forceVector.x<0){
            forceVector.x-=10F;
        }



        entity.body.applyForceToCenter(forceVector, true);
        forceVector.set(0F, 0F);
        jumpTicks--;

        if(Gdx.input.isKeyPressed(Input.Keys.W) && entity.canJump && jumpTicks<0){
            System.out.println(entity.jumpAngle);
            //forceVector.y+= 2000;
            forceVector.set(entity.jumpAngle).setLength(2000);
            entity.body.applyForceToCenter(forceVector, true);
            jumpTicks = 5;
        }
        entity.canJump = false;


        if(entity.body.getPosition().x >=10F){ //Shift right to prevent loss of precision

            world.getBodies(bodyArray);
            bodyArray.forEach((body) -> {
                if(entity.body.getPosition().x - body.getPosition().x > 100F){
                    world.destroyBody(body);
                    entityList.removeIf(entity1 -> entity1.body.equals(body));
                }
                else body.setTransform(body.getPosition().sub(10F, 0F), body.getAngle());
            });
            scoreStep +=10F;
        }
        highScore = Math.max(highScore, scoreStep+entity.body.getPosition().x);

        Platform.getRightMostPoint(rightMostGenerated.body, rightMostPoint);
        if(rightMostPoint.x - 20F < entity.body.getPosition().x){
            System.out.println("new generation");
            rightMostGenerated = rightMostGenerated.generateNextPlatform();
        }
        if(entity.body.getPosition().y < rightMostGenerated.body.getPosition().y - 50F){
            entity.body.setTransform(rightMostGenerated.body.getPosition().x, rightMostGenerated.body.getPosition().y+5F, 0F);
            //score = 0F;
            world.getBodies(bodyArray);
            entityList.removeIf(new Predicate<Entity>() {
                @Override
                public boolean test(Entity entity) {
                    if(entity instanceof PlayerEntity && entity!= GameManager.this.entity){
                        world.destroyBody(entity.body);
                        return true;
                    }
                    return false;
                }
            });
        }



        /*
        if(Math.random()<0.1D){
            PlayerEntity projectile = new PlayerEntity(this, MathUtils.random(0.5F, 2F));
            projectile.body.setTransform(entity.body.getWorldCenter().x-40F, MathUtils.random(-2F, 10F)+ rightMostGenerated.body.getPosition().y, 0F);
            projectile.body.setLinearVelocity(MathUtils.random(20F, 40F)/projectile.radius*Math.max(1F, scoreStep /1000F), MathUtils.random(-1F, 20F));
            addEntity(projectile);
        }

         */



        for(Entity entity:entityList){
            entity.act(delta);
        }
        world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
    }


}
