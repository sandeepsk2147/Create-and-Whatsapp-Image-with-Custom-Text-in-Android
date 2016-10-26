package com.example.customtextimage;

import java.io.File;
import java.io.FileOutputStream;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Paint.Align;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn= (Button)findViewById(R.id.sendmsg);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sendMessgae();
			}
		});
    }
    
    private void sendMessgae(){
    	
    	String text="Hello World!";
    	String imagePath= Environment.getExternalStorageDirectory()+File.separator+System.currentTimeMillis()+".png";
    	try {
		     Bitmap  b = Bitmap.createBitmap(1000,1000,Bitmap.Config.ARGB_8888);
		    Canvas cv=new Canvas(b);
		     cv.drawColor(Color.WHITE);
		    Paint  paint=new Paint();
		     paint.setColor(Color.BLACK);
		     paint.setTextAlign(Align.CENTER);
		     paint.setTextSize(50);
		     paint.setStyle(Paint.Style.FILL);
		     paint.setAntiAlias(true);
		     paint.setTextSize(50);
		     cv.drawText(text, 500, 500, paint);
			   FileOutputStream stream = new FileOutputStream(imagePath); //create your FileOutputStream here
			    b.compress(CompressFormat.JPEG, 85, stream);
			    b.recycle();
			    
			    // Send image to whatsapp
			    Uri imageUri = Uri.parse(imagePath);
			    Intent shareIntent = new Intent();
			    shareIntent.setAction(Intent.ACTION_SEND);
			    //Target whatsapp:
			    shareIntent.setPackage("com.whatsapp");
			    //Add text and then Image URI
			    shareIntent.putExtra(Intent.EXTRA_TEXT, text);
			    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
			    shareIntent.setType("image/jpeg");
			    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

			    try {
			        startActivity(shareIntent);
			    } catch (android.content.ActivityNotFoundException ex) {
			        Toast.makeText(getApplicationContext(), "Whatsapp have not been installed.", 1000).show();
			    }
    	}catch(Exception e){}
    }


    
}
