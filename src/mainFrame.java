import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.UIManager;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.ComponentOrientation;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

@SuppressWarnings("serial")
public class mainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel Player;
	private JPanel panel;
	private JPanel pnlAviso;
	private JLabel lblW;
	private JLabel lblH;
	private JLabel lblX;
	private JLabel lblY;
	private JLabel lblNewLabel;
	private JLabel lblQtdvida;
	private JLabel lblAviso;
	private JPanel pnlPoint;
	Rectangle ob1r;
	Rectangle ob2r;
	Rectangle ob3r;
	Rectangle ob4r;
	Rectangle ob5r;
	Rectangle po;

	int delay = 1;
	int vidas = 10;
	int pontos = 0;
	boolean flag = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
					new Thread() {
						@Override
						public void run() {
							while (true) {
								try {
									Thread.sleep(1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if (!(frame.getPanel().getMousePosition() == null)) {
									Point p = frame.getPanel().getMousePosition();
									frame.getPlayer().setLocation((int) p.getX() - (frame.getPlayer().getWidth() / 2),
											(int) p.getY() - (frame.getPlayer().getHeight() / 2));
								}
							}
						}
					}.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	final Timer Up = new Timer(delay, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			goTo(0, -1);

		}
	});

	final Timer Down = new Timer(delay, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			goTo(0, 1);
		}
	});

	final Timer Right = new Timer(delay, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			goTo(1, 0);
		}
	});
	final Timer Left = new Timer(delay, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			goTo(-1, 0);
		}
	});

	final Timer wait = new Timer(500, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			wait.stop();
		}
	});
	private JPanel ob1;
	private JPanel ob3;
	private JPanel ob4;
	private JPanel ob5;
	private JPanel ob2;

	public void alert(String s) {

		new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 3; i++) {
					try {
						Thread.sleep(100);
						pnlAviso.setBackground(Color.black);
						Thread.sleep(100);
						pnlAviso.setBackground(Color.white);
						lblAviso.setText(s);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					pnlAviso.setBackground(Color.gray);
				}
			};
		}.start();
	}

	public void goTo(int x, int y) {

		if (wait.isRunning())
			return;

		int Y = (int) Player.getLocation().getY();
		int H = Player.getHeight() + Y;
		int X = (int) Player.getLocation().getX();
		int W = Player.getWidth() + X;

		if ((Y <= 2 && y == -1) || (H >= panel.getHeight() - 2 && y == 1) || (W >= panel.getWidth() - 2 && x == 1)
				|| (X <= 2) && x == -1) {
			perdeuVida();
			return;
		}

		Rectangle pl = new Rectangle((int) Player.getLocation().getX(), (int) Player.getLocation().getY(),
				(int) Player.getWidth(), (int) Player.getHeight());

		if (pl.intersects(ob1r) || pl.intersects(ob2r) || pl.intersects(ob3r) || pl.intersects(ob4r)
				|| pl.intersects(ob5r)) {
			System.out.println((X + x * -1) + " " + (Y + y * -1));
			Player.setLocation(X + x * -1, Y + y * -1);
			perdeuVida();
			return;
		}
		
		lblY.setText(String.valueOf(Y));
		lblH.setText(String.valueOf(H));
		lblX.setText(String.valueOf(X));
		lblW.setText(String.valueOf(W));

		if (pl.intersects(po)) {
			System.out.println("PONTO!");
			pontos++;
			inserirPonto();
		}

		Player.setLocation((int) Player.getLocation().getX() + x, (int) Player.getLocation().getY() + y);
		
	}

	private void perdeuVida() {
		if (vidas <= 0) {
			JOptionPane.showMessageDialog(null,
					"<html><body><b>** HAHA THE GAME **</b></html></body>" + "\nSua pontuação: " + pontos,
					"VOCE PERDEU", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} else if (vidas >= 1) {

			alert("VOCE PERDEU UMA VIDA");
			vidas--;
			flag = false;
			wait.start();
		}

		lblQtdvida.setText(String.valueOf(vidas));
	}

	private void inserirPonto() {
		Random randomizer = new Random();
		System.out.println(randomizer.nextInt(panel.getWidth() - 10) + 5);
		Point p = new Point(randomizer.nextInt(panel.getWidth() - 10) + 5,
				randomizer.nextInt(panel.getHeight() - 10) + 5);
		pnlPoint.setLocation(p);

		po = new Rectangle((int) pnlPoint.getLocation().getX(), (int) pnlPoint.getLocation().getY(),
				(int) pnlPoint.getWidth(), (int) pnlPoint.getHeight());
	}

	public mainFrame() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				inserirPonto();
			}
		});

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 475, 303);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, Color.ORANGE));
		panel.setFocusTraversalKeysEnabled(false);
		panel.setFocusable(false);
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		JButton btnSobe = new JButton("horizontal");
		btnSobe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inserirPonto();
			}
		});
		btnSobe.setFocusable(false);
		btnSobe.setFocusTraversalKeysEnabled(false);
		btnSobe.setFocusPainted(false);

		JButton btnDesce = new JButton("Vertical");
		btnDesce.setFocusPainted(false);
		btnDesce.setFocusTraversalKeysEnabled(false);
		btnDesce.setFocusable(false);

		Player = new JPanel();
		Player.setBackground(Color.BLACK);
		Player.setBounds(10, 11, 28, 25);
		panel.add(Player);
		Player.setLayout(null);

		lblY = new JLabel("Y");
		lblY.setFont(new Font("Tahoma", Font.PLAIN, 6));
		lblY.setBounds(1, 1, 24, 14);
		Player.add(lblY);

		lblX = new JLabel("X");
		lblX.setFont(new Font("Tahoma", Font.PLAIN, 6));
		lblX.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		lblX.setBounds(1, 1, 22, 14);
		Player.add(lblX);

		lblH = new JLabel("H");
		lblH.setFont(new Font("Tahoma", Font.PLAIN, 6));
		lblH.setBounds(1, 11, 24, 14);
		Player.add(lblH);

		lblW = new JLabel("W");
		lblW.setFont(new Font("Tahoma", Font.PLAIN, 6));
		lblW.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		lblW.setBounds(1, 11, 24, 14);
		Player.add(lblW);

		pnlPoint = new JPanel();
		pnlPoint.setBackground(Color.BLUE);
		pnlPoint.setBounds(234, 11, 10, 10);
		panel.add(pnlPoint);

		pnlAviso = new JPanel();
		pnlAviso.setFocusable(false);
		pnlAviso.setFocusTraversalKeysEnabled(false);
		pnlAviso.setBackground(Color.LIGHT_GRAY);

		lblAviso = new JLabel("QtdVida");
		lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lblAviso.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAviso.setAutoscrolls(true);

		lblNewLabel = new JLabel("Vidas:");

		lblQtdvida = new JLabel("10");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(1)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblNewLabel).addGap(10)
								.addComponent(lblQtdvida, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
								.addComponent(btnDesce, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSobe, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE))
						.addComponent(pnlAviso, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(4).addComponent(lblNewLabel))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(4).addComponent(lblQtdvida))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSobe, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnDesce, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(pnlAviso, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(3)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(6, Short.MAX_VALUE)));

		ob1 = new JPanel();
		ob1.setBackground(Color.RED);
		ob1.setBounds(58, 47, 4, 134);
		panel.add(ob1);

		ob3 = new JPanel();
		ob3.setBackground(Color.RED);
		ob3.setBounds(157, 11, 4, 134);
		panel.add(ob3);

		ob4 = new JPanel();
		ob4.setBackground(Color.RED);
		ob4.setBounds(240, 142, 118, 3);
		panel.add(ob4);

		ob5 = new JPanel();
		ob5.setBackground(Color.RED);
		ob5.setBounds(297, 47, 118, 3);
		panel.add(ob5);

		ob2 = new JPanel();
		ob2.setBackground(Color.RED);
		ob2.setBounds(194, 84, 4, 10);
		panel.add(ob2);
		GroupLayout gl_pnlAviso = new GroupLayout(pnlAviso);
		gl_pnlAviso.setHorizontalGroup(
				gl_pnlAviso.createParallelGroup(Alignment.LEADING).addGroup(gl_pnlAviso.createSequentialGroup()
						.addGap(10).addComponent(lblAviso, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE).addGap(10)));
		gl_pnlAviso.setVerticalGroup(gl_pnlAviso.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlAviso.createSequentialGroup().addGap(11).addComponent(lblAviso,
						GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)));
		pnlAviso.setLayout(gl_pnlAviso);
		contentPane.setLayout(gl_contentPane);

		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent evt) {
			}

			@Override
			public void keyReleased(KeyEvent evt) {

				int key = evt.getKeyCode();
				if (key == KeyEvent.VK_LEFT) {
					if (Left.isRunning()) {
						Left.stop();
					}
				} else if (key == KeyEvent.VK_RIGHT) {
					if (Right.isRunning()) {
						Right.stop();
					}
				} else if (key == KeyEvent.VK_UP) {
					if (Up.isRunning()) {
						Up.stop();
					}
				} else if (key == KeyEvent.VK_DOWN) {
					if (Down.isRunning()) {
						Down.stop();
					}
				}

			}

			@Override
			public void keyPressed(KeyEvent evt) {

				int key = evt.getKeyCode();
				if (key == KeyEvent.VK_LEFT) {
					if (!Left.isRunning()) {
						Left.start();
					}
				} else if (key == KeyEvent.VK_RIGHT) {
					if (!Right.isRunning()) {
						Right.start();
					}
				} else if (key == KeyEvent.VK_UP) {
					if (!Up.isRunning()) {
						Up.start();
					}
				} else if (key == KeyEvent.VK_DOWN) {
					if (!Down.isRunning()) {
						Down.start();
					}
				}
			}
		});

		this.setFocusable(true);
		this.requestFocus();
		if (panel.getWidth() > 0) {
			inserirPonto();
		}
		ob1r = new Rectangle((int) ob1.getLocation().getX(), (int) ob1.getLocation().getY(), (int) ob1.getWidth(),
				(int) ob1.getHeight());
		ob2r = new Rectangle((int) ob2.getLocation().getX(), (int) ob2.getLocation().getY(), (int) ob2.getWidth(),
				(int) ob2.getHeight());
		ob3r = new Rectangle((int) ob3.getLocation().getX(), (int) ob3.getLocation().getY(), (int) ob3.getWidth(),
				(int) ob3.getHeight());
		ob4r = new Rectangle((int) ob4.getLocation().getX(), (int) ob4.getLocation().getY(), (int) ob4.getWidth(),
				(int) ob4.getHeight());
		ob5r = new Rectangle((int) ob5.getLocation().getX(), (int) ob5.getLocation().getY(), (int) ob5.getWidth(),
				(int) ob5.getHeight());

	}

	public JPanel getPanel() {
		return panel;
	}

	public JPanel getPlayer() {
		return Player;
	}
}
