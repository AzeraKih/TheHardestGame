
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class BounceSquad extends JPanel {

	private static final long serialVersionUID = 5935524L;

	private int pv = -1;
	private int ph = 1;
	private BounceSquad eu = this;

	private int speed;

	private List<Thread> horizontal = new ArrayList<Thread>();
	private List<Thread> vertical = new ArrayList<Thread>();

	private JPanel pai;

	public BounceSquad(int speed, JPanel pai) {
		super();
		this.speed = speed;
		this.pai = pai;
	}

	public void startBounce() {

		horizontal.add(new Thread() {

			@Override
			public void run() {
				while (true) {
					try {
						if (getLocation().getX() <= 0) {
							ph = 1;
							randomizeColor();
						} else if (getLocation().getX() + getWidth() > pai.getWidth()) {
							ph = -1;
							randomizeColor();
						}
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setLocation((int) getLocation().getX() + (1 * ph), (int) getLocation().getY());
				}

			}
		});
		horizontal.get(horizontal.size() - 1).start();

		vertical.add(new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						if (getLocation().getY() < 0) {
							pv = 1;
							randomizeColor();
						} else if (getLocation().getY() + getHeight() > pai.getHeight()) {
							pv = -1;
							randomizeColor();
						}
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					setLocation((int) getLocation().getX(), (int) getLocation().getY() + (1 * pv));
				}

			}
		});
		vertical.get(vertical.size() - 1).start();

	}

	public Rectangle getRectangle() {
		return (new Rectangle((int) getLocation().getX(), (int) getLocation().getY(), (int) getWidth(),
				(int) getHeight()));
	}
	
	public void randomizeColor() {
		Random random = new Random();
		int R = random.nextInt(255);
		int G = random.nextInt(255);
		int B = random.nextInt(255);
		
		this.setBackground(new Color(R, G , B));
		
	}
	
	

}
