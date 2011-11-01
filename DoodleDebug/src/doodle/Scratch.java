package doodle;

import java.util.ArrayList;
import java.util.List;

import rendering.ScratchRendering;

import view.DoodleCanvas;

/**
 * Organized in a tree structure, draws object and its nested objects inside.
 * 
 * @author Cedric Reichenbach
 * 
 */
public class Scratch {

	private DoodleCanvas canvas;

	/**
	 * List of Scratches contained inside this one
	 */
	private List<Scratch> inner;

	/**
	 * List of Scratches contained on the outside of this one
	 */
	private List<Scratch> outer;

	/**
	 * Creates a new Scratch for visualizing objects
	 */
	public Scratch(Object o) {
		this.inner = new ArrayList<Scratch>();
		this.outer = new ArrayList<Scratch>();
		this.canvas = this.drawWhole(o);
	}

	/**
	 * Visualizes any Object on this whole Scratch (not inside other object).
	 * Should only be called the first time (top object).
	 * 
	 * @param o
	 * @return
	 */
	private DoodleCanvas drawWhole(Object o) {
		if (o instanceof Drawable) {
			((Drawable) o).drawOn(this);
		} else {
			this.drawDefault(o, this);
		}
			// TODO: Ask registry for Scratch visualization
		return new ScratchRendering().render(this);
	}

	/**
	 * Visualizes any Object inside the caller, either using draw Method of the
	 * object itself (if existing) or does a default drawing. Should only be
	 * called from another draw Method.
	 * 
	 * @param Object
	 *            o
	 */
	public void draw(Object o) {
		Scratch subScratch = new Scratch(this);
		this.inner.add(subScratch);
		subScratch.drawWhole(o);
	}

	/**
	 * Visualizes any Object outside of the caller, either using draw Method of
	 * the object itself (if existing) or does a default drawing. Should only be
	 * called from another draw Method.
	 * 
	 * @param Object
	 *            o
	 */
	public void drawOuter(Object o) {
		Scratch subScratch = new Scratch(this);
		this.outer.add(subScratch);
		subScratch.drawWhole(o);
	}

	/**
	 * Makes a default drawing for objects that don't implement their own draw
	 * method.
	 * 
	 * @param Object
	 *            o
	 */
	private void drawDefault(Object o, Scratch s) {
		// TODO: get Rendering from RenderingRegistry
	}

	/**
	 * Visualizes any Object with few details, either using drawSmall Method of
	 * the object itself (if existing) or does a default drawing.
	 * 
	 * @param Object
	 *            o
	 */
	public void drawSmall(Object o) {
		Scratch subScratch = new Scratch(this);
		this.inner.add(subScratch);
		if (o instanceof Drawable) {
			((Drawable) o).drawSmallOn(subScratch);
		} else {
			this.drawSmallDefault(o, subScratch);
		}
	}

	/**
	 * Makes a default drawing with few details for objects that don't implement
	 * their own drawSmall method.
	 * 
	 * @param Object
	 *            o
	 */
	private void drawSmallDefault(Object o, Scratch s) {
		// TODO: get Rendering from RenderingRegistry
	}

	public List<Scratch> getInner() {
		return this.inner;
	}

	public List<Scratch> getOuter() {
		return outer;
	}

	public DoodleCanvas getCanvas() {
		return this.canvas;
	}

}
