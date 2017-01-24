import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

public class KlondikeBoard {

	//	A list of seven Piles that are drawn horizontally on the Board
	private static List<Pile> piles;

	private static final String[] RANKS = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

	/**
	 * The suits of the cards for this game to be sent to the deck.
	 */
	private static final String[] SUITS = {"spades", "hearts", "diamonds", "clubs"};

	private boolean clickedCard;

	public KlondikeBoard() {
		piles = new ArrayList<>();

		//	Initialize the seven piles with (i - 1) cards each
		for (int i = 1; i < 8; i++) {
			piles.add(new Pile(i, i - 1));
		}

		this.clickedCard = false;
	}

	/**
	* @param x is the X coordinate of the click
	* @param y is the Y coordinate of the click
	* Determines what card has been clicked in the board
	* and returns if there is no card at the clicked position
	* by using a cards getX() and getY() methods, if a card is clicked
	* then it will tell the board that a card was clicked
	*/
	public void clickedAt(int x, int y) {
		for (Pile p : piles) {
			p.clickedAt(x,y);
			for (Card c : p.cards()) {
				if (p.addedCardsContainPoint(x,y)) {
					clickedCard = true;
				}
			}
		}
	}

	public boolean clickedCard() {
		return clickedCard;
	}

	/**
	* @param g Graphics2D context sent from 
	* KlondikePanel in paintComponent(Graphics g)
	* Draw all necessary GUI when called
	*/
	public void drawBoardGUI(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(2));
		int x = 170;
		for (int i = 0; i < 5; i++) {
			g.drawRoundRect(x, 30, 90, 150, 25, 50);
			switch (i) {
			case 0:
				x += 170;
				break;
			default:
				x += 120;
				break;
			}
		}
		for (final Pile p : piles) {
			p.draw(g);
		}
	}

}
