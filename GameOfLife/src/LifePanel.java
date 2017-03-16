import java.awt.Dimension;
import java.awt.Graphics;
//import javax.swing.AbstractButton;
//import javax.swing.JButton;
//import javax.swing.JPanel;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;

public class LifePanel extends JPanel {

	private int[][] grid;

	final static int SQUARE_WIDTH = 12;
	final static int LINE_THICKNESS = 1;
	final static Dimension DIMENSIONS = new Dimension(750, 500);

	private JButton nextButton;
	private JButton startButton;

	private LifeAsWeKnowIt life;
	
	public LifePanel(int[][] grid, LifeAsWeKnowIt life) {
		this.grid = grid;
		//		setBackground(new Color(84,110,122));
		setBackground(new Color(69,90,100));
		setPreferredSize(DIMENSIONS);

		this.life = life;
		setUpClickListener();
		addButtons();
		setVisible(true);
	}


	public void displayGrid(int[][] gr) {		
		repaint();
	}

	public void refresh() {
		repaint();
	}

	private void setUpClickListener() {
		this.requestFocusInWindow();
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}
			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent click) {

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				clicked(arg0);
			}

		});
	}

	private void clicked(MouseEvent e) {
		int row = e.getY() / SQUARE_WIDTH;
		int col = e.getX() / SQUARE_WIDTH;

		if (grid[row][col] == 1) {
			grid[row][col] = 0;
		} else {
			grid[row][col] = 1;
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] == 1) {
					g.setColor(new Color(67, 160, 71));
					g2.fillRect(c * SQUARE_WIDTH + LINE_THICKNESS, r * SQUARE_WIDTH + LINE_THICKNESS, SQUARE_WIDTH - LINE_THICKNESS, SQUARE_WIDTH - LINE_THICKNESS);		
					g.setColor(Color.BLACK);
				}
			}
		}
		drawGrid(g2);
	}

	private void drawGrid(Graphics2D g2) {
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				//	TODO: Fix grid resizing so that it adjusts when the panel is resized
				g2.drawLine(SQUARE_WIDTH * c, 0, SQUARE_WIDTH * c, (DIMENSIONS.height) - 92);
			}
			g2.drawLine(0, SQUARE_WIDTH * r, (DIMENSIONS.width), SQUARE_WIDTH * r);
		}
//		for (int r = 0; r < grid.length; r++) {
//			for (int c = 0; c < grid[0].length; c++) {
//				//	TODO: Fix grid resizing so that it adjusts when the panel is resized
//				g2.drawLine(SQUARE_WIDTH * c, 80, SQUARE_WIDTH * c, (DIMENSIONS.height));
//			}
//			g2.drawLine(0, (SQUARE_WIDTH * r) + 80, (DIMENSIONS.width), (SQUARE_WIDTH * r) + 80);
//		}
	}

	private void addButtons() {
		nextButton = new JButton("Next");
		startButton = new JButton("Start");
		
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextButtonAction();
			}
		});
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startButtonAction();
			}		
		});

		this.add(startButton);
		this.add(nextButton);
	}

	private void nextButtonAction() {
		life.step();
	}

	private void startButtonAction() {
		life.shouldPlay();
		life.play();
	}

}
