package com.shz.castledefence.util;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;

import com.shz.castledefence.scene.MainActivity;

public class LoadTexture {
	public static ITextureRegion ENEMY;
	public static TextureRegion HERO;
	public static TextureRegion BULLET;
	public static TextureRegion BOX;
	public static TextureRegion SUP;
	public static TextureRegion BUTTONIMAGE;
	public static TextureRegion BACK;

	public static void LoadGFX() {

		// BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		final BitmapTextureAtlas Texture = new BitmapTextureAtlas(
				MainActivity.ENGINE.getTextureManager(), 512, 64);
		HERO = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture,
				MainActivity.CONTEXT, "image.png", 0, 0);
		ENEMY = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture,
				MainActivity.CONTEXT, "drakon.png", 60, 0);
		BULLET = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				Texture, MainActivity.CONTEXT, "bullet.png", 120, 0);
		BOX = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture,
				MainActivity.CONTEXT, "box.png", 150, 0);
		SUP = BitmapTextureAtlasTextureRegionFactory.createFromAsset(Texture,
				MainActivity.CONTEXT, "sup.png", 210, 0);
		BUTTONIMAGE = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				Texture, MainActivity.CONTEXT, "button.png", 270, 0);
		Texture.load();

		BitmapTextureAtlas TextureBack = new BitmapTextureAtlas(
				MainActivity.ENGINE.getTextureManager(), 512, 256);
		BACK = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				TextureBack, MainActivity.CONTEXT, "back.png", 0, 0);
		TextureBack.load();

	}
}
