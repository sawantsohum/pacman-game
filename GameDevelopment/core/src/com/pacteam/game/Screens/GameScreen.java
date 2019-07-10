package com.pacteam.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.pacteam.game.Pacman;

import java.security.Key;

import static com.pacteam.game.Pacman.BIT_GROUND;
import static com.pacteam.game.Pacman.BIT_PLAYER;
import static com.pacteam.game.Pacman.UNIT_SCALE;

public class GameScreen extends AbstractScreen {
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    private final Body player;

    private final AssetManager assetManager;
    private final OrthographicCamera gameCamera;
    private final OrthogonalTiledMapRenderer mapRenderer;


    public GameScreen(final Pacman context) {
        super(context);

        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, context.getSpriteBatch());
        assetManager = context.getAssetManager();
        gameCamera = context.getGameCamera();

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        //create player
        bodyDef.position.set(4.5f, 3);
        bodyDef.gravityScale = 1;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bodyDef);
        player.setUserData("PLAYER");

        fixtureDef.density = 1;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = polygonShape;
        player.createFixture(fixtureDef);
        polygonShape.dispose();

        //create room
        bodyDef.position.set(0, 0);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        final Body body = world.createBody(bodyDef);
        body.setUserData("GROUND");

        fixtureDef.density = 0;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        final ChainShape chainShape = new ChainShape();
        chainShape.createLoop(new float[]{1, 1, 1, 15});
        fixtureDef.shape = chainShape;
        body.createFixture(fixtureDef);
        chainShape.dispose();
    }

    @Override
    public void show() {
        mapRenderer.setMap(assetManager.get("maps/PacMap.tmx", TiledMap.class));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        final float speedX;
        final float speedY;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            speedX = -3;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            speedX = 3;
        } else {
            speedX = 0;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            speedY = -3;
        } else if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            speedY = 3;
        } else {
            speedY = 0;
        }

        player.applyLinearImpulse(
                (speedX - player.getLinearVelocity().x * player.getMass()),
                (speedY - player.getLinearVelocity().y * player.getMass()),
                player.getWorldCenter().x, player.getWorldCenter().y, true
        );

        viewport.apply(true);
        mapRenderer.setView(gameCamera);
        mapRenderer.render();
        box2DDebugRenderer.render(world, viewport.getCamera().combined);
    }

    @Override
    public void resize(int width, int height) {

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
        mapRenderer.dispose();
    }
}
