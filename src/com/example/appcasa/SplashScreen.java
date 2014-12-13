package com.example.appcasa;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class SplashScreen extends ActionBarActivity {
	private Thread threadSplash;
	private boolean click;
	private ProgressBar mProgressBar;

	protected static final int TIMER_RUNTIME = 5000; // in ms --> 4s
	protected boolean mbActive;

	private WifiManager wifiManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);

		wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
			Log.e("wifi", "..:" + wifiManager.isWifiEnabled());

		}
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		mProgressBar.setMax(100);

		Toast toast = null;

		boolean existeConexao;

		// if (isConnected(SplashScreen.this.getApplicationContext())) {
		// toast = Toast.makeText(SplashScreen.this.getApplicationContext(),
		// "Com conexao ", Toast.LENGTH_LONG);
		// existeConexao = true;
		// } else {
		// toast = Toast.makeText(SplashScreen.this.getApplicationContext(),
		// "Sem conexao ", Toast.LENGTH_LONG);
		// existeConexao = false;
		// }
		//
		// toast.show();

		threadSplash = new Thread() {

			public void run() {

				int waited = 0;
				mbActive = true;

				try {
					synchronized (this) {
						// wait(3000);

						while (mbActive && (waited < TIMER_RUNTIME)) {
							sleep(200);
							if (mbActive) {
								waited += 200;
								updateProgress(waited);

							}
						}

					}
				} catch (Exception e) {
				} finally {

				}

				if (click)
					finish();
				finish();
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);

				Intent intent = new Intent();
				intent.setClass(SplashScreen.this, MainActivity.class);
				startActivity(intent);

			}

		};

		// if (isConnected(SplashScreen.this.getApplicationContext()))
		threadSplash.start();
		// else
		// toast = Toast.makeText(SplashScreen.this.getApplicationContext(),
		// "Ative a conexão a rede", Toast.LENGTH_LONG);
		// toast.show();
	}

	@Override
	protected void onPause() {

		super.onPause();
		threadSplash.interrupt();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (threadSplash) {
				click = true;

				threadSplash.notifyAll();
			}
		}
		return true;
	}

	private void updateProgress(final int timePassed) {
		if (null != mProgressBar) {
			// Ignore rounding error here
			final int progress = mProgressBar.getMax() * timePassed
					/ TIMER_RUNTIME;
			mProgressBar.setProgress(progress);
		}

	}

	public static boolean isConnected(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo mobile = cm
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

			if (mobile.isConnected()) {
				return true;
			} else if (wifi.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}