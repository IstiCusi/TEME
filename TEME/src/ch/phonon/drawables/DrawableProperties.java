/*************************************************************************
 * 
 *  WWW.PHONON.CH CONFIDENTIAL 
 *
 *  2012 - 2020, Stephan Strauss, www.phonon.ch, Zurich, Switzerland
 *  All Rights Reserved.
 * 
 *************************************************************************/

package ch.phonon.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

/**
 * 
 * This class is used by {@link Drawable}s to directly define several properties
 * like color, line thickness. This class is a following the builder design
 * pattern to avoid heavy constructors (in allowing operator chaining). For all
 * properties a default value is defined.
 * 
 * @author phonon
 * 
 */

public class DrawableProperties {

	private final Color color;
	private final Stroke stroke;
	private final boolean filled;

	/**
	 * This private constructor is used by the static {@link Builder} class and
	 * it's {@link Builder#build()} function to construct the
	 * {@link DrawableProperties} object.
	 * 
	 * @param builder
	 */
	private DrawableProperties(Builder builder) {
		this.color = builder.color;
		this.stroke = builder.stroke;
		this.filled = builder.filled;
	}

	/**
	 * @return color for the {@link Drawable}
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return stroke type of the {@link Drawable}
	 */
	public Stroke getStroke() {
		return stroke;
	}

	/**
	 * @return filling type of the {@link Drawable}
	 */
	public boolean isFilled() {
		return filled;
	}

	/**
	 * 
	 * The static inner Builder class is used to construct an
	 * {@link DrawableProperties} object.
	 * 
	 * @author phonon
	 * 
	 */
	public static class Builder {

		private Color color = Color.WHITE;
		private Stroke stroke = new BasicStroke();
		private boolean filled = false;

		/**
		 * @param color
		 * @return builder object updated about the color property
		 */
		public Builder color(Color color) {
			this.color = color;
			return this;
		}

		/**
		 * @param stroke
		 * @return builder object updated about the stroke property
		 */
		public Builder stroke(Stroke stroke) {
			this.stroke = stroke;
			return this;
		}

		/**
		 * @param filled
		 * @return builder object updated about the filling property
		 */
		public Builder filling(boolean filled) {
			this.filled = filled;
			return this;
		}

		/**
		 * Build a {@link DrawableProperties} object and give it back
		 * 
		 * @return a {@link DrawableProperties} object with the chosen
		 *         properties.
		 */
		public DrawableProperties build() {
			return new DrawableProperties(this);
		}

	}

}
