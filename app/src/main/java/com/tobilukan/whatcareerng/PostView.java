package com.tobilukan.whatcareerng;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PostView extends Activity {

    TextView postTitle;
    TextView postContent;
    ImageView postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item);
        postTitle = (TextView)findViewById(R.id.post_title);
        postContent = (TextView)findViewById(R.id.post_content);
        postImage = (ImageView)findViewById(R.id.post_image);

        postTitle.setText("sdsfsdfvd");
        postContent.setText("sfdsgdh");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.careerwoman);
        postImage.setImageBitmap(bitmap);


    }
}