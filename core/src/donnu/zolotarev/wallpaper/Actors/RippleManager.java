package donnu.zolotarev.wallpaper.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RippleManager extends Actor {

    private final ShaderProgram shader;
    private float time;
    private boolean hasShader = false;

    public RippleManager() {
        shader = new ShaderProgram(Gdx.files.internal("shaders/ripple.vsh"),Gdx.files.internal("shaders/ripple.fsh"));
        System.out.println(shader.isCompiled() ? "shader compaled, yay" : shader.getLog());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta;
        shader.begin();
        shader.setUniformf("iGlobalTime", time);
        shader.end();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!hasShader) {
            batch.setShader(shader);
            hasShader = true;
        }
    }

    public void setResolution(float width, float height){
        shader.begin();
        shader.setUniformf("u_resolution", width, height);
        shader.end();
    }

    public void click(float x, float y) {
        time = 0;
        shader.begin();
        shader.setUniformf("iGlobalTime", time);
        shader.setUniformf("iMouseX", x);
        shader.setUniformf("iMouseY", y);
        shader.end();
    }
}
