package it.braccosoft.utils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.database.Cursor;

public class CursorBean {

	public static Object CursorToObject (Cursor c, Object bean)
	{
		if(c.getCount()>0 && c.getColumnCount()>0)
		{
			if(c!=null && c.getCount()>0 && c.isBeforeFirst())
				c.moveToNext();
			Method[] metodi = bean.getClass().getMethods();
			
			try {
				bean = bean.getClass().getConstructor(null).newInstance(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for(int i=0;i<metodi.length;i++)
			{
				if(metodi[i].getName().indexOf("set_")!=-1 && c.getColumnIndex(metodi[i].getName().replace("set_", ""))!= -1)
				{
					try {
						metodi[i].invoke(bean,c.getString(c.getColumnIndex(metodi[i].getName().replace("set_", ""))));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}			
			}
		}
		return bean;
	}

	public static Object[] CursorsToVector (Cursor c, Object bean)
	{
		Object[] prova = (Object[])Array.newInstance(bean.getClass(),(c.getCount()>0?c.getCount():0));
		try {
			if(c!=null && c.getCount()>0 && c.isBeforeFirst())
				c.moveToNext();
			while (!c.isAfterLast()) {
				Array.set(prova, c.getPosition(), CursorToObject(c, bean));
				c.moveToNext();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return prova;
	}
}
