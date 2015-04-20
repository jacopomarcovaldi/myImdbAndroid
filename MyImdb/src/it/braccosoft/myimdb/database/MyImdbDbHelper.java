package it.braccosoft.myimdb.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.concurrent.ExecutionException;

import it.braccosoft.myimdb.model.LastLogin;
import it.braccosoft.myimdb.model.Movie;
import it.braccosoft.myimdb.model.User;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;


public class MyImdbDbHelper extends SQLiteOpenHelper {

	private static final String DB_NOME = "myImdbDB";
	private static final int DB_VERSIONE = 1;

	public MyImdbDbHelper(Context context) {
		super(context, DB_NOME, null, DB_VERSIONE);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creo la tabella utenti
		String sql = "CREATE TABLE "+User.TABLE_NAME+" "; 
		sql += "("+User.USERNAME_COLUMN+" TEXT PRIMARY KEY,";
		sql += ""+User.NOME_COLUMN+" TEXT NOT NULL,";
		sql += ""+User.COGNOME_COLUMN+" TEXT,";
		sql += ""+User.EMAIL_COLUMN+" TEXT,";
		sql += ""+User.PASSWORD_COLUMN+" TEXT,";
		sql += ""+User.ENABLE_COLUMN+" INTEGER,";
		sql += ""+User.DEFAULTLANGUAGE_COLUMN+" TEXT,";
		sql += ""+User.ADDED_COLUMN+" TEXT,";
		sql += ""+User.UPDATED_COLUMN+" TEXT,";
		sql += ""+User.TOKEN_COLUMN+" TEXT";		
		sql += ");";
		//Eseguiamo la query
		db.execSQL(sql);

		// creo la tabella dei film
		sql = "CREATE TABLE "+Movie.TABLE_NAME; 
		sql += "("+Movie.IMDBID_COLUMN+" TEXT PRIMARY KEY,";
		sql += ""+Movie.TITLE_COLUMN+" TEXT,";
		sql += ""+Movie.YEAR_COLUMN+" TEXT,";
		sql += ""+Movie.RATED_COLUMN+" TEXT,";
		sql += ""+Movie.RELEASED_COLUMN+" TEXT,";
		sql += ""+Movie.RUNTIME_COLUMN+" TEXT,";
		sql += ""+Movie.GENRE_COLUMN+" TEXT,";
		sql += ""+Movie.DIRECTOR_COLUMN+" TEXT,";
		sql += ""+Movie.WRITER_COLUMN+" TEXT,";
		sql += ""+Movie.ACTORS_COLUMN+" TEXT,";
		sql += ""+Movie.PLOT_COLUMN+" TEXT,";
		sql += ""+Movie.PLOT_TRANSLATED_COLUMN+" TEXT,";
		sql += ""+Movie.LANGUAGE_COLUMN+" TEXT,";
		sql += ""+Movie.COUNTRY_COLUMN+" TEXT,";
		sql += ""+Movie.AWARDS_COLUMN+" TEXT,";
		sql += ""+Movie.POSTER_COLUMN+" TEXT,";
		sql += ""+Movie.METASCORE_COLUMN+" TEXT,";
		sql += ""+Movie.IMDBRATING_COLUMN+" TEXT,";
		sql += ""+Movie.IMDBVOTES_COLUMN+" TEXT,";
		sql += ""+Movie.TYPE_COLUMN+" TEXT,";
		sql += ""+Movie.ADDED_COLUMN+" TEXT,";
		sql += ""+Movie.UPDATED_COLUMN+" TEXT,";
		sql += ""+Movie.USERNAME_COLUMN+" TEXT,";
		sql += ""+Movie.SEEN_COLUMN+" TEXT,";
		sql += "FOREIGN KEY("+Movie.USERNAME_COLUMN+") REFERENCES "+User.TABLE_NAME+"("+User.USERNAME_COLUMN+")";		
		sql += ");";
		//Eseguiamo la query
		db.execSQL(sql);
		//db.close();		

		// creo la tabella del lastlogin
		sql = "CREATE TABLE "+LastLogin.TABLE_NAME; 
		sql += "("+LastLogin.USERNAME_COLUMN+" TEXT PRIMARY KEY,";
		sql += ""+LastLogin.PASSWORD_COLUMN+" TEXT,";
		sql += "FOREIGN KEY("+Movie.USERNAME_COLUMN+") REFERENCES "+User.TABLE_NAME+"("+User.USERNAME_COLUMN+")";		
		sql += ");";
		//Eseguiamo la query
		db.execSQL(sql);
		//db.close();
	}

	public static Cursor GetLastLoggedin (MyImdbDbHelper db) throws InterruptedException, ExecutionException
	{
		return (new AsyncTask <MyImdbDbHelper, Void, Cursor>() {
			MyImdbDbHelper db;
			@Override
			protected Cursor doInBackground(MyImdbDbHelper... params) {
				db = params[0];
				String sql = "SELECT * FROM "+LastLogin.TABLE_NAME;
				Cursor c = db.getReadableDatabase().rawQuery(sql,null);
				//db.close();
				return c;
			}

			@Override
			protected void onPostExecute(Cursor result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, db).get());		
	}

	public static void InsertLastLogin (LastLogin user,MyImdbDbHelper db)
	{
		Object [] parametri = new Object[2];
		parametri[0] = db;
		parametri[1]=user;
		new AsyncTask <Object[], Void, Void>() {
			MyImdbDbHelper db;
			LastLogin user;
			@Override
			protected Void doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				user = (LastLogin)(params[0][1]);
				try {
					db.getWritableDatabase().execSQL(user.getDeleteSqlString());
					db.getWritableDatabase().execSQL(user.getInsertSqlString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}


	public static Cursor getUser (User user,MyImdbDbHelper db) throws InterruptedException, ExecutionException
	{
		Object [] parametri = new Object[2];
		parametri[0] = db;
		parametri[1]=user;
		return (new AsyncTask <Object [], Void, Cursor>() {
			MyImdbDbHelper db;
			User user;
			@Override
			protected Cursor doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				user = (User)(params[0][1]);
				String sql = "SELECT * FROM "+User.TABLE_NAME;
				if(user.get_userName()!=null&&!user.get_userName().equalsIgnoreCase(""))
					sql += " WHERE "+User.USERNAME_COLUMN+"='"+user.get_userName()+"'";
				Cursor c = db.getReadableDatabase().rawQuery(sql,null);
				//db.close();
				return c;
			}

			@Override
			protected void onPostExecute(Cursor result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri).get());		
	}


	public static Cursor getMoviesUser (User user,MyImdbDbHelper db) throws InterruptedException, ExecutionException
	{
		
		Object [] parametri = new Object[2];
		parametri[0] = db;
		parametri[1]=user;
		return (new AsyncTask <Object [], Void, Cursor>() {
			MyImdbDbHelper db;
			User user;
			@Override
			protected Cursor doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				user = (User)(params[0][1]);
				String sql = "SELECT * FROM "+Movie.TABLE_NAME;
				if(user.get_userName()!=null&&!user.get_userName().equalsIgnoreCase(""))
					sql += " WHERE "+Movie.USERNAME_COLUMN+"='"+user.get_userName()+"'";
				sql += " ORDER BY "+Movie.TITLE_COLUMN;
				Cursor c = db.getReadableDatabase().rawQuery(sql,null);
				//db.close();
				return c;
			}

			@Override
			protected void onPostExecute(Cursor result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri).get());
		
	}

	public static Cursor getMoviesFromText (User user,MyImdbDbHelper db,String text) throws InterruptedException, ExecutionException
	{
		Object [] parametri = new Object[3];
		parametri[0] =db;
		parametri[1]=user;
		parametri[2]=text;
		return (new AsyncTask <Object [], Void, Cursor>() {
			MyImdbDbHelper db;
			User user;
			String text;
			@Override
			protected Cursor doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				user = (User)(params[0][1]);
				text = (String)(params[0][2]);
				boolean where = false;
				String sql = "SELECT * FROM "+Movie.TABLE_NAME;
				if(user.get_userName()!=null&&!user.get_userName().equalsIgnoreCase(""))
				{
					if(!where)
					{
						sql += " WHERE "+Movie.USERNAME_COLUMN+"='"+user.get_userName()+"'";
						where = true;
					}
					else
						sql +=" AND "+Movie.USERNAME_COLUMN+"='"+user.get_userName()+"'";			
				}

				if(text!=null&&text.length()!=0)
				{
					if(!where)
					{
						sql += " WHERE "+Movie.TITLE_COLUMN+" LIKE '%"+text+"%'";
						where = true;
					}
					else
						sql +=" AND "+Movie.TITLE_COLUMN+" LIKE '%"+text+"%'";
				}
				sql += " ORDER BY "+Movie.TITLE_COLUMN;
				Cursor c = db.getReadableDatabase().rawQuery(sql,null);
				//db.close();
				return c;
			}

			@Override
			protected void onPostExecute(Cursor result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri).get());
	}

	public static Long getMaxMovieDateUser(User user,MyImdbDbHelper db) throws InterruptedException, ExecutionException
	{
		
		Object [] parametri = new Object[2];
		parametri[0] =db;
		parametri[1]=user;
		return (new AsyncTask <Object [], Void, Long>() {
			MyImdbDbHelper db;
			User user;
			@Override
			protected Long doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				user = (User)(params[0][1]);
				long ret = 0;
				String sql = "SELECT max(updated) FROM "+Movie.TABLE_NAME;
				if(user.get_userName()!=null&&!user.get_userName().equalsIgnoreCase(""))
					sql += " WHERE "+Movie.USERNAME_COLUMN+"='"+user.get_userName()+"'";
				Cursor c = db.getReadableDatabase().rawQuery(sql,null);
				if(c.getCount()==1)
				{
					c.moveToNext();
					if(c.getString(0)!=null)
					{
						//2014-07-31T09:35:39.584Z esempio formato data
						SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
						//format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
						try {  
							Date data = format.parse(c.getString(0));
							//format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
							ret = data.getTime();
						} catch (ParseException e) {  
							// TODO Auto-generated catch block  
							e.printStackTrace();  
						}
					}			
				}			
				return ret;
			}

			@Override
			protected void onPostExecute(Long result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri).get());
		
	}

	public static Cursor getMovieUser (Movie movie,MyImdbDbHelper db) throws InterruptedException, ExecutionException
	{
		Object [] parametri = new Object[2];
		parametri[0] =db;
		parametri[1]=movie;
		return (new AsyncTask <Object [], Void, Cursor>() {
			MyImdbDbHelper db;
			Movie movie;
			@Override
			protected Cursor doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				movie = (Movie)(params[0][1]);
				boolean where = false;
				String sql = "SELECT * FROM "+Movie.TABLE_NAME;
				if(movie.get_imdbID()!=null&&!movie.get_imdbID().equalsIgnoreCase(""))
				{
					if(where)
						sql += " AND "+Movie.IMDBID_COLUMN+"='"+movie.get_imdbID()+"'";
					else
					{
						sql += " WHERE "+Movie.IMDBID_COLUMN+"='"+movie.get_imdbID()+"'";
						where = true;
					}
				}			
				if(movie.get_userName()!=null&&!movie.get_userName().equalsIgnoreCase(""))
				{
					if(where)
						sql += " AND "+Movie.USERNAME_COLUMN+"='"+movie.get_userName()+"'";
					else
					{
						sql += " WHERE "+Movie.USERNAME_COLUMN+"='"+movie.get_userName()+"'";
						where = true;
					}
				}			
				Cursor c = db.getReadableDatabase().rawQuery(sql,null);
				//db.close();
				return c;
			}

			@Override
			protected void onPostExecute(Cursor result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri).get());	
	}

	public static void insertMovie (Movie movie,MyImdbDbHelper db)
	{
		Object [] parametri = new Object[2];
		parametri[0] = db;
		parametri[1]=movie;
		new AsyncTask <Object[], Void, Void>() {
			MyImdbDbHelper db;
			Movie movie;
			@Override
			protected Void doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				movie = (Movie)(params[0][1]);
				try {
					db.getWritableDatabase().execSQL(movie.getInsertSqlString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri);
	}

	public static void updateMovie (Movie movie,MyImdbDbHelper db)
	{
		Object [] parametri = new Object[2];
		parametri[0] = db;
		parametri[1]=movie;
		new AsyncTask <Object[], Void, Void>() {
			MyImdbDbHelper db;
			Movie movie;
			@Override
			protected Void doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				movie = (Movie)(params[0][1]);
				try {
					db.getWritableDatabase().execSQL(movie.getUpdateSqlString());
				} catch (Exception e) {
					e.printStackTrace();

				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri);
	}
	
	public static void deleteMovie (Movie movie,MyImdbDbHelper db)
	{
		Object [] parametri = new Object[2];
		parametri[0] = db;
		parametri[1]=movie;
		new AsyncTask <Object[], Void, Void>() {
			MyImdbDbHelper db;
			Movie movie;
			@Override
			protected Void doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				movie = (Movie)(params[0][1]);
				try {
					db.getWritableDatabase().execSQL(movie.getDeleteSqlString());
				} catch (Exception e) {
					e.printStackTrace();

				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri);
	}
	
	

	public static void deleteUser (Movie movie,MyImdbDbHelper db)
	{
		Object [] parametri = new Object[2];
		parametri[0] = db;
		parametri[1]=movie;
		new AsyncTask <Object[], Void, Void>() {
			MyImdbDbHelper db;
			Movie movie;
			@Override
			protected Void doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				movie = (Movie)(params[0][1]);
				try {
					db.getWritableDatabase().execSQL(movie.getDeleteSqlString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri);
	}


	public static void insertUser (User user,MyImdbDbHelper db)
	{
		Object [] parametri = new Object[2];
		parametri[0] = db;
		parametri[1]=user;
		new AsyncTask <Object[], Void, Void>() {
			MyImdbDbHelper db;
			User user;
			@Override
			protected Void doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				user = (User)(params[0][1]);
				try {
					db.getWritableDatabase().execSQL(user.getInsertSqlString());
				} catch (Exception e) {
					e.printStackTrace();			
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri);
	}




	public static void updateUser (User user,MyImdbDbHelper db)
	{
		Object [] parametri = new Object[2];
		parametri[0] = db;
		parametri[1]=user;
		new AsyncTask <Object[], Void, Void>() {
			MyImdbDbHelper db;
			User user;
			@Override
			protected Void doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				user = (User)(params[0][1]);
				try {
					db.getWritableDatabase().execSQL(user.getUpdateSqlString());
				} catch (Exception e) {
					e.printStackTrace();			
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri);
	}

	public static void deleteUser (User user,MyImdbDbHelper db)
	{
		Object [] parametri = new Object[2];
		parametri[0] = db;
		parametri[1]=user;
		new AsyncTask <Object[], Void, Void>() {
			MyImdbDbHelper db;
			User user;
			@Override
			protected Void doInBackground(Object[]... params) {
				db = (MyImdbDbHelper)(params[0][0]);
				user = (User)(params[0][1]);
				try {
					db.getWritableDatabase().execSQL(user.getDeleteSqlString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri);
	}
}
