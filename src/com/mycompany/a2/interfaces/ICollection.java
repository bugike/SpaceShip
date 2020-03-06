package com.mycompany.a2.interfaces;

import com.mycompany.a2.gameobjects.GameObject;

public interface ICollection {
	public void add(GameObject newObject);
	
	public void remove(GameObject obj);
	
	public IIterator getIterator();
}
