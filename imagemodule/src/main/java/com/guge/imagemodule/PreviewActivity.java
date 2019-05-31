package com.guge.imagemodule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;


public class PreviewActivity extends AppCompatActivity {

    ViewPager viewPager;
    List<String> mList;

    private List<Fragment> fragments = new ArrayList<>();

    ImageFragmentAdapter adapter;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_preview);

        viewPager=findViewById(R.id.view_pager);
        mList= (List<String>) getIntent().getSerializableExtra("key_data");
        index=getIntent().getIntExtra("key_index",-1);

        if(mList==null||mList.isEmpty()){
            throw new IllegalArgumentException("参数不能为空");
        }

        for (int i = 0; i < mList.size(); i++) {
            fragments.add(ImageFragment.newInstance(mList.get(i),index));
        }

        adapter=new ImageFragmentAdapter(getSupportFragmentManager(),fragments);


        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);

    }

    public void callFinish(){
        finish();
    }


}
