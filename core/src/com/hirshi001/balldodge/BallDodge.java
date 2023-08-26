package com.hirshi001.balldodge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.hirshi001.balldodge.gamerenderer.Sprites;
import com.hirshi001.balldodge.screens.homescreen.HomeScreen;
import com.hirshi001.balldodge.util.Assets;
import com.hirshi001.balldodge.util.TextureRegions;

public class BallDodge extends Game {

	public static BallDodge INSTANCE;

	public BallDodge() {
		super();
		INSTANCE = this;
	}

	@Override
	public void create () {
		Box2D.init();
		Assets.loadAssets();
		TextureRegions.loadTextureRegions();
		System.out.println(Assets.GAME_ASSETS.getDiagnostics());
		Sprites.loadSprites();
		setScreen(new HomeScreen());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		Assets.GAME_ASSETS.dispose();
	}
}
