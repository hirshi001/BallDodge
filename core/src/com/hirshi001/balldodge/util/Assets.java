package com.hirshi001.balldodge.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    public static AssetManager GAME_ASSETS;
    public static TextureAtlas GAME_TEXTURES;

    public static void loadAssets(){
        if(GAME_ASSETS==null)
            GAME_ASSETS = new AssetManager();
        GAME_ASSETS.clear();
        GAME_ASSETS.load("atlases/BallDodge.atlas", TextureAtlas.class);
        for(int i=16;i<=256;i*=2){
            GAME_ASSETS.load("fonts/fnt/opensans/opensans-"+i+".fnt", BitmapFont.class);
        }
        GAME_ASSETS.finishLoading();
        GAME_TEXTURES = GAME_ASSETS.get("atlases/BallDodge.atlas", TextureAtlas.class);

    }

    public static TextureRegion getTextureRegion(String name){
        return GAME_TEXTURES.findRegion(name);
    }

    public static BitmapFont getFont(String name){
        return GAME_ASSETS.get("fonts/fnt/opensans/"+name+".fnt");
    }


}
