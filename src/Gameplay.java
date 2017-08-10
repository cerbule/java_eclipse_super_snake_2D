import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	//position of snake insde the panel
	private int[] snakexlength = new int[750];
	private int[] snakeylength = new int[750];
	
	//4 variables in what side the snake is moving
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	//4 variables for the snake face
	private ImageIcon rightmouth;
	private ImageIcon leftmouth;
	private ImageIcon upmouth;
	private ImageIcon downmouth;
	
	//define the default length of the snake
	private int lengthofsnake = 3;
	
	//speed of the snake inside the panel
	private Timer timer;
	private int delay = 100;
	
	//default position for the pick-up(enemy)
	private int[] enemyxpos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
	private int[] enemyypos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
	
	//spawn the enemy randomly from the upper list array
	private ImageIcon enemyimage;
	
	private Random random = new Random();
	private int xpos = random.nextInt(34);
	private int ypos = random.nextInt(23);
	
	//variables for score and length
	private int score = 0;
	
	//this variable will set the first postition of the snake, when he is not moving
	private int moves = 0;
	
	//define 1 more variable for the snake body
	private ImageIcon snakeimage;
	
	//this is an object for ImageIcon class, to draw on the frame
	private ImageIcon titleImage;
	
	public Gameplay(){
		//add the default position pf the snake on the panel
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	//build in method that draw everything
	public void paint(Graphics g){
		
		//will detect if the has just started and will set a default location for the snake
		//if you are in a playmode don't check this
		//executed first time, when the game has just started
		if(moves == 0){
			snakexlength[2] = 50;
			snakexlength[1] = 75;
			snakexlength[0] = 100;
			
			snakeylength[2] = 100;
			snakeylength[1] = 100;
			snakeylength[0] = 100;
			
		}
		//draw title image border
		g.setColor(Color.white);
		g.drawRect(24, 10, 851, 55);
		
		//draw the title image == Snake
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);
		
		//draw the border for the playing area
		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 851, 577);
		
		//draw background for the gameplay
		g.setColor(Color.BLACK);
		g.fillRect(25, 75, 850, 575);
		
		//draw the score
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		//draw the font and set the position
		g.drawString("Score: " + score, 780, 30);
		
		//draw the length of snake
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		//draw the font and set the position
		g.drawString("Length: " + lengthofsnake, 780, 50);

		//draw the snake
		rightmouth = new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
		
		//position of the snake
		for(int a = 0; a < lengthofsnake; a++){
			if(a == 0 && right){
				rightmouth = new ImageIcon("rightmouth.png");
				rightmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			if(a == 0 && left){
				leftmouth = new ImageIcon("leftmouth.png");
				leftmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			if(a == 0 && up){
				upmouth = new ImageIcon("upmouth.png");
				upmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			if(a == 0 && down){
				downmouth = new ImageIcon("downmouth.png");
				downmouth.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
			
			if(a != 0){
				snakeimage = new ImageIcon("snakeimage.png");
				snakeimage.paintIcon(this, g, snakexlength[a], snakeylength[a]);
			}
		}
		

		//draw the enemy
		enemyimage = new ImageIcon("enemy.png");
		//detect if the head is colliding with the enemy
		if(enemyxpos[xpos] == snakexlength[0] && enemyypos[ypos] == snakeylength[0]){
			score++;
			//increment the length of snake
			lengthofsnake++;
			xpos = random.nextInt(34);
			ypos = random.nextInt(23);
		}
		//draw the enemy
		enemyimage.paintIcon(this, g, enemyxpos[xpos], enemyypos[ypos]);
		
		//detecting the collision of the snake whit itself
		for(int b = 1; b < lengthofsnake; b++){
			if(snakexlength[b] == snakexlength[0] && snakeylength[b] == snakeylength[0]){
				right = false; left = false; up = false; down = false;
				
				g.setColor(Color.white);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("Game Over", 300, 300);
				
				g.setFont(new Font("arial", Font.BOLD, 20));
				g.drawString("Space to RESTART", 350, 340);
				
			}
		}
		
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(right){
			
			for(int r = lengthofsnake - 1; r >=0; r--){
				//shift the position of snake's head to it's next index
				snakeylength[r+1] = snakeylength[r];
			}
			//shift the position of snake x length
			for(int r = lengthofsnake; r >=0; r--){
				if(r == 0){
					snakexlength[r] = snakexlength[r] + 25;
				}else{
					snakexlength[r] = snakexlength[r-1];
				}
				//check if the snake is moving and touches the border, he will exit the other side
				if(snakexlength[r] > 850){
					snakexlength[r] = 25;
				}
			}
			repaint();
		}
		if(left){
			for(int r = lengthofsnake - 1; r >=0; r--){
				//shift the position of snake's head to it's next index
				snakeylength[r+1] = snakeylength[r];
			}
			//shift the position of snake x length
			for(int r = lengthofsnake; r >=0; r--){
				if(r == 0){
					snakexlength[r] = snakexlength[r] - 25;
				}else{
					snakexlength[r] = snakexlength[r-1];
				}
				//check if the snake is moving and touches the border, he will exit the other side
				if(snakexlength[r] < 25){
					snakexlength[r] = 850;
				}
			}
			repaint();		
				}
		if(up){
			for(int r = lengthofsnake - 1; r >=0; r--){
				//shift the position of snake's head to it's next index - x length
				snakexlength[r+1] = snakexlength[r];
			}
			//shift the position of snakey length
			for(int r = lengthofsnake; r >=0; r--){
				if(r == 0){
					snakeylength[r] = snakeylength[r] - 25;
				}else{
					snakeylength[r] = snakeylength[r-1];
				}
				//check if the snake is moving and touches the border, he will exit the other side
				if(snakeylength[r] < 75){
					snakeylength[r] = 625;
				}
			}
			repaint();
		}
		if(down){
			for(int r = lengthofsnake - 1; r >=0; r--){
				//shift the position of snake's head to it's next index - x length
				snakexlength[r+1] = snakexlength[r];
			}
			//shift the position of snake y length
			for(int r = lengthofsnake; r >=0; r--){
				if(r == 0){
					snakeylength[r] = snakeylength[r] + 25;
				}else{
					snakeylength[r] = snakeylength[r-1];
				}
				//check if the snake is moving and touches the border, he will exit the other side
				if(snakeylength[r] > 625){
					snakeylength[r] = 75;
				}
			}
			repaint();
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//space to restart
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			moves = 0;
			score = 0;
			lengthofsnake = 3;
			repaint();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			//added moves++ because if not, the snake will remain allways in default position
			moves++;
			
			right = true;
			// if the snake goes to left it should not be able to go to left
			if(!left){
				right = true;
			}else{
				right = false;
				left = true;
			}
			up = false;
			down = false;					
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			//added moves++ because if not, the snake will remain allways in default position
			moves++;
			
			left = true;
			// if the snake goes to left it should not be able to go to left
			if(!right){
				left = true;
			}else{
				left = false;
				right = true;
			}
			up = false;
			down = false;					
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			//added moves++ because if not, the snake will remain allways in default position
			moves++;
			
			up = true;
			// if the snake goes to left it should not be able to go to left
			if(!down){
				up = true;
			}else{
				up = false;
				down = true;
			}
			left = false;
			right = false;					
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			//added moves++ because if not, the snake will remain allways in default position
			moves++;
			
			down = true;
			// if the snake goes to left it should not be able to go to left
			if(!up){
				down = true;
			}else{
				down = false;
				up = true;
			}
			left = false;
			right = false;					
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
