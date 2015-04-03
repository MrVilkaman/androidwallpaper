package donnu.zolotarev.wallpaper.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import donnu.zolotarev.wallpaper.Actors.Background;
import donnu.zolotarev.wallpaper.Actors.RippleManager;
import donnu.zolotarev.wallpaper.Assets.ImageLoader;
import donnu.zolotarev.wallpaper.Assets.TextureAssets;
import donnu.zolotarev.wallpaper.Utils.Timer;
import donnu.zolotarev.wallpaper.WallPaper;

public class MainScreen implements Screen {
    private final OrthographicCamera camera;
    private final WallPaper wallPaper;

    private final TextureAssets assets;
    private final SpriteBatch batch;
    private final Stage stage;

    private final Timer timer;
    private final Timer rippleTimer;
    private final RippleManager rippleManager;
    private final ImageLoader imageLoader;
    private Background background;

    private boolean isScreenHided;
    private boolean isScreenResting;

    private Color bgColor;

    private com.badlogic.gdx.math.Vector3 touchPos;

    public MainScreen(final WallPaper wallPaper) {
        this.wallPaper = wallPaper;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        touchPos = new Vector3();
        
        batch = new SpriteBatch();
        Viewport view = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),camera);
        stage = new Stage(view,batch){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                touchPos.set(screenX, screenY, 0);
                camera.unproject(touchPos);
                rippleManager.click(touchPos.x, touchPos.y);
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {

                if (!wallPaper.isMoveRipple()) {
                    return true;
                }
                touchPos.set(screenX, screenY, 0);
                camera.unproject(touchPos);
                rippleManager.click(touchPos.x, touchPos.y);
                return true;
            }
        };



       timer = new Timer(new Timer.Listner() {
            @Override
            public void complite() {
                background.changeImage();
            }
        }, wallPaper.getImageTime());
        timer.setLoop(true);
        timer.start();

        rippleTimer = new Timer(new Timer.Listner() {
            @Override
            public void complite() {
            //    batch.setShader(null);
            }
        },5f);


        assets = new TextureAssets();

        rippleManager =  new RippleManager();
        if (wallPaper.isRipple()) {
            stage.addActor(rippleManager);
        }

        imageLoader = new ImageLoader();

        background = new Background(imageLoader);
        stage.addActor(background);


        ShaderProgram.pedantic = false; //todo ???



        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        boolean settingChanged = wallPaper.isSettingChanged();
        if(settingChanged) {
            backgroundSetting();
        }

        if (!isScreenHided && !isScreenResting && !settingChanged){

            timer.update(delta);

            rippleTimer.update(delta);

            Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            Gdx.gl20.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);

            float d = wallPaper.getScreenOffset() - 0.5f;
            if(d < 0.0f) d *= -1.0f;

            camera.update();
            assets.update();
            background.setScreenOffset(wallPaper.getScreenOffset());
            stage.draw();
            stage.act(delta);
        }
    }

    private void backgroundSetting() {

        bgColor = new Color(1f,1f,1f,1f);
        timer.setDuraction(wallPaper.getImageTime());
        if (wallPaper.isRipple()) {
            stage.addActor(rippleManager);
        }else{
            rippleManager.remove();
            batch.setShader(null);
        }
        if(imageLoader.setCustomImage(wallPaper.getCustomImage())){
            timer.reset();
            timer.start();
            background.changeImage();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        rippleManager.setResolution(width, height);
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
        stage.dispose();
        assets.dispose();
        batch.dispose();
    }

}
