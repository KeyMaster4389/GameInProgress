package game;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {

    private int tileX, tileY, type;
    public Image tileImage;
    final int TiledDIMENTION = 27;
    private boolean visible = true; // Tells if a tile is visible or not.
    private int numberOfIterations; // Saves the number of iterations after a Tile has been destroyed.
    private boolean reconstruct = false; // Tells if a tile needs to be reconstructed in the level.
    private boolean isCollidingWithLadder = false; // Tells if the player is colliding with a Ladder.
    
    private Player character = MainClass.getPlayer();
    private Rectangle tileRect;


    public Tile(int x, int y, int typeInt) {
        tileX = x * TiledDIMENTION;
        tileY = y * TiledDIMENTION;

        type = typeInt;
        
        tileRect = new Rectangle();


        if (type == 1) {
            tileImage = MainClass.tiledirt;
        } else if (type == 8) {
            tileImage = MainClass.ladder;
        }else if (type == 2) {
            character.setCenterX(tileX);
            character.setCenterY(tileY);
        }else{
        	type = 0;
        }
    }


    public void update() {
    	    	
    	tileRect.setBounds(tileX, tileY, 27, 27);
    	    	
        if (tileRect.intersects(Player.rect) && type != 0) {
        	
            checkTileCollision(Player.rect);
            checkLadderCollision();
        }      
    }

    public void checkTileCollision(Rectangle playerRect) {
        if (type == 1 && playerRect.intersects(tileRect)){
           
        	int b_collision = (int) (tileRect.getMaxY() - character.getCenterY());
			int t_collision = (int) (playerRect.getMaxY() - tileY);
			int l_collision = (int) (playerRect.getMaxX() - tileX);
			int r_collision = (int) (tileRect.getMaxX() - character.getCenterX());

				if (t_collision < b_collision && t_collision < l_collision && t_collision < r_collision && isVisible() == true){ //Top collision (collides with a tile from the Top of the Tile)       
					character.setCenterY(tileY - 32);
	            	character.setSpeedY(0);
				}
				if (b_collision < t_collision && b_collision < l_collision && b_collision < r_collision && isVisible() == true){ // Bottom collision (collides with a tile from the Botton of the Tile)
					character.setCenterY(tileY + 28);
	            	character.setSpeedY(0);
				}
				if (l_collision < r_collision && l_collision < t_collision && l_collision < b_collision && isVisible() == true){ //Left collision (collides with a tile from the Left of the Tile)
					character.setCenterX(tileX - 24);
	            	character.setSpeedX(0);
				}
				if (r_collision < l_collision && r_collision < t_collision && r_collision < b_collision && isVisible() == true){ //Right collision (collides with a tile from the Right of the Tile)
					character.setCenterX(tileX + 24);
	            	character.setSpeedX(0);
				}       	
        }
    }
            
    private void checkLadderCollision() {
    	if (type == 8){
        	character.setEnableClimbing(true);
        }
	}
    
    public void destroyTile() {
    	if(numberOfIterations <= 300){ // Destroys the tile for a certain number of iterations.
    		numberOfIterations = numberOfIterations + 1;
    		visible = false;
    		reconstruct = false;
    	}else{ // If the tile has been destroyed more than a certain number of iterations, we reconstruct it.
    		numberOfIterations = 0;
    		visible = true;
    		reconstruct = true;
    	}
	}


    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public Image getTileImage() {
        return tileImage;
    }

    public void setTileImage(Image tileImage){
        this.tileImage = tileImage;
    }
    
    public boolean isVisible() {
		return visible;
	}
    
    public boolean isCollidingWithLadder() {
		return isCollidingWithLadder;
	}
    
    public boolean reContructTile(){
		return reconstruct;
    }
    

}
