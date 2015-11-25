package com.zhuiji7.jigsaw.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zhuiji7.jigsaw.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by cww on 15-11-5.
 */
public class GameView extends View {
    private Context mycontext;
    private Random random = new Random();
    private int level = 3;//默认分3层
    private int padding = 3;
    private Resources mResources;
    private Bitmap mBitmap;
    private ArrayList<Patch> patches;
    private int viewH;//view的高度
    private int viewW;//view的宽度
    private int canvasH;//一格画布的高度
    private int canvasW;//一格画布的宽度
    private int bitmapH;//一片图片的高度
    private int bitmapW;//一片图片的宽度

    private SoundPool soundPool;//声音池
    private int s_move;//移动声音
    private int s_error;//不能移动声音
    private OnFinishListener listener;
    public GameView(Context context){
        this(context, null);
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mycontext = context;
        mResources = getResources();
        mBitmap = ((BitmapDrawable)mResources.getDrawable(R.drawable.demo)).getBitmap();
        bitmapW = mBitmap.getWidth()/level;
        bitmapH = mBitmap.getHeight()/level;
        initPatches();
        initSound();
    }

    //设置等级
    public void setLevel(int level){
        this.level = level;
        bitmapW = mBitmap.getWidth()/level;
        bitmapH = mBitmap.getHeight()/level;
        canvasH = viewH/level;
        canvasW = viewW/level;
        initPatches();
        invalidate();
    }

    //设置图片
    public void setmBitmap(Bitmap bitmap){
        this.mBitmap = bitmap;
        invalidate();
    }

    public void setOnFinishListener(OnFinishListener listener){
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAllPic(patches, canvas);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewW = w;
        viewH = h;
        canvasW = w/level;
        canvasH = h/level;
    }

    private void initSound(){
        //创建一个SoundPool对象，该对象可以容纳2个音频流
        soundPool=new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        s_move = soundPool.load(mycontext,R.raw.s_move,1);
        s_error = soundPool.load(mycontext,R.raw.s_error,1);
    }

    private void initPatches(){
        patches = new ArrayList<Patch>();
        Patch emptyPatch = null;
        for(int i = 0;i < level;i++){
            for(int j = 0;j < level;j++){
                Patch patch = new Patch();

                PicPoint bp = new PicPoint();
                bp.setX(i);
                bp.setY(j);

                PicPoint cp = new PicPoint();
                cp.setX(i);
                cp.setY(j);

                patch.setBitmapPicPoint(bp);
                patch.setCanvasPicPoint(cp);
                if(bp.getX() == (level - 1) && bp.getY() == (level - 1)){
                    patch.setEmpty(true);
                    emptyPatch = patch;
                }
                patches.add(patch);
            }
        }

        for(int i = 0;i < 50*level;i++){
            emptyPatch = exchange(emptyPatch);
        }

    }
    //让空的碎片随机跟上下左右中的某一块对调位置
    private Patch exchange(Patch emptyPatch){
        int randomtype = random.nextInt(4);
        PicPoint cp = new PicPoint();
        switch (randomtype){
            case 0: //
                if (emptyPatch.getCanvasPicPoint().getX() - 1 >= 0){
                    cp.setX(emptyPatch.getCanvasPicPoint().getX() - 1);
                    cp.setY(emptyPatch.getCanvasPicPoint().getY());
                }else {
                    cp.setX(emptyPatch.getCanvasPicPoint().getX() + 1);
                    cp.setY(emptyPatch.getCanvasPicPoint().getY());
                }
                break;
            case 1:
                if (emptyPatch.getCanvasPicPoint().getX() + 1 < level){
                    cp.setX(emptyPatch.getCanvasPicPoint().getX() + 1);
                    cp.setY(emptyPatch.getCanvasPicPoint().getY());
                }else {
                    cp.setX(emptyPatch.getCanvasPicPoint().getX() - 1);
                    cp.setY(emptyPatch.getCanvasPicPoint().getY());
                }
                break;
            case 2:
                if (emptyPatch.getCanvasPicPoint().getY() - 1 >= 0){
                    cp.setX(emptyPatch.getCanvasPicPoint().getX());
                    cp.setY(emptyPatch.getCanvasPicPoint().getY() - 1);
                }else {
                    cp.setX(emptyPatch.getCanvasPicPoint().getX());
                    cp.setY(emptyPatch.getCanvasPicPoint().getY() + 1);
                }
                break;
            case 3:
                if (emptyPatch.getCanvasPicPoint().getY() + 1 < level){
                    cp.setX(emptyPatch.getCanvasPicPoint().getX());
                    cp.setY(emptyPatch.getCanvasPicPoint().getY() + 1);
                }else {
                    cp.setX(emptyPatch.getCanvasPicPoint().getX());
                    cp.setY(emptyPatch.getCanvasPicPoint().getY() - 1);
                }
                break;
        }

        Patch patch = findPatch(cp);
        return change(patch,emptyPatch);

    }


    private void drawAllPic(ArrayList<Patch> patches,Canvas canvas){
        int size = patches.size();
        for(int i = 0;i < size;i++){
            drawPic(canvas,patches.get(i));
        }
    }

    //在画布的指定区域画图片上的指定区域
    private void drawPic(Canvas canvas,Patch patch){
        if(patch.isEmpty()){
            return;
        }
        Rect rb = new Rect(patch.getBitmapPicPoint().getX()*bitmapW + padding, patch.getBitmapPicPoint().getY()*bitmapH + padding,
                (patch.getBitmapPicPoint().getX()+1)*bitmapW - padding, (patch.getBitmapPicPoint().getY()+1)*bitmapH - padding);
        Rect rc = new Rect(patch.getCanvasPicPoint().getX()*canvasW + padding,patch.getCanvasPicPoint().getY()*canvasH + padding,
                (patch.getCanvasPicPoint().getX()+1)*canvasW - padding, (patch.getCanvasPicPoint().getY()+1)*canvasH - padding);

        canvas.drawBitmap(mBitmap, rb, rc, null);
    }

    //点击事件
    private void click(int x, int y){
        PicPoint clickPicPoint = new PicPoint();
        clickPicPoint.setX(x / canvasW);
        clickPicPoint.setY(y / canvasH);
        checkAndMove(clickPicPoint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        click((int)event.getX(),(int)event.getY());
        return super.onTouchEvent(event);
    }

    //检查所点击的碎片能否移动，如果能移动就移动
    private void checkAndMove(PicPoint cp){
        Patch clickPatch =  findPatch(cp);
        Patch emptyPatch = findEmptyPatch();

        if(isNeighbor(clickPatch,emptyPatch)){
            soundPool.play(s_move,1,1,0,0,1);
            change(clickPatch, emptyPatch);
            invalidate();
            if(isFinish()){
                listener.onFinish();
            }
        }else{
            soundPool.play(s_error,1,1,0,0,1);
        }
    }

    //是否临近的两个图片碎片
    private boolean isNeighbor(Patch fPatch,Patch sPatch){
        PicPoint nearPicPoint = new PicPoint();
        nearPicPoint.setX(fPatch.getCanvasPicPoint().getX()+1);
        nearPicPoint.setY(fPatch.getCanvasPicPoint().getY());
        if(sPatch.getCanvasPicPoint().equals(nearPicPoint)){
            return true;
        }
        nearPicPoint.setX(fPatch.getCanvasPicPoint().getX()-1);
        nearPicPoint.setY(fPatch.getCanvasPicPoint().getY());
        if(sPatch.getCanvasPicPoint().equals(nearPicPoint)){
            return true;
        }
        nearPicPoint.setX(fPatch.getCanvasPicPoint().getX());
        nearPicPoint.setY(fPatch.getCanvasPicPoint().getY()+1);
        if(sPatch.getCanvasPicPoint().equals(nearPicPoint)){
            return true;
        }
        nearPicPoint.setX(fPatch.getCanvasPicPoint().getX());
        nearPicPoint.setY(fPatch.getCanvasPicPoint().getY()-1);
        if(sPatch.getCanvasPicPoint().equals(nearPicPoint)){
            return true;
        }
        return false;
    }

    //根据画布上的位置查找相应的图片碎片对象
    private Patch findPatch(PicPoint cp){
        Patch p = null;
        int size = patches.size();
        for(int i = 0;i < size; i++){
            Patch patch = patches.get(i);
            if(patch.getCanvasPicPoint().getX() == cp.getX() && patch.getCanvasPicPoint().getY() == cp.getY()){
                p = patch;
                break;
            }
        }
        return p;
    }
    //查找空图片碎片
    private Patch findEmptyPatch(){
        Patch p = null;
        int size = patches.size();
        for(int i = 0;i < size; i++){
            Patch patch = patches.get(i);
            if(patch.isEmpty()){
                p = patch;
                break;
            }
        }
        return p;
    }


    //一个图片碎片跟空的图片碎片对调
    private Patch change(Patch pctch,Patch patchEmpty){
        pctch.setEmpty(true);
        patchEmpty.setEmpty(false);
        patchEmpty.setBitmapPicPoint(pctch.getBitmapPicPoint());
        return pctch;
    }
    //检查是否拼图完成
    private boolean isFinish(){
        boolean isfinish = true;
        int size = patches.size();
        for(int i = 0;i < size;i++){
            Patch patch = patches.get(i);
            if(patch.getCanvasPicPoint().getX() != patch.getBitmapPicPoint().getX() || patch.getBitmapPicPoint().getY() != patch.getCanvasPicPoint().getY()){
                if(patch.getCanvasPicPoint().getX() == level - 1 && patch.getCanvasPicPoint().getY() == level - 1 && patch.isEmpty()){

                }else{
                    isfinish = false;
                    break;
                }
            }
        }
        return isfinish;
    }

    public interface OnFinishListener{
       public void onFinish();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        soundPool.release();
    }

}
