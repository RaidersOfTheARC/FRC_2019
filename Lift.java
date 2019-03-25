/*----------------------------------------------------------------------------*/
/* 2019 TEAR-A-BYTE #6905                                                     */
/* Code by: Jackson Isenberg, Sahan Reddy, Shreya Das                         */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.NidecBrushless;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	static NidecBrushless liftR, liftL;
	

    public Lift() {
		// constructor method for the lift
		
		liftR = new NidecBrushless(0, 0);
		liftL = new NidecBrushless(1, 1);
	
    }

	
    public void setLift(XboxController cont) {
		
    	if (cont.getXButton()) {
    		// lift to first position
    	}
    	else if (cont.getYButton()) {
    		// lift to second position
    	}
    	else if (cont.getBButton()) {
    		// reset lift to start position
    	}
	    
	    // right joystick up -> lift up
	    liftR.set(cont.getY(GenericHID.Hand.kRight));
	    // right joystick down -> lift down
		liftL.set(cont.getY(GenericHID.Hand.kRight) * -1);
		
	}
	
}
