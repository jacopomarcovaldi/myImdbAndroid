package it.braccosoft.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class FileUtil {

	public static String saveFileFromInputStream (InputStream inputStream,long totalSize,String filename) throws IOException
	{
		File root = getStorage();
		Log.i("Local filename:",""+filename);
		createDir(filename, true);
		File file = new File(root,filename);
		if(file.createNewFile())
		{
			file.createNewFile();
			FileOutputStream fileOutput = new FileOutputStream(file);
			int downloadedSize = 0;   
			byte[] buffer = new byte[1024];
			int bufferLength = 0;
			while ( (bufferLength = inputStream.read(buffer)) > 0 ) 
			{                 
				fileOutput.write(buffer, 0, bufferLength);                  
				downloadedSize += bufferLength;                 
				//Log.i("Progress:","downloadedSize:"+downloadedSize+"totalSize:"+ totalSize) ;
			}             
			fileOutput.close();
		}		
		return file.getPath();
	}
	
	public static void createDir (String path,boolean subString)
	{
		String subPath = path;
		if(subString)
			subPath = path.substring(0, path.lastIndexOf("/"));
		File root = getStorage();
		Log.i("Local filename:",""+subPath);
		File file = new File(root,subPath);
		if(!file.exists())
			file.mkdirs();
	}
	
	public static File getStorage ()
	{
		if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			return Environment.getExternalStorageDirectory().getAbsoluteFile();
		else
			return Environment.getRootDirectory().getAbsoluteFile();
	}
	
	public static FileInputStream getFileFromMemory (String filename) throws FileNotFoundException
	{
		
		// Using an AsyncTask to load the slow images in a background thread
		try {
			return (new AsyncTask <String, Void, FileInputStream>() {
			   private String v;

			    @Override
			    protected FileInputStream doInBackground(String... params) {
			    	v = params[0];
			       
			        	//Find the directory for the SD Card using the API
			    		//*Don't* hardcode "/sdcard"
			    		File storage = getStorage();
			    		FileInputStream fileInputStream  =  null;
			    		//Get the text file
			    		File file = new File(storage,v);
			    		if(file.exists())
			    		{
			    			try {
								return new FileInputStream(file);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    			return null;
			    		}
			    		else
			    			return fileInputStream;
			    }

			    @Override
			    protected void onPostExecute(FileInputStream result) {
			        super.onPostExecute(result);
			    }
			}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, filename).get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static File getFile (String filename)
	{
		File storage = getStorage();
		return new File(storage,filename);
	}
	
	public static String getFileName(Context context,String url) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
	    return context.getString(context.getApplicationInfo().labelRes)+"/"+url+".jpg";
	}
	
	private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
}
