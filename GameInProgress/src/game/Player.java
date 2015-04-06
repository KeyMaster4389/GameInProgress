package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Player {

	final int MOVESPEED = 2;
	final int gravity = 10;
	
	private int centerX = 0;
	private int centerY = 0;
	
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean movingDown = false;
	private boolean movingUp = false;
	private char characterWasMoving = 'r'; // r Right - l Left - u Up - d Down. Saves the last direction on the character.
	private boolean enableClimbing = false; // Tells if the character is on a ladder.
	private boolean isDeath = false;

	private int speedX = 0;
	private int speedY = 0;
	
	public static Rectangle rect = new Rectangle(0, 0, 0, 0);	 
		
	private ArrayList<Hole> holes = new ArrayList<Hole>();

	public void update() {
		
		if (speedX < 0) {
			centerX += speedX;
		}
		if (speedX > 0) {
			centerX += speedX;
		}
			if (centerX + speedX <= 0) { // Prevents the player from going beyond the left side of the screen.
				centerX = 0;
			}
			if (centerX + speedX >= MainClass.WidthOfScreen - 23) { // Prevents the player from going beyond the right side of the screen.
				centerX = MainClass.WidthOfScreen - 23;
			}
		if (speedY < 0 && enableClimbing == true) {
			centerY += speedY;
		}
		if (speedY > 0 && enableClimbing == true) {
			centerY += speedY;
		}
		
		if(enableClimbing == false){
			centerY = centerY + gravity;
		}
				
		if(enableClimbing == true){
			rect.setRect(centerX + 3, centerY+gravity+3, 20, 28);	
		}else{
			rect.setRect(centerX + 3, centerY+3, 20, 28);
		}
		enableClimbing = false;	
		
	}
	
	
	public void moveRight() {
		speedX = MOVESPEED;
	}
	
	public void moveLeft() {
		speedX = -MOVESPEED;
	}
	
	public void moveUp() {
		speedY = -MOVESPEED;
	}
	
	public void moveDown() {
		speedY = MOVESPEED;
	}

	public void stopRight() {
		setMovingRight(false);
		stop();
	}

	public void stopLeft() {
		setMovingLeft(false);
		stop();
	}
	
	public void stopUp() {
		setMovingUp(false);
		stop();
	}
	
	public void stopDown() {
		setMovingDown(false);
		stop();
	}

	public void stop() {
		if (isMovingRight() == false && isMovingLeft() == false && isMovingUp() == false && isMovingDown() == false) {
			speedX = 0;
			speedY = 0;
		}
		if (isMovingRight() == false && isMovingLeft() == true) {
			moveLeft();
		}
		if (isMovingRight() == true && isMovingLeft() == false) {
			moveRight();
		}
		if (isMovingUp() == false && isMovingDown() == true) {
			moveDown();
		}
		if (isMovingUp() == true && isMovingDown() == false) {
			isMovingUp();
		}

	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	
	public void createHole() { // Creates a point with coordinates X and Y. If that point is inside a tile, then we destroy the tile.
		if(isMovingRight() || wasMoving() == 'r'){
			Hole p = new Hole(centerX + 40, centerY + 20);
			holes.add(p);
		}
		if(isMovingLeft() || wasMoving() == 'l'){
			Hole p = new Hole(centerX - 15, centerY + 20);
			holes.add(p);
		}
		/*if(isMovingDown() || wasMoving() == 'd'){
			Hole p = new Hole(centerX + 3, centerY + 10);
			holes.add(p);
		}*/
	}
	
	public boolean isDeath(){
		return isDeath;
	}
	
	public void setIsDeath(boolean isDeath) {
		this.isDeath = isDeath;
	}
	
	public ArrayList getHoles() {
		return holes;
	}
	
	public void setEnableClimbing(boolean enableClimbing) {
		this.enableClimbing = enableClimbing;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}
	
	public boolean isMovingUp() {
		return movingUp;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}
	
	public boolean isMovingDown() {
		return movingDown;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}
	
	public char wasMoving() {
		return characterWasMoving;
	}
	public void setWasMoving(char characterWasMoving) {
		this.characterWasMoving = characterWasMoving;
	}
	
}
