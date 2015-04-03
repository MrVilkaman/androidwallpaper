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

    private float scale;
    private float offset;
    private float imageSize;

    public Background(IImageLoader imageLoader) {
        image = new TextureRegion();
        isReady = false;
        this.imageLoader = imageLoader;
        updateImage();
    }


    public void setScreenOffset(float offset){
        this.offset = offset;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isReady) {
            batch.draw(image, imageSize * offset, 0,getOriginX(),getOriginY()
                    ,image.getRegionWidth(),image.getRegionHeight(),scale,scale,0);
        }
    }

    public void changeImage(){
        imageLoader.getNext(new IImageLoader.IImageLoaded() {
            @Override
            public void onCompleate(Texture texture) {
                imageLoader.unloadLast();
                image.setRegion(texture);
                scale = 1f*Gdx.graphics.getHeight()/image.getRegionHeight();
                imageSize = image.getRegionWidth()*scale - Gdx.graphics.getWidth();
                imageSize *= -1;
                isReady = true;
            }
        });

    }

    public void updateImage() {
        changeImage();
    }
}
