package game;

import gameInProgress.framework.Box;
import gameInProgress.framework.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Tile {

    private int tileX, tileY, speedX, type;
    public Image tileImage;
    final int TiledDIMENTION = 27;
    private boolean visible = true;

   // private Background bg =StartingClass.getBg1();
    
    //begin mesh of code here
    public Box collision;
	public int gridX;
	public int gridY;
	private int disableTimer;
	
	public boolean openTop;
	public boolean openRight;
	public boolean openBottom;
	public boolean openLeft;
	
	//flag telling level to recompile collision
	//flag will get set back to false by level
	public boolean changedState;

    public Tile(int x, int y, int typeInt) {
        tileX = x * TiledDIMENTION;
        tileY = y * TiledDIMENTION;

        type = typeInt;

        if (type == 1) {
            tileImage = MainClass.tiledirt;
        } else if (type == 8) {
            tileImage = MainClass.ladder;
        } 
    }

    public Tile(Vector pos) {
		disableTimer = 0;
		
		gridX = (int)pos.x;
		gridY = (int)pos.y;
		
		
		collision = new Box(
				new Vector(pos.x * MainClass.TILESIZE + MainClass.TILESIZE/2,pos.y * MainClass.TILESIZE + MainClass.TILESIZE/2), 
				new Vector(MainClass.TILESIZE, MainClass.TILESIZE));
		
		openTop = true;
		openRight = true;
		openBottom = true;
		openLeft = true;
		
		changedState = false;
	}

    public void update() {
    	/*if (x > 800){
			visible = false;
		}*/
    	visible = false;
    	
    	//modification here
    	if (isDisabled()) {
			disableTimer--;
			if (!isDisabled()) {
				changedState = true;
			}
		}
    }
    
    public void setDisabled(int frames) {
		if (!isDisabled()) {
			disableTimer = frames;
			changedState = true;
		}
	}
	
	public boolean isDisabled() {
		return disableTimer > 0;
	}
	
	public void paint(Graphics g) {
		if (!isDisabled()) {
			
			if ((gridX + gridY) % 2 == 0)
				collision.paint(g, Color.GREEN);
			else
				collision.paint(g, new Color(0,200,0));
			
			g.setColor(Color.BLACK);
			if (openTop) {
				int x1 = (int)(collision.position.x - collision.size.x/2);
				int y1 = (int)(collision.position.y - collision.size.y/2);
				int x2 = (int)(collision.position.x + collision.size.x/2);
				int y2 = (int)(collision.position.y - collision.size.y/2);
				
				g.drawLine(x1, y1, x2, y2);
			}
			if (openRight) {

				int x1 = (int)(collision.position.x + collision.size.x/2);
				int y1 = (int)(collision.position.y - collision.size.y/2);
				int x2 = (int)(collision.position.x + collision.size.x/2);
				int y2 = (int)(collision.position.y + collision.size.y/2);
				
				g.drawLine(x1, y1, x2, y2);
			}
			if (openBottom) {

				int x1 = (int)(collision.position.x - collision.size.x/2);
				int y1 = (int)(collision.position.y + collision.size.y/2);
				int x2 = (int)(collision.position.x + collision.size.x/2);
				int y2 = (int)(collision.position.y + collision.size.y/2);
				
				g.drawLine(x1, y1, x2, y2);
			}
			if (openLeft) {

				int x1 = (int)(collision.position.x - collision.size.x/2);
				int y1 = (int)(collision.position.y - collision.size.y/2);
				int x2 = (int)(collision.position.x - collision.size.x/2);
				int y2 = (int)(collision.position.y + collision.size.y/2);
				
				g.drawLine(x1, y1, x2, y2);
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

}
