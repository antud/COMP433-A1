package com.example.a1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private File imageDir;
    private ImageView imageViewMain;
    private ImageView imageViewOne;
    private ImageView imageViewTwo;
    private ImageView imageViewThree;
    private String[] allFiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imageViewMain = findViewById(R.id.imageViewMain);
        imageViewOne = findViewById(R.id.smallImageOne);
        imageViewTwo = findViewById(R.id.smallImageTwo);
        imageViewThree = findViewById(R.id.smallImageThree);

        showLatestImage();
    }

    public void cameraClick(View view) {
        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File imageFile = new File(imageDir, "IMG_" + timeStamp + ".png");
        Uri imageUri = FileProvider.getUriForFile(this, "com.example.fileprovider2", imageFile);
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(camIntent, 1);
    }

    public void updateImageHandler(View view) {
        if (view.getId() == R.id.smallImageOne) {
            imageViewMain.setImageBitmap(BitmapFactory.decodeFile((imageDir.getAbsolutePath() + "/" + allFiles[allFiles.length - 2])));
        }
        else if (view.getId() == R.id.smallImageTwo) {
            imageViewMain.setImageBitmap(BitmapFactory.decodeFile((imageDir.getAbsolutePath() + "/" + allFiles[allFiles.length - 3])));
        }
        else if (view.getId() == R.id.smallImageThree) {
            imageViewMain.setImageBitmap(BitmapFactory.decodeFile((imageDir.getAbsolutePath() + "/" + allFiles[allFiles.length - 4])));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            showLatestImage();
        }
    }

    private void showLatestImage() {
        allFiles = imageDir.list();
        System.out.println(imageDir.getAbsolutePath());
        if (allFiles.length != 0) {
            String latestImagePath = imageDir.getAbsolutePath() + "/" + allFiles[allFiles.length - 1];
            Bitmap image = BitmapFactory.decodeFile(latestImagePath);
            imageViewMain.setImageBitmap(image);

            imageViewOne.setImageBitmap((BitmapFactory.decodeFile((imageDir.getAbsolutePath() + "/" + allFiles[allFiles.length - 2]))));
            imageViewTwo.setImageBitmap((BitmapFactory.decodeFile((imageDir.getAbsolutePath() + "/" + allFiles[allFiles.length - 3]))));
            imageViewThree.setImageBitmap((BitmapFactory.decodeFile((imageDir.getAbsolutePath() + "/" + allFiles[allFiles.length - 4]))));
        }
    }
}