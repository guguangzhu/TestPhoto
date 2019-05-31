package com.photo.testphoto;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.photo.testphoto.bean.ChartDataBean;
import com.photo.testphoto.chart.chart.CurveChart;
import com.photo.testphoto.chart.data.CurveData;
import com.photo.testphoto.chart.data.PointShape;
import com.photo.testphoto.chart.interfaces.iData.ICurveData;
import com.photo.testphoto.view.MyChartView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Line2Activity extends AppCompatActivity {
    private ArrayList<ICurveData> mDataList = new ArrayList<>();
    private CurveData mCurveData = new CurveData();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();
    private MyChartView chartView;
    private List<ChartDataBean> dataBeans=new ArrayList<>();

    private Handler handler=new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_2);
        chartView=findViewById(R.id.chart_view);
        CurveChart curveChart = findViewById(R.id.chart2);
        initData();
        curveChart.setDataList(mDataList);


        getData();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chartView.setDate(dataBeans);
            }
        },100);

    }


    private void getData(){
        for (int i = 0; i < 5; i++) {
            ChartDataBean chartDataBean=new ChartDataBean();
            chartDataBean.setDate(System.currentTimeMillis());
            if(i==0)
                chartDataBean.setPrice(100215);
            else if(i==2)
                chartDataBean.setPrice(150000);
            else
                chartDataBean.setPrice(125000);
            dataBeans.add(chartDataBean);
        }
    }

    private void initData() {
        for (int i = 0; i < 8; i++) {
            mPointArrayList.add(new PointF(points[i][0], points[i][1]));
        }
        mCurveData.setValue(mPointArrayList);
        mCurveData.setColor(Color.RED);

        Drawable drawable =getDrawable(R.drawable.bg_gradient_orange);
        mCurveData.setDrawable(drawable);

        mCurveData.setPointShape(PointShape.SOLIDROUND);
        mCurveData.setPaintWidth(pxTodp(3));
        mCurveData.setTextSize(pxTodp(10));
        mDataList.add(mCurveData);
    }


    protected float[][] points = new float[][]{{1,20}, {2,19}, {3,18}, {4,17}, {5,16},{6,15}, {7,14}, {8,13}, {9,12}, {10,11}};
    protected float[][] points2 = new float[][]{{1,52}, {2,13}, {3,51}, {4,20}, {5,19},{6,20}, {7,54}, {8,7}, {9,19}, {10,41}};
    protected int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    protected float pxTodp(float value){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float valueDP= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,metrics);
        return valueDP;
    }
}
