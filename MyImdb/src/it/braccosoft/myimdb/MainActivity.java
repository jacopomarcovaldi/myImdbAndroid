package it.braccosoft.myimdb;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import it.braccosoft.myimdb.database.MyImdbDbHelper;
import it.braccosoft.myimdb.model.LastLogin;
import it.braccosoft.myimdb.model.Movie;
import it.braccosoft.myimdb.model.Risposta;
import it.braccosoft.myimdb.model.User;
import it.braccosoft.utils.CursorBean;
import it.braccosoft.utils.HttpUtil;
import it.braccosoft.utils.NetworkUtil;
import it.braccosoft.myimdb.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button bLogin;
	private EditText tLogin;
	private EditText tPassword;
	private ProgressBar progress;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private MyImdbDbHelper db;
	private LastLogin lastLogin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new MyImdbDbHelper(getApplicationContext());
		setContentView(R.layout.activity_main);
		bLogin = (Button)findViewById(R.id.LoginBT) ;
		tLogin = ( EditText )this.findViewById(R.id.UserNameET);
		tPassword = ( EditText )this.findViewById(R.id.PasswordET);
		progress = ( ProgressBar )this.findViewById(R.id.progressBar1);
		CheckLastLogin();
		bLogin.setOnClickListener(new View.OnClickListener () {
			@Override
			public void onClick ( View arg0 ) {
				//progress.setVisibility(View.VISIBLE);
				login();
			}
		});
	}

	
	
	@Override
	protected void onResume () {
		super.onResume();
		// L'Activity e' diventata visibile
	}
	@Override
	protected void onPause () {
		super.onPause();
		// L'Activity sta per essere distrutta
	}
	
	 public void onDestroy() {
	        super.onDestroy();

	        /*
	         * Kill application when the root activity is killed.
	         */
	        System.exit(0);
	    }

	private void CheckLastLogin()
	{
		// controllo se c'è un utlimo login
		try {
			lastLogin = (LastLogin)CursorBean.CursorToObject(MyImdbDbHelper.GetLastLoggedin(db), new LastLogin());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(lastLogin!=null&&lastLogin.get_userName()!=null&&lastLogin.get_userName().length()!=0)
		{
			tLogin.setText(lastLogin.get_userName());
			tPassword.setText(lastLogin.get_password());
		}
	}

	private void insertLogin (String userName,String password)
	{
		LastLogin last = new LastLogin();
		last.set_userName(userName);
		last.set_password(password);
		MyImdbDbHelper.InsertLastLogin(last, db);
	}
	private void login ()
	{
		if(!NetworkUtil.isNetworkAvailable(this))
		{
			Toast.makeText(getApplicationContext(),"Attenzione:Rete non disponibile",Toast.LENGTH_LONG).show();			
		}
		else if(!tLogin.getText().toString().equalsIgnoreCase("") && !tPassword.getText().toString().equalsIgnoreCase(""))
		{
			insertLogin(tLogin.getText().toString(), tPassword.getText().toString());
			HttpUtil http = new HttpUtil(Constants.HTTP_METHOD_POST,Constants.SERVER_COMPLETE_URL+Constants.SERVER_LOGIN_URL, Constants.CONTENT_TYPE_X_WWW, Constants.CONTENT_TYPE_JSON, Constants.PARAMETER_USERNAME_CODE+"="+tLogin.getText()+"&"+Constants.PARAMETER_PASSWORD_CODE+"="+tPassword.getText(),MainActivity.this,progress);
			try {
				Risposta risposta = new Risposta(new JSONObject(http.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null).get()));
				if(risposta.get_status().equalsIgnoreCase("0"))
				{
					User user = new User(risposta.get_object().getJSONObject(Constants.JSON_PARAMATER_USER)); 
					user.set_token(risposta.get_object().getString(Constants.JSON_PARAMATER_TOKEN));
					setUserInSession(user);				
					try {
						if(MyImdbDbHelper.getUser(user, db).getCount()>0)
							MyImdbDbHelper.updateUser(user, db);
						else
							MyImdbDbHelper.insertUser(user, db);
						Movie.UpdateMoviesFromServer((MyImdbDbHelper.getMaxMovieDateUser(user, db)), user, db, http, risposta, progress, MainActivity.this);
					} catch (Exception e) {
						e.printStackTrace();
					}
					finally
					{
						startMoviesList();
					}
				}
				else
				{
					Toast.makeText(getApplicationContext()," Errore: "+risposta.get_message(),Toast.LENGTH_LONG).show();
				}

			} catch (JSONException e) {
				Toast.makeText(getApplicationContext()," Errore: "+e.getMessage(),Toast.LENGTH_LONG).show();
			}catch (InterruptedException e) {
				Toast.makeText(getApplicationContext()," Errore: "+e.getMessage(),Toast.LENGTH_LONG).show();
			}catch (ExecutionException e) {
				Toast.makeText(getApplicationContext()," Errore: "+e.getMessage(),Toast.LENGTH_LONG).show();
			}
		}
		else if(tLogin.getText().toString().equalsIgnoreCase(""))
		{
			Toast.makeText(getApplicationContext()," inserisci la username ",Toast.LENGTH_LONG).show();
		}
		else
			Toast.makeText(getApplicationContext()," inserisci la password ",Toast.LENGTH_LONG).show();
	}

	private void startMoviesList ()
	{
		Intent intent = new Intent(this, MovieListActivity.class);
		startActivity(intent);
	}

	private void setUserInSession (User user)
	{
		SharedPreferences sharedpreferences = getSharedPreferences(Constants.APPLICATION_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedpreferences.edit();
		editor.putString(Constants.PARAMETER_USERNAME_CODE, user.get_userName());
		editor.commit();
	}
}
