package edu.berlin.htw.cg.Exercise5.interfaces;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GLEventListener;

public interface CGView3D extends GLEventListener, MouseListener, KeyListener,
		MouseMotionListener {
	/*
	 * Implement a view with a WSAD/Mouse interaction for flying around.
	 * Implementation details:
	 * not all listener functions will be used:
	 * mouseMoved(), keyPressed(), keyReleased are likely candidates for usage!
	 * GLEventListener will be used somewhat like in the sunsystem example
	 * 
	 * Implement yaw, pitch, roll camera with roll==0 und UP = 0,1,0
	 * Clamp pitch to something like a little bit LESS than -PI/2 to PI/2 for not
	 * getting into trouble with the UP Vector!!
	 * Computation of the first yaw, pitch from camera direction: use atan2, if possible
	 * 
	 * View3D
	 *    clears screen
	 *    sets camera changes from input 
	 *    calls GCScene->update() und CGScene->draw()
	 *    looks for ESC-Key to exit scene
	 */

}
