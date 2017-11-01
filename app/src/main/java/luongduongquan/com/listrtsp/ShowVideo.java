package luongduongquan.com.listrtsp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import luongduongquan.com.library.VlcListener;
import luongduongquan.com.library.VlcVideoLibrary;

public class ShowVideo extends AppCompatActivity implements VlcListener, View.OnClickListener {

	private VlcVideoLibrary vlcVideoLibrary;
	private Button bStartStop;
	private EditText etEndpoint;
	private ProgressDialog progressDialog;
	static final int MIN_WIDTH = 100;
	private CustomVideo surfaceView;
	private FrameLayout.LayoutParams mRootParam;

	// detector to pinch zoom in/out
	private ScaleGestureDetector mScaleGestureDetector;
	// detector to single tab
	private GestureDetector mGestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		Intent intent = getIntent();
		String URLNhan = intent.getStringExtra("URL");

		progressDialog = new ProgressDialog(ShowVideo.this);
		progressDialog.setTitle("Please wait...");
		progressDialog.setCancelable(false);

		setContentView(R.layout.activity_show_video);
		mRootParam = (FrameLayout.LayoutParams) ((View) findViewById(R.id.root_view)).getLayoutParams();
		surfaceView = (CustomVideo) findViewById(R.id.surfaceView);
		bStartStop = (Button) findViewById(R.id.b_start_stop);
		bStartStop.setOnClickListener(this);
		etEndpoint = (EditText) findViewById(R.id.et_endpoint);
		etEndpoint.setText(URLNhan);
		vlcVideoLibrary = new VlcVideoLibrary(this, this, surfaceView);

		// set up gesture listeners
		mScaleGestureDetector = new ScaleGestureDetector(this, new MyScaleGestureListener());
		mGestureDetector = new GestureDetector(this, new MySimpleOnGestureListener());
		surfaceView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);
				mScaleGestureDetector.onTouchEvent(event);
				return true;
			}
		});
	}

	@Override
	public void onComplete() {
		progressDialog.dismiss();
		Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onError() {
		progressDialog.dismiss();
		Toast.makeText(this, "Error, make sure your endpoint is correct", Toast.LENGTH_SHORT).show();
		vlcVideoLibrary.stop();
		bStartStop.setText(getString(R.string.start_player));
	}

	@Override
	public void onClick(View view) {
		if (!vlcVideoLibrary.isPlaying()) {
			progressDialog.show();
			vlcVideoLibrary.play(etEndpoint.getText().toString());
			bStartStop.setText(getString(R.string.stop_player));
		} else {
			vlcVideoLibrary.stop();
			bStartStop.setText(getString(R.string.start_player));
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		vlcVideoLibrary.stop();
		finish();
	}

	private class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (surfaceView == null)
				return false;
			if (surfaceView.isPlaying())
				surfaceView.pause();
			else
				surfaceView.start();
			return true;
		}

	}

	private class MyScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
		private int mW, mH;

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			// scale our video view
			mW *= detector.getScaleFactor();
			mH *= detector.getScaleFactor();
			if (mW < MIN_WIDTH) { // limits width
				mW = surfaceView.getWidth();
				mH = surfaceView.getHeight();
			}
			Log.d("onScale", "scale=" + detector.getScaleFactor() + ", w=" + mW + ", h=" + mH);
			surfaceView.setFixedVideoSize(mW, mH); // important
			mRootParam.width = mW;
			mRootParam.height = mH;
			return true;
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			mW = surfaceView.getWidth();
			mH = surfaceView.getHeight();
			Log.d("onScaleBegin", "scale=" + detector.getScaleFactor() + ", w=" + mW + ", h=" + mH);
			return true;
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			Log.d("onScaleEnd", "scale=" + detector.getScaleFactor() + ", w=" + mW + ", h=" + mH);
		}
	}
}
