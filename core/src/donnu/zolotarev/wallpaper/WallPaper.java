package donnu.zolotarev.wallpaper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;

import donnu.zolotarev.wallpaper.Screens.MainScreen;


public class WallPaper extends Game {


    private Screen screen;

    private boolean paused = false;
    private float screenOffset;
    private boolean settingChanged;

    private float imageTime = 5f;
    private boolean isRipple = true;
    private boolean isMoveRipple = true;

    private FPSLogger fpsLogger;


    @Override
    public void create() {

        screenOffset = 0.0f;
        settingChanged = true;
        paused = false;

        screen = new MainScreen(this);
        setScreen(screen);

        fpsLogger = new FPSLogger();
    }

    @Override
    public void dispose() { screen.dispose(); }

    @Override
    public void render() {
        if(!paused){
            screen.render( Gdx.graphics.getDeltaTime() );
        }
        fpsLogger.log();
    }

    @Override
    public void resize(int width, int height) { screen.resize( width, height ); }

    @Override
    public void pause() { paused = true; }

    @Override
    public void resume() { paused = false; }


    public boolean isSettingChanged() {
        boolean b = settingChanged;
        settingChanged = false;
        return b;
    }

    public void setSettingChanged(float imageTime, boolean isRipple, boolean moveripple) {
        this.imageTime = imageTime;
        this.isRipple = isRipple;
        this.isMoveRipple = moveripple;
        this.settingChanged = true;
    }

    public float getScreenOffset() {
        return screenOffset;
    }

    public void setScreenOffset(float screenOffset) {
        this.screenOffset = screenOffset;
    }

    public float getImageTime() {
        return imageTime;
    }

    public boolean isRipple() {
        return isRipple;
    }

    public boolean isMoveRipple() {
        return isMoveRipple;
    }
}
