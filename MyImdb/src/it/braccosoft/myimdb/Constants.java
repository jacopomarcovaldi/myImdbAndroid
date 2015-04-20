package it.braccosoft.myimdb;

public class Constants {
	public static final String SERVER_PROTOCOL = "http://";
	public static final String SERVER_URL = "myimdb-bracco.rhcloud.com";
	//public static final String SERVER_URL = "100.43.1.59";
	public static final String SERVER_LOGIN_URL = "/clientLogin";
	public static final String SERVER_GET_MOVIE_LIST_URL = "/getClientMovieList";
	public static final String SERVER_GET_DETAILS_MOVIE_URL = "/getClientDettagliFilmFromIdInterno";
	public static final int SERVER_PORT = 80;
	//public static final int SERVER_PORT = 3000;
	public static final int CONNECTION_TIMEOUT = 30;
	public static final int SOCKET_TIMEOUT = 30;
	public static final String CHARSET = "UTF-8";
	public static final String SERVER_COMPLETE_URL = SERVER_PROTOCOL+SERVER_URL+(SERVER_PORT!=80?":"+SERVER_PORT:"");
	public static final String CONTENT_TYPE_ATOM_XML = "application/atom+xml";
	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_X_WWW = "application/x-www-form-urlencoded";
	public static final String CONTENT_TYPE_XML = "application/xml";
	public static final String CONTENT_TYPE_FORM_DATA = "multipart/form-data";
	public static final String CONTENT_TYPE_HTML = "text/html";
	public static final String CONTENT_TYPE_PLAIN = "text/plain";
	public static final String PARAMETER_USERNAME_CODE = "username";
	public static final String PARAMETER_PASSWORD_CODE = "password";
	public static final String PARAMETER_TOKEN = "access_token";
	public static final String PARAMATER_HEADER_TOKEN = "x-access-token";
	public static final String PARAMATER_TIMESTAMP = "timestamp";
	
	public static final String JSON_PARAMATER_TOKEN = "token";
	public static final String JSON_PARAMATER_EXPIRES = "expires";
	public static final String JSON_PARAMATER_USER = "user";
	public static final String JSON_PARAMATER_MOVIES_LIST = "movies";
	
	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";
	
	public static final String APPLICATION_NAME = "MyImdb";
	
	public static final String SEPARATORE_FILTRO_LISTA_FILM = "§";
	public static final String SEEN_TUTTI = "0_0";
	public static final String SEEN_VISTI = "1_1";
	public static final String SEEN_NON_VISTI = "2_2";
}
