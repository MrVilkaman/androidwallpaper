package donnu.zolotarev.wallpaper.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import donnu.zolotarev.wallpaper.Assets.IImageLoader;

public class Background extends Actor  {


    private final IImageLoader imageLoader;
    private TextureRegion image;
    private  boolean isReady;
    private  boolean isNextReady;

    private float scale;
    private float offset;
    private float imageSize;

    private TextureRegion imageNext;
    private float scaleNext;
    private float imageSizeNext;

    private float updateTime;
    private final static float UPDATE_TIME_MAX = 1f;

    public Background(IImageLoader imageLoader) {
        image = new TextureRegion();
        imageNext = new TextureRegion();
        isReady = false;
        isNextReady = false;
        this.imageLoader = imageLoader;
        updateImage();
    }


    public void setScreenOffset(float offset){
        this.offset = offset;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if ( isNextReady) {
            updateTime += delta;
            if (updateTime > UPDATE_TIME_MAX) {
                updateTime = 0;
                isNextReady = false;
                image.setRegion(imageNext.getTexture());
                scale = scaleNext;
                imageSize = imageSizeNext;
                imageLoader.unloadLast();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (isReady) {
            if (isNextReady) {
                float v =  (1.5f - updateTime / UPDATE_TIME_MAX);
                batch.setColor(1f,1f,1f,v <1.f?v:1f);
            }

            batch.draw(image, imageSize * offset, 0,getOriginX(),getOriginY()
                    ,image.getRegionWidth(),image.getRegionHeight(),scale,scale,0);

            if (isNextReady) {
                batch.setColor(1f,1f,1f,updateTime / UPDATE_TIME_MAX);
                batch.draw(imageNext, imageSizeNext * offset, 0, getOriginX(), getOriginY()
                        , imageNext.getRegionWidth(), imageNext.getRegionHeight(), scaleNext,scaleNext, 0);
            }
        }
    }

    public void changeImage(){
        imageLoader.getNext(new IImageLoader.IImageLoaded() {
            @Override
            public void onCompleate(Texture texture) {
                imageNext.setRegion(texture);
                scaleNext = 1f*Gdx.graphics.getHeight()/imageNext.getRegionHeight();
                imageSizeNext = imageNext.getRegionWidth()*scaleNext - Gdx.graphics.getWidth();
                imageSizeNext *= -1;
                isNextReady = true;
            }
        });

    }

    public void updateImage() {
        imageLoader.getNext(new IImageLoader.IImageLoaded() {
            @Override
            public void onCompleate(Texture texture) {
                image.setRegion(texture);
                scale = 1f*Gdx.graphics.getHeight()/image.getRegionHeight();
                imageSize = image.getRegionWidth()*scale - Gdx.graphics.getWidth();
                imageSize *= -1;
                isReady = true;
                isNextReady = false;
            }
        });

    }
}
