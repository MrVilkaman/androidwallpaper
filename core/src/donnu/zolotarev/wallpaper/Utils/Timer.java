package donnu.zolotarev.wallpaper.Utils;

public class Timer {

    private final Listner listner;

    private final float duraction;
    private float time;
    private boolean isComplite = false;
    private boolean isStart = false;

    public interface Listner{
        public void complite();
    }

    public Timer(Listner listner,float duraction) {
        this.listner = listner;
        this.time = this.duraction = duraction;
    }

    public void update(float delta){
        if (isStart && !isComplite) {
            if (time <0){
               isComplite = true;
                listner.complite();
            }
            time -= delta;
        }
    }

    public void reset(){
        isStart = false;
        isComplite = false;
        time = duraction;
    }

    public void state(boolean b){
        isStart = b;
    }

    public boolean isStart() {
        return isStart;
    }
}
