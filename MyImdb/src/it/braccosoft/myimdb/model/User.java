package it.braccosoft.myimdb.model;

import java.io.Serializable;

import it.braccosoft.utils.JsonBean;

import org.json.JSONObject;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName; 
	private String  email;
	private String nome;
	private String cognome;
	private String password;
	private String Added;
	private boolean enable;
	private String default_language;
	private String updated;
	private String token;
	
	public static final String TABLE_NAME = "users";
	
	public static final String USERNAME_COLUMN = "userName";
	public static final String EMAIL_COLUMN = "email";
	public static final String NOME_COLUMN = "nome";
	public static final String COGNOME_COLUMN = "cognome";
	public static final String PASSWORD_COLUMN = "password";
	public static final String ADDED_COLUMN = "Added";
	public static final String ENABLE_COLUMN = "enable";
	public static final String DEFAULTLANGUAGE_COLUMN = "default_language";
	public static final String UPDATED_COLUMN = "updated";
	public static final String TOKEN_COLUMN = "token";
		
	public User() {};
	
	public User(String userName)
	{
		super();
		this.userName = userName;
	}
	
	public User (JSONObject json)
	{
		JsonBean.JsonToObject(json, this);
	}
	
	public JSONObject getJsonUser ()
	{
		return JsonBean.ObjectToJson(this);
	}
	
	public void set_token (String token)
	{
		this.token = token;
	}
	
	public String get_token ()
	{
		return token;
	}
	
	public void set_userName (String userName)
	{
		this.userName = userName;
	}
	
	public String get_userName ()
	{
		return userName;
	}
	
	public void set_email (String email)
	{
		this.email = email;
	}
	
	public String get_email ()
	{
		return email;
	}
	
	public void set_nome (String nome)
	{
		this.nome = nome;
	}
	
	public String get_nome ()
	{
		return nome;
	}
	
	public void set_cognome (String cognome)
	{
		this.cognome = cognome;
	}
	
	public String get_cognome ()
	{
		return cognome;
	}
	
	public void set_password (String password)
	{
		this.password = password;
	}
	
	public String get_password ()
	{
		return password;
	}
	
	public void set_Added (String Added)
	{
		this.Added = Added;
	}
	
	public String get_Added ()
	{
		return Added;
	}
	
	public void set_enable (boolean enable)
	{
		this.enable = enable;
	}
	
	public boolean get_enable ()
	{
		return enable;
	}
	
	public void set_default_language (String default_language)
	{
		this.default_language = default_language;
	}
	
	public String get_default_language ()
	{
		return default_language;
	}
	
	public void set_updated (String updated)
	{
		this.updated = updated;
	}
	
	public String get_updated ()
	{
		return updated;
	}
	
	public String getUpdateSqlString ()
	{
		boolean virgola = false;
		String sql = new String("UPDATE "+User.TABLE_NAME+" SET ");
		if(this.Added != null && this.Added.length()>0)
		{
			sql += User.ADDED_COLUMN+"='"+this.Added+"'";
			virgola = true;
		}
		if(this.cognome!=null && this.cognome.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += User.COGNOME_COLUMN+"='"+this.cognome+"'";
			virgola = true;
		}
		if(this.default_language!=null && this.default_language.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += User.DEFAULTLANGUAGE_COLUMN+"='"+this.default_language+"'";
			virgola = true;
		}
		if(this.email!=null && this.email.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += User.EMAIL_COLUMN+"='"+this.email+"'";
			virgola = true;
		}
		if(this.updated!=null && this.updated.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += User.UPDATED_COLUMN+"='"+this.updated+"'";
			virgola = true;
		}
		if(this.nome!=null && this.nome.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += User.NOME_COLUMN+"='"+this.nome+"'";
			virgola = true;
		}
		if(this.password!=null && this.password.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += User.PASSWORD_COLUMN+"='"+this.password+"'";
			virgola = true;
		}
		if(this.token!=null && this.token.length()>0)
		{
			if(virgola)
				sql += ",";
			sql += User.TOKEN_COLUMN+"='"+this.token+"'";
			virgola = true;
		}
		
		if(virgola)
			sql += ",";
		sql += User.ENABLE_COLUMN+"='"+this.enable+"'";
		
		sql += " WHERE "+User.USERNAME_COLUMN+"='"+this.userName+"'";
		
		return sql;		
	}
	
	public String getInsertSqlString ()
	{
		String sql = new String("INSERT INTO "+User.TABLE_NAME+" (");
		sql += User.ADDED_COLUMN;
		sql += ","+User.COGNOME_COLUMN;
		sql += ","+User.DEFAULTLANGUAGE_COLUMN;
		sql += ","+User.EMAIL_COLUMN;
		sql += ","+User.UPDATED_COLUMN;
		sql += ","+User.NOME_COLUMN;
		sql += ","+User.PASSWORD_COLUMN;
		sql += ","+User.TOKEN_COLUMN;
		sql += ","+User.ENABLE_COLUMN;
		sql += ","+User.USERNAME_COLUMN;
		sql +=") VALUES (";
		sql += "'"+this.Added+"'";
		sql += ","+"'"+this.cognome+"'";
		sql += ","+"'"+this.default_language+"'";
		sql += ","+"'"+this.email+"'";
		sql += ","+"'"+this.updated+"'";
		sql += ","+"'"+this.nome+"'";
		sql += ","+"'"+this.password+"'";
		sql += ","+"'"+this.token+"'";
		sql += ","+"'"+this.enable+"'";
		sql += ","+"'"+this.userName+"'";
		sql += ")";
		return sql;		
	}
	
	public String getDeleteSqlString ()
	{
		return new String("DELETE FROM "+User.TABLE_NAME+" WHERE "+User.USERNAME_COLUMN+"='"+this.userName+"'");
	}
	
}
