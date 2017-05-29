import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;
import java.io.PrintStream;
public class Frogger extends JFrame implements KeyListener{
	CarPanel MycarPanel;

	public Frogger() {
		// Set the properties of the frame
		MycarPanel = new CarPanel();
		setLayout(new BorderLayout());
		add(MycarPanel, "Center");
		setTitle("Frogger");
		setSize(400, 600);
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
		setVisible(true);
		addKeyListener(this);
		setResizable(false);

		//this.add(MycarPanel);

	}
	public static void main(String[] args){
		new Frogger();
	}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyChar();
		if (key == 's'|| key =='S') {
			MycarPanel.frog.moveDown();
		}
		if (key == 'w'|| key =='W') {
			MycarPanel.frog.moveUp();
		}
		if (key == 'a'|| key =='A') {
			MycarPanel.frog.moveLeft();
		}
		if (key == 'd'|| key =='D') {
			MycarPanel.frog.moveRight();
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
class CarPanel extends JPanel implements ActionListener {
	Car[] cars;
	Frog frog;
	Frog[] lifeFrogs = { new Frog(10, 520), new Frog(40, 520), new Frog(70, 520) };
	int FROG_LEFT = 175; int FROG_TOP = 505;
	Timer myTimer;
	int lives = 3;
	int score = 0;
	public CarPanel(){
		setSize(400, 600);
		frog = new Frog(FROG_LEFT, FROG_TOP);
		cars = new Car[16];
		for (int i = 0; i < 4; i++) {
			cars[i] = new Car(300 - i * 100, 415, 1, 1);
		}
		for (int i = 4; i < 8; i++) {
			cars[i] = new Car(300 - (i - 4) * 100, 360, 1, -1);
		}
		for (int i = 8; i < 12; i++) {
			cars[i] = new Car(300 - (i - 8) * 100, 265, 2, 1);
		}
		for (int i = 12; i < 16; i++) {
			cars[i] = new Car(300 - (i - 12) * 100, 215, 2, -1);
		}

		myTimer = new Timer (10, new ActionListener(){
			public void actionPerformed (ActionEvent evt){
				repaint()	;
			}
		});
		myTimer.start();

	}
	public static void main(String[] args) {
		new Frogger();
	}

	public void paint(Graphics g){
		super.paint(g);

		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		for (int i = 0; i < lives; i++) {
			lifeFrogs[i].draw(g2);
		}
		frog.draw(g2);
		for (int i = 0; i < 16; i++) {
			cars[i].move();
			cars[i].draw(g2);
			if (frog.isTouching(cars[i])) {
				die(g2);
			}
		}
		
		g2.setPaint(Color.black);
		g2.drawString("Lives: ", 15, 505);
		g2.drawString("Score: ", 350, 505);
		g2.drawString(score+"", 350,525);
		 if (lives < 0) {
		      gameOver(g2);
		    }
	}


	public void drawBackground(Graphics2D g2){
		g2.setPaint(new Color(0, 255, 0));
		g2.fill(new Rectangle2D.Double(0.0D, 0.0D, 400.0D, 600.0D));


		g2.setPaint(new Color(0, 0, 255));
		g2.fill(new Arc2D.Double(0.0D, -25.0D, 400.0D, 75.0D, 180.0D, 360.0D, 1));


		Color roadColor = new Color(150, 150, 150);
		g2.setPaint(roadColor);
		g2.fill(new Rectangle2D.Double(0.0D, 350.0D, 400.0D, 100.0D));
		g2.fill(new Rectangle2D.Double(0.0D, 200.0D, 400.0D, 100.0D));

		Color stripesColor = new Color(200, 200, 0);
		g2.setPaint(stripesColor);
		for (int x = 0; x < 400; x += 40) {
			g2.fill(new Rectangle(x, 399, 20, 3));
			g2.fill(new Rectangle(x, 249, 20, 3));
		}
	}
	void die(Graphics2D g2){
		lives -= 1;
		System.out.println("lives" + lives);
		frog.move(FROG_LEFT, FROG_TOP);
		frog.draw(g2);
	}

	void win(Graphics2D g2) {
		score += 100;
		frog.move(FROG_LEFT, FROG_TOP);
		frog.draw(g2);
	}

	void gameOver(Graphics2D g2) {
		Color background = new Color(0, 255, 0);
		g2.setPaint(background);
		g2.fill(new Rectangle2D.Double(0.0D, 0.0D, 400.0D, 600.0D));
		g2.setPaint(Color.black);
		g2.drawString("Game Over", 130, 270);
		g2.drawString("Score: " + score, 130, 300);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
class Car{
	int speed;
	int direction;
	int left;
	int right;
	int top;
	int bottom;
	int width;
	int height;
	Color outline;
	Color fill2;


	Car(int left, int top, int speed, int direction)
	{
		outline = Color.black;
		fill2 = new Color((int)(255.0D * Math.random()), (int)(255.0D * Math.random()), (int)(255.0D * Math.random()));

		this.left = left;
		this.top = top;
		width = 35;
		height = 20;
		this.speed = speed;
		this.direction = direction;

		right = (left + width);
		bottom = (top + height);
	}

	void move() { 
		left += speed * direction;
		right += speed * direction;
		if (right < 0) {
			left = 400;
			right = (400 + width);
		}
		if (left > 400) {
			right = 0;
			left = (0 - width);
		}
	}

	void draw(Graphics2D g2) {
		g2.setPaint(fill2);
		g2.fill(new Rectangle2D.Double(left, top, width, height));
	}
}
class Frog{
	int speed;
	int direction;
	int left;
	int right;
	int top;
	int bottom;
	int width;
	int height;
	Color outline;
	Color fill2;

	Frog(int left, int top)
	{
		this.left = left;
		this.top = top;

		outline = Color.black;
		fill2 = new Color(20, 220, 20);

		width = 25;
		height = 40;
		right = (left + width);
		bottom = (top + height);
	}

	public boolean isTouching(Car other) {
		return ((this.left < other.right) && (this.right > other.left) && (this.top < other.bottom)&& (this.bottom > other.top));
	}

	void moveLeft() {
		left -= width;
		right -= width;
	}

	void moveRight() { left += width;
	right += width;
	}

	void moveUp() { top -= 50;
	bottom -= 50;
	}

	void moveDown() { top += 50;
	bottom += 50;
	}

	void move(int left, int top) { this.left = left;
	this.top = top;
	right = (left + width);
	bottom = (top + height);
	}

	void draw(Graphics2D g2) { g2.setPaint(outline);
	g2.setStroke(new BasicStroke(3.0F));
	g2.draw(new Ellipse2D.Double(left, top, width, height));
	g2.setPaint(fill2);
	g2.fill(new Ellipse2D.Double(left, top, width, height));
	}
}


