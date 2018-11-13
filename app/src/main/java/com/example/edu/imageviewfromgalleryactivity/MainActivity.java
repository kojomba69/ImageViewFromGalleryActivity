package com.example.edu.imageviewfromgalleryactivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
//http://developer.anroid.com/reference/android/content/pm/PackageManager 참조하래.
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int IMAGE_CAPTURE = 101;//? IMAGE_CAPTURE에 빨간줄이 떠서 이거 만들어짐.
    private static final int LOAD_IMAGE=102;
    ImageView imageView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==LOAD_IMAGE && data != null) {
            Uri selectedImage = data.getData();
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode==IMAGE_CAPTURE && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap=(Bitmap)extras.get("data");
            imageView.setImageBitmap(bitmap);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageViewFromGallery);

        Button fromGalleryButton=findViewById(R.id.fromGalleryButton);
        fromGalleryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.imageCaptureButton:
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
                    Intent intent1 = new Intent();
                    intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent1,IMAGE_CAPTURE);
                    break;
                }
            case R.id.fromGalleryButton:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/");
                startActivityForResult(intent,LOAD_IMAGE);
                break;
        }


    }

}
