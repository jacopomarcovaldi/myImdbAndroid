package it.braccosoft.myimdb.model;

import java.io.Serializable;

public class LastLogin implements Serializable {
	
	private String userName;
	private String password;
	
	public static final String TABLE_NAME = "last_login";
	public static final String USERNAME_COLUMN = "userName";
	public static final String PASSWORD_COLUMN = "password";
	
	public void set_userName (String userName)
	{
		this.userName = userName;
	}
	
	public String get_userName ()
	{
		return userName;
	}

	public void set_password (String password)
	{
		this.password = password;
	}
	
	public String get_password ()
	{
		return password;
	}
	
	
	public String getInsertSqlString ()
	{
		String sql = new String("INSERT INTO "+LastLogin.TABLE_NAME+" (");
		sql += LastLogin.USERNAME_COLUMN;
		sql += ","+LastLogin.PASSWORD_COLUMN;
		sql +=") VALUES (";
		sql += "'"+this.userName+"'";
		sql += ","+"'"+this.password+"'";
		sql += ")";
		return sql;		
	}
	
	public String getDeleteSqlString ()
	{
		return new String("DELETE FROM "+LastLogin.TABLE_NAME);
	}
}
