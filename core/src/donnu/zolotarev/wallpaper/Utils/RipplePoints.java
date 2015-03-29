package donnu.zolotarev.wallpaper.Utils;

public class RipplePoints {

    private final float MAX_TIME;
    private final int MAX_COUNT;

    private float[] mouseX;
    private float[] mouseY;
    private float[] time;
    private boolean[] flag;

    private int index = 0;

    public RipplePoints(int i,float maxTime) {
        MAX_COUNT = i;
        MAX_TIME = maxTime;
        mouseX = new float[MAX_COUNT];
        mouseY = new float[MAX_COUNT];
        time = new float[MAX_COUNT];
        flag = new boolean[MAX_COUNT];
    }

    public void addTime(float d){
        for (int i = 0; i < MAX_COUNT; i++) {
            if(flag[i]){
                time[i] += d;
                if ( MAX_TIME < time[i]){
                    time[i] = -1f;
                    flag[i] = false;
                }
            }
        }
    }

    public void addPoint(float x, float y){
        mouseX[index] = x;
        mouseY[index] = y;
        time[index] = 0;
        flag[index] = true;
        index = (index +1 != MAX_COUNT)?index +1:0;
    }


    public float[] getX(){
        return mouseX;
    }

    public float[] getY(){
        return mouseY;
    }

    public float[] getTime(){
        return time;
    }


}