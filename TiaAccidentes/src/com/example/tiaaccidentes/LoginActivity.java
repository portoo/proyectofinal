package com.example.tiaaccidentes;

import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;

public class LoginActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener{
	
    private PlusClient mPlusClient;
    private ProgressBar progress;
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    private ConnectionResult mConnectionResult;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		progress = (ProgressBar)findViewById(R.id.progressBar1);
		
		mPlusClient = new PlusClient.Builder(this, this, this)
				.setActions("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity")
		        .build();
		progress.setVisibility(View.INVISIBLE);
		LoginButton bt = (LoginButton)findViewById(R.id.sign_in_facebook);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FaceLogin(v);
			}
		});
		SignInButton bt1 = (SignInButton)findViewById(R.id.sign_in__google_plus);
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GooglePlusLogin(v);
			}
		});
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if (mPlusClient.isConnected())
			mPlusClient.disconnect();
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mPlusClient.isConnected()){
			mPlusClient.clearDefaultAccount();
			mPlusClient.disconnect();
		}
	}
	
	public void FaceLogin(View v)
	{
		Intent inte = new Intent(this, FaceLogin.class);
		startActivity(inte);
		this.finish();
	}
	
	public void GooglePlusLogin(View v)
	{
		mPlusClient.connect();
		progress.setVisibility(View.VISIBLE);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		if (result.hasResolution()) {
            try {
                result.startResolutionForResult(this, result.getErrorCode());
            } catch (SendIntentException e) {
                	mPlusClient.connect();
            }
        }
		mConnectionResult = result;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == REQUEST_CODE_RESOLVE_ERR && responseCode == RESULT_OK) {
            mConnectionResult = null;
            mPlusClient.connect();
        } else {
        	progress.setVisibility(View.INVISIBLE);
        }
    }
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		progress.setVisibility(View.INVISIBLE);
		Intent inte = new Intent(this, MapaActivity.class);
		startActivity(inte);
		finish();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		progress.setVisibility(View.VISIBLE);
	}
}
