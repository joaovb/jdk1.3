/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * An improved "Fetch a URL" ImageArea class.
 * This class extends the basic ImageArea Class to fetch a URL when
 * the user clicks in the area.  In addition, special custom highlights
 * are used to make the area look and feel like a 3-D button.
 *
 * @author 	Jim Graham
 * @version 	1.9, 02/06/02
 */
class HrefButtonArea extends ImageMapArea {
    /** The URL to be fetched when the user clicks on this area. */
    URL anchor;
    /** The highlight image for when the button is "UP". */
    Image upImage;
    /** The highlight image for when the button is "DOWN". */
    Image downImage;
    /** This flag indicates if the "button" is currently pressed. */
    boolean pressed = false;
    /** The border size for the 3-D effect. */
    int border = 5;

    /**
     * The argument string is the URL to be fetched.
     * This method also constructs the various highlight images needed
     * to achieve the 3-D effect.
     */
    public void handleArg(String arg) {
	try {
	    anchor = new URL(parent.getDocumentBase(), arg);
	} catch (MalformedURLException e) {
	    anchor = null;
	}
	if (border * 2 > W || border * 2 > H) {
	    border = Math.min(W, H) / 2;
	}
    }

    public void makeImages() {
	upImage = parent.getHighlight(X, Y, W, H,
				      new ButtonFilter(false,
						       parent.hlpercent,
						       border, W, H));
	downImage = parent.getHighlight(X, Y, W, H,
					new ButtonFilter(true,
							 parent.hlpercent,
							 border, W, H));
    }

    public boolean imageUpdate(Image img, int infoflags,
			       int x, int y, int width, int height) {
	if (img == (pressed ? downImage : upImage)) {
	    return parent.imageUpdate(img, infoflags, x + X, y + Y,
				      width, height);
	} else {
	    return (img == downImage || img == upImage);
	}
    }

    /**
     * The isTerminal method indicates whether events should propagate
     * to the areas underlying this one.
     */
    public boolean isTerminal() {
	return true;
    }

    /**
     * The status message area is updated to show the destination URL.
     * The graphical highlight is achieved using the ButtonFilter.
     */
    public void highlight(Graphics g) {
	if (entered) {
	    g.drawImage(pressed ? downImage : upImage, X, Y, this);
	}
    }

    public void enter() {
	showStatus((anchor != null)
		   ? "Go To " + anchor.toExternalForm()
		   : null);
	repaint();
    }

    public void exit() {
	showStatus(null);
	repaint();
    }

    /**
     * Since the highlight changes when the button is pressed, we need
     * to record the "pressed" state and induce a repaint.
     */
    public boolean press() {
	pressed = true;
	repaint();
	return true;
    }

    /**
     * The new URL is fetched when the user releases the mouse button
     * only if they are still in the area.
     */
    public boolean lift(int x, int y) {
	pressed = false;
	repaint();
	if (inside(x, y) && anchor != null) {
	    showDocument(anchor);
	}
	return true;
    }
}

