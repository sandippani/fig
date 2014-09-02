package com.example.fig;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CameraPhotoCaptureActivity extends Activity implements ImageUploadListener  {
	ImageView viewImage;
    Button b;
    
    private String savedImageFilePath;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_photo_capture);
        b=(Button)findViewById(R.id.btnSelectPhoto);
        viewImage=(ImageView)findViewById(R.id.viewImage);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               selectImage();

            }
        });
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds options to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
 
     private void selectImage() {
 
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
 
        AlertDialog.Builder builder = new AlertDialog.Builder(CameraPhotoCaptureActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
 
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
 
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions); 
                   
                    viewImage.setImageBitmap(bitmap);
 
                    File parent = new File(android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "fig");
                    if(!parent.exists()){
                    	boolean dirCreated = parent.mkdirs();
                    	if(!dirCreated){
                    		System.out.println("...Directory could not be created.."+parent.getAbsolutePath());
                    	}
                    }
                    f.delete();
                    OutputStream outFile = null;
                    File finalFileName = new File(parent.getPath(), String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(finalFileName);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        savedImageFilePath=finalFileName.getAbsolutePath();
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
 
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                savedImageFilePath = picturePath;
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath+"");
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }
    
    public void uploadPhoto(View view){
    	if(savedImageFilePath == null || savedImageFilePath.trim().length()==0){
    		Toast.makeText(getApplicationContext(), "Image not found.", Toast.LENGTH_LONG).show();
    	}else{
    	String comment = "mycomment";
    	String[] input= new String[]{savedImageFilePath,comment};
    	ImageUploader imageUploader = new ImageUploader(this);
    	imageUploader.execute(input);
    	}
    }

	@Override
	public void onImageUploadResult(String result) {
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}
}
