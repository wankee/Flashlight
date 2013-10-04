package com.example.flashlight;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	//private ToggleButton flash_switch;
	private ImageButton flash_switch;
	private RelativeLayout rela_layout;
	private Camera mCamera;
	private Camera.Parameters mParams;
	private List<String> flash_modes;
	private static String FLASH_STATUS="on";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("onCreate");
		setContentView(R.layout.activity_main);
		
		flash_switch = (ImageButton) findViewById(R.id.flash_switch);
		rela_layout=(RelativeLayout)findViewById(R.id.relative_layout);
		//flash_switch.setChecked(true);
		flash_switch.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (FLASH_STATUS=="off") {
					open_flashlight();
					rela_layout.setBackground(getResources().getDrawable(R.drawable.background_light_on));
					flash_switch.setImageDrawable(getResources().getDrawable(R.drawable.switch_on));
					
				} else if(FLASH_STATUS=="on") {
					close_flashlight();
					rela_layout.setBackground(getResources().getDrawable(R.drawable.background));
					flash_switch.setImageDrawable(getResources().getDrawable(R.drawable.switch_off));
				}
			}
		});
	}
	
	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
	public void open_flashlight(){
		mParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
		mCamera.setParameters(mParams);
		mCamera.startPreview();
		FLASH_STATUS="on";
	}
	
	public void close_flashlight(){
		mParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
		mCamera.setParameters(mParams);
		mCamera.startPreview();
		FLASH_STATUS="off";
	}
	
	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		mCamera = getCameraInstance();
		mParams = mCamera.getParameters();
		flash_modes = mParams.getSupportedFlashModes();
		if (!flash_modes.contains(Camera.Parameters.FLASH_MODE_TORCH)
				|| !flash_modes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
			Toast.makeText(this, "手电筒功能不正常", Toast.LENGTH_SHORT);
		}
		//System.out.println(mParams.getFlashMode());
		//System.out.println(Camera.Parameters.FLASH_MODE_OFF);
	//	if (mParams.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF)) {
			open_flashlight();
		//}
		
		System.out.println("onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		close_flashlight();
		releaseCamera();
		System.out.println("onPause");
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("onStart");
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		System.out.println("onReStart");
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("onStop");
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("onDestroy");
	}
}