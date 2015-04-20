package it.braccosoft.myimdb;


import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import it.braccosoft.myimdb.customlist.CustomList;
import it.braccosoft.myimdb.database.MyImdbDbHelper;
import it.braccosoft.myimdb.model.Movie;
import it.braccosoft.myimdb.model.Risposta;
import it.braccosoft.myimdb.model.User;
import it.braccosoft.utils.CursorBean;
import it.braccosoft.utils.HttpUtil;
import it.braccosoft.myimdb.R;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Filter.FilterListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

public class MovieListActivity extends ListActivity  {
	private User user;
	private MyImdbDbHelper db;
	private ActionBar ab;
	private Movie[]listaFilms = null;
	private ListView listView;
	private CustomList adapter;
	private DisplayImageOptions options;
	private ImageLoaderConfiguration config;
	private Spinner seen_spinner;
	private SearchView searchView;
	private ArrayAdapter<CharSequence> adapter_spinner;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new MyImdbDbHelper(getApplicationContext());
		user = new User();
		try {
			getUserInSession();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.loading)
		.showImageForEmptyUri(R.drawable.placeholder1)
		.showImageOnFail(R.drawable.placeholder1)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		//.displayer(new RoundedBitmapDisplayer(10))
		.build();

		// Create global configuration and initialize ImageLoader with this configuration
		config = new ImageLoaderConfiguration.Builder(getApplicationContext()).
				defaultDisplayImageOptions(options).build();
		ImageLoader.getInstance().init(config);


		setContentView(R.layout.activity_movies_list);
		try {
			listaFilms = getMovieUser();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter = new CustomList(this,listaFilms);
		listView = getListView();
		listView.setScrollingCacheEnabled(false);
		listView.setAdapter(adapter);
		listView.setClickable(true);
		listView.setTextFilterEnabled(true);
		// listening to single list item on click
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				ApriSchermataDettaglio(((Movie)parent.getItemAtPosition(position)));
				//Toast.makeText(getApplicationContext(),((Movie)parent.getItemAtPosition(position)).get_imdbID(),Toast.LENGTH_SHORT).show();
			}
		});
		ab = getActionBar();
		ab.setHomeButtonEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater mMenuInflater = getMenuInflater();
		mMenuInflater.inflate(R.menu.actionbar, menu);
		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchView = (SearchView)(menu.findItem(R.id.action_search).getActionView());
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		
		seen_spinner = (Spinner) menu.findItem(R.id.seen_spinner).getActionView();
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapter_spinner = ArrayAdapter.createFromResource(this,
		        R.array.seen_array_label, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		seen_spinner.setAdapter(adapter_spinner);
		seen_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				//String selectedVal = getResources().getStringArray(R.array.seen_array_value)[position];
				/*if(!getResources().getStringArray(R.array.seen_array_value)[position].equalsIgnoreCase("0_0"))
				{*/
					try {
						callSearch(searchView.getQuery().toString(),getResources().getStringArray(R.array.seen_array_value)[position]);
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				//          	Toast.makeText(getApplicationContext(),"onQueryTextSubmit "+query,Toast.LENGTH_SHORT).show();
				
				try {
					callSearch(query,getResources().getStringArray(R.array.seen_array_value)[seen_spinner.getSelectedItemPosition()]);
					return true;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				//              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
				//            	Toast.makeText(getApplicationContext(),"onQueryTextChange "+newText,Toast.LENGTH_SHORT).show();
				try {
					callSearch(newText,getResources().getStringArray(R.array.seen_array_value)[seen_spinner.getSelectedItemPosition()]);
					return true;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//              }
				return false;
			}

			

		});

		return super.onCreateOptionsMenu(menu);
	}

	
	public void callSearch(String query_text,String spinner) throws InterruptedException, ExecutionException {
		if (adapter != null) {
			adapter.getFilter().filter(query_text+Constants.SEPARATORE_FILTRO_LISTA_FILM+spinner);			
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if((item.getTitle()+"").equalsIgnoreCase("action_refresh"))
		{
			Object [] parametri = new Object[5];
			parametri[0] =db;
			parametri[1]=user;
			parametri[2]=listaFilms;
			parametri[3]=adapter;
			parametri[4]=listView;
			new AsyncTask <Object [], Void, Boolean>() {
				private MyImdbDbHelper db;
				private User user;
				private Movie[] listaFilms;
				private CustomList adapter;
				private ListView listview;
				@Override
				protected Boolean doInBackground(Object[]... params) {
					db = (MyImdbDbHelper)(params[0][0]);
					user = (User)(params[0][1]);

					adapter = (CustomList)(params[0][3]);
					listview = (ListView)(params[0][4]);
					MovieListActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),"aggiornamento in corso....",Toast.LENGTH_SHORT).show();
						}
					});

					try {
						Movie.UpdateMoviesFromServer((MyImdbDbHelper.getMaxMovieDateUser(user, db)), user, db, new HttpUtil(), new Risposta(), null, MovieListActivity.this);
						listaFilms = getMovieUser();						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						MovieListActivity.this.runOnUiThread(new Runnable(){
							@Override
							public void run(){
								Toast.makeText(getApplicationContext(),"aggiornamento non riuscito ",Toast.LENGTH_SHORT).show();
							}
						});

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						MovieListActivity.this.runOnUiThread(new Runnable(){
							@Override
							public void run(){
								Toast.makeText(getApplicationContext(),"aggiornamento non riuscito ",Toast.LENGTH_SHORT).show();
							}
						});
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						MovieListActivity.this.runOnUiThread(new Runnable(){
							@Override
							public void run(){
								Toast.makeText(getApplicationContext(),"aggiornamento non riuscito ",Toast.LENGTH_SHORT).show();
							}
						});
					}
					return true;
				}

				@Override
				protected void onPostExecute(Boolean result) {
					super.onPostExecute(result);
					adapter = new CustomList(MovieListActivity.this, listaFilms);
					listview.setAdapter(adapter);
					MovieListActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),"aggiornamento riuscito",Toast.LENGTH_SHORT).show();
						}
					});

				}
			}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, parametri);

		}

		return true;
	}

	private void ApriSchermataDettaglio(Movie movie)
	{
		Intent intent = new Intent(this, MovieDetailActivity.class);
		intent.putExtra("movie", movie);
		startActivity(intent);
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

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
		.setMessage("Sei sicuro di voler uscire?")
		.setCancelable(false)
		.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				MovieListActivity.this.finish();
			}
		})
		.setNegativeButton("No", null)
		.show();
	}

	@Override
	public void onActivityResult(int requestCode,int resultCode,Intent data) {
		super.onActivityResult(requestCode, resultCode, data);	
		if(data.getStringExtra("it.braccosoft.myimdb.queryquery")!=null&&data.getStringExtra("it.braccosoft.myimdb.queryquery").length()!=0)
		{
			Toast.makeText(getApplicationContext(),"Digitato "+data.getStringExtra("it.braccosoft.myimdb.queryquery"),Toast.LENGTH_SHORT).show();
			listView.setFilterText(data.getStringExtra("it.braccosoft.myimdb.queryquery"));
		}

	}

	private void getUserInSession () throws InterruptedException, ExecutionException
	{
		SharedPreferences sharedpreferences = getSharedPreferences(Constants.APPLICATION_NAME, Context.MODE_PRIVATE);
		user.set_userName(sharedpreferences.getString(Constants.PARAMETER_USERNAME_CODE, null));
		user = (User)CursorBean.CursorToObject(MyImdbDbHelper.getUser(user, db),user);
	}

	private Movie[] getMovieUser () throws InterruptedException, ExecutionException
	{
		return (Movie [])CursorBean.CursorsToVector(MyImdbDbHelper.getMoviesUser(user, db),new Movie());
	}

	private Movie[] getMovieFromText (String text) throws InterruptedException, ExecutionException
	{
		return (Movie [])CursorBean.CursorsToVector(MyImdbDbHelper.getMoviesFromText(user, db, text),new Movie());
	}

}
