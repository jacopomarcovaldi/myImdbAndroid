package it.braccosoft.utils;

import it.braccosoft.myimdb.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.protocol.HTTP;


import android.app.Activity;


import android.content.Context;


import android.os.AsyncTask;

import android.view.View;


import android.view.WindowManager;
import android.widget.ProgressBar;


public class HttpUtil extends AsyncTask<String, Void, String>{

	private String url;
	private String contentType;
	private String accept;
	private String parameters;
	private String data;
	private String method;
	private Context context;
	private ProgressBar progress;
	public HttpUtil (String method,String url,String contentType,String accept,String dati,Context context,ProgressBar progress)
	{
		this.method = method;
		this.url = url;
		this.contentType = contentType;
		this.accept = accept;
		if(method.equalsIgnoreCase(Constants.HTTP_METHOD_GET))
			this.parameters = dati;
		else
			this.data = dati;
		this.context = context;
		this.progress = progress;
	}
	
	public HttpUtil ()
	{}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(progress != null)
			progress.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(progress != null)
			progress.setVisibility(View.INVISIBLE);
		((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	/**
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String httpGet(String url,String contentType,String accept,String parameters) throws ClientProtocolException,
	IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url+(parameters!=null?parameters:""));

		/* headers */
		httpget.setHeader("Accept", accept);
		httpget.setHeader("Content-Type", contentType);

		/* execute */
		BasicHttpResponse httpResponse = null;
		httpResponse = (BasicHttpResponse) httpclient.execute(httpget);
		/* return response */
		return TextHelper.GetText(httpResponse);
	}

	/**
	 * @param method
	 *            - example: "https://api.cashboardapp.com/projects"
	 * @param data
	 *            - example:
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private static String httpPost(String url,String contentType,String accept,String data)
			throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		/* headers */
		httppost.setHeader("Accept", accept);
		httppost.setHeader("Content-Type", contentType);

		StringEntity se = new StringEntity(data, HTTP.UTF_8);
		se.setContentType(contentType);
		httppost.setEntity(se);

		/* execute */
		BasicHttpResponse httpResponse = null;
		httpResponse = (BasicHttpResponse) httpclient.execute(httppost);
		/* return response */
		return TextHelper.GetText(httpResponse);
	}

	@Override
	protected String doInBackground(String... params) {
		String ret = "";
		if(method.equalsIgnoreCase(Constants.HTTP_METHOD_GET))
		{
			try {
				ret = httpGet(url, contentType, accept, parameters);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				ret = httpPost(url, contentType, accept, data);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ret;
	}

}

class TextHelper {
	public static String GetText(InputStream in) {
		String text = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			text = sb.toString();
		} catch (Exception ex) {

		} finally {
			try {

				in.close();
			} catch (Exception ex) {
			}
		}
		return text;
	}

	public static String GetText(HttpResponse response) {
		String text = "";
		try {
			text = GetText(response.getEntity().getContent());
		} catch (Exception ex) {
		}
		return text;
	}
}