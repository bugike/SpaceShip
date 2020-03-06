package com.mycompany.a2.gameobjects;

import java.util.Observable;
import java.util.Random;
import java.util.Vector;

import com.codename1.ui.Dialog;
import com.mycompany.a2.GameWorldProxy;
import com.mycompany.a2.interfaces.ICollider;
import com.mycompany.a2.interfaces.IGameWorld;
import com.mycompany.a2.interfaces.IIterator;
import com.mycompany.a2.sound.Sound;
import com.mycompany.a2.views.Game;

public class GameWorld extends Observable implements IGameWorld{
	
	private GameObjectCollection worldObjects;
	
	Random r = new Random();
	
	// Game World Setting
	private int score;
	private int missileCount;
	private int time;
	private int lives;
	private boolean sound;
	
	// Use ArrayList to Store Game Objects
	//private Vector<GameObject> worldObjects = new Vector<GameObject>();
	
	// Score Accessor
	public void setScore(int score) {
		this.score = score;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	public int getScore() {
		return score;
	}
	public void setTime(int time) {
		this.time = time;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	public int getTime() {
		return time;
	}
	public void setLives(int lives) {
		this.lives = lives;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	public int getLives() {
		return lives;
	}
	
	public void setSound(boolean sound) {
		this.sound = sound;
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public boolean getSound() {
		return this.sound;
	}
	
	public void setMissileCount(int missileCount) {
		this.missileCount = missileCount;
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	public int getMissileCount() {
		return this.missileCount;
	}
	
	public GameObjectCollection getWorldObjects() {
		return this.worldObjects;
	}
	
	// Game over setting
	public void gameOver() {
		if (sound) {
			Sound gameover = new Sound("gameover.wav");
			gameover.play();
		}
		
		boolean startOver = Dialog.show("Game Over", "Do you want to start a new game?", "Yes", "No");
		if (startOver) {
			worldObjects.clear();
			this.setScore(0);
			this.setMissileCount(0);
			this.setTime(0);
			this.setLives(0);
		}
		else {}
	}
	
	public GameWorld() {
		worldObjects = new GameObjectCollection();
		score = 0;
		missileCount = 0;
		time = 0;
		lives = 0;
		sound = false;
	}
	
	
	
	// 'a': add asteroid
	public void addAsteroid() {
		Asteroid as = new Asteroid();
		worldObjects.add(as);
		System.out.println("A new ASTEROID has been created.");
		System.out.println(as.toString());
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'y': add NPS
	public void addNonPlayerShip() {
		NonPlayerShip nps = new NonPlayerShip();
		worldObjects.add(nps);
		System.out.println("A new NON-PLAYER SHIP has been created.");
		System.out.println(nps.toString());
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'b': add Space Station
	public void addSpaceStation() {
		SpaceStation ss = new SpaceStation();
		worldObjects.add(ss);
		System.out.println("A new SPACE STATION has been created.");
		System.out.println(ss.toString());
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 's': add PS
	public void addPlayerShip() {
		boolean hasPlayerShip = false;
		// check if player ship already exists.
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			if ( theElements.getNext() instanceof PlayerShip ) {
				hasPlayerShip = true;
				System.out.println("Only one player ship is allowed to be add!");
				break;
			}
		}
		if (!hasPlayerShip) {
			this.lives = 3;
			PlayerShip ps = new PlayerShip();
			this.setMissileCount(ps.getMissileCount());
			worldObjects.add(ps);
			System.out.println("A PLAYER SHIP has been created.");
			System.out.println(ps.toString());
		}
		
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'i': increase the speed of PS
	public void increaseSpeed() {
		boolean hasPlayerShip = false;
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if ( go instanceof PlayerShip ) {
				hasPlayerShip = true;
				PlayerShip ps = (PlayerShip) go;
				
				// Player ship has maximum speed of 10
				if (ps.getSpeed() < 10) {
					ps.setSpeed(ps.getSpeed() + 1);
					System.out.println("The speed of player ship INCREASES!");
					System.out.println(ps.toString());
				}
				else
					System.out.println("Can't increase speed! "
							+ "Player ship has reached maximum speed!");
				
				break;
			}
		}
		
		// Print out error if there is no player ship added
		if (!hasPlayerShip)
			System.out.println("Can't increase speed! No player ship has been added yet!");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
		
	// 'd': decrease the speed of PS
	public void decreaseSpeed() {
		boolean hasPlayerShip = false;
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if ( go instanceof PlayerShip ) {
				hasPlayerShip = true;
				PlayerShip ps = (PlayerShip) go;
				
				// Speed can't be negative 
				if (ps.getSpeed() > 0) {
					ps.setSpeed(ps.getSpeed() - 1);
					System.out.println("The speed of player ship DECREASES!");
					System.out.println(ps.toString());
				}
				else
					System.out.println("Can't decrease speed! "
							+ "The speed can't be negative!");
				
				break;
			}
		}
		
		// Print out error if there is no player ship added
		if (!hasPlayerShip)
			System.out.println("Can't decrease speed! No player ship has been added yet!");
	
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'l': turn PS left by a small amount
	public void turnPSLeft() {
		boolean hasPlayerShip = false;
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if ( go instanceof PlayerShip ) {
				hasPlayerShip = true;
				PlayerShip ps = (PlayerShip) go;
				
				ps.turnLeft();
				System.out.println("The player ship turns left!");
				System.out.println(ps.toString());
				
				break;
			}
		}
		
		// Print out error if there is no player ship added
		if (!hasPlayerShip)
			System.out.println("Can't turn left! No player ship has been added yet!");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
		
	// 'r': turn PS right by a small amount
	public void turnPSRight() {
		boolean hasPlayerShip = false;
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if ( go instanceof PlayerShip ) {
				hasPlayerShip = true;
				PlayerShip ps = (PlayerShip) go;
				
				ps.turnRight();
				System.out.println("The player ship turns right!");
				System.out.println(ps.toString());
				
				break;
			}
		}
		
		// Print out error if there is no player ship added
		if (!hasPlayerShip)
			System.out.println("Can't turn right! No player ship has been added yet!");
	
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// '<': PS's missile launcher rotates counterclockwise
	public void PSMissileLauncherTurnLeft() {
		boolean hasPlayerShip = false;
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if ( go instanceof PlayerShip ) {
				hasPlayerShip = true;
				PlayerShip ps = (PlayerShip) go;
				
				ps.MLTurnLeft();
				System.out.println("The player ship's missile launcher rotates counterclockwise!");
				System.out.println("Missile launcher direction: " + ps.getMLDir());
				if (sound) {
					Sound s = new Sound("launcher.wav");
					s.play();
				}
				
				break;
			}
		}
		
		// Print out error if there is no player ship added
		if (!hasPlayerShip)
			System.out.println("Can't turn left! No player ship has been added yet!");
	
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
		
	// '>': PS's missile launcher rotates clockwise
	public void PSMissileLauncherTurnRight() {
		boolean hasPlayerShip = false;
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if ( go instanceof PlayerShip ) {
				hasPlayerShip = true;
				PlayerShip ps = (PlayerShip) go;
				
				ps.MLTurnRight();
				System.out.println("The player ship's missile launcher rotates clockwise!");
				System.out.println("Missile launcher direction: " + ps.getMLDir());
				
				if (sound) {
					Sound s = new Sound("launcher.wav");
					s.play();
				}
				break;
			}
		}
		
		// Print out error if there is no player ship added
		if (!hasPlayerShip)
			System.out.println("Can't turn right! No player ship has been added yet!");
	
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'f': fire
	public void firePSMissile() {
		boolean hasPlayerShip = false;
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if ( go instanceof PlayerShip ) {
				hasPlayerShip = true;
				PlayerShip ps = (PlayerShip) go;
				if (ps.getMissileCount() > 0) {
					ps.setMissileCount(ps.getMissileCount() - 1);
					this.setMissileCount(ps.getMissileCount());
					Missile m = new Missile(ps.getMLDir(), 
							ps.getLocationX() + Math.cos(Math.toRadians(-(90 - ps.getMLDir())))*90, 
							ps.getLocationY() + Math.sin(Math.toRadians(-(90 - ps.getMLDir())))*90, 0);  
											// 0 indicates it is PS's missile
					
					worldObjects.add(m);	
					if (sound) {
						Sound fire = new Sound("fire.wav");
						fire.play();
					}
					System.out.println("PLAYER SHIP missile fires!");
					System.out.println(ps.getMissileCount() + " missile(s) left");
					}
				else // No missile
					System.out.println("Out of missile. Player ship can't fire.");
				
				break;
			}
		}
	
		// Print out error if there is no player ship added
		if (!hasPlayerShip)
			System.out.println("Can't fire! No player ship has been added yet!");

		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'L': Launch a missile
	public void launchNPSMissile() {
		boolean hasNonPlayerShip = false;
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if ( go instanceof NonPlayerShip ) {
				hasNonPlayerShip = true;
				NonPlayerShip nps = (NonPlayerShip) go;
				if (nps.getNPSMissileCount() > 0) {
					nps.setNPSMissileCount(nps.getNPSMissileCount() - 1);
					Missile m = new Missile(nps.getDirection(), 
							nps.getLocationX() + Math.cos(Math.toRadians(-(90 - nps.getDirection())))*90, 
							nps.getLocationY() + Math.sin(Math.toRadians(-(90 - nps.getDirection())))*90, 1); 
											// 1 indicates it is NPS's missile
					
					worldObjects.add(m);	
					if (sound) {
						Sound fire = new Sound("fire2.wav");
						fire.play();
					}
					System.out.println("NON-PLAYER SHIP missile fires!");
					System.out.println(nps.getNPSMissileCount() + " missile(s) left");
					}
				else // No missile
					System.out.println("Out of missile. Non-player ship can't launch missile!");
			}
		}
	
		// Print out error if there is no player ship added
		if (!hasNonPlayerShip)
			System.out.println("Can't launch missiles! No non-player ship has been added yet!");
	
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
		
	// 'j': jump through hyperspace
	public void jumpHyperspace() {
		boolean hasPlayerShip = false;
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if ( go instanceof PlayerShip ) {
				hasPlayerShip = true;
				PlayerShip ps = (PlayerShip) go;
				ps.jump();
				System.out.println("Player ship jumps to another hyperspace!");
				System.out.println(ps.toString());
				
				break;
			}
		}
		
		// Print out error if there is no player ship added
		if (!hasPlayerShip)
			System.out.println("No player ship has been added yet!");
	
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'n': load a new supply of missiles into the PS
	public void loadPSMissile() {
		boolean hasPlayerShip = false;
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if ( go instanceof PlayerShip ) {
				hasPlayerShip = true;
				PlayerShip ps = (PlayerShip) go;
				ps.setMissileCount(10);
				this.setMissileCount(ps.getMissileCount());
				if (sound) {
					Sound reload = new Sound("reload.wav");
					reload.play();
				}
				System.out.println("New supply of PS's missiles has been loaded!");
				System.out.println(ps.getMissileCount() + " missiles left");
				
				break;
			}
		}
		
		// Print out error if there is no player ship added
		if (!hasPlayerShip)
			System.out.println("No player ship has been added yet!");
	
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'k': a PS's missile has struck and killed an asteroid
	public void PSMissileSKAsteroid() {
		boolean anyMissile = false;
		boolean anyAsteroid = false;
		
		IIterator theElements1 = worldObjects.getIterator();
		while (theElements1.hasNext()) {
			GameObject go1 = (GameObject) theElements1.getNext();
			if ( go1 instanceof Missile) {
				Missile m = (Missile) go1;
				if (m.getWhichShip() == 0) {  // indicate it is PS's missile
					anyMissile = true;
					
					IIterator theElements2 = worldObjects.getIterator();
					while (theElements2.hasNext()) {
						GameObject go2 = (GameObject) theElements2.getNext();
						if( go2 instanceof Asteroid) {	
							anyAsteroid = true;
							worldObjects.remove(go1); // remove missile
							worldObjects.remove(go2); // remove Asteroid
							System.out.println("One asteroid is killed!");
							this.score += 2;
							System.out.println("Player's score: " + this.score);
							if (sound) {
								Sound s = new Sound("explode.wav");
								s.play();
							}
							break;
						}
					}
					if(!anyAsteroid)
						System.out.println("This command doesn't work! No Asteroid in the GameWorld!");
					
					break;
				}		
			}		
		}
		if(!anyMissile) 
			System.out.println("This command doesn't work! No PS missile in the GameWorld!");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'e': a PS's missile has struck and eliminated a NPS
	public void PSMissileSKNPS() {
		boolean anyMissile = false;
		boolean anyNPS = false;
		IIterator theElements1 = worldObjects.getIterator();
		while (theElements1.hasNext()) {
			GameObject go1 = (GameObject) theElements1.getNext();
			if(go1 instanceof Missile) {
				Missile m = (Missile) go1;
				if (m.getWhichShip() == 0) {
					anyMissile = true;
					
					IIterator theElements2 = worldObjects.getIterator();
					while (theElements2.hasNext()) {
						GameObject go2 = (GameObject) theElements2.getNext();
						if (go2 instanceof NonPlayerShip) {
							anyNPS = true;
							worldObjects.remove(go1);  // remove missile
							worldObjects.remove(go2);	 // remove NPS
							System.out.println("One non-player ship is killed!");
							this.score += 5;
							System.out.println("Player's score: " + this.score);
							if (sound) {
								Sound s = new Sound("explode.wav");
								s.play();
							}
							break;
						}
					}	
					if(!anyNPS) 
						System.out.println("This command doesn't work! No NPS in the GameWorld!");
					
					break;
				}
			}
		}	
		if(!anyMissile) 
			System.out.println("This command doesn't work! No PS missile in the GameWorld!");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'E': a NPS's missile has struck and Exploded a PS
	public void NPSMissileSEPS() {
		boolean anyMissile = false;
		boolean hasPlayerShip = false;
		
		IIterator theElements1 = worldObjects.getIterator();
		while (theElements1.hasNext()) {
			GameObject go1 = (GameObject) theElements1.getNext();
			if (go1 instanceof Missile) {
				Missile m = (Missile) go1;
				if (m.getWhichShip() == 1) {  // 1 indicates NPS's missile
					anyMissile = true;
					
					IIterator theElements2 = worldObjects.getIterator();
					while (theElements2.hasNext()) {
						GameObject go2 = (GameObject) theElements2.getNext();
						if(go2 instanceof PlayerShip) {
							hasPlayerShip = true;
							worldObjects.remove(go1); // remove NPS missile
							
							if (this.lives <= 1) {
								this.lives -= 1;
								worldObjects.remove(go2);
								System.out.println("Player ship exploded! Game Over!");
								this.gameOver();
							}
							else {
								this.lives -= 1;
								System.out.println("The player ship lost 1 life!");
								if (sound) {
									Sound s = new Sound("gothit.wav");
									s.play();
								}
							}
							break;
						}
					}
					if(!hasPlayerShip) 
						System.out.println("This command doesn't work! No PS in the GameWorld!");
					
					break;
				}
			}
		}
		
		if(!anyMissile) 
			System.out.println("This command doesn't work! No NPS missile in the GameWorld!");
			
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'c': the PS has crashed into an asteroid
	public void PSCrashAsteroid() {
		boolean anyAsteroid = false;
		boolean hasPlayerShip = false;
		IIterator theElements1 = worldObjects.getIterator();
		while (theElements1.hasNext()) {
			GameObject go1 = (GameObject) theElements1.getNext();
			if(go1 instanceof Asteroid) {
				anyAsteroid = true;
				
				IIterator theElements2 = worldObjects.getIterator();
				while (theElements2.hasNext()) {
					GameObject go2 = (GameObject) theElements2.getNext();
					if(go2 instanceof PlayerShip) {
						hasPlayerShip = true;
						worldObjects.remove(go1); // remove Asteroid
						
						if (this.lives <= 1) {
							this.lives -= 1;
							worldObjects.remove(go2);
							System.out.println("Player ship exploded! Game Over!");
							this.gameOver();
						}
						else {
							this.lives -= 1;
							System.out.println("The player ship lost 1 life!");
							if (sound) {
								Sound s = new Sound("gothit.wav");
								s.play();
							}
						}
						break;
					}
				}
				if(!hasPlayerShip) 
					System.out.println("This command doesn't work! No PS in the GameWorld!");
				break;
			}
		}
		
		if(!anyAsteroid)
			System.out.println("This command doesn't work! No Asteroid in the GameWorld!");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'h': PS has hit a NPS
	public void PSHitNPS() {
		boolean anyNPS = false;
		boolean hasPlayerShip = false;
		
		IIterator theElements1 = worldObjects.getIterator();
		while (theElements1.hasNext()) {
			GameObject go1 = (GameObject) theElements1.getNext();
			if(go1 instanceof NonPlayerShip) {
				anyNPS = true;
				
				IIterator theElements2 = worldObjects.getIterator();
				while (theElements2.hasNext()) {
					GameObject go2 = (GameObject) theElements2.getNext();
					if(go2 instanceof PlayerShip) {
						hasPlayerShip = true;
						worldObjects.remove(go1);  // remove NPS
						
						if (this.lives <= 1) {
							this.lives -= 1;
							worldObjects.remove(go2);
							System.out.println("Player ship exploded! Game Over!");
							this.gameOver();
						}
						else {
							this.lives -= 1;
							System.out.println("The player ship lost 1 life!");
							if (sound) {
								Sound s = new Sound("gothit.wav");
								s.play();
							}
						}
						break;
					}
				}
				if(!hasPlayerShip) 
					System.out.println("This command doesn't work! No PS in the GameWorld!");
				break;
			}
		}
		
		if(!anyNPS) 
			System.out.println("This command doesn't work! No NPS in the GameWorld!");
			
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'x': Two asteroids have collided with each other
	public void twoAsteroidsCollided() {
		boolean anyAsteroid1 = false;
		boolean anyAsteroid2 = false;
		IIterator theElements1 = worldObjects.getIterator();
		while (theElements1.hasNext()) {
			GameObject go1 = (GameObject) theElements1.getNext();
			if(go1 instanceof Asteroid) {
				anyAsteroid1 = true;
				
				IIterator theElements2 = worldObjects.getIterator();
				while (theElements2.hasNext()) {
					GameObject go2 = (GameObject) theElements2.getNext();
					if((go2 instanceof Asteroid) && (go1 != go2)) {
						anyAsteroid2 = true;
						worldObjects.remove(go1);  // remove first Asteroid
						worldObjects.remove(go2);  // remove second Asteroid
						if (sound) {
							Sound s = new Sound("explode.wav");
							s.play();
						}
						break;
					}
				}
				
				if(!anyAsteroid2)
					System.out.println("This command doesn't work! There is only one Asteroid in the GameWorld!");
				break;
			}
		}
		
		if(!anyAsteroid1)
			System.out.println("This command doesn't work! No Asteroid in the GameWorld!");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'I': One asteroid have collided and impacted the NPS
	public void asteroidCollidedNPS() {
		boolean anyNPS = false;
		boolean anyAsteroid = false;
		
		IIterator theElements1 = worldObjects.getIterator();
		while (theElements1.hasNext()) {
			GameObject go1 = (GameObject) theElements1.getNext(); 
			if(go1 instanceof NonPlayerShip) {
				anyNPS = true;
				
				IIterator theElements2 = worldObjects.getIterator();
				while (theElements2.hasNext()) {
					GameObject go2 = (GameObject) theElements2.getNext();
					if(go2 instanceof Asteroid) {
						anyAsteroid = true;
			
						worldObjects.remove(go1);  // remove NPS
						worldObjects.remove(go2);	 // remove Asteroid
						
						if (sound) {
							Sound s = new Sound("explode.wav");
							s.play();
						}
						break;
					}
				}
				if(!anyAsteroid)
					System.out.println("This command doesn't work! No Asteroid in the GameWorld!");
				break;
			}
		}
		
		if(!anyNPS) 
			System.out.println("This command doesn't work! No NPS in the GameWorld!");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 't': "Game clock" has ticked
	public void clockTicked() {
		this.time += 1;
		Vector<GameObject> deleteList = new Vector<GameObject>();
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if(go instanceof PlayerShip) {
				PlayerShip ps = (PlayerShip) go;
				ps.move();
			}
			else if(go instanceof NonPlayerShip) {
				NonPlayerShip nps = (NonPlayerShip) go;
				// Non player ship automatically fire
				if(nps.getFireCount() <= 0) {
					if (nps.getNPSMissileCount() > 0) {
						nps.setNPSMissileCount(nps.getNPSMissileCount() - 1);
						Missile m = new Missile(nps.getDirection(), 
								nps.getLocationX() + Math.cos(Math.toRadians(-(90 - nps.getDirection())))*90, 
								nps.getLocationY() + Math.sin(Math.toRadians(-(90 - nps.getDirection())))*90, 1); 
												// 1 indicates it is NPS's missile
						
						worldObjects.add(m);
						// reset the timer to auto launch missile
						Random r = new Random();
						nps.setFireCount(r.nextInt(201) + 300);  // reset for next fire
						if (sound) {
							Sound fire = new Sound("fire2.wav");
							fire.play();
						}
					}
				}
				else
					nps.setFireCount(nps.getFireCount() - 1);
				nps.move();
			}
			else if(go instanceof Asteroid) {
				Asteroid a = (Asteroid) go;
				a.asteroidRotate();
				a.move();
			}
			else if(go instanceof SpaceStation) {
				SpaceStation ss = (SpaceStation) go;
				if(ss.getBlinkRate() > 0) {
					ss.setBlinkRate(ss.getBlinkRate() - 1);
				}
				else {
					ss.setBlinkLight(!ss.getBlinkLight());
					ss.setBlinkRate(ss.getBlinkRateReset());
				}	
			}
			// Missile
			else { 
				Missile m = (Missile) go;
				if (m.getFuelLevel() > 0) {
					m.setFuelLevel(m.getFuelLevel() - 1);
					m.move();
				}
				else {
					deleteList.add(go);
				}	
			}
		}

		// delete all the missiles which are out of fuel
		for(int i = 0; i < deleteList.size(); i++) {
			worldObjects.remove(deleteList.elementAt(i));
		}
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'P': print out game status
	public void print() {
		boolean hasPlayerShip = false;
		IIterator theElements = worldObjects.getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			if ( go instanceof PlayerShip ) {
				hasPlayerShip = true;
				PlayerShip ps = (PlayerShip) go;
				System.out.println("Game score =" + this.score + " Missiles =" 
						+ ps.getMissileCount() + " Time ="+ this.time + " Lives =" 
						+ this.lives);
			}
		}
		if (!hasPlayerShip)
			System.out.println("No player ship has been added!");
		
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	// 'm': print out game objects' location
	public void printMap() {
		System.out.println("World Map");
		IIterator theElements = worldObjects.getIterator();
		while ( theElements.hasNext() ) {
			GameObject go = (GameObject) theElements.getNext();
			System.out.println(go);
		}
	}
	
	// 'q': quit
	public void quit() {
		boolean quit = Dialog.show("Quit", "Confirm to quit?", "Yes", "No");
		if (quit)
			System.exit(0);
	}
	
	
	// handle the collided objects
	public void  handleCollideObjects() {
		IIterator theElements = worldObjects.getIterator();
		Vector<GameObject> tmp = new Vector<GameObject>();
		
		while(theElements.hasNext()) {
			ICollider currObj = (ICollider) theElements.getNext();
			// check if this object collides with any other object
			IIterator theElements2 = worldObjects.getIterator();
			while(theElements2.hasNext()) {
				ICollider otherObj = (ICollider) theElements2.getNext();
				// check for collision
				if (currObj != otherObj) {
					if(currObj.collidesWith(otherObj)) {
						currObj.handleCollision(otherObj);
						tmp.add((GameObject) currObj);
					}
				}
			}
		}
		
		// find two objects which have the same case and do the case operation
		for (int i = 0; i < tmp.size(); i++) {
			for (int j = 1; j < tmp.size(); j++) {
				if (tmp.elementAt(i) != tmp.elementAt(j)) {
					
					// case 1 Player Ship Missile hits Asteroid
					if (tmp.elementAt(i).getCollideCase() == 1 && tmp.elementAt(j).getCollideCase() == 1) {
						tmp.elementAt(i).setCollideCase(-1);
						tmp.elementAt(j).setCollideCase(-1);
						worldObjects.remove(tmp.elementAt(i));
						worldObjects.remove(tmp.elementAt(j));
						this.score += 2;
						if (sound) {  // if sound is on
							Sound s = new Sound("explode.wav");
							s.play();
						}
					}
					
					// case 2 Player Ship Missile hits Non Player Ship
					if (tmp.elementAt(i).getCollideCase() == 2 && tmp.elementAt(j).getCollideCase() == 2) {
						tmp.elementAt(i).setCollideCase(-1);
						tmp.elementAt(j).setCollideCase(-1);
						worldObjects.remove(tmp.elementAt(i));
						worldObjects.remove(tmp.elementAt(j));
						this.score += 5;
						if (sound) {
							Sound s = new Sound("explode.wav");
							s.play();
						}
					}
					// case 3 Non Player Ship Missile hits Player Ship
					if (tmp.elementAt(i).getCollideCase() == 3 && tmp.elementAt(j).getCollideCase() == 3) {
						tmp.elementAt(i).setCollideCase(-1);
						tmp.elementAt(j).setCollideCase(-1);
						if (tmp.elementAt(i) instanceof PlayerShip) {
							worldObjects.remove(tmp.elementAt(j)); // remove NPS missile
						
							if (this.lives <= 1) {
								this.lives -= 1;
								worldObjects.remove(tmp.elementAt(i));
								this.gameOver();
							}
							else {
								this.lives -= 1;
								if (sound) {
								Sound s = new Sound("gothit.wav");
								s.play();
								}
							}
						}
						
						if (tmp.elementAt(j) instanceof PlayerShip) {
							worldObjects.remove(tmp.elementAt(i)); // remove NPS missile
						
							if (this.lives <= 1) {
								this.lives -= 1;
								worldObjects.remove(tmp.elementAt(j));
								this.gameOver();
							}
							else {
								this.lives -= 1;
								if (sound) {
									Sound s = new Sound("gothit.wav");
									s.play();
								}
							}
						}
					}
					
					// case 4 Player Ship hits Asteroid
					if (tmp.elementAt(i).getCollideCase() == 4 && tmp.elementAt(j).getCollideCase() == 4) {
						tmp.elementAt(i).setCollideCase(-1);
						tmp.elementAt(j).setCollideCase(-1);
						if (tmp.elementAt(i) instanceof PlayerShip) {
							worldObjects.remove(tmp.elementAt(j)); // remove Asteroid
						
							if (this.lives <= 1) {
								this.lives -= 1;
								worldObjects.remove(tmp.elementAt(i));
								this.gameOver();
							}
							else {
								this.lives -= 1;
								if (sound) {
									Sound s = new Sound("gothit.wav");
									s.play();
								}
							}
						}
						
						if (tmp.elementAt(j) instanceof PlayerShip) {
							worldObjects.remove(tmp.elementAt(i)); // remove Asteroid
						
							if (this.lives <= 1) {
								this.lives -= 1;
								worldObjects.remove(tmp.elementAt(j));
								this.gameOver();
							}
							else {
								this.lives -= 1;
								if (sound) {
									Sound s = new Sound("gothit.wav");
									s.play();
								}
							}
						}
					}
					
					// case 5 player ship hits non player ship
					if (tmp.elementAt(i).getCollideCase() == 5 && tmp.elementAt(j).getCollideCase() == 5) {
						tmp.elementAt(i).setCollideCase(-1);
						tmp.elementAt(j).setCollideCase(-1);
						if (tmp.elementAt(i) instanceof PlayerShip) {
							worldObjects.remove(tmp.elementAt(j)); // remove Non Player Ship
						
							if (this.lives <= 1) {
								this.lives -= 1;
								worldObjects.remove(tmp.elementAt(i));
								this.gameOver();
							}
							else {
								this.lives -= 1;
								if (sound) {
									Sound s = new Sound("gothit.wav");
									s.play();
								}
							}
						}
						
						if (tmp.elementAt(j) instanceof PlayerShip) {
							worldObjects.remove(tmp.elementAt(i)); // remove Non Player Ship
						
							if (this.lives <= 1) {
								this.lives -= 1;
								worldObjects.remove(tmp.elementAt(j));
								this.gameOver();
							}
							else {
								this.lives -= 1;
								if (sound) {
									Sound s = new Sound("gothit.wav");
									s.play();
								}
							}
						}
						
					}
					
					// case 6 Asteroid hits another Asteroid
					if (tmp.elementAt(i).getCollideCase() == 6 && tmp.elementAt(j).getCollideCase() == 6) {
						tmp.elementAt(i).setCollideCase(-1);
						tmp.elementAt(j).setCollideCase(-1);
						worldObjects.remove(tmp.elementAt(i));
						worldObjects.remove(tmp.elementAt(j));
						if (sound) {  // if sound is on
							Sound s = new Sound("explode.wav");
							s.play();
						}
					}
					
					// case 7 Asteroid hits Non Player Ship
					if (tmp.elementAt(i).getCollideCase() == 7 && tmp.elementAt(j).getCollideCase() == 7) {
						tmp.elementAt(i).setCollideCase(-1);
						tmp.elementAt(j).setCollideCase(-1);
						worldObjects.remove(tmp.elementAt(i));
						worldObjects.remove(tmp.elementAt(j));
						if (sound) {  // if sound is on
							Sound s = new Sound("explode.wav");
							s.play();
						}
					}
					
					// case 8 PlayerShip hits Space Station and reload
					if (tmp.elementAt(i).getCollideCase() == 8 && tmp.elementAt(j).getCollideCase() == 8) {
						tmp.elementAt(i).setCollideCase(-1);
						tmp.elementAt(j).setCollideCase(-1);
						if (this.getMissileCount() != 10) {  // reload when player ship does not have full missiles
							if (tmp.elementAt(i) instanceof PlayerShip) {
								((PlayerShip) tmp.elementAt(i)).setMissileCount(10);
								this.setMissileCount(10);
								if (sound) {  // if sound is on
									Sound s = new Sound("reload.wav");
									s.play();
								}	
							}
							
							if (tmp.elementAt(j) instanceof PlayerShip) {
								((PlayerShip) tmp.elementAt(j)).setMissileCount(10);
								this.setMissileCount(10);
								if (sound) {  // if sound is on
									Sound s = new Sound("reload.wav");
									s.play();
								}
							}
						}
					}
				}
			}
		}
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
}
