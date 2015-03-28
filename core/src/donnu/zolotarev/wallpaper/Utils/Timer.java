package donnu.zolotarev.wallpaper.Utils;

public class Timer {

    private final Listner listner;

    private float duraction;
    private float time;
    private boolean isComplite = false;
    private boolean isStart = false;
    private boolean loop;

    public void start() {
        isStart = true;
    }


    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void stop() {
        isStart = false;
    }

    public float getLostTime() {
        return time;
    }

    public float getTime() {
        return duraction-time;
    }

    public void setDuraction(float time) {
        this.time = this.duraction = time;
    }

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
                if (loop) {
                    isComplite = false;
                    time = duraction;
                }
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

    public boolean isComplite() {
        return isComplite;
    }
}
