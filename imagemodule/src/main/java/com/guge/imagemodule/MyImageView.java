package com.guge.imagemodule;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

public class MyImageView extends android.support.v7.widget.AppCompatImageView{

    private static final String TAG = "MyImageView";

    /**
     * 初始化状态常量
     */
    public static final int STATUS_INIT = 1;

    /**
     * 图片放大状态常量
     */
    public static final int STATUS_ZOOM_OUT = 2;

    /**
     * 图片缩小状态常量
     */
    public static final int STATUS_ZOOM_IN = 3;
    /**
     * 图片拖动状态常量
     */
    public static final int STATUS_MOVE = 4;

    /**
     * 记录当前操作的状态，可选值为STATUS_INIT、STATUS_ZOOM_OUT、STATUS_ZOOM_IN和STATUS_MOVE
     */
    private int currentStatus;

    private float firstPointX,firstPointY;
    private float secondPointX,secondPointY;
    private float initialDistance; //两指间的初始距离
    private float currentDistance; //两指间的当前距离
    private float zoomRatio; //缩放比

    private VelocityTracker mVelocityTracker;//生命变量

    private int mMaxVelocity;

    private Bitmap bitmap;
    private int drawableWidth,drawableHeight;
    private float currentWidth,currentHeight;
    private float height,width;

    private float translateX,translateY;


    GestureDetector gestureDetector;



    /**
     * 记录两指同时放在屏幕上时，中心点的横坐标值
     */
    private float centerPointX;

    /**
     * 记录两指同时放在屏幕上时，中心点的纵坐标值
     */
    private float centerPointY;



    /**
     * 记录手指在横坐标方向上的移动距离
     */
    private float movedDistanceX;

    /**
     * 记录手指在纵坐标方向上的移动距离
     */
    private float movedDistanceY;

    /**
     * 记录图片在矩阵上的横向偏移值
     */
    private float totalTranslateX;

    /**
     * 记录图片在矩阵上的纵向偏移值
     */
    private float totalTranslateY;
    /**
     * 记录图片在矩阵上的初始横向偏移值
     */
    private float initialTranslateX;
    /**
     * 记录图片在矩阵上的初始纵向偏移值
     */
    private float initialTranslateY;


    /**
     * 记录图片在矩阵上的总缩放比例
     */
    private float totalRatio;


    /**
     * 记录图片初始化时的缩放比例
     */
    private float initRatio;


    private float lastAnimX;

    private float lastAnimY;



    private Matrix matrix;


    private int index;

    public MyImageView(Context context,int index){
        this(context);
        this.index=index;
    }

    public MyImageView(Context context) {
        super(context);
        initView();
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }



    private void initView(){
        currentStatus=STATUS_INIT;
        mMaxVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();

        setBackgroundColor(Color.BLACK);

        gestureDetector=new GestureDetector(getContext(),gestureListener);

        matrix=new Matrix();
        Drawable drawable = getDrawable();

        //空值判断，必要步骤，避免由于没有设置src导致的异常错误
        if (drawable == null) {
            return;
        }

        if (!(drawable instanceof BitmapDrawable)) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();

        if (null == b) {
            return;
        }



    }



    public void resetImage(){
        currentStatus=STATUS_INIT;
        invalidate();
    }





    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        if (!(drawable instanceof GlideBitmapDrawable)) {
            return;
        }

        height=getHeight();
        width=getWidth();

        bitmap = ((GlideBitmapDrawable) drawable).getBitmap();
        currentStatus=STATUS_INIT;
        super.setImageDrawable(drawable);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (bitmap != null) {
//            canvas.drawBitmap(bitmap, width/2-drawableWidth/2, height/2-drawableHeight/2, null);

            switch (currentStatus) {
                case STATUS_ZOOM_OUT:
                case STATUS_ZOOM_IN:
                    zoom(canvas);
                    break;
                case STATUS_MOVE:
                    move(canvas);
                    break;
                case STATUS_INIT:
                    initBitmap(canvas);
                    break;
                default:
                    canvas.drawBitmap(bitmap, matrix, null);
                    break;

            }
        }
    }

    private void move(Canvas canvas){
        matrix.reset();
        matrix.postScale(totalRatio, totalRatio);
        currentWidth=drawableWidth*totalRatio;
        currentHeight=drawableHeight*totalRatio;



        if(totalRatio==initRatio&&(totalTranslateY>initialTranslateY||movedDistanceX>0)){  //已经是最小缩放，判断向下滑动关闭

            totalTranslateY+=movedDistanceY;
            calculateAlpha();
        }else {
            if(currentWidth>width){
                totalTranslateX+=movedDistanceX;
                if(totalTranslateX>0)
                    totalTranslateX=0;

                if(totalTranslateX<0&&Math.abs(totalTranslateX)>=currentWidth-width)
                    totalTranslateX=-(currentWidth-width);
            }

            if(currentHeight>height){
                totalTranslateY+=movedDistanceY;

                if(totalTranslateY>0)
                    totalTranslateY=0;

                if(totalTranslateY<0&&Math.abs(totalTranslateY)>=currentHeight-height)
                    totalTranslateY=-(currentHeight-height);
            }
        }


//        LogUtils.e("movedDistanceX----"+movedDistanceX+"---movedDistanceY---"+movedDistanceY);

        matrix.postTranslate(totalTranslateX,totalTranslateY);
        canvas.drawBitmap(bitmap, matrix, null);
    }


    private void zoom(Canvas canvas){
        matrix.reset();
        // 将图片按总缩放比例进行缩放
        matrix.postScale(totalRatio, totalRatio);
        float offsetWidth=drawableWidth*zoomRatio;
        float offsetHeight=drawableHeight*zoomRatio;
        currentWidth=drawableWidth*totalRatio;
        currentHeight=drawableHeight*totalRatio;
//        centerPointX=(width-currentWidth)/2F;
//        centerPointY=(height-currentHeight)/2F;

        float translateX ;
        float translateY ;

        if (currentWidth < width) {
            translateX = (width - currentWidth) / 2f;
        } else {
            translateX = totalTranslateX * zoomRatio + centerPointX * (1 - zoomRatio);
            // 进行边界检查，保证图片缩放后在水平方向上不会偏移出屏幕
            if (translateX > 0) {
                translateX = 0;
            } else if (width - translateX > currentWidth) {
                translateX = width - currentWidth;
            }
        }

        if (currentHeight < height) {
            translateY = (height - currentHeight) / 2f;
        } else {
            translateY = totalTranslateY * zoomRatio + centerPointY * (1 - zoomRatio);
            // 进行边界检查，保证图片缩放后在水平方向上不会偏移出屏幕
            if (translateY > 0) {
                translateY = 0;
            } else if (height - translateY > currentHeight) {
                translateY = height - currentHeight;
            }
        }

        Log.e(TAG,"translateX----"+translateX+"---translateY---"+translateY);



        totalTranslateX=translateX;
        totalTranslateY=translateY;

//        totalTranslateX=centerPointX+(totalTranslateX-centerPointX)*totalRatio;
//        totalTranslateY=centerPointY+(totalTranslateY-centerPointY)*totalRatio;
        matrix.postTranslate(translateX,translateY);
        canvas.drawBitmap(bitmap, matrix, null);

    }


    private void initBitmap(Canvas canvas){
        matrix.reset();
        drawableWidth=bitmap.getWidth();
        drawableHeight=bitmap.getHeight();
        float ratio=1F;
        if(drawableWidth>width||drawableHeight>height){  //当图片大于屏幕 压缩比例显示

            if(drawableWidth-width>drawableHeight-height){ //当图片的宽度
                ratio=(drawableWidth/(width*1F));
                matrix.postScale(ratio,ratio);
                totalTranslateY=(height-(drawableHeight*ratio))/2f;

            }else {
                ratio=(drawableHeight/(height*1F));
                matrix.postScale(ratio,ratio);
                totalTranslateX=(width-(drawableWidth*ratio))/2F;

            }

        }else {  //图片小于等于屏幕，居中显示
            totalTranslateX=(width-drawableWidth)/2F;
            totalTranslateY=(height-drawableHeight)/2F;
        }
        initialTranslateY=totalTranslateY;
        initialTranslateX=totalTranslateX;
        totalRatio=initRatio=ratio;
        matrix.postTranslate(totalTranslateX,totalTranslateY);
        canvas.drawBitmap(bitmap, matrix, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        acquireVelocityTracker(event);

        int index = event.getActionIndex();

        ViewParent parent = getParent();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG,"第1个手指按下");
                firstPointX=event.getX();
                firstPointY=event.getY();
                if(parent!=null)
                    parent.requestDisallowInterceptTouchEvent(true);
//                Log.e(TAG, "firstPointX="+firstPointX+", firstPointY="+firstPointY);
                break;
            case MotionEvent.ACTION_CANCEL:
                movedDistanceX=0;
                movedDistanceY=0;
                if (mVelocityTracker != null) {
                    // This may have been cleared when we called out to the
                    // application above.
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_UP:




                // 手指离开屏幕时将临时值还原
                firstPointX = -1;
                firstPointY = -1;
                Log.e(TAG,"最后1个手指抬起");

                final VelocityTracker velocityTracker = mVelocityTracker;
                //求伪瞬时速度  如果速度小于mMaxVelocity，正常显示。如果大于mMaxVelocity，则显示mMaxVelocity
                velocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                //1000表示像素/秒，1表示像素/毫秒。因为mMaxVelocity是用像素/秒做单位，所以此时用1000

                final float velocityX = velocityTracker.getXVelocity();
                final float velocityY = velocityTracker.getYVelocity();

                handleVelocity(velocityX,velocityY);

                Log.e(TAG,"onTouchEvent  velocityX---- "+velocityX+" ;velocityY---- "+velocityY);


                if (mVelocityTracker != null) {
                    // This may have been cleared when we called out to the
                    // application above.
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(event.getPointerCount()==2){
                    centerPointBetweenFingers(event);
                    firstPointX=event.getX(0); //第一个手指位置
                    firstPointY=event.getY(0);
                    secondPointX=event.getX(1);  //第二个手指位置
                    secondPointY=event.getY(1);
                    currentDistance=distanceBetweenFingers(event);

                    if (currentDistance > initialDistance) {
                        currentStatus = STATUS_ZOOM_OUT;
                    } else {
                        currentStatus = STATUS_ZOOM_IN;
                    }


                    zoomRatio=currentDistance/initialDistance;
                    totalRatio=zoomRatio*totalRatio;
                    if(totalRatio>4){
                        totalRatio=4;
                    }

                    if(totalRatio<initRatio)
                        totalRatio=initRatio;
                    invalidate();
                    initialDistance=currentDistance;
                }else if(event.getPointerCount()==1){
                    // 只有单指按在屏幕上移动时，为拖动状态

                    float xMove = event.getX();
                    float yMove = event.getY();
                    if(firstPointX==-1&&firstPointY==-1){
                        firstPointX=xMove;
                        firstPointY=yMove;
                    }
                    currentStatus = STATUS_MOVE;
                    movedDistanceX = xMove - firstPointX;
                    movedDistanceY = yMove - firstPointY;
                    if(totalRatio==initRatio&&initialTranslateY>=totalTranslateY&&Math.abs(movedDistanceY)>Math.abs(movedDistanceX)&&movedDistanceY<0){  //禁止向上滑
                        Log.e(TAG,"阻止事件  movedDistanceY--->"+movedDistanceY);
                        return true;
                    }

                    if(Math.abs(movedDistanceX)>Math.abs(movedDistanceY)){
                        if(totalRatio==initRatio){
                            if(parent!=null){
                                parent.requestDisallowInterceptTouchEvent(false);
                                movedDistanceY=0;
                            }


                        }else {

                            if(totalTranslateX>=0&&movedDistanceX>0){
                                if(parent!=null){
                                    parent.requestDisallowInterceptTouchEvent(false);
                                    movedDistanceY=0;
                                }

                            }

                            if(totalTranslateX<0&&Math.abs(totalTranslateX)>=currentWidth-width&&movedDistanceX<0){
                                if(parent!=null){
                                    parent.requestDisallowInterceptTouchEvent(false);
                                    movedDistanceY=0;
                                }

                            }


                        }
                    }




                    // 调用onDraw()方法绘制图片
                    invalidate();
                    firstPointX = xMove;
                    firstPointY = yMove;
                }

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if(event.getPointerCount()==2){

                    initialDistance=distanceBetweenFingers(event);
//                    Log.e(TAG, "secondPointX="+secondPointX+", secondPointY="+secondPointY);
                }

                Log.e(TAG,"第"+(index+1)+"个手指按下");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerCount() == 2) {
                    // 手指离开屏幕时将临时值还原
                    firstPointX = -1;
                    firstPointY = -1;
                }


                Log.e(TAG,"第"+(index+1)+"个手指抬起");
                break;
        }

        gestureDetector.onTouchEvent(event);
        return true;
    }


    private void handleVelocity(float velocityX,float velocityY){
        if(totalRatio==initRatio){
            if (velocityY>3000){   //滑动关闭
                moveAnim(0,height,100,true);
            }else {
                moveAnim(0,initialTranslateY-totalTranslateY,100,false);
            }
            return;
        }


        if(Math.abs(velocityX)>Math.abs(velocityY)) {  //横向滚动偏多
            if (Math.abs(velocityX) > Math.abs(velocityY) * 3)  //横向滚动
                moveAnim(velocityX/6, 0);
            else moveAnim(velocityX/6,velocityY/6);
        }else { //垂直滚动偏多
            if (Math.abs(velocityY) > Math.abs(velocityX) * 3)  //垂直滚动
                moveAnim(0,velocityY/6);
            else moveAnim(velocityX/6,velocityY/6);
        }
    }




    //获取VelocityTracker同时把event增加进去
    private void acquireVelocityTracker(final MotionEvent event) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    //滑动关闭设置背景
    private void calculateAlpha(){
        //0-255
        int alpha= (int) (155*((height-(totalTranslateY+(drawableHeight/2)))/(height/2)));
//        LogUtils.e("alpha---->"+alpha);
        alpha+=100;
        if(alpha>255)
            alpha=255;
        if(alpha<100)
            alpha=100;
        getBackground().setAlpha(alpha);
    }



    /**
     * 计算两个手指之间的距离。
     *
     * @param event
     * @return 两个手指之间的距离
     */
    private float distanceBetweenFingers(MotionEvent event) {
        float disX = Math.abs(event.getX(0) - event.getX(1));
        float disY = Math.abs(event.getY(0) - event.getY(1));
        return (float) Math.sqrt(disX * disX + disY * disY);
    }



    /**
     * 计算两个手指之间中心点的坐标。
     *
     * @param event
     */
    private void centerPointBetweenFingers(MotionEvent event) {
        float xPoint0 = event.getX(0);
        float yPoint0 = event.getY(0);
        float xPoint1 = event.getX(1);
        float yPoint1 = event.getY(1);
        centerPointX = (xPoint0 + xPoint1) / 2;
        centerPointY = (yPoint0 + yPoint1) / 2;
    }



    GestureDetector.SimpleOnGestureListener gestureListener=new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e(TAG,"onDoubleTap  ");
            if(initRatio==totalRatio){
                scaleAnim(initRatio,4);
            }else {
                scaleAnim(totalRatio,initRatio);

            }
            return super.onDoubleTap(e);
        }

//        @Override
//        public boolean onSingleTapConfirmed(MotionEvent e) {
//            Log.e(TAG,"onSingleTapConfirmed  ");
//            if(initRatio==totalRatio){
//                ((PreviewActivity)getContext()).callFinish();
//            }
//            return super.onSingleTapConfirmed(e);
//        }


        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e(TAG,"onSingleTapUp  ");
            return super.onSingleTapUp(e);
        }
    };



    private void scaleAnim(float startRatio, float endRatio){
        centerPointX=width/2F;
        centerPointY=height/2F;
        ValueAnimator animator = ValueAnimator.ofFloat(startRatio, endRatio);
        animator.setDuration(300);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curValue=(float)animation.getAnimatedValue();
                if(curValue>totalRatio)
                    currentStatus = STATUS_ZOOM_OUT;
                else {
                    currentStatus = STATUS_ZOOM_IN;
                }
                zoomRatio=curValue/totalRatio;
                totalRatio = curValue;
                if(totalRatio==4||totalRatio==1)
                    return;
                postInvalidate();
//                LogUtils.e("totalRatio----"+totalRatio+"---zoomRatio---"+zoomRatio);
            }
        });
        animator.start();
    }

    private void moveAnim(float startX,float startY){
        moveAnim(startX,startY,300,false);
    }

    /**
     * 使用属性动画移动图像
     * @param startX  横向移动距离
     * @param startY  纵向移动距离
     * @param finishAfterEnd  纵向移动完成是否要关闭页面
     */
    private void moveAnim(float startX, float startY,int duration, final boolean finishAfterEnd){
        currentStatus = STATUS_MOVE;
        lastAnimX=-1;
        lastAnimY=-1;
        movedDistanceX=0;
        movedDistanceY=0;
        if(startX!=0){
            ValueAnimator animatorX = ValueAnimator.ofFloat(startX, 0);
            animatorX.setDuration(duration);
            animatorX.setInterpolator(new DecelerateInterpolator());

            animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float curValue=(float)animation.getAnimatedValue();
                    if(lastAnimX==-1)
                        lastAnimX=curValue;
                    movedDistanceX=lastAnimX-curValue;
                    postInvalidate();
                    lastAnimX=curValue;
//                LogUtils.e("totalRatio----"+totalRatio+"---zoomRatio---"+zoomRatio);
                }
            });
            animatorX.start();
        }



        if(startY!=0){
            ValueAnimator animatorY = ValueAnimator.ofFloat(startY, 0);
            animatorY.setDuration(duration);
            animatorY.setInterpolator(new DecelerateInterpolator());
            animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float curValue=(float)animation.getAnimatedValue();

                    if(lastAnimY==-1)
                        lastAnimY=curValue;
                    movedDistanceY=lastAnimY-curValue;

//                    LogUtils.e("curValue----》"+curValue+"movedDistanceY----> "+ movedDistanceY);
                    postInvalidate();
                    lastAnimY=curValue;
//                LogUtils.e("totalRatio----"+totalRatio+"---zoomRatio---"+zoomRatio);
                }

            });
            animatorY.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(finishAfterEnd){
                        Log.e(TAG,"callFinish()");
                        ((PreviewActivity)getContext()).callFinish();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animatorY.start();
        }

    }
}
