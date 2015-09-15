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

public class DrawableShapeDecorations implements Decorations {

	/**
	 * A static STANDARDPROPERTIES constant is defined that can be used by the
	 * constructor to initialize the normal look and feel of the Drawable items.
	 */
	public static final DrawableShapeDecorations STANDARD_DECORATIONS =
			new DrawableShapeDecorations.Builder().buildImmutable();

	private Color color;
	private Stroke stroke;
	private boolean filled;

	/**
	 * This private constructor is used by the static {@link Builder} class and
	 * it's {@link Builder#build()} function to construct the
	 * {@link DrawableShapeDecorations} object.
	 * 
	 * @param builder
	 */
	private DrawableShapeDecorations(Builder builder) {
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
	 * Set the color of this {@link Decorations} object.
	 * 
	 * @param color
	 * @throws UnsupportedOperationException
	 */
	public void setColor(Color color) {

		System.out.println("UnsupportedOperationException");
		throw new UnsupportedOperationException(
				"You try to modify an immutable DrawableShapeDecorations"
						+ "constant");
	}

	/**
	 * Set the stroke of this {@link Decorations} object.
	 * 
	 * @param stroke
	 */
	public void setStroke(Stroke stroke) {

		System.out.println("UnsupportedOperationException");
		throw new UnsupportedOperationException(
				"You try to modify an immutable DrawableShapeDecorations"
						+ "constant");
	}

	/**
	 * Set the filling flag of this {@link Decorations} object.
	 * 
	 * @param filled
	 */
	public void setFilled(boolean filled) {

		System.out.println("UnsupportedOperationException");
		throw new UnsupportedOperationException(
				"You try to modify an immutable DrawableShapeDecorations"
						+ "constant");
	}

	/**
	 * 
	 * The static inner Builder class is used to construct an
	 * {@link DrawableShapeDecorations} object.
	 * 
	 * @author phonon
	 * 
	 */
	public static class Builder {

		private Color color = Color.WHITE;
		private Stroke stroke = new BasicStroke(2.0f);
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
		 * Build a {@link DrawableShapeDecorations} object and give it back
		 * 
		 * @return a {@link DrawableShapeDecorations} object with the chosen
		 *         properties.
		 */
		public DrawableShapeDecorations build() {
			return new DrawableShapeDecorations(this) {
				/**
				 * Set the color of this {@link Decorations} object.
				 * 
				 * @param c
				 */
				@Override
				public void setColor(Color c) {
					color = c;
				}

				/**
				 * Set the stroke of this {@link Decorations} object.
				 * 
				 * @param s
				 */
				@Override
				public void setStroke(Stroke s) {
					stroke = s;
				}

				/**
				 * Set the filling flag of this {@link Decorations} object.
				 * 
				 * @param f
				 */
				@Override
				public void setFilled(boolean f) {
					filled = f;
				}

			};
		}

		/**
		 * @return Build a {@link DrawableShapeDecorations} object and give it
		 *         back as immutable. The object will throw a an
		 *         {@link UnsupportedOperationException} in case someone uses a
		 *         setter method.
		 */
		public DrawableShapeDecorations buildImmutable() {
			return new DrawableShapeDecorations(this);
		}

	}

}
