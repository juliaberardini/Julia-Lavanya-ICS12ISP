package com.tootireddevelopmentco.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class Splash implements Screen {

	private SpriteBatch batch;
	private Sprite splash;
	private TweenManager tweenManager;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		splash.draw(batch);
		batch.end();

		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	public void show() {
		// apply preferences
		Gdx.graphics.setVSync(true);
		batch = new SpriteBatch();

		tweenManager = new TweenManager();
		Tween.registerAccessor (Sprite.class, new SpriteAccessor ());

		splash = new Sprite(new Texture ("background.png"));

		Tween.set (splash, SpriteAccessor.ALPHA).target(0).start(tweenManager); 
		Tween.to(splash, SpriteAccessor.ALPHA, 1.5f).target(1).repeatYoyo(1, .5f).setCallback(new TweenCallback () {

			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				((Game) Gdx.app.getApplicationListener()).setScreen (new MainMenu ());
			}
			}).start (tweenManager);
		tweenManager.update (Float.MIN_VALUE); 
}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		dispose (); 
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose (); 
		splash.getTexture().dispose (); 
	}
}
		

