package it.braccosoft.myimdb.model;

import it.braccosoft.utils.JsonBean;

import org.json.JSONObject;

public class Risposta {

	private String action;
	private String status;
	private String message;
	private String timestamp;
	private JSONObject object;
	
	public JSONObject get_object()
	{
		return object;
	}
	
	public void set_object(JSONObject object)
	{
		this.object = object;
	}
	
	public String get_action()
	{
		return action;
	}
	
	public void set_action(String action)
	{
		this.action = action;
	}
	
	public String get_status()
	{
		return status;
	}
	
	public void set_status(String status)
	{
		this.status = status;
	}
	
	public String get_message()
	{
		return message;
	}
	
	public void set_message(String message)
	{
		this.message = message;
	}
	
	public String get_timestamp()
	{
		return timestamp;
	}
	
	public void set_timestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}
	
	
	public Risposta (JSONObject json)
	{
		JsonBean.JsonToObject(json, this);
	}
	
	public Risposta ()
	{}
	
	public JSONObject getJsonUser ()
	{
		return JsonBean.ObjectToJson(this);
	}
	
}
