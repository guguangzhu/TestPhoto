package com.photo.testphoto.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.photo.testphoto.R;
import com.photo.testphoto.bean.ChartDataBean;
import com.photo.testphoto.utils.DateUtils;
import com.photo.testphoto.utils.DeviceUtils;
import com.photo.testphoto.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyChartView extends View {
    private List<ChartDataBean> list=new ArrayList<>();
    private Paint textPaint;
    private Paint colorLinePaint;
    private Paint whitePaint;

    private int height;

    private int width;

    private int offsetX; //x坐标偏移

    private int bottomTextHeight=50;

    private int topTextHeight=50;

    private float proportion=0;//比例，用于显示动画


    public MyChartView(Context context) {
        super(context);
        init();
    }

    public MyChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        offsetX=DeviceUtils.dip2px(getContext(),20);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(getResources().getColor(R.color.gray));
        textPaint.setTextSize(DeviceUtils.dip2px(getContext(),11));

        colorLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        colorLinePaint.setColor(getResources().getColor(R.color.orange));
        colorLinePaint.setStyle(Paint.Style.STROKE);
        colorLinePaint.setStrokeWidth(3);


        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w-getPaddingLeft()-getPaddingRight();
        height=h-getPaddingTop()-getPaddingBottom();
    }

    public void setDate(List<ChartDataBean> list){
        this.list.clear();
        this.list.addAll(list);
        handleData();
        startAnimator();
    }

    private void handleData(){
        float offset=DeviceUtils.dip2px(getContext(),10);
        float startX=getPaddingLeft()+offset;  //左边距
        float itemWidth=getWidth()/(list.size());
        float maxPrice=0,minPrice=list.get(0).getPrice();
        for (int i = 0; i < list.size(); i++) {
            ChartDataBean dataBean=list.get(i);
            dataBean.setItemX(startX);
            startX+=itemWidth;
            if(dataBean.getPrice()>maxPrice)
                maxPrice=dataBean.getPrice();
            if(dataBean.getPrice()<minPrice)
                minPrice=dataBean.getPrice();
        }

        float maxHeight=height-topTextHeight-bottomTextHeight;
        float minHeight=maxHeight/2;

        float itemHeight=0;

        for(ChartDataBean bean:list){
            float currentPrice=bean.getPrice();
            itemHeight= minHeight+minHeight*((currentPrice-minPrice)/(maxPrice-minPrice));
            bean.setItemHeight(itemHeight);
        }


    }


    private void startAnimator(){
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                 proportion= (float) animation.getAnimatedValue();
                 postInvalidate();
            }
        });
        animator.setDuration(500);
        animator.start();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //底部横线
        canvas.drawLine(getPaddingStart(),height-bottomTextHeight,getWidth()-getPaddingRight(),height-(bottomTextHeight+1),textPaint);
        drawContent(canvas);

    }



    private void drawContent(Canvas canvas){

        Path path = new Path();
        for (int i = 0; i < list.size(); i++) {
            ChartDataBean dataBean=list.get(i);
            if(i==0){
                path.moveTo(dataBean.getItemX()+offsetX,height-bottomTextHeight-dataBean.getItemHeight()+dataBean.getItemHeight()*proportion);
            }else {
                path.lineTo(dataBean.getItemX()+offsetX,height-bottomTextHeight-dataBean.getItemHeight()+dataBean.getItemHeight()*proportion);
            }


            //底部日期
            String dateText=DateUtils.formatTimeToStringYearMonthTwo(new Date(dataBean.getDate()));
            canvas.drawText(dateText, dataBean.getItemX(), height, textPaint);

            //竖线
            canvas.drawLine(dataBean.getItemX()+offsetX,height-bottomTextHeight,dataBean.getItemX()+offsetX+1,height-bottomTextHeight-dataBean.getItemHeight()+dataBean.getItemHeight()*proportion,textPaint);

            //底部圆点
            canvas.drawCircle( dataBean.getItemX()+offsetX, height-bottomTextHeight-3,4, colorLinePaint);

            //上部圆点
            canvas.drawCircle( dataBean.getItemX()+offsetX, height-bottomTextHeight-dataBean.getItemHeight()+dataBean.getItemHeight()*proportion,7, colorLinePaint);


            String priceText=StringUtil.getFormatDouble((double)dataBean.getPrice()/10000)+"万";
            canvas.drawText(priceText, dataBean.getItemX(), height-bottomTextHeight-dataBean.getItemHeight()-15+dataBean.getItemHeight()*proportion, textPaint);

        }
        canvas.drawPath(path, colorLinePaint);

        for (int i = 0; i < list.size(); i++) {
            ChartDataBean dataBean=list.get(i);
            //上部圆点填充白色
            canvas.drawCircle( dataBean.getItemX()+offsetX, height-bottomTextHeight-dataBean.getItemHeight()+dataBean.getItemHeight()*proportion,4, whitePaint);
        }
    }














}
