package donnu.zolotarev.wallpaper.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import donnu.zolotarev.wallpaper.Assets.TextureAssets;

public class Background extends Actor  {


    private TextureRegion image;
    private  boolean isReady;
    private  boolean isNextReady;

    private float scale;
    private float offset;
    private float imageSize;

    private TextureRegion imageNext;
    private float scaleNext;
    private float imageSizeNext;

    private boolean hasNext;
    private float updateTime;
    private final static float UPDATE_TIME_MAX = 1f;

    public Background() {
        image = new TextureRegion();
        imageNext = new TextureRegion();
        isReady = false;
        isNextReady = false;
    }


    public void setScreenOffset(float offset){
        this.offset = offset;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (hasNext && isNextReady) {
            updateTime += delta;
            if (updateTime > UPDATE_TIME_MAX) {
                updateTime = 0;
                hasNext = isNextReady = false;
                image.setRegion(imageNext.getTexture());
                scale = scaleNext;
                imageSize = imageSizeNext;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (isReady) {

            if (hasNext && isNextReady) {
                float v =  (1.5f - updateTime / UPDATE_TIME_MAX);
                batch.setColor(1f,1f,1f,v <1.f?v:1f);
            }

            batch.draw(image, imageSize * offset, 0,getOriginX(),getOriginY()
                    ,image.getRegionWidth(),image.getRegionHeight(),scale,scale,0);

            if (hasNext && isNextReady) {
                batch.setColor(1f,1f,1f,updateTime / UPDATE_TIME_MAX);
                batch.draw(imageNext, imageSizeNext * offset, 0, getOriginX(), getOriginY()
                        , imageNext.getRegionWidth(), imageNext.getRegionHeight(), scaleNext,scaleNext, 0);
            }
        }
    }

    public void changeImage(){
        imageNext.setRegion(getNext());
        scaleNext = 1f*Gdx.graphics.getHeight()/imageNext.getRegionHeight();
        imageSizeNext = -imageNext.getRegionWidth()/2;
        isNextReady = true;
        hasNext = true;
    }

    public void updateImage() {

        image.setRegion(getNext());
        scale = 1f*Gdx.graphics.getHeight()/image.getRegionHeight();
        imageSize = -image.getRegionWidth()/2;
        isReady = true;
        hasNext = false;
    }

    private int index = 0;
    private int indexMax = 3;

    private Texture getNext(){

        index = (index +1 != indexMax)?index+1 :0;

        switch (index){
            case 0:
                return TextureAssets.getTextureAssets().getImage();
            case 1:
                return TextureAssets.getTextureAssets().getImage2();
            case 2:
                return TextureAssets.getTextureAssets().getImage3();
        }

        return null;
    }

}
