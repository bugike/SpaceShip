package com.mycompany.a2.interfaces;

public interface ICollider {
	public boolean collidesWith(ICollider otherObject);
	
	public void handleCollision(ICollider otherObject);
}
