package com.mobidever.activity;
import com.mobidever.activity.base.ActivityFrame;
import com.mobidever.R;
import android.os.Bundle;
public class AboutActivity extends ActivityFrame {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppendMainBody(R.layout.about);
	}
}
