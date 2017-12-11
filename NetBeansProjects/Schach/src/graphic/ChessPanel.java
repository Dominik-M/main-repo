/*
 * Copyright (C) 2017 Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import platform.graphic.InputConfig;
import platform.graphic.MainPanel;
import platform.image.ImageIO;
import platform.utils.IO;
import chess.Chess;
import chess.Position;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 08.03.2017
 */
public class ChessPanel extends MainPanel {

	public static final Color BGCOLOR1 = new Color(227, 197, 140),
	BGCOLOR2 = new Color(149, 95, 34), FONTCOLOR = new Color(165, 42, 42);
	public static final int MARGIN = 1, BORDER = 1;
	private final Rectangle CHESSFIELD_BOUNDS;
	private final int WIDTH, HEIGHT;

	private int selX = -1, selY = -1, mouseX = -1, mouseY = -1;
	private Chess.Figure selFigure = null;
	private Position[] movePositions = new Position[0];

	private final MouseAdapter mouseListener = new MouseAdapter() {

		@Override
		public void mouseMoved(MouseEvent ev) {
			mouseX = translateScreenX(ev.getX());
			mouseY = translateScreenY(ev.getY());
		}

		@Override
		public void mouseClicked(MouseEvent ev) {
			if (mouseX >= 0 && mouseY >= 0 && selFigure != null) {
				for (Position pos : movePositions) {
					if (pos.x == mouseX && pos.y == mouseY) {
						Chess.getInstance().moveFigure(selX, selY, mouseX, mouseY);
						removeSelection();
						return;
					}
				}
			}

			if (mouseX == selX && mouseY == selY) {
				removeSelection();
			} else {
				selectFigureAt(mouseX, mouseY);
			}
		}
	};

	public int translateScreenX(int screenX) {
		return Chess.getInstance().getWidth() * screenX / getWidth();
	}

	public int translateScreenY(int screenY) {
		return Chess.getInstance().getHeight() * (screenY) / (getHeight());
	}

	public void selectFigureAt(int x, int y) {
		selX = x;
		selY = y;
		selFigure = Chess.getInstance().getFigureAt(selX, selY);
		if (selFigure != null) {
			IO.println("Selected Figure: " + selFigure, IO.MessageType.DEBUG);
			movePositions = selFigure.getPossibleLocations();
		} else {
			removeSelection();
		}
	}

	public void removeSelection() {
		selX = -1;
		selY = -1;
		selFigure = null;
		movePositions = new Position[0];
	}

	public ChessPanel() {
		WIDTH = getScreenWidth();
		HEIGHT = getScreenHeight();
		CHESSFIELD_BOUNDS = new Rectangle(2 * BORDER + MARGIN, BORDER, WIDTH - MARGIN - 2 * BORDER,
		HEIGHT - MARGIN - 2 * BORDER);
	}

	@Override
	public void onDisselect() {
		this.removeMouseListener(mouseListener);
		this.removeMouseMotionListener(mouseListener);
	}

	@Override
	public void onSelect() {
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
	}

	private void drawChessfield(Graphics2D g, Rectangle bounds) {
		Chess game = Chess.getInstance();
		int spotWidth = bounds.width / game.getWidth();
		int spotHeight = bounds.height / game.getHeight();
		g.setColor(FONTCOLOR);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.white);
		g.fillRect(BORDER, BORDER, WIDTH - 2 * BORDER, HEIGHT - 2 * BORDER);

		for (int x = 0; x < game.getWidth(); x++) {
			for (int y = 0; y < game.getHeight(); y++) {
				if (x == selX && y == selY) {
					g.setColor(Color.green);
				} else if (x == mouseX && y == mouseY) {
					g.setColor(Color.blue);
				} else if (Chess.getInstance().getKing(Chess.Team.WEIS) != null
				&& Chess.getInstance().getKing(Chess.Team.WEIS).isSchach()
				&& Chess.getInstance().getKing(Chess.Team.WEIS).getX() == x
				&& Chess.getInstance().getKing(Chess.Team.WEIS).getY() == y) {
					g.setColor(Color.red);
				} else if (Chess.getInstance().getKing(Chess.Team.SCHWARZ) != null
				&& Chess.getInstance().getKing(Chess.Team.SCHWARZ).isSchach()
				&& Chess.getInstance().getKing(Chess.Team.SCHWARZ).getX() == x
				&& Chess.getInstance().getKing(Chess.Team.SCHWARZ).getY() == y) {
					g.setColor(Color.red);
				} else if ((y + x) % 2 == 0) {
					g.setColor(BGCOLOR1);
				} else {
					g.setColor(BGCOLOR2);
				}
				g.fillRect(bounds.x + x * spotWidth, bounds.y + y * spotHeight, spotWidth,
				spotHeight);
				Chess.Figure fig = Chess.getInstance().getFigureAt(x, y);
				if (fig != null) {
					// fig.getSprite().draw(g, bounds.x + x * spotWidth, bounds.y + y * spotHeight, 0);
					g.drawImage(fig.getSprite().getImages()[0].getScaledInstance(spotWidth
					+ spotWidth / 2, spotHeight + spotHeight / 2, 0), bounds.x + x * spotWidth
					- spotWidth / 4, bounds.y + y * spotHeight - spotHeight / 4, this);
				}
				for (Position pos : movePositions) {
					if (x == pos.x && y == pos.y) {
						g.drawImage(ImageIO.getSprite("fieldmarker.png").getImages()[0]
						.getScaledInstance(spotWidth + spotWidth / 2, spotHeight + spotHeight / 2,
						0), bounds.x + x * spotWidth - spotWidth / 4, bounds.y + y * spotHeight
						- spotHeight / 4, this);
						break;
					}
				}
			}
		}
	}

	@Override
	public void drawGUI(Graphics2D g) {
		drawChessfield(g, CHESSFIELD_BOUNDS);
	}

	@Override
	public void keyPressed(InputConfig.Key key) {
	}

	@Override
	public void keyReleased(InputConfig.Key key) {
	}

}
