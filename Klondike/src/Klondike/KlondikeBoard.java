package Klondike;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KlondikeBoard {

	private static List<Pile> piles;

	public KlondikeBoard() {
		piles = new ArrayList<>();
		for (int i = 1; i < 8; i++) {
			piles.add(new Pile(i, i - 1));
		}

	}

	public void clickedAt(int x, int y) {

	}

	public void drawBoardGUI(Graphics2D g) {
		
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(2));
		int x = 310;
		for (int i = 0; i < 5; i++) {
			g.drawRoundRect(x, 30, 115, 210, 25, 50);
			switch (i) {
			case 0:
				x += 320;
				break;
			default:
				x += 130;
				break;
			}
		}
		
		for (Pile p : piles) {
			p.draw(g);
		}
	}

}
