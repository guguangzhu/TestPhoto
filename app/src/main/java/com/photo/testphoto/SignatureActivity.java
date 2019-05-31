package com.photo.testphoto;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.photo.testphoto.mvp.MVPBaseActivity;

public class SignatureActivity extends AppCompatActivity {
    private static final String TAG = "SignatureActivity";
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        textView=findViewById(R.id.textView);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSignature();
            }
        });
    }


    private void getSignature(){
        try {
            Context context=createPackageContext("com.cubic.autohome",Context.CONTEXT_IGNORE_SECURITY);
            PackageInfo packageInfo=context.getPackageManager().getPackageInfo(context.getPackageName(),PackageManager.GET_SIGNATURES);
            Log.e(TAG,"sign:"+packageInfo.signatures[0].toCharsString()+"------");
            Log.e(TAG,"hashCode:"+packageInfo.signatures[0].hashCode());
            textView.setText(packageInfo.signatures[0].hashCode()+"");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
