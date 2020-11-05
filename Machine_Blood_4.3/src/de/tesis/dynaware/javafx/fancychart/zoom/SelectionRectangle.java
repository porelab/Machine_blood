/*
 * Copyright (C) 2014 TESIS DYNAware GmbH.
 * All rights reserved. Use is subject to license terms.
 * 
 * This file is licensed under the Eclipse Public License v1.0, which accompanies this
 * distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 */
package de.tesis.dynaware.javafx.fancychart.zoom;

import javafx.scene.shape.Rectangle;

/**
 * Represents an area on the screen that was selected by a mouse drag operation.
 *
 */
public class SelectionRectangle extends Rectangle {


	public SelectionRectangle() {
		setStyle(" -fx-stroke: rgba(135, 206, 250, 0.8);-fx-stroke-type: inside;-fx-fill: rgba(135, 206, 250, 0.2);");
		setVisible(false);
		setManaged(false);
		setMouseTransparent(true);
		
	}
}
