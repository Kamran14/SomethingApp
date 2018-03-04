package com.starterhacks.somethingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    public String myTimeStamp;
    private ImageView imageView;
    protected static final int urVar = 0;
    //Long timeStamp = Calendar.getInstance().getTimeInMillis();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ImageView img = (ImageView) findViewById(R.id.imageView);
        this.imageView = (ImageView) this.findViewById(R.id.imageView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                File myNewFile = new File("storage/" + Environment.DIRECTORY_DCIM + "/" + "Camera" + "/" + imageFileName + ".jpg");
                Uri contentUri = Uri.fromFile(myNewFile);
                imageView.setImageURI(contentUri);
            }
        });
        //Button click to take photo.
        final Button button = findViewById(R.id.matt_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    dispatchTakePictureIntent();
                }catch (Exception e){}
            }
        });

    }

    //Matt's stuff for taking image, gets used by button
    static final int REQUEST_TAKE_PHOTO = 1;
    private void dispatchTakePictureIntent() throws IOException{
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File object
            File photoFile;
            try {
                photoFile = createImageFile();
                if (photoFile != null) {
                    Uri photoURI = takePictureIntent.getData();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 5000);
                    File myFile = new File("storage/" + Environment.DIRECTORY_DCIM, "/" + "Camera" + "/" + imageFileName + ".jpg");
                    Bitmap img1 = BitmapFactory.decodeFile(myFile.toString());
                    imageView.setImageBitmap(img1);
                    Uri contentUri = Uri.fromFile(myFile);
                    imageView.setImageURI(contentUri);
                    //onActivityResult();
//                    if (true) {
//                        myTimeStamp = timeStamp.toString();
//                        File myFile = new File(Environment.DIRECTORY_PICTURES, "/" + myTimeStamp + ".jpg");
//                        Bitmap img1 = BitmapFactory.decodeFile(myFile.toString());
//                        ImageView img = (ImageView) findViewById(R.id.imageView);
//                        img.setImageBitmap(img1);
//                        Toast.makeText(this, photoURI.toString(), Toast.LENGTH_SHORT).show();
//                    }
                }
            } catch (IOException ex) {
                // Error occurred while creating the File

                //We should probably do something in this case, but tbh i have no idea what.
            }
            // Continue only if the File was successfully created

        }


    }

    static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                //use imageUri here to access the image

                Bundle extras = data.getExtras();

                //Log.e("URI", imageUri.toString());

                Bitmap bmp = (Bitmap) extras.get("data");

                // here you will get the image as bitmap

            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
            }
        }


    }


    // lets us save images to device (Matt)
    String mCurrentPhotoPath;
    String imageFileName;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DCIM + "/Camera/");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // For loading the images from folder (matt)
    private void loadImageFromStorage(String path)
    {

        try {
            File f = new File(path, "/" + myTimeStamp + ".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
           // img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
