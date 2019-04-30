/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.awt.Component;


/**
 * Demos that animate and have custom controls extend this class.
 */
public abstract class AnimatingControlsSurface extends AnimatingSurface implements CustomControlsContext {


    public void setControls(Component[] controls) {
        this.controls = controls;
    }
  
    public void setConstraints(String[] constraints) {
        this.constraints = constraints;
    }
    
    public String[] getConstraints() {
        return constraints;
    }

    public Component[] getControls() { 
        return controls;
    }

    public void handleThread(int state) {
        for (int i = 0; i < controls.length; i++) {
            if (state == CustomControlsContext.START) {
                if (controls[i] instanceof CustomControls) {
                    ((CustomControls) controls[i]).start();
                }
            } else if (state == CustomControlsContext.STOP) {
                if (controls[i] instanceof CustomControls) {
                    ((CustomControls) controls[i]).stop();
                }
            }
        }
    }


    private Component[] controls;
    private String[] constraints = { java.awt.BorderLayout.NORTH };
}
