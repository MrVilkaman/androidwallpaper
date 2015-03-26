package donnu.zolotarev.wallpaper.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import donnu.zolotarev.wallpaper.Assets.TextureAssets;
import donnu.zolotarev.wallpaper.WallPaper;

public class MainScreen implements Screen {
    private final OrthographicCamera camera;
    private final WallPaper wallPaper;

    private final ShapeRenderer renderer;
    private final TextureAssets assets;
    private final SpriteBatch batch;

    private boolean isScreenHided;
    private boolean isScreenResting;

    private Color bgColor;
    private com.badlogic.gdx.math.Vector3 screenPos;


    public MainScreen(WallPaper wallPaper) {
        this.wallPaper = wallPaper;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        screenPos = new Vector3();
        batch = new SpriteBatch();
        assets = new TextureAssets();

        renderer = new ShapeRenderer(10);
        renderer.setProjectionMatrix(camera.combined);
    }


    @Override
    public void render(float delta) {

        boolean settingChanged = wallPaper.isSettingChanged();
        if(settingChanged) {
            backgroundSetting();
        }

        if (!isScreenHided && !isScreenResting && !settingChanged){
            camera.update();


            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            Gdx.gl.glClearColor( bgColor.r, bgColor.g, bgColor.b, bgColor.a );

            float d = wallPaper.getScreenOffset() - 0.5f;
            if(d < 0.0f) d *= -1.0f;

//            camera.position.set(0 + 2.0f - lwp.screenOffset * 4.0f + cameraX_Actor.getX(), 0.0f + cameraY_Actor.getX(), 305.0f - d * 4 - cameraZ_Actor.getX());
//            camera.lookAt(0.0f - 5.0f + lwp.screenOffset * 10.0f + cameraX_Actor.getX(), 0.0f + cameraY_Actor.getX(), 0.0f - d * 50 - cameraZ_Actor.getX());
//            camera.lookAt(0,Gdx.graphics.getWidth()*wallPaper.getScreenOffset(),0);


//            camera.position.set(Gdx.graphics.getWidth() * wallPaper.getScreenOffset(), 0, 0);
            camera.update();
//            camera.unproject(screenPos);
            assets.update();

            batch.begin();
            batch.draw(assets.getImage(), -Gdx.graphics.getWidth() * wallPaper.getScreenOffset(),0);
            batch.end();

            //renderer.ellipse();


        }
    }

    private void backgroundSetting() {
        bgColor = new Color(1f,1f,1f,1f);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void show() {
        isScreenHided = false;
        isScreenResting = false;
    }

    @Override
    public void hide() { isScreenHided = true; }

    @Override
    public void pause() { isScreenResting = true; }

    @Override
    public void resume() { isScreenHided = false; isScreenResting = false; }

    @Override
    public void dispose() {
        assets.dispose();
        batch.dispose();
    }
}
