package it.braccosoft.utils;

import it.braccosoft.myimdb.Constants;
import it.braccosoft.myimdb.customlist.CustomList;
import it.braccosoft.myimdb.model.Movie;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import it.braccosoft.myimdb.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class HttpGetInpustStream extends AsyncTask<String, Void, String>{

	private String contentType;
	private String accept;
	private String parameters;
	private String data;
	private String method;
	private Context context;
	private ProgressBar progress;
	private static CustomList list;
	private String fileSalvato;
	private ImageView imageView;
	private Movie movie;
	private HashMap<String,Bitmap> immagini;

	public HttpGetInpustStream (String method,Movie movie,String contentType,String accept,String dati,Context context,ProgressBar progress,CustomList list,ImageView imageView,HashMap<String,Bitmap> immagini)
	{
		this.method = method;
		this.contentType = contentType;
		this.accept = accept;
		if(method.equalsIgnoreCase(Constants.HTTP_METHOD_GET))
			this.parameters = dati;
		else
			this.data = dati;
		this.context = context;
		this.progress = progress;
		this.list = list;
		this.imageView = imageView;
		this.movie = movie;
		this.immagini = immagini;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(progress!=null)
			progress.setVisibility(ProgressBar.VISIBLE);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(fileSalvato != null && !fileSalvato.equalsIgnoreCase(""))
		{
			try {
				String nomeFile = FileUtil.getFileName(context, fileSalvato);
				FileInputStream fis = FileUtil.getFileFromMemory(nomeFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				if(bis.available()>0)
					imageView.setImageBitmap(BitmapFactory.decodeStream(bis));
				else
					imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder1));	
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder1));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder1));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder1));
			}
			catch(IOException e)
			{
				e.printStackTrace();
				imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder1));
			}
		}
		else
		{
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder1));
		}
		if(immagini.size()%30 == 0)
			immagini.clear();
		immagini.put(movie.get_imdbID(), ((BitmapDrawable)imageView.getDrawable()).getBitmap());
		imageView.invalidate();
		list.notifyDataSetChanged();
		if(progress!=null)
			progress.setVisibility(ProgressBar.INVISIBLE);
		((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	/**
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException 
	 * @throws IllegalStateException 
	 */
	private static String httpGet(String url,String contentType,String accept,String parameters,Context context,String idImdb) throws ClientProtocolException,
	IOException, IllegalStateException, NoSuchAlgorithmException {
		String ret = "";
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 4000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 6000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
		HttpGet httpget = new HttpGet(url+(parameters!=null?parameters:""));

		/* headers */
		httpget.setHeader("Accept", accept);
		httpget.setHeader("Content-Type", contentType);

		/* execute */
		BasicHttpResponse httpResponse = null;
		httpResponse = (BasicHttpResponse) httpclient.execute(httpget);
		/* return response */
		if (httpResponse.getEntity() != null) {
			ret = FileUtil.saveFileFromInputStream(httpResponse.getEntity().getContent(), httpResponse.getEntity().getContentLength(),FileUtil.getFileName(context, idImdb));
			//imageView.setImageURI(Uri.parse(FileUtil.getFile((FileUtil.getFileName(context, idImdb))).getAbsolutePath()));

			//imageView.setImageBitmap(BitmapFactory.decodeStream(FileUtil.getFileFromMemory(ret)));
		}
		return ret;
	}

	/**
	 * @param method
	 *            - example: "https://api.cashboardapp.com/projects"
	 * @param data
	 *            - example:
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException 
	 * @throws IllegalStateException 
	 */
	private static String httpPost(String url,String contentType,String accept,String data,Context context,String idImdb)
			throws ClientProtocolException, IOException, IllegalStateException, NoSuchAlgorithmException {
		String ret = "";
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 4000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 6000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
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
		if (httpResponse.getEntity() != null) {
			if (httpResponse.getEntity() != null) {
				ret = FileUtil.saveFileFromInputStream(httpResponse.getEntity().getContent(), httpResponse.getEntity().getContentLength(),FileUtil.getFileName(context, idImdb));
				//imageView.setImageURI(Uri.parse(FileUtil.getFile((FileUtil.getFileName(context, idImdb))).getAbsolutePath()));
			}
		}

		return ret;
	}

	@Override
	protected String doInBackground(String... params) {
		String ret = "";
		if(method.equalsIgnoreCase(Constants.HTTP_METHOD_GET))
		{
			try {
				fileSalvato = httpGet(movie.get_Poster(), contentType, accept, parameters,context,movie.get_imdbID());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				fileSalvato = httpPost(movie.get_Poster(), contentType, accept, data,context,movie.get_imdbID());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ret;
	}
}
