package game;

import gameInProgress.framework.Animation;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainClass extends Applet implements Runnable, KeyListener {

	static public int WidthOfScreen = 1200;
	static public int HeightOfScreen = 620;
	
	private static Player character;
	private Image image, characterMovingRightImage, characterMovingRightImage2, characterMovingRightImage3, characterMovingLeftImage, characterMovingLeftImage2, characterMovingLeftImage3;
	private Image characterMovingUpImage, characterMovingUpImage2, characterMovingUpImage3, characterMovingDownImage, characterMovingDownImage2, characterMovingDownImage3;
	
	public static Image ladder, tiledirt;
	
	private Graphics second;
	private URL base;
	private Animation animR, animL, animU, animD;

	private ArrayList<Tile> tilearray = new ArrayList<Tile>();
	
	@Override
	public void init() {

		setSize(WidthOfScreen, HeightOfScreen);
		setBackground(Color.LIGHT_GRAY);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups
		characterMovingRightImage = getImage(base, "images/r1.png");
		characterMovingRightImage2 = getImage(base, "images/r2.png");
		characterMovingRightImage3 = getImage(base, "images/r3.png");
		
		characterMovingLeftImage = getImage(base, "images/l1.png");
		characterMovingLeftImage2 = getImage(base, "images/l2.png");
		characterMovingLeftImage3 = getImage(base, "images/l3.png");
		
		characterMovingUpImage = getImage(base, "images/u1.png");
		characterMovingUpImage2 = getImage(base, "images/u2.png");
		characterMovingUpImage3 = getImage(base, "images/u3.png");
		
		characterMovingDownImage = getImage(base, "images/d1.png");
		characterMovingDownImage2 = getImage(base, "images/d2.png");
		characterMovingDownImage3 = getImage(base, "images/d3.png");
				
		tiledirt = getImage(base, "images/tile.png");
		ladder = getImage(base, "images/Ladder.png");
		//ladder = getImage(base, "images/oie_transparent.png");
        		
		animR = new Animation();
		animL = new Animation();
		animU = new Animation();
		animD = new Animation();
		
		animR.addFrame(characterMovingRightImage, 100);
		animR.addFrame(characterMovingRightImage2, 100);
		animR.addFrame(characterMovingRightImage3, 100);
		
		animL.addFrame(characterMovingLeftImage, 100);
		animL.addFrame(characterMovingLeftImage2, 100);
		animL.addFrame(characterMovingLeftImage3, 100);
		
		animU.addFrame(characterMovingUpImage, 100);
		animU.addFrame(characterMovingUpImage2, 100);
		animU.addFrame(characterMovingUpImage3, 100);
		
		animD.addFrame(characterMovingDownImage, 100);
		animD.addFrame(characterMovingDownImage2, 100);
		animD.addFrame(characterMovingDownImage3, 100);
	}

	@Override
	public void start() {
		character = new Player();
		
		// Initialize Tiles	
        try {
            loadMap("images/mapLevel1.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		Thread thread = new Thread(this);
		thread.start();
	}
	
	private void loadMap(String filename) throws IOException {
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            if (!line.startsWith("!")) {
                lines.add(line);
                width = Math.max(width, line.length());
            }
        }
        
        height = lines.size();

        for (int j = 0; j < 23; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < width; i++) {
                //System.out.println(i + "is i ");

                if (i < line.length()) {
                    char ch = line.charAt(i);
                    Tile t = new Tile(i, j, Character.getNumericValue(ch));
                    tilearray.add(t);
                }
            }
        }
        //System.out.println(tilearray.size() + " ");
    }

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		while (true) {
			character.update();
			
			if(character.isMovingRight() == true){
				animR.update(20);
				character.setWasMoving('r');
			}
			if(character.isMovingLeft() == true){
				animL.update(20);
				character.setWasMoving('l');
			}
			if(character.isMovingUp() == true){
				animU.update(20);
				character.setWasMoving('u');
			}
			if(character.isMovingDown() == true){
				animD.update(20);
				character.setWasMoving('d');
			}
			
			updateTiles();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {
		paintTiles(g);
		
		/* When the character is moving */
		if(character.isMovingRight() == true){
			g.drawImage(animR.getImage(), character.getCenterX(), character.getCenterY(), this);
		}
		if(character.isMovingLeft() == true){
			g.drawImage(animL.getImage(), character.getCenterX(), character.getCenterY(), this);
		}
		if(character.isMovingUp() == true){
			g.drawImage(animU.getImage(), character.getCenterX(), character.getCenterY(), this);
		}
		if(character.isMovingDown() == true){
			g.drawImage(animD.getImage(), character.getCenterX(), character.getCenterY(), this);
		}
		
		/* When the character stops */
		if(character.wasMoving() == 'r'){ // If the character was moving right, we leave the static sprite image facing right.
			g.drawImage(animR.getImage(), character.getCenterX(), character.getCenterY(), this);
		}
		if(character.wasMoving() == 'l'){
			g.drawImage(animL.getImage(), character.getCenterX(), character.getCenterY(), this);
		}
		if(character.wasMoving() == 'u'){
			g.drawImage(animU.getImage(), character.getCenterX(), character.getCenterY(), this);
		}
		if(character.wasMoving() == 'd'){
			g.drawImage(animD.getImage(), character.getCenterX(), character.getCenterY(), this);
		}
		//g.drawRect((int)character.rect.getX(), (int)character.rect.getY(), (int)character.rect.getWidth(), (int)character.rect.getHeight());

	}
	
	private void updateTiles() {

		ArrayList holes = character.getHoles();
		
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
				for (int j = 0; j < holes.size(); j++) {
					Hole h = (Hole) holes.get(j);
						/*If the point created in the createHole() method, is inside a tile, we destroy it*/
						if ((h.getX() >= t.getTileX() && h.getX() < t.getTileX() + 27) && (h.getY() <= t.getTileY() && h.getY() > t.getTileY()-27)) {
							t.destroyTile();
								if(t.reContructTile() == true){ // If a title has been destroyed for more than a certain number of iterations, we remove the hole.
									holes.remove(j);
										break;
								}
						}
				}
		}
	}

	private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
				if(t.isVisible() == true){
					g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
				}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			character.moveUp();
			character.setMovingUp(true);
			break;

		case KeyEvent.VK_DOWN:
			character.moveDown();
			character.setMovingDown(true);
			break;

		case KeyEvent.VK_LEFT:
			character.moveLeft();
			character.setMovingLeft(true);
			break;

		case KeyEvent.VK_RIGHT:
			character.moveRight();
			character.setMovingRight(true);
			break;

		case KeyEvent.VK_SPACE:
			break;
			
		case KeyEvent.VK_CONTROL:
				character.createHole();
			break;

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			character.stopUp();
			break;

		case KeyEvent.VK_DOWN:
			character.stopDown();
			break;

		case KeyEvent.VK_LEFT:
			character.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			character.stopRight();
			break;

		case KeyEvent.VK_SPACE:
			break;

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	public static Player getPlayer() {
        return character;
    }
}
