package com.mygdx.towerdefence.framework.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.towerdefence.TowerDefenceGame;
import com.mygdx.towerdefence.framework.StageMultiplexer;

public class BasicScreen implements Screen {
    private final Viewport viewport;
    private final OrthographicCamera camera;
    protected final TowerDefenceGame game;
    private final StageMultiplexer stageMultiplexer;
    public static final float WORLD_SIZE_X = 1920;
    public static final float WORLD_SIZE_Y = 1080;

    public BasicScreen(TowerDefenceGame game) {
        this.game = game;
        stageMultiplexer = new StageMultiplexer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_SIZE_X, WORLD_SIZE_Y);
        viewport = new ExtendViewport(WORLD_SIZE_X, WORLD_SIZE_Y, camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(220, 220, 220, 0);
        camera.update();
        stageMultiplexer.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stageMultiplexer.dispose();
    }

    public Viewport getViewport() {
        return viewport;
    }

    public StageMultiplexer getStageMultiplexer() {
        return stageMultiplexer;
    }
}
