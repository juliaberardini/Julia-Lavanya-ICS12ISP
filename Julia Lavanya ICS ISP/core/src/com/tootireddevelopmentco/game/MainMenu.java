package com.tootireddevelopmentco.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen {

	private Stage stage;
	private Skin skin;
	private Table table;  
	private TweenManager tweenManager;

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		//stage.setViewport (width, height, false);
		table.invalidateHierarchy (); 
	}

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal("clean-crispy-ui.json"), new TextureAtlas("clean-crispy-ui.atlas"));

		table = new Table(skin);
		table.setFillParent(true);

		// creating heading
		Label heading = new Label(RabbitRun.TITLE, skin);
		heading.setFontScale(2);

		// creating buttons
		TextButton buttonPlay = new TextButton("PLAY", skin);
		buttonPlay.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
					}
				})));
			}
		});
		buttonPlay.pad(15);

		TextButton buttonScores = new TextButton("HIGH SCORES", skin);
		buttonScores.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new HighScoreDisplay());
					}
				})));
			}
		});
		buttonScores.pad(15);

		TextButton buttonExit = new TextButton("EXIT", skin);
		buttonExit.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Timeline.createParallel().beginParallel()
						.push(Tween.to(table, ActorAccessor.ALPHA, .75f).target(0))
						.push(Tween.to(table, ActorAccessor.Y, .75f).target(table.getY() - 50)
								.setCallback(new TweenCallback() {

									@Override
									public void onEvent(int type, BaseTween<?> source) {
										Gdx.app.exit();
									}
								}))
						.end().start(tweenManager);
			}
		});
		buttonExit.pad(15);

		// putting stuff together
		table.add(heading).spaceBottom(100).row();
		table.add(buttonPlay).spaceBottom(15).row();
		table.add(buttonScores).spaceBottom(15).row();
		table.add(buttonExit);

		stage.addActor(table);

		// creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// heading colour animation
		Timeline.createSequence().beginSequence()
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1))
				.end().repeat(Tween.INFINITY, 0).start(tweenManager);

		// heading and buttons fade-in
		Timeline.createSequence().beginSequence()
				.push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonScores, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
				.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(buttonScores, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(buttonExit, ActorAccessor.ALPHA, .25f).target(1))
				.end().start(tweenManager);

		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, .75f).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, .75f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

}