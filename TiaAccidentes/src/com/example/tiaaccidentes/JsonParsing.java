package com.example.tiaaccidentes;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class JsonParsing extends AsyncTask<URI, Integer, JSONArray>{
	
	ExecuteJson parse;
	Activity c;
	
	public JsonParsing(ExecuteJson parse, Activity c) {
		// TODO Auto-generated constructor stub
		this.parse = parse;
		this.c=c;
	}
	
	@Override
	protected JSONArray doInBackground(URI... params) {
		// TODO Auto-generated method stub
		URI value = params[0];
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(value);
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			HttpEntity httpEntity = response.getEntity();
			String resultString = EntityUtils.toString(httpEntity);
			JSONArray jObj=new JSONArray(resultString);
			return jObj;
		} catch (final ClientProtocolException e) {
			// TODO Auto-generated catch block
			c.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
				}
			});
			
		} catch (final IOException e) {
			// TODO Auto-generated catch block
				c.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
				}
			});
		} catch (final JSONException e) {
			// TODO Auto-generated catch block
				c.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(c, "Server isn't running", Toast.LENGTH_LONG).show();
				}
			});
		}
		return null;
	}
	
	
	@Override
	protected void onPostExecute(JSONArray result) {
		// TODO Auto-generated method stub
		parse.JsonParse(result);
	}
}
