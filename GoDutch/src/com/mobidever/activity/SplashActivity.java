package com.mobidever.activity;

import com.mobidever.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

public class SplashActivity extends Activity {
	private LinearLayout sLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		sLayout = (LinearLayout)findViewById(R.id.splashLayout);
		startAnimation();
	}

	private void startAnimation() {
		AlphaAnimation tAnimation = new AlphaAnimation(0.1f, 1.0f);
    	tAnimation.setDuration(1000);
    	sLayout.setAnimation(tAnimation);
    	sLayout.startAnimation(tAnimation);
    	new Handler().postDelayed(new loadSplash(), 1000);
	}
	private class loadSplash implements Runnable{

		public void run() {
			Intent intent = new Intent(SplashActivity.this,ActivityMain.class);
			startActivity(intent);
			finish();
		}
		
	}

}
