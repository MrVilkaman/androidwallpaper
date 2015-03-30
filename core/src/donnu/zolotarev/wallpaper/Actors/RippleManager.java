package donnu.zolotarev.wallpaper.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;

import donnu.zolotarev.wallpaper.Utils.RipplePoints;

public class RippleManager extends Actor {

    private static final float R = 0.15f;

    private static final int MAX_COUNT = 10; // MAX 84
    private static final float MAX_LIFE_TIME = 2.5f;

    private final ShaderProgram shader;
    private final RipplePoints ripplePoints;
    private boolean hasShader = false;

    private float oldX = Float.MIN_VALUE;
    private float oldY = Float.MIN_VALUE;
    private float r;


    public RippleManager() {
        shader = new ShaderProgram(Gdx.files.internal("shaders/ripple.vsh"),Gdx.files.internal("shaders/ripple.fsh"));
        Gdx.app.log("TAG",shader.isCompiled() ? "shader compaled, yay" : shader.getLog());
        ripplePoints = new RipplePoints(MAX_COUNT,MAX_LIFE_TIME);
        r = R*Gdx.graphics.getWidth()*R*Gdx.graphics.getWidth();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        ripplePoints.addTime(delta);
            shader.begin();
            shader.setUniform1fv("iGlobalTime", ripplePoints.getTime(), 0, MAX_COUNT);
            shader.end();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!hasShader) {
            batch.setShader(shader);
            hasShader = true;
        }
    }

    @Override
    public boolean remove() {
        hasShader = false;
        return super.remove();
    }

    public void setResolution(float width, float height){
        shader.begin();
        shader.setUniformf("u_resolution", width, height);
        shader.end();
    }

    public void click(float x, float y) {
        if ( r < distanceSqr(x,y,oldX,oldY)) {
            ripplePoints.addPoint(x,y);
            shader.begin();
            shader.setUniform1fv("iGlobalTime", ripplePoints.getTime(), 0, MAX_COUNT);
            shader.setUniform1fv("iMouseX", ripplePoints.getX(), 0, MAX_COUNT);
            shader.setUniform1fv("iMouseY", ripplePoints.getY(),0,MAX_COUNT);
            shader.end();
            oldX = x;
            oldY = y;
        }

    }

    private static float distanceSqr(float x1, float y1, float x2, float y2){
        float dx = x2 - x1;
        float dy = y2 - y1;
        return (dx * dx + dy * dy);
    }
}
