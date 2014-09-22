package com.lelewan.flashlight;

import java.util.List;

import com.lelewan.flashlight.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	// private ToggleButton flash_switch;
	private ImageButton flash_switch;
	private RelativeLayout rela_layout;
	private Camera mCamera;
	private Camera.Parameters mParams;
	private List<String> flash_modes;
	private static String FLASH_STATUS = "on";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("log", "onCreate");
		setContentView(R.layout.activity_main);

		flash_switch = (ImageButton) findViewById(R.id.flash_switch);
		rela_layout = (RelativeLayout) findViewById(R.id.relative_layout);
		// flash_switch.setChecked(true);
		flash_switch.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (FLASH_STATUS == "off") {
					open_flashlight();
					// rela_layout.setBackground(getResources().getDrawable(R.drawable.background_light_on));
					// flash_switch.setImageDrawable(getResources().getDrawable(R.drawable.switch_on));

				} else if (FLASH_STATUS == "on") {
					close_flashlight();
					// rela_layout.setBackground(getResources().getDrawable(R.drawable.background));
					// flash_switch.setImageDrawable(getResources().getDrawable(R.drawable.switch_off));
				}
			}
		});
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}

	@SuppressLint("NewApi")
	public void open_flashlight() {
		mParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
		mCamera.setParameters(mParams);
		mCamera.startPreview();
		rela_layout.setBackground(getResources().getDrawable(
				R.drawable.background_light_on));
		flash_switch.setImageDrawable(getResources().getDrawable(
				R.drawable.switch_on));

		FLASH_STATUS = "on";
	}

	@SuppressLint("NewApi")
	public void close_flashlight() {
		mParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
		mCamera.setParameters(mParams);
		mCamera.startPreview();
		rela_layout.setBackground(getResources().getDrawable(
				R.drawable.background));
		flash_switch.setImageDrawable(getResources().getDrawable(
				R.drawable.switch_off));
		FLASH_STATUS = "off";
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
			Toast.makeText(this, "手电筒功能不正常", Toast.LENGTH_SHORT).show();
		}
		open_flashlight();
		// System.out.println(mParams.getFlashMode());
		// System.out.println(Camera.Parameters.FLASH_MODE_OFF);
		// if (mParams.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF))
		// {
		//
		// }

		Log.i("log", "onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		close_flashlight();
		releaseCamera();
		Log.i("log", "onPause");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_about:
			/*
			 * final AlertDialog alert = new
			 * AlertDialog.Builder(MainActivity.this).create();
			 * //builder.setTitle(R.string.about_title);
			 * alert.setMessage(getResources().getText(R.string.about_message));
			 * alert.show(); //alert.dismiss(); Handler handler = new Handler();
			 * handler.postDelayed(new Runnable() {
			 * 
			 * public void run() { alert.dismiss(); } }, 1000);
			 */
			Toast toast = Toast.makeText(getApplicationContext(),
					R.string.about_message, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 100);
			toast.show();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}