package com.hirshi001.balldodge.game.entity.platforms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.hirshi001.balldodge.game.GameManager;

public abstract class RectangularPlatform extends Platform{

    public float width, height;

    public RectangularPlatform(GameManager manager, Body body) {
        super(manager, body);
    }

    public RectangularPlatform(GameManager manager, float x, float y, float width, float height, float angle, BodyDef bodyDef, FixtureDef fixtureDef) {
        super(manager);

        this.width = width;
        this.height = height;

        PolygonShape box = new PolygonShape();
        box.set(new float[]{-width/2F, -height/2F, -width/2F, height/2F, width/2F, height/2F, width/2F, -height/2F});
        //box.set(new float[]{x-width/2F, y-height/2F, x-width/2F, y+height/2F, x+width/2F, y+height/2F, x+width/2F, y-height/2F});
        //box.setAsBox(width/2F, height/2F);

        body = manager.world.createBody(bodyDef);
        fixtureDef.shape = box;
        body.createFixture(fixtureDef);
        body.setTransform(x, y, angle);

    }

    public static void renderPlatform(TextureRegion repeatableRegion, float width, float height, Body body, SpriteBatch batch){
        TextureRegion regionToDraw = new TextureRegion(repeatableRegion);
        float repeatingLength = (float)regionToDraw.getRegionWidth()/regionToDraw.getRegionHeight();
        float x = -width/2F;

        for(int i=0;i<width/repeatingLength-1; i++){
            Affine2 trans = new Affine2();

            trans.translate(body.getPosition().x, body.getPosition().y);
            trans.rotateRad(body.getAngle());
            trans.translate(x, - height/2F);
            batch.draw(regionToDraw, repeatingLength, height, trans);
            x += repeatingLength;
        }
        Affine2 trans = new Affine2();

        trans.translate(body.getPosition().x, body.getPosition().y);
        trans.rotateRad(body.getAngle());
        trans.translate(x, - height/2F);

        float portionRemaining = width/2F - x;
        regionToDraw.setRegionWidth((int) (regionToDraw.getRegionWidth()*(portionRemaining/repeatingLength)));
        batch.draw(regionToDraw, portionRemaining, height, trans);
    }

    @Override
    public Vector2 getStartPoint(Vector2 vector2) {
        ((PolygonShape)body.getFixtureList().get(0).getShape()).getVertex(3, vector2);
        return body.getTransform().mul(vector2);
    }

    @Override
    public Vector2 getEndPoint(Vector2 vector2) {
        ((PolygonShape)body.getFixtureList().get(0).getShape()).getVertex(1, vector2);
        return body.getTransform().mul(vector2);
    }
}
