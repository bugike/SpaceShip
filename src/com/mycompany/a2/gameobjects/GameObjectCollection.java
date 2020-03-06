package com.mycompany.a2.gameobjects;

import java.util.Vector;

import com.mycompany.a2.interfaces.ICollection;
import com.mycompany.a2.interfaces.IIterator;

public class GameObjectCollection implements ICollection{
	private Vector<GameObject> theCollection;
	
	public GameObjectCollection() {
		theCollection = new Vector<GameObject>();
	}

	public void add(GameObject newObject) {
		theCollection.addElement(newObject);
	}

	public void remove(GameObject obj) {
		theCollection.remove(obj);
	}
	
	public void clear() {
		theCollection.clear();
	}
	
	public IIterator getIterator() {
		return new GameObjectVectorIterator();
	}
	
	public class GameObjectVectorIterator implements IIterator{
		private int currElementIndex;
		
		GameObjectVectorIterator(){
			currElementIndex = -1;
		}

		public boolean hasNext() {
			if (theCollection.size() <= 0)
				return false;
			if (currElementIndex == (theCollection.size() - 1))
				return false;
			return true;
		}

		public GameObject getNext() {
			currElementIndex ++;
			return theCollection.elementAt(currElementIndex);
		}
		
	}



}
