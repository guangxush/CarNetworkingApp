package com.example.chelianwang.Activity;

import android.app.Activity;
import android.graphics.Bitmap;

import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chelianwang.R;

public class QrActivity extends Activity {
    private ImageView imageView;
    public String S,U,V,W,X,Y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        S=getIntent().getStringExtra("jiayoucheliang");
        System.out.print("二维码内容"+S.toString());
        U=getIntent().getStringExtra("shijian");
        V=getIntent().getStringExtra("jiayouzhan");
        W=getIntent().getStringExtra("leixing");
        X=getIntent().getStringExtra("shuliang");
        Y=S+U+V+W+X;
        initViews();

    }
    protected void initViews() {

        imageView = (ImageView) findViewById(R.id.imageView);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int w = outMetrics.widthPixels * 8 / 11;//设置宽度
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = layoutParams.width = w;//设置高度
        imageView.setLayoutParams(layoutParams);

        try {
            Bitmap bitmap = QRUtils.encodeToQRWidth(Y,w);//要生成二维码的内容，我这就是一个网址
            imageView.setImageBitmap(bitmap);
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "生成二维码失败", Toast.LENGTH_SHORT);
        }
    }
}


