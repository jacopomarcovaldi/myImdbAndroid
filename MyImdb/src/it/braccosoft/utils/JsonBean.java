package it.braccosoft.utils;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class JsonBean {

	public static Object JsonToObject (JSONObject json,Object oggetto)
	{

		Object [] parametri = new Object[2];
		parametri[0] = json;
		parametri[1]=oggetto;
		try {
			return(new AsyncTask <Object[], Void, Object>() {
				private JSONObject json;
				private Object oggetto;
				@Override
				protected Object doInBackground(Object[]... params) {
					json = (JSONObject)(params[0][0]);
					oggetto = (Object)(params[0][1]);
					Method[] metodi = oggetto.getClass().getMethods();
					for(int i=0;i<metodi.length;i++)
					{
						if(metodi[i].getName().indexOf("set_")!=-1)
						{
							try {
								if(metodi[i].getParameterTypes()!=null && metodi[i].getParameterTypes().length!=0)
								{
									if((metodi[i].getParameterTypes()[0]).equals(String.class))
										metodi[i].invoke(oggetto, json.getString(metodi[i].getName().replace("set_", "")));
									else if((metodi[i].getParameterTypes()[0]).equals(Boolean.class))
										metodi[i].invoke(oggetto, json.getBoolean(metodi[i].getName().replace("set_", "")));
									else if((metodi[i].getParameterTypes()[0]).equals(Double.class))
										metodi[i].invoke(oggetto, json.getDouble(metodi[i].getName().replace("set_", "")));
									else if((metodi[i].getParameterTypes()[0]).equals(Integer.class))
										metodi[i].invoke(oggetto, json.getInt(metodi[i].getName().replace("set_", "")));
									else if((metodi[i].getParameterTypes()[0]).equals(JSONObject.class))
										metodi[i].invoke(oggetto, json.getJSONObject(metodi[i].getName().replace("set_", "")));
									else if((metodi[i].getParameterTypes()[0]).equals(JSONArray.class))
										metodi[i].invoke(oggetto, json.getJSONArray(metodi[i].getName().replace("set_", "")));
								}
								else
									metodi[i].invoke(oggetto, json.getString(metodi[i].getName().replace("set_", "")));

							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					return oggetto;
				}

				@Override
				protected void onPostExecute(Object result) {
					super.onPostExecute(result);
				}
			}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri).get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;



	}

	public static JSONObject ObjectToJson (Object oggetto)
	{
		Object [] parametri = new Object[1];
		parametri[0] = oggetto;
		try {
			return(new AsyncTask <Object[], Void, JSONObject>() {
				private JSONObject json;
				private Object oggetto;
				@Override
				protected JSONObject doInBackground(Object[]... params) {
					oggetto = (Object)(params[0][0]);
					JSONObject json = new JSONObject();
					Method[] metodi = oggetto.getClass().getMethods();
					for(int i=0;i<metodi.length;i++)
					{
						if(metodi[i].getName().indexOf("get_")!=-1)
						{
							try {
								json.put(metodi[i].getName().replace("get_", ""), metodi[i].invoke(oggetto, null));
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					return json;
				}

				@Override
				protected void onPostExecute(JSONObject result) {
					super.onPostExecute(result);
				}
			}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri).get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
