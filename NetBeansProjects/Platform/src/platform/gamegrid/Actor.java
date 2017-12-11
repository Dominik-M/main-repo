package platform.gamegrid;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.lang.reflect.Field;

import platform.image.ImageIO;
import platform.image.Sprite;
import platform.utils.Dictionary;
import platform.utils.IO;
import platform.utils.SerializableReflectObject;
import platform.utils.Utilities;
import platform.utils.Vector2D;

/**
 * Base class of acting Objects in the application.
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 31.10.2016
 */
public abstract class Actor extends SerializableReflectObject implements ClockTimed {
	private static final long serialVersionUID = -7667378322115498335L;
	protected double x;
	protected double y;
	protected Vector2D speedvector;
	protected double direction;
	protected boolean visible, invalid;
	protected final String[] spritenames;
	protected int spriteID = 0;
	protected int team;
	protected AI ai;

	public Actor(String mainSprite, String... spritenames) {
		this.spritenames = new String[spritenames.length + 1];
		this.spritenames[0] = mainSprite;
		System.arraycopy(this.spritenames, 1, spritenames, 0, spritenames.length);
		init();
	}

	public final void init() {
		x = 0;
		y = 0;
		speedvector = Vector2D.NULLVECTOR;
		direction = 0;
		visible = true;
		invalid = false;
		team = 0;
		ai = null;
	}

	public int getXOnScreen() {
		return (int) x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getYOnScreen() {
		return (int) y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Vector2D getPosition() {
		Rectangle bounds = this.getBounds();
		return new Vector2D(bounds.getCenterX(), bounds.getCenterY());
	}

	public Vector2D getCenterPosition() {
		Rectangle bounds = this.getBounds();
		return new Vector2D(bounds.getCenterX(), bounds.getCenterY());
	}

	public double getSpeed() {
		return speedvector.magnitude();
	}

	public void setSpeed(Vector2D speed) {
		speedvector = speed;
	}

	public Vector2D getSpeedVector() {
		return speedvector;
	}

	public double getDirection() {
		return direction;
	}

	public double getDirectionRad() {
		return Utilities.toRad(direction - 90);
	}

	public void setDirection(double direction) {
		while (direction < 0)
			direction += 360;
		while (direction >= 360)
			direction -= 360;
		this.direction = direction;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isColiding(Actor other) {
		if (isNear(other)) {
			return true;
		}
		return false;
	}

	public boolean isNear(Actor other) {
		return this.getBounds().intersects(other.getBounds());
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public AI getAI() {
		return ai;
	}

	public void setAI(AI ki) {
		this.ai = ki;
	}

	public void invalidate() {
		invalid = true;
	}

	public boolean isInvalid() {
		return invalid;
	}

	public Sprite getSprite() {
		if (spriteID < spritenames.length && ImageIO.containsSprite(spritenames[spriteID]))
			return ImageIO.getSprite(spritenames[spriteID]);
		else if (ImageIO.containsSprite(spritenames[0]))
			return ImageIO.getSprite(spritenames[0]);
		else
			return null;
	}

	public Rectangle getBounds() {
		Sprite sprite = getSprite();
		if (sprite != null)
			return new Rectangle(this.getXOnScreen(), this.getYOnScreen(), sprite.getWidth(),
			sprite.getHeight());
		else
			return new Rectangle(this.getXOnScreen(), this.getYOnScreen(), 1, 1);
	}

	public void paint(Graphics g) {
		Sprite sprite = getSprite();
		if (visible && sprite != null) {
			sprite.draw(g, this.getXOnScreen(), this.getYOnScreen(), getDirection());
		}
	}

	@Override
	public final void clock() {
		if (ai != null) {
			ai.preAct(this);
		}
		act();
	}

	protected abstract void act();

	public void move() {
		x += speedvector.x;
		y += speedvector.y;
	}

	@Override
	public Dictionary<String, Object> getAttributes() {
		Dictionary<String, Object> atts = new Dictionary<>();
		try {
			for (Field f : this.getClass().getFields()) {
				if (f.isAccessible()) {
					String name = f.getName();
					Object value = f.get(this);
					atts.add(name, value);
				}
			}
		} catch (Exception ex) {
			IO.printException(ex);
		}
		atts.addAll(super.getAttributes());
		return atts;
	}
}
