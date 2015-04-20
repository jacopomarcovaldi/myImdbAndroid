package it.braccosoft.myimdb;

import java.util.concurrent.ExecutionException;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import it.braccosoft.myimdb.R;
import it.braccosoft.myimdb.database.MyImdbDbHelper;
import it.braccosoft.myimdb.model.Movie;
import it.braccosoft.myimdb.model.User;
import it.braccosoft.utils.CursorBean;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

public class MovieDetailActivity extends Activity {

	private User user;
	private MyImdbDbHelper db;
	private ImageView poster;
	private EditText titolo;
	private EditText anno;
	private EditText genere;
	private EditText trama;
	private EditText attori;
	private EditText durata;
	private EditText regista;
	private RatingBar imdbVote;
	private Movie movie;
	private ActionBar ab;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private ImageLoaderConfiguration config;
	private CheckBox seen;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new MyImdbDbHelper(getApplicationContext());
		user = new User();
		movie = ((Movie)getIntent().getSerializableExtra("movie"));
		getUserInSession();
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.placeholder1)
		.showImageForEmptyUri(R.drawable.placeholder1)
		.showImageOnFail(R.drawable.placeholder1)
		.cacheInMemory(false)
		.cacheOnDisk(false)
		.considerExifParams(false)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
		
		// Create global configuration and initialize ImageLoader with this configuration
	    config = new ImageLoaderConfiguration.Builder(getApplicationContext()).
	            defaultDisplayImageOptions(options).build();
	    ImageLoader.getInstance().init(config);
		setContentView(R.layout.activity_detail_movie);
		poster = (ImageView)this.findViewById(R.id.poster);
		titolo = (EditText)this.findViewById(R.id.editTitolo);
		titolo.setText(movie.get_Title());
		anno = (EditText)this.findViewById(R.id.editAnno);
		anno.setText(movie.get_Year());
		genere = (EditText)this.findViewById(R.id.editGenere);
		genere.setText(movie.get_Genre());
		trama = (EditText)this.findViewById(R.id.editTrama);
		trama.setText(movie.get_Plot());
		attori = (EditText)this.findViewById(R.id.editAttori);
		attori.setText(movie.get_Actors());
		regista = (EditText)this.findViewById(R.id.editRegista);
		regista.setText(movie.get_Director());
		seen = (CheckBox)this.findViewById(R.id.checkbox_seen);
		seen.setChecked(movie.get_seen()!=null&&movie.get_seen().equalsIgnoreCase("1"));
		seen.setClickable(false);
		durata = (EditText)this.findViewById(R.id.editDurata);
		durata.setText(movie.get_Runtime());
		imdbVote = (RatingBar)this.findViewById(R.id.rbImdbRating);
		imdbVote.setRating((Float.parseFloat(movie.get_imdbRating())*5)/10);
		imdbVote.invalidate();
		poster.setVisibility(ImageView.VISIBLE);
		imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(movie.get_Poster(), poster);
		
		/*Object [] parametri = new Object[4];
		parametri[0]=movie;
		parametri[1]=this;
		parametri[2] = poster;
		parametri[3] = imageLoader;
		new AsyncTask <Object [], Void, Bitmap>() {			
			private Movie movie;
			private Activity context;
			private ImageView poster;
			private ImageLoader imageLoader;
			@Override
			protected Bitmap doInBackground(Object[]... params) {
				movie = (Movie)(params[0][0]);
				context = (Activity)(params[0][1]);
				poster = (ImageView)(params[0][2]);
				imageLoader = (ImageLoader)(params[0][3]);
				try {
					return BitmapFactory.decodeStream(new BufferedInputStream(FileUtil.getFileFromMemory(FileUtil.getFileName(context, movie.get_imdbID()))));					
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				super.onPostExecute(result);
				poster.setImageBitmap(result);
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri);	*/	
		ab = getActionBar();
		ab.setHomeButtonEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		this.finish();
		return true;
	}
	
	private void getUserInSession ()
	{
		SharedPreferences sharedpreferences = getSharedPreferences(Constants.APPLICATION_NAME, Context.MODE_PRIVATE);
		user.set_userName(sharedpreferences.getString(Constants.PARAMETER_USERNAME_CODE, null));
		try {
			user = (User)CursorBean.CursorToObject(MyImdbDbHelper.getUser(user, db),user);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
