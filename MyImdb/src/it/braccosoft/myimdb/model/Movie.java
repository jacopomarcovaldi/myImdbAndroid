package it.braccosoft.myimdb.model;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import it.braccosoft.myimdb.Constants;
import it.braccosoft.myimdb.database.MyImdbDbHelper;
import it.braccosoft.utils.HttpUtil;
import it.braccosoft.utils.JsonBean;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Parcel;
import android.widget.ProgressBar;

public class Movie implements Serializable{

	private String Title; 
	private String Year;
	private String Rated;
	private String Released;
	private String Runtime;
	private String Genre;
	private String Director;
	private String Writer;
	private String Actors;
	private String Plot;
	private String plot_translated;
	private String Language;
	private String Country;
	private String Awards;
	private String Poster;
	private String Metascore;
	private String imdbRating;
	private String imdbVotes;
	private String imdbID;
	private	String Type;
	private String Added;
	private String userName;
	private String updated;
	private String deleted;
	private String seen;
	
	
	public static final String TITLE_COLUMN = "Title";
	public static final String YEAR_COLUMN = "Year";
	public static final String RATED_COLUMN = "Rated";
	public static final String RELEASED_COLUMN = "Released";
	public static final String RUNTIME_COLUMN = "Runtime";
	public static final String GENRE_COLUMN = "Genre";
	public static final String DIRECTOR_COLUMN = "Director";
	public static final String WRITER_COLUMN = "Writer";
	public static final String ACTORS_COLUMN = "Actors";
	public static final String PLOT_COLUMN = "Plot";
	public static final String PLOT_TRANSLATED_COLUMN = "plot_translated";
	public static final String LANGUAGE_COLUMN = "Language";
	public static final String COUNTRY_COLUMN = "Country";
	public static final String AWARDS_COLUMN = "Awards";
	public static final String POSTER_COLUMN = "Poster";
	public static final String METASCORE_COLUMN = "Metascore";
	public static final String IMDBRATING_COLUMN = "imdbRating";
	public static final String IMDBVOTES_COLUMN = "imdbVotes";
	public static final String IMDBID_COLUMN = "imdbID";
	public static final String TYPE_COLUMN = "Type";
	public static final String ADDED_COLUMN = "Added";
	public static final String USERNAME_COLUMN = "userName";
	public static final String TABLE_NAME = "movies";
	public static final String UPDATED_COLUMN = "updated";
	public static final String DELETED_COLUMN = "deleted";
	public static final String SEEN_COLUMN = "seen";
	
	public Movie (){};
	
	public Movie (String userName,String imdbID)
	{
		this.userName = userName;
		this.imdbID = imdbID;
	}
	
	public Movie (Parcel source)
	{
		Title = source.readString().replaceAll("\"", "");
		Year = source.readString().replaceAll("\"", "");
		Rated= source.readString().replaceAll("\"", "");
		Released = source.readString().replaceAll("\"", "");
		Runtime = source.readString().replaceAll("\"", "");
		Genre= source.readString().replaceAll("\"", "");
		Director = source.readString().replaceAll("\"", "");
		Writer = source.readString().replaceAll("\"", "");
		Actors= source.readString().replaceAll("\"", "");
		Plot = source.readString().replaceAll("\"", "");
		plot_translated = source.readString().replaceAll("\"", "");
		Language = source.readString().replaceAll("\"", "");
		Country = source.readString().replaceAll("\"", "");
		Awards = source.readString().replaceAll("\"", "");
		Poster = source.readString().replaceAll("\"", "");
		Metascore = source.readString().replaceAll("\"", "");
		imdbRating = source.readString().replaceAll("\"", "");
		imdbVotes= source.readString().replaceAll("\"", "");
		imdbID = source.readString().replaceAll("\"", "");
		Type= source.readString().replaceAll("\"", "");
		Added= source.readString().replaceAll("\"", "");
		userName= source.readString().replaceAll("\"", "");
		updated= source.readString().replaceAll("\"", "");
		deleted = source.readString().replaceAll("\"", "");
		seen = source.readString().replaceAll("\"", "");
	}
	
	public void set_deleted (String deleted)
	{
		this.deleted = deleted.replaceAll("\"", "");
	}
	
	public String get_deleted ()
	{
		return deleted;
	}
	
	
	public void set_Title (String Title)
	{
		this.Title = Title.replaceAll("\"", "");
	}
	
	public String get_Title ()
	{
		return Title;
	}
	
	public void set_Year (String Year)
	{
		this.Year = Year.replaceAll("\"", "");
	}
	
	public String get_Year()
	{
		return Year;
	}
	
	public void set_Rated(String Rated)
	{
		this.Rated = Rated.replaceAll("\"", "");
	}
	
	public String get_Rated()
	{
		return Rated;
	}
	
	public void set_userName(String userName)
	{
		this.userName = userName.replaceAll("\"", "");
	}
	
	public String get_userName()
	{
		return userName;
	}	
	
	public void set_Added(String Added)
	{
		this.Added = Added.replaceAll("\"", "");
	}
	
	public String get_Added()
	{
		return Added;
	}	
	
	public void set_Type(String Type)
	{
		this.Type = Type.replaceAll("\"", "");
	}
	
	public String get_Type()
	{
		return Type;
	}	
	
	public void set_imdbID(String imdbID)
	{
		this.imdbID = imdbID.replaceAll("\"", "");
	}
	
	public String get_imdbID()
	{
		return imdbID;
	}	
	
	public void set_imdbVotes(String imdbVotes)
	{
		this.imdbVotes = imdbVotes.replaceAll("\"", "");
	}
	
	public String get_imdbVotes()
	{
		return imdbVotes;
	}	
	
	public void set_imdbRating(String imdbRating)
	{
		this.imdbRating = imdbRating.replaceAll("\"", "");
	}
	
	public String get_imdbRating()
	{
		return imdbRating;
	}	
	
	public void set_Metascore(String Metascore)
	{
		this.Metascore = Metascore.replaceAll("\"", "");
	}
	
	public String get_Metascore()
	{
		return Metascore;
	}	
	
	public void set_Poster(String Poster)
	{
		this.Poster = Poster.replaceAll("\"", "");
	}
	
	public String get_Poster()
	{
		return Poster;
	}	
	
	public void set_Awards(String Awards)
	{
		this.Awards = Awards.replaceAll("\"", "");
	}
	
	public String get_Awards()
	{
		return Awards;
	}
		
	public void set_Country(String Country)
	{
		this.Country = Country.replaceAll("\"", "");
	}
	
	public String get_Country()
	{
		return Country;
	}	
	
	public void set_Language(String Language)
	{
		this.Language = Language.replaceAll("\"", "");
	}
	
	public String get_Language()
	{
		return Language;
	}	
	
	public void set_plot_translated(String plot_translated)
	{
		this.plot_translated = plot_translated.replaceAll("\"", "");
	}
	
	public String get_plot_translated()
	{
		return plot_translated;
	}
	
	public void set_Plot(String Plot)
	{
		this.Plot = Plot.replaceAll("\"", "");
	}
	
	public String get_Plot()
	{
		return Plot;
	}
	
	public void set_Actors(String Actors)
	{
		this.Actors = Actors.replaceAll("\"", "");
	}
	
	public String get_Actors()
	{
		return Actors;
	}
	
	public void set_Writer(String Writer)
	{
		this.Writer = Writer.replaceAll("\"", "");
	}
	
	public String get_Writer()
	{
		return Writer;
	}	
	
	public void set_Director(String Director)
	{
		this.Director = Director.replaceAll("\"", "");
	}
	
	public String get_Director()
	{
		return Director;
	}
	
	public void set_Genre(String Genre)
	{
		this.Genre = Genre.replaceAll("\"", "");
	}
	
	public String get_Genre()
	{
		return Genre;
	}
		
	public void set_Runtime(String Runtime)
	{
		this.Runtime = Runtime.replaceAll("\"", "");
	}
	
	public String get_Runtime()
	{
		return Runtime;
	}
	
	public void set_Released(String Released)
	{
		this.Released = Released.replaceAll("\"", "");
	}
	
	public String get_Released()
	{
		return Released;
	}
	
	public Movie (JSONObject json)
	{
		JsonBean.JsonToObject(json, this);
	}
	
	public JSONObject getJsonUser ()
	{
		return JsonBean.ObjectToJson(this);
	}
	
	public void set_updated (String updated)
	{
		this.updated = updated.replaceAll("\"", "");
	}
	
	public String get_updated ()
	{
		return updated;
	}
	
	
	public void set_seen (String seen)
	{
		this.seen = seen.replaceAll("\"", "");
	}
	
	public String get_seen ()
	{
		return seen;
	}
	
	public String getUpdateSqlString ()
	{
		boolean virgola = false;
		String sql = new String("UPDATE "+Movie.TABLE_NAME+" SET ");
		if(this.Added != null && this.Added.length()>0)
		{
			sql += Movie.ADDED_COLUMN+"=\""+this.Added+"\"";
			virgola = true;
		}
		if(this.Actors!=null && this.Actors.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.ACTORS_COLUMN+"=\""+this.Actors+"\"";
			virgola = true;
		}
		if(this.Awards!=null && this.Awards.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.AWARDS_COLUMN+"=\""+this.Awards+"\"";
			virgola = true;
		}
		if(this.Country!=null && this.Country.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.COUNTRY_COLUMN+"=\""+this.Country+"\"";
			virgola = true;
		}
		if(this.updated!=null && this.updated.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.UPDATED_COLUMN+"=\""+this.updated+"\"";
			virgola = true;
		}
		if(this.Director!=null && this.Director.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.DIRECTOR_COLUMN+"=\""+this.Director+"\"";
			virgola = true;
		}
		if(this.Genre!=null && this.Genre.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.GENRE_COLUMN+"=\""+this.Genre+"\"";
			virgola = true;
		}
		
		if(this.imdbRating!=null && this.imdbRating.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.IMDBRATING_COLUMN+"=\""+this.imdbRating+"\"";
			virgola = true;
		}
		if(this.imdbVotes!=null && this.imdbVotes.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.IMDBVOTES_COLUMN+"=\""+this.imdbVotes+"\"";
			virgola = true;
		}
		if(this.Language!=null && this.Language.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.LANGUAGE_COLUMN+"=\""+this.Language+"\"";
			virgola = true;
		}
		if(this.Metascore!=null && this.Metascore.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.METASCORE_COLUMN+"=\""+this.Metascore+"\"";
			virgola = true;
		}
		if(this.Plot!=null && this.Plot.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.PLOT_COLUMN+"=\""+this.Plot+"\"";
			virgola = true;
		}
		if(this.plot_translated!=null && this.plot_translated.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.PLOT_TRANSLATED_COLUMN+"=\""+this.plot_translated+"\"";
			virgola = true;
		}
		if(this.Poster!=null && this.Poster.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.POSTER_COLUMN+"=\""+this.Poster+"\"";
			virgola = true;
		}
		if(this.Rated!=null && this.Rated.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.RATED_COLUMN+"=\""+this.Rated+"\"";
			virgola = true;
		}
		if(this.Released!=null && this.Released.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.RELEASED_COLUMN+"=\""+this.Released+"\"";
			virgola = true;
		}
		if(this.Runtime!=null && this.Runtime.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.RUNTIME_COLUMN+"=\""+this.Runtime+"\"";
			virgola = true;
		}
		if(this.Title!=null && this.Title.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.TITLE_COLUMN+"=\""+this.Title+"\"";
			virgola = true;
		}
		if(this.Type!=null && this.Type.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.TYPE_COLUMN+"=\""+this.Type+"\"";
			virgola = true;
		}
		if(this.updated!=null && this.updated.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.UPDATED_COLUMN+"=\""+this.updated+"\"";
			virgola = true;
		}
		
		if(this.Writer!=null && this.Writer.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.WRITER_COLUMN+"=\""+this.Writer+"\"";
			virgola = true;
		}
		if(this.Year!=null && this.Year.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.YEAR_COLUMN+"=\""+this.Year+"\"";
			virgola = true;
		}
		if(this.seen!=null && this.seen.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += Movie.SEEN_COLUMN+"=\""+this.seen+"\"";
		}
		
		sql += " WHERE "+Movie.USERNAME_COLUMN+"=\""+this.userName+"\"";
		sql += " AND "+Movie.IMDBID_COLUMN+"=\""+this.imdbID+"\"";
		
		return sql;		
	}
	
	public String getInsertSqlString ()
	{
		String sql = new String("INSERT INTO "+Movie.TABLE_NAME+" (");
		sql += Movie.ADDED_COLUMN;
		sql += ","+Movie.ACTORS_COLUMN;
		sql += ","+Movie.AWARDS_COLUMN;
		sql += ","+Movie.COUNTRY_COLUMN;
		sql += ","+Movie.UPDATED_COLUMN;
		sql += ","+Movie.DIRECTOR_COLUMN;
		sql += ","+Movie.GENRE_COLUMN;
		sql += ","+Movie.IMDBID_COLUMN;
		sql += ","+Movie.IMDBRATING_COLUMN;
		sql += ","+Movie.IMDBVOTES_COLUMN;
		sql += ","+Movie.LANGUAGE_COLUMN;
		sql += ","+Movie.METASCORE_COLUMN;
		sql += ","+Movie.PLOT_COLUMN;
		sql += ","+Movie.PLOT_TRANSLATED_COLUMN;
		sql += ","+Movie.POSTER_COLUMN;
		sql += ","+Movie.RATED_COLUMN;
		sql += ","+Movie.RELEASED_COLUMN;
		sql += ","+Movie.RUNTIME_COLUMN;
		sql += ","+Movie.TITLE_COLUMN;
		sql += ","+Movie.TYPE_COLUMN;
		sql += ","+Movie.USERNAME_COLUMN;
		sql += ","+Movie.WRITER_COLUMN;
		sql += ","+Movie.YEAR_COLUMN;
		sql += ","+Movie.SEEN_COLUMN;
		sql +=") VALUES (";
		sql += "\""+this.Added+"\"";
		sql += ","+"\""+this.Actors+"\"";
		sql += ","+"\""+this.Awards+"\"";
		sql += ","+"\""+this.Country+"\"";
		sql += ","+"\""+this.updated+"\"";
		sql += ","+"\""+this.Director+"\"";
		sql += ","+"\""+this.Genre+"\"";
		sql += ","+"\""+this.imdbID+"\"";
		sql += ","+"\""+this.imdbRating+"\"";
		sql += ","+"\""+this.imdbVotes+"\"";
		sql += ","+"\""+this.Language+"\"";
		sql += ","+"\""+this.Metascore+"\"";
		sql += ","+"\""+this.Plot+"\"";
		sql += ","+"\""+this.plot_translated+"\"";
		sql += ","+"\""+this.Poster+"\"";
		sql += ","+"\""+this.Rated+"\"";
		sql += ","+"\""+this.Released+"\"";
		sql += ","+"\""+this.Runtime+"\"";
		sql += ","+"\""+this.Title+"\"";
		sql += ","+"\""+this.Type+"\"";
		sql += ","+"\""+this.userName+"\"";
		sql += ","+"\""+this.Writer+"\"";
		sql += ","+"\""+this.Year+"\"";
		sql += ","+"\""+this.seen+"\"";
		sql += ")";
		return sql;		
	}
	
	public String getDeleteSqlString ()
	{
		return new String("DELETE FROM "+Movie.TABLE_NAME+" WHERE "+Movie.USERNAME_COLUMN+"=\""+this.userName+"\""+" AND "+Movie.IMDBID_COLUMN+"=\""+this.imdbID+"\"");
	}
	
	public static void UpdateMoviesFromServer (Long timestamp,User user,MyImdbDbHelper db,HttpUtil http,Risposta risposta,ProgressBar progress,Context context) throws JSONException, InterruptedException, ExecutionException
	{
		if(timestamp>0)
		{						
			http = new HttpUtil(Constants.HTTP_METHOD_GET,Constants.SERVER_COMPLETE_URL+Constants.SERVER_GET_MOVIE_LIST_URL, Constants.CONTENT_TYPE_X_WWW, Constants.CONTENT_TYPE_JSON, "?"+Constants.PARAMETER_TOKEN+"="+user.get_token()+"&"+Constants.PARAMATER_TIMESTAMP+"="+timestamp,context,progress);
		}						
		else
			http = new HttpUtil(Constants.HTTP_METHOD_GET,Constants.SERVER_COMPLETE_URL+Constants.SERVER_GET_MOVIE_LIST_URL, Constants.CONTENT_TYPE_X_WWW, Constants.CONTENT_TYPE_JSON, "?"+Constants.PARAMETER_TOKEN+"="+user.get_token(),context,progress);
		risposta = new Risposta(new JSONObject(http.execute("").get()));
		//Toast.makeText(getApplicationContext(),"lista film:"+risposta.get_object().getJSONArray(Constants.JSON_PARAMATER_MOVIES_LIST).length(),Toast.LENGTH_LONG).show();
		if(risposta.get_object().getJSONArray(Constants.JSON_PARAMATER_MOVIES_LIST) != null && risposta.get_object().getJSONArray(Constants.JSON_PARAMATER_MOVIES_LIST).length()>0)
		{
			Movie movie = new Movie();
			for(int i=0;i<risposta.get_object().getJSONArray(Constants.JSON_PARAMATER_MOVIES_LIST).length();i++)
			{
				movie = new Movie((JSONObject)risposta.get_object().getJSONArray(Constants.JSON_PARAMATER_MOVIES_LIST).get(i));
				movie.set_userName(user.get_userName());
				if(movie.get_deleted()!=null && movie.get_deleted().equalsIgnoreCase("true"))
				{
					MyImdbDbHelper.deleteMovie(movie, db);
				}
				else
				{
					MyImdbDbHelper.insertMovie(movie, db);
					MyImdbDbHelper.updateMovie(movie, db);
				}				
			}
		}	
	}

	/*@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Title);
		dest.writeString(Year);
		dest.writeString(Rated);
		dest.writeString(Released);
		dest.writeString(Runtime);
		dest.writeString(Genre);
		dest.writeString(Director);
		dest.writeString(Writer);
		dest.writeString(Actors);
		dest.writeString(Plot);
		dest.writeString(plot_translated);
		dest.writeString(Language);
		dest.writeString(Country);
		dest.writeString(Awards);
		dest.writeString(Poster);
		dest.writeString(Metascore);
		dest.writeString(imdbRating);
		dest.writeString(imdbVotes);
		dest.writeString(imdbID);
		dest.writeString(Type);
		dest.writeString(Added);
		dest.writeString(userName);
		dest.writeString(updated);
	}*/
	
	/*public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {

		@Override
		public Movie createFromParcel(Parcel source) {
			return new Movie(source);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};*/
	
}
