package com.hirshi001.balldodge.game.entity.platforms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.hirshi001.balldodge.game.GameManager;
import com.hirshi001.balldodge.util.Assets;

public class WoodenPlatform extends RectangularPlatform{

    TextureRegion region = Assets.getTextureRegion("wood");

    public static WoodenPlatform fromStartPosition(GameManager manager, float startX, float startY, float width, float height, float angle){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0.1F;
        fixtureDef.restitution = 0.3F;

        WoodenPlatform platform = new WoodenPlatform(manager, 0, 0, width, height, angle, bodyDef, fixtureDef);

        float hw = width/2F, hh = height/2F;
        float a = (float)Math.sqrt(hw*hw + hh*hh);
        float theta = MathUtils.atan2(hh, hw);
        float b = angle - theta;
        float dy = MathUtils.sin(b)*a;
        float dx = MathUtils.cos(b)*a;
        platform.body.setTransform(startX + dx, startY + dy, angle);

        return platform;
    }



    public WoodenPlatform(GameManager manager, float x, float y, float width, float height, float angle, BodyDef bodyDef, FixtureDef fixtureDef) {
        super(manager, x, y, width, height, angle, bodyDef, fixtureDef);
    }

    public WoodenPlatform(GameManager manager, Body body) {
        super(manager, body);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        RectangularPlatform.renderPlatform(region, width, height, body, batch);
    }

    @Override
    public Platform generateNextPlatform() {
        return super.generateNextPlatform();
    }
}
