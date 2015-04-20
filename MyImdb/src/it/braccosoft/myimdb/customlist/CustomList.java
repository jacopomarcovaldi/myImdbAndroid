package it.braccosoft.myimdb.customlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import com.nostra13.universalimageloader.core.ImageLoader;

import it.braccosoft.myimdb.model.Movie;


import it.braccosoft.myimdb.Constants;
import it.braccosoft.myimdb.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
public class CustomList extends ArrayAdapter<Object> implements Filterable{
	private final Activity context;
	private Movie[] film;
	private Movie[] backupFilmList;
	private ImageLoader imageLoader;
	public CustomList(Activity context,Movie[] film) {
		super(context, R.layout.movie_list_structure);
		this.context = context;
		this.film = film;
		this.backupFilmList = film;
		imageLoader = ImageLoader.getInstance();
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.movie_list_structure, parent, false);
			holder.text = ((TextView) view.findViewById(R.id.info));
			holder.icon = (ImageView) view.findViewById(R.id.flyer);
			holder.seen = (CheckBox)view.findViewById(R.id.seen);
			view.setTag(holder);			
		} 
		else 
		{
			holder = (ViewHolder) view.getTag();
		}		
		holder.position = position;
		if(film[position]!=null)
		{
			holder.idImdb = film[position].get_imdbID();		
			holder.text.setText(film[position].get_Title());
			imageLoader.displayImage(film[position].get_Poster(), holder.icon);
			if(film[position].get_seen()!=null && film[position].get_seen().equalsIgnoreCase("1"))
				holder.seen.setChecked(true);
			else
				holder.seen.setChecked(false);
			holder.seen.setClickable(false);
		}		
		return view;
	}

	@Override
	public int getCount ()
	{
		return film!=null?film.length:0;
	}

	@Override
	public Movie getItem(int position)
	{
		return film!=null&&film.length!=0?film[position]:null;
	}


	@Override
	public Filter getFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				FilterResults results = new FilterResults();
				// la charSequence che mi arriva ha i due campi per filtrare divisi da §
				StringTokenizer st = new StringTokenizer(charSequence.toString(),Constants.SEPARATORE_FILTRO_LISTA_FILM);
				String spinner;
				String testo = null;
				if(st.countTokens() == 1)
				{
					spinner = st.nextToken();
				}
				else 
				{
					testo = st.nextToken();
					spinner = st.nextToken();
				}


				// If there's nothing to filter on, return the original data for
				// your list
				if ((testo == null || testo.length() == 0) && spinner.equalsIgnoreCase(Constants.SEEN_TUTTI)) {
					results.values = backupFilmList;
					results.count = backupFilmList.length;
				} else if(testo == null || testo.length() == 0 && !spinner.equalsIgnoreCase(Constants.SEEN_TUTTI)){
					// Testo nullo
					List<Movie> filterResultsData = new ArrayList<Movie>();					
					for (Movie c : backupFilmList) {
						if (c.get_seen().equalsIgnoreCase("1") && spinner.equalsIgnoreCase(Constants.SEEN_VISTI)
								||
								c.get_seen().equalsIgnoreCase("0") && spinner.equalsIgnoreCase(Constants.SEEN_NON_VISTI)
								) 
						{
							filterResultsData.add(c);
						}
					}
					results.values = filterResultsData;
					results.count = filterResultsData.size();
				}
				else if(testo != null && testo.length() != 0 && !spinner.equalsIgnoreCase(Constants.SEEN_TUTTI))
				{
					//entrambi i filtri attivi
					List<Movie> filterResultsData = new ArrayList<Movie>();					
					for (Movie c : backupFilmList) {
						if ( (c.get_seen().equalsIgnoreCase("1") && spinner.equalsIgnoreCase(Constants.SEEN_VISTI)
								||
								c.get_seen().equalsIgnoreCase("0") && spinner.equalsIgnoreCase(Constants.SEEN_NON_VISTI))
								&& c.get_Title().toLowerCase(Locale.ITALIAN).contains(testo)
								) 
						{
							filterResultsData.add(c);
						}
					}
					results.values = filterResultsData;
					results.count = filterResultsData.size();
				}
				else if(testo != null && testo.length() != 0 && spinner.equalsIgnoreCase(Constants.SEEN_TUTTI))
				{
					List<Movie> filterResultsData = new ArrayList<Movie>();					
					for (Movie c : backupFilmList) {
						if (c.get_Title().toLowerCase(Locale.ITALIAN).contains(testo)
								) 
						{
							filterResultsData.add(c);
						}
					}
					results.values = filterResultsData;
					results.count = filterResultsData.size();
				}
				return results;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence charSequence,FilterResults filterResults) {
				try {
					film = new Movie[((List<Movie>) filterResults.values).size()];
					int i = 0;
					for(Movie c : (List<Movie>) filterResults.values)
					{
						film[i] = c;
						i++;
					}
				} catch (Exception e) {
					film = (Movie[])filterResults.values;
				}
				finally
				{
					notifyDataSetChanged();
				}				
			}
		};
	}
}