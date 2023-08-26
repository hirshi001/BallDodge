package com.hirshi001.balldodge.game.entity.platforms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.hirshi001.balldodge.game.GameManager;
import com.hirshi001.balldodge.game.entity.Entity;

public abstract class Platform extends Entity {

    public static final int FILTER_BIT_MASK = 0b10;

    public Platform(GameManager manager) {
        super(manager);
    }

    public Platform(GameManager manager, Body body) {
        super(manager, body);

    }

    public Platform generateNextPlatform(){

        double ran = Math.random();
        float len, angle, height;
        Vector2 endPoint = new Vector2();
        getEndPoint(endPoint);
        if(ran<0.5) {
            angle = MathUtils.random(-MathUtils.PI / 4, MathUtils.PI / 4);
            len = MathUtils.random(2F, 20F);
            height = 1F;
            WoodenPlatform woodenPlatform = WoodenPlatform.fromStartPosition(manager, endPoint.x, endPoint.y, len, height, angle);
            manager.addEntity(woodenPlatform);
            return woodenPlatform;

        }else if (ran<0.8 && !(this instanceof Ground)){
            angle = 0F;
            height = 2F;
            len = MathUtils.random(5F, 20F);
            Ground ground = Ground.fromStartPosition(manager, endPoint.x, endPoint.y, len,  height, angle);
            manager.addEntity(ground);
            return ground;
        }else{

            angle = 0F;
            height = 2F;
            len = MathUtils.random(1F, 40F);
            RotatingPlatform platform = RotatingPlatform.fromStartPosition(manager, endPoint.x, endPoint.y, len,  height, angle);
            manager.addEntity(platform);
            return platform;
        }
    }

    public abstract Vector2 getStartPoint(Vector2 vector2);
    public abstract Vector2 getEndPoint(Vector2 vector2);

    public static void getRightMostPoint(Body body, Vector2 rightMost){
        rightMost.set(Float.NEGATIVE_INFINITY, 0F);
        Vector2 temp = new Vector2();
        CircleShape circle;
        PolygonShape polygon;
        Shape shape;
        Shape.Type type;
        for(Fixture fixture:body.getFixtureList()){
            shape = fixture.getShape();
            type = shape.getType();
            if(type == Shape.Type.Circle){
                circle = (CircleShape) shape;
                if(rightMost.x<circle.getRadius() + circle.getPosition().x){
                    rightMost.set(circle.getPosition()).add(circle.getRadius(), 0F);
                }
            }
            else if(type == Shape.Type.Polygon){
                polygon = (PolygonShape) shape;
                for(int i=0;i < polygon.getVertexCount(); i++){
                    polygon.getVertex(i, temp);
                    body.getTransform().mul(temp);
                    if(rightMost.x < temp.x || (rightMost.x==temp.x && rightMost.y<temp.y)){
                        rightMost.set(temp);
                    }
                }
            }
        }
    }


}
