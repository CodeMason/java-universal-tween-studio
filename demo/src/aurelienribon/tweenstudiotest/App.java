package aurelienribon.tweenstudiotest;

import aurelienribon.tweenstudio.TweenStudio;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class App implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Sprite[] sprites;
	private SpriteTweenable[] spriteTweenables;
	private TweenStudio tweenStudio;

	@Override
	public void create() {
		float ratio = (float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		camera = new OrthographicCamera(10, 10/ratio);
		spriteBatch = new SpriteBatch();
		createSprites();

		// Registration of the editor/player (only needed once per application)
		TweenStudio.registerEditor(new LibGdxTweenStudioEditorX());
		TweenStudio.registerPlayer(new LibGdxTweenStudioPlayer());

		// Instantiation of the studio
		tweenStudio = new TweenStudio();

		// Configuration of the editor
		LibGdxTweenStudioEditorX editor = (LibGdxTweenStudioEditorX) TweenStudio.getRegisteredEditor(LibGdxTweenStudioEditorX.class);
		editor.setCamera(camera);

		// Registration of the Tweenables we want to animate
		tweenStudio.registerTweenable(spriteTweenables[0], "Logo LibGDX");
		tweenStudio.registerTweenable(spriteTweenables[1], "Logo Tween Engine");
		tweenStudio.registerTweenable(spriteTweenables[2], "Logo Tween");
		tweenStudio.registerTweenable(spriteTweenables[3], "Logo Studio");
		tweenStudio.registerTweenable(spriteTweenables[4], "Wave 1");
		tweenStudio.registerTweenable(spriteTweenables[5], "Wave 2");
		tweenStudio.registerTweenable(spriteTweenables[6], "Wave 3");

		// ...then spawn it when you want !
		tweenStudio.edit(LibGdxTweenStudioEditorX.class, "data/anim.tweens");
	}

	private void createSprites() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/pack"));

		sprites = new Sprite[7];
		spriteTweenables = new SpriteTweenable[7];

		sprites[0] = atlas.createSprite("logoLibgdx");
		sprites[1] = atlas.createSprite("logoTweenEngine");
		sprites[2] = atlas.createSprite("logoTween");
		sprites[3] = atlas.createSprite("logoStudio");
		sprites[4] = atlas.createSprite("wave");
		sprites[5] = atlas.createSprite("wave");
		sprites[6] = atlas.createSprite("wave");

		for (int i=0; i<sprites.length; i++) {
			float ratio = sprites[i].getWidth() / sprites[i].getHeight();
			sprites[i].setSize(5, 5/ratio);
			sprites[i].setOrigin(sprites[i].getWidth()/2, sprites[i].getHeight()/2);
			spriteTweenables[i] = new SpriteTweenable(sprites[i]);
		}
	}

	@Override
	public void render() {
		tweenStudio.update();

		GL10 gl = Gdx.gl10;
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for (int i=0; i<sprites.length; i++)
			sprites[i].draw(spriteBatch);
		spriteBatch.end();
	}

	@Override public void resize(int w, int h) {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void dispose() {}
}
