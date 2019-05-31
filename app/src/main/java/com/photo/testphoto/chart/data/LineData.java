package com.photo.testphoto.chart.data;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.photo.testphoto.chart.interfaces.iData.ILineData;
import com.photo.testphoto.chart.interfaces.iData.IPointData;

/**
 * Created by Idtk on 2016/6/7.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 折线图数据类
 */
public class LineData extends BarLineCurveData implements ILineData,IPointData {

    public float NOSETING=-1;
    private LineAnimated mLineAnimated = LineAnimated.SYNC;
    private Drawable drawable;
    private float outRadius = NOSETING;
    private float inRadius = NOSETING;
    private Paint outPaint;
    private Paint inPaint;
    private PointShape pointShape = PointShape.CIRCLE;

    public void setAnimatedMod(LineAnimated LineAnimated) {
        mLineAnimated = LineAnimated;
    }

    public LineAnimated getLineAnimated() {
        return mLineAnimated;
    }

    public void setOutRadius(float outRadius) {
        this.outRadius = outRadius;
    }

    public float getOutRadius() {
        return outRadius;
    }

    public void setInRadius(float inRadius) {
        this.inRadius = inRadius;
    }

    public float getInRadius() {
        return inRadius;
    }

    public void setOutPaint(Paint outPaint) {
        this.outPaint = outPaint;
    }

    public Paint getOutPaint() {
        return outPaint;
    }

    public void setInPaint(Paint inPaint) {
        this.inPaint = inPaint;
    }

    public Paint getInPaint() {
        return inPaint;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public void setPointShape(PointShape pointShape) {
        this.pointShape = pointShape;
    }

    @Override
    public PointShape getPointShape() {
        return pointShape;
    }
}
