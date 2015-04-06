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
            checkLadderCollision(Player.rect);
        }      
    
    }

    public void checkTileCollision(Rectangle playerRect){ // Checks for collisions between the Player and the Tiles.
        if (type == 1){
           
        	int b_collision = (int) (tileRect.getMaxY() - character.getCenterY());
			int t_collision = (int) (playerRect.getMaxY() - tileY);
			int l_collision = (int) (playerRect.getMaxX() - tileX);
			int r_collision = (int) (tileRect.getMaxX() - character.getCenterX());

				if (t_collision < b_collision && t_collision < l_collision && t_collision < r_collision && isVisible() == true){ //Top collision (collides with a tile from the Top of the Tile)       
					//character.setCenterY(tileY - 32);
					character.setCenterY(tileY - 40);
	            	character.setSpeedY(0);
				}
				if (b_collision < t_collision && b_collision < l_collision && b_collision < r_collision && isVisible() == true){ // Bottom collision (collides with a tile from the Botton of the Tile)
					//character.setCenterY(tileY + 28);
					character.setCenterY(tileY +14);
	            	character.setSpeedY(0);
				}
				if (l_collision < r_collision && l_collision < t_collision && l_collision < b_collision && isVisible() == true){ //Left collision (collides with a tile from the Left of the Tile)
					character.setCenterX(tileX - 24);
					//character.setCenterX(tileX - 32);
	            	character.setSpeedX(0);
				}
				if (r_collision < l_collision && r_collision < t_collision && r_collision < b_collision && isVisible() == true){ //Right collision (collides with a tile from the Right of the Tile)
					character.setCenterX(tileX + 24);
					//character.setCenterX(tileX + 16);
	            	character.setSpeedX(0);
				}       	
        }
    }
            
    private void checkLadderCollision(Rectangle playerRect){ // Checks for collisions between the Player and the Ladders.
    	
    	if (type == 8){
    		character.setEnableClimbing(true);
        }
  
	}
    
    public void destroyTile() {
    	if(type == 1){ // Can only destroy the tiles
    		if(numberOfIterations <= 300){ // Destroys the tile for a certain number of iterations.
    			numberOfIterations = numberOfIterations + 1;
    			visible = false;
    			reconstruct = false;
    		}else{ // If the tile has been destroyed more than a certain number of iterations, we reconstruct it.
    			numberOfIterations = 0; // Reset the count for the iterations.
    			visible = true;
    			reconstruct = true;
    				/* Check if the character is inside a hole when it closes. If he is, kill him. */
    				if((character.getCenterX()+5 >= getTileX() && character.getCenterX()+5 < getTileX() + 27) && (character.getCenterY() <= getTileY() && character.getCenterY() > getTileY()-27)) {
    					character.setIsDeath(true);
    				}
    		}
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
