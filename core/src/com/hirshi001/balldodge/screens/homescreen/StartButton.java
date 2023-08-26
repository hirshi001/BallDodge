package com.hirshi001.balldodge.screens.homescreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.hirshi001.balldodge.util.Assets;
import com.hirshi001.balldodge.util.TextureRegions;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class StartButton extends TextButton {

    public StartButton(String text) {
        super(text, new TextButtonStyle(new UpDrawable(), new DownDrawable(), null, Assets.getFont("opensans-256")));
        getStyle().fontColor = Color.BLACK;
        setTransform(true);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        setOrigin(getWidth()/2F, getHeight()/2F);
        setBounds(getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        rotateBy(1);
    }


    public static class UpDrawable extends BaseDrawable {

        ShapeDrawer drawer;

        public UpDrawable(){
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            super.draw(batch,x,y,width,height);
            if(drawer==null || drawer.getBatch()!=batch) drawer = new ShapeDrawer(batch, TextureRegions.whitePixel);

            drawer.setColor(0.8F,0.8F,0.8F,1F);
            drawer.filledRectangle(x,y,width,height);

        }
    }

    public static class DownDrawable extends BaseDrawable {

        ShapeDrawer drawer;

        public DownDrawable(){
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            super.draw(batch,x,y,width,height);
            if(drawer==null || drawer.getBatch()!=batch) drawer = new ShapeDrawer(batch, TextureRegions.whitePixel);


            drawer.setColor(0.5F,0.5F,0.5F,1F);
            drawer.filledRectangle(x,y,width,height);

            /*
            batch.end();

            shapeRenderer.begin();
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(x,y,width,height);
            shapeRenderer.end();

            batch.begin();

             */
        }
    }


}
