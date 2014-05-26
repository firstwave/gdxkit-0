package io.firstwave.gdxkit.screen;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

/**
 * Created by obartley on 2/20/14.
 */
public class TransitionScreen implements Screen {
    private static final String TAG = TransitionScreen.class.getSimpleName();

    public static final int FADE_IN = 1;
    public static final int FROM_TOP = 2;
    public static final int FROM_BOTTOM = 3;
    public static final int FROM_RIGHT = 4;
    public static final int FROM_LEFT = 5;

    public static final int SINGLE_RENDER = 0;
    public static final int CONTINUOUS_RENDER = 1;

    private final static float screenWidth = Gdx.graphics.getWidth();
    private final static float screenHeight = Gdx.graphics.getHeight();

    private Game game;
    private Screen current;
    private Screen next;
    private int mRenderMode;
    private int mTransitionMode;
    private float mDurationSeconds;

    private FrameBuffer currentBuffer;
    private FrameBuffer nextBuffer;

    private SpriteBatch spriteBatch;
    private TweenManager manager;
    private TweenCallback backgroundAnimationTweenComplete;

    private Sprite currentScreenSprite;
    private Sprite nextScreenSprite;

    private TransitionListener mListener;

    public TransitionScreen(Game game, Screen current, Screen next, float durationSeconds, int transitionMode, int renderMode, TransitionListener listener) {
        this.current = current;
        this.next = next;
        this.game = game;
        mRenderMode = renderMode;
        mTransitionMode = transitionMode;
        mDurationSeconds = durationSeconds;
        mListener = listener;
    }



    @Override
    public void render(float delta) {
        manager.update(Gdx.graphics.getDeltaTime());

        if (mRenderMode == CONTINUOUS_RENDER) {
            updateBuffers(delta);
        }

        spriteBatch.begin();
        currentScreenSprite.draw(spriteBatch);
        nextScreenSprite.draw(spriteBatch);
        spriteBatch.end();

    }

    private void updateBuffers(float delta) {
        nextBuffer.begin();
        next.render(delta);
        nextBuffer.end();


        currentBuffer.begin();
        current.render(delta);
        currentBuffer.end();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();

        manager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new ScreenTween());

        backgroundAnimationTweenComplete = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(next);
                if (mListener != null) {
                    mListener.onTransitionComplete(current, next);
                }
            }
        };

        currentBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int) screenWidth, (int) screenHeight, false);
        nextBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int) screenWidth, (int) screenHeight, false);


        updateBuffers(Gdx.graphics.getDeltaTime());


        float target = 0;
        nextScreenSprite = new Sprite(nextBuffer.getColorBufferTexture());
        nextScreenSprite.flip(false, true);
        currentScreenSprite = new Sprite(currentBuffer.getColorBufferTexture());
        currentScreenSprite.flip(false, true);

        switch (mTransitionMode) {
            case FADE_IN:
                target = 1;
                nextScreenSprite.setColor(1, 1, 1, 0);
                break;
            case FROM_TOP:
                nextScreenSprite.setPosition(0, screenHeight);
                break;
            case FROM_BOTTOM:
                nextScreenSprite.setPosition(0, screenHeight * -1);
                break;
            case FROM_RIGHT:
                nextScreenSprite.setPosition(screenHeight, 0);
                break;
            case FROM_LEFT:
                nextScreenSprite.setPosition(screenHeight * -1, 0);
                break;

        }

        Tween.to(nextScreenSprite, mTransitionMode, mDurationSeconds)
                .target(target)
                .setCallback(backgroundAnimationTweenComplete)
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);
    }

    @Override
    public void dispose() {
        current.dispose();
        next.dispose();
    }


    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }


    protected static class ScreenTween implements TweenAccessor<Sprite> {
        @Override
        public int getValues(Sprite target, int tweenType, float[] returnValues) {
            switch (tweenType) {
                case FADE_IN:
                    returnValues[0] = target.getColor().a;
                    return 1;
                case FROM_TOP:
                case FROM_BOTTOM:
                    returnValues[0] = target.getY();
                    return 1;
                case FROM_RIGHT:
                case FROM_LEFT:
                    returnValues[0] = target.getX();
                    return 1;
                default:
                    return 0;


            }
        }

        @Override
        public void setValues(Sprite target, int tweenType, float[] newValues) {
            switch (tweenType) {
                case FADE_IN:
                    target.setColor(1, 1, 1, newValues[0]);
                    break;
                case FROM_TOP:
                case FROM_BOTTOM:
                    target.setPosition(target.getX(), newValues[0]);
                    break;
                case FROM_RIGHT:
                case FROM_LEFT:
                    target.setPosition(newValues[0], target.getY());
                    break;
            }
        }

    }

    public static interface TransitionListener {
        public void onTransitionComplete(Screen previous, Screen current);
    }
}
