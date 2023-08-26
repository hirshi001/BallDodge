package com.hirshi001.balldodge.gamerenderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.hirshi001.balldodge.util.Assets;

import java.util.HashMap;
import java.util.Map;

public class Sprites {

    private static Map<String, Sprite> SPRITES = new HashMap<>();

    public static void loadSprites(){
        SPRITES.clear();
        for(TextureAtlas.AtlasRegion region:Assets.GAME_TEXTURES.getRegions()){
            SPRITES.put(region.name, new Sprite(region.getTexture()));
        }
    }

    public static Sprite getSprite(String name){
        return SPRITES.get(name);
    }


}
