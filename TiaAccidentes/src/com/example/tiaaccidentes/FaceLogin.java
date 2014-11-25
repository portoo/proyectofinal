package com.example.tiaaccidentes;

import java.util.Arrays;

import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.widget.UserSettingsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class FaceLogin extends FragmentActivity {
	
	UserSettingsFragment fragmentsetting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState==null) {
			setContentView(R.layout.activity_facelogin);
			fragmentsetting = new UserSettingsFragment();
			fragmentsetting.setSessionStatusCallback(new StatusCallback() {
				
				@Override
				public void call(Session session, SessionState state, Exception exception) {
					// TODO Auto-generated method stub
					OnSessionStateChanged(session, state, exception);
				}
			});
			fragmentsetting.setReadPermissions(Arrays.asList("user_likes", "user_status"));
			getSupportFragmentManager()
			.beginTransaction().
			add(R.id.facelogincontainer, fragmentsetting)
			.commit();
		}
	}
	
	public void OnSessionStateChanged(Session session, SessionState state, Exception exception) {
		// TODO Auto-generated method stub
		if (state == SessionState.OPENED){
			Intent inte = new Intent(this, MapaActivity.class);
			startActivity(inte);
		}
		if (state == SessionState.CLOSED_LOGIN_FAILED) {
			this.finish();
			Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	    fragmentsetting.onActivityResult(requestCode, resultCode, data); 
	} 
}
