package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	public static final Color GRAY_MARKER = new Color(0x00202020);
	private static final long serialVersionUID = 3006769532505931833L;

	private BufferedImage img;
	private Image scaledImg;
	private double scale;
	private int cursorX, cursorY, dragX, dragY;

	public ImagePanel() {
		init();
	}

	private void init() {
		setPreferredSize(new Dimension(500, 300));
		img = null;
		scaledImg = null;
		scale = 1;
		cursorX = 0;
		cursorY = 0;
		dragX = -1;
		dragY = -1;
	}

	public BufferedImage getImage() {
		return img;
	}

	public void setImage(BufferedImage img) {
		this.img = img;
		this.scaledImg = getScaledImage();
		setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
	}

	public Image getScaledImage() {
		if (img != null) {
			int width = (int) (img.getWidth() * scale);
			width = (width <= 0) ? 1 : width;
			int height = (int) (img.getHeight() * scale);
			height = (height <= 0) ? 1 : height;
			return img.getScaledInstance(width, height, 0);
		}
		return null;
	}

	public void setScale(double scale) {
		this.scale = scale;
		scaledImg = getScaledImage();
	}

	public double getScale() {
		return scale;
	}

	public void setCursor(int x, int y) {
		cursorX = x;
		cursorY = y;
	}

	public int getCursorX() {
		return cursorX;
	}

	public int getCursorY() {
		return cursorY;
	}

	public void setDrag(int x, int y) {
		dragX = x;
		dragY = y;
	}

	public int getDragX() {
		return dragX;
	}

	public int getDragY() {
		return dragY;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(scaledImg, 0, 0, null);
		if (dragX >= 0 && dragY >= 0) {
			g.setColor(GRAY_MARKER);
			int x = dragX < cursorX ? dragX : cursorX;
			int y = dragY < cursorY ? dragY : cursorY;
			int width = Math.abs(dragX - cursorX);
			int height = Math.abs(dragY - cursorY);
			g.drawRect(x, y, width, height);
		}
		g.setColor(Color.black);
		g.drawLine(cursorX, 0, cursorX, getHeight());
		g.drawLine(0, cursorY, getWidth(), cursorY);
	}
}
