import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.HashMap;

public class LifeAsWeKnowIt {

	private int displayType = 3;
	private int rows = 40;
	private int cols = 63;
	
	// Contains layout of the grid (selected/unselected squares)
	private int[][] grid;

	//	Contains the number of neighbors each cell has
	private int[][] neighbors;
	
	//	The past grids that have been created, used to rewind to past generations
	private HashMap<Integer, TwoDimensionArray> history;
	
	private HashMap<String, Layout> savedLayouts;

	private JFrame frame;
	private LifePanel panel;

	// private LifeWorld world;

	//	The timer that is used for the game loop which redraws the panel every 1ms
	private Timer timer;
	
	//	The timer that is used to step the game every 500ms when the start button is pressed
	private Timer playTimer;
	//	Tells the game if it should play
	private boolean shouldPlay;

	private String SAVE_FILE_DIRECTORY = "../SaveFile.txt";
	
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
		new LifeAsWeKnowIt().start();
	}

	private void start() {
		//		displayType = promptDisplay();

		//		world = new LifeWorld(rows, cols);
		neighbors = new int[rows][cols];
		history = new HashMap<>();
		grid = new int[rows][cols];

		show();
		playTimer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				play();
			}
		});
		playTimer.start();
	}

	private int promptDisplay() {
		// TODO Auto-generated method stub
		return 1;
	}

	public void step() {
		for (int r = 0; r < neighbors.length - 1; r++) {
			for (int c = 0; c < neighbors[r].length - 1; c++) {
				neighbors[r][c] = getNumNeighbors(r,c);
			}
		}
		
		for (int r = 0; r < grid.length - 1; r++) {
			for (int c = 0; c < grid[r].length - 1; c++) {
				switch(grid[r][c]) {
					case 1:
						if (neighbors[r][c] == 1 || neighbors[r][c] == 0) {
							grid[r][c] = 0;
						} else if (neighbors[r][c] >= 4) {
							grid[r][c] = 0;
						}
						break;
					default:
						if (neighbors[r][c] == 3) {
							grid[r][c] = 1;
						}
						break;
				}

			}
		}
		history.put(new Integer(history.size()), new TwoDimensionArray(grid));
	}

	private int getNumNeighbors(int row, int col) {
		int neighbors = 0;    
		if(row != 0 && row != rows - 1 && col != 0 && col != cols - 1) {
			if(grid[row+1][col] == 1) {
				neighbors++;
			}
			if(grid[row-1][col] == 1) {
				neighbors++;
			}
			if(grid[row][col + 1] == 1) {
				neighbors++;
			}
			if(grid[row][col-1] == 1) {
				neighbors++;
			}
			if(grid[row+1][col+1] == 1) {
				neighbors++;
			}
			if(grid[row-1][col-1] == 1) {
				neighbors++;
			}
			if(grid[row-1][col+1] == 1) {
				neighbors++;
			}
			if(grid[row+1][col-1] == 1) {
				neighbors++;
			}
		}
		return neighbors;
	}

	public void play() {
		if (shouldPlay) {
			step();
		}
	}
	
	public void shouldPlay() {
		shouldPlay = !shouldPlay;
	}
	
	private void show() {
		switch (displayType) {
			case 1:
			dispConsole();
			case 2:
			displayGridWorld();
			case 3:
			displayCool();
		}
	}

	private void displayCool() {
		if (panel == null) {
			panel = new LifePanel(grid, this);
			frame = new JFrame("Life As We Know It");
			frame.add(panel);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			startTimer();
		}
	}

	private void refresh() {
		panel.refresh();
	}

	private int currentGridVersion = -1;
	public void rewind() {	
		if (currentGridVersion == -1) {
			currentGridVersion = history.size() - 1; 
		} 
		System.out.println(currentGridVersion);
		if (currentGridVersion >= 0) {
			System.out.println(currentGridVersion);
			int[][] temp = history.get(new Integer(currentGridVersion)).array();
			for (int r = 0; r < grid.length; r++) {
				for (int c = 0; c < grid[r].length; c++) {
					grid[r][c] = temp[r][c];
					System.out.print(grid[r][c] + ", ");
				}
				System.out.println();
			}
			panel.setGrid(grid);
			currentGridVersion--;
		}	
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
	}

	private void startTimer() {
		timer = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh()
;			}
		});
		timer.start();
	}

	private void displayGridWorld() {
//		world.display(grid);
	}

	private void dispConsole() {
//		world.print(grid);
	}

	public String[] getSavedLayoutTitles() {
		if (this.savedLayouts  == null) {
			this.savedLayouts = new HashMap<>();
			List<Layout> savedLayouts = new ArrayList<>();
			Layout layout = new Layout();

			try {
				BufferedReader reader = new BufferedReader(new FileReader(new File(SAVE_FILE_DIRECTORY)));	
				for (String x = reader.readLine(); x != null; x = reader.readLine()) {
					if (x.contains("name: ")) {
						layout.setName(x.substring(6, x.length()));
						savedLayouts.add(layout);
						layout = new Layout();
						continue;
					}
					String[] coords = x.split("  ");
					System.out.println("X: " + coords[0] + " Y: " + coords[1]);
					layout.addLocation(new Location(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]))); 				
				}
				reader.close();
			} catch (IOException e) {
				System.out.println("Error reading (" + SAVE_FILE_DIRECTORY + ")");
				e.printStackTrace();
			}

			for (Layout l : savedLayouts) {
				this.savedLayouts.put(l.name(), l);
			}
		}

		String[] fileNames = new String[savedLayouts.size()];
		int i = 0;
		for (String name : this.savedLayouts.keySet()) {
			fileNames[i] = name;
			i++;
		}
		return fileNames;
	}
	
	public void loadGrid(String key) {
		int[][] newGrid = savedLayouts.get(key).grid(grid.length, grid[0].length);
		// for (int[] r : newGrid) {
		// 	for (int c : r) {
		// 		System.out.print(c);
		// 	}
		// 	System.out.println();
		// }
		System.out.println(this.grid == newGrid);
		for (int r = 0; r < grid.length; r++) {
			// for (int c = 0; c < grid[0].length; c++) {
			// 	this.grid[r][c] = newGrid[r][c];
			// }
			System.arraycopy(newGrid[r], 0, grid[r], 0, grid.length);
		}
		System.out.println(this.grid == newGrid);
		panel.setGrid(this.grid);
	}

	private void loadLife() {
		File f = getFile();
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch(Exception e) {
			System.out.println("Ouch!  Problem with file!! "+e);
		}
		if(scan == null)
			loadLife();
	}

	private File getFile() {
		return new File("life100.txt");
	}

}