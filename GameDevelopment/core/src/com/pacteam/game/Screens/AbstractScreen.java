package com.pacteam.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pacteam.game.Pacman;

public abstract class AbstractScreen implements Screen {
    protected final Pacman context;
    protected final FitViewport viewport;
    protected final World world;
    protected final Box2DDebugRenderer box2DDebugRenderer;

    public AbstractScreen(final Pacman context) {
        this.context = context;
        viewport = context.getScreenViewport();
        this.world = context.getWorld();
        this.box2DDebugRenderer = context.getBox2DDebugRenderer();
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width,height);
    }


}
