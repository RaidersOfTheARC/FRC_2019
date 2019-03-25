/*----------------------------------------------------------------------------*/
/* 2019 TEAR-A-BYTE #6905                                                     */
/* Code by: Jackson Isenberg, Sahan Reddy, Shreya Das                         */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveTrain {
    // Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	private static SpeedControllerGroup groupLeft, groupRight;
	private static WPI_VictorSPX fRight, fLeft, rRight, rLeft;	
	private static DifferentialDrive drive;
	
    public DriveTrain() {
		// constructor method for the drive train
		
		fLeft = new WPI_VictorSPX(RobotMap.DRIVETRAIN_LEFT_FRONT_VICTOR);
		rLeft = new WPI_VictorSPX(RobotMap.DRIVETRAIN_LEFT_BACK_VICTOR);
		groupLeft = new SpeedControllerGroup(fLeft, rLeft);

		fRight = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_FRONT_VICTOR);
		rRight = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_BACK_VICTOR);
		groupRight = new SpeedControllerGroup(fRight, rRight);

		drive = new DifferentialDrive(groupLeft, groupRight);

    }
	
	public void strafeDrive(double x, double y) {

		// left x and up y -> up left diagonal
		if (x < 0 && y > 0) {
			fLeft.set(y);
			rRight.set(y);
		// left x and down y -> down left diagonal
		} else if (x < 0 && y < 0) {
			fRight.set(y);
			rLeft.set(y);
		// right x and up y -> up right diagonal
		} else if (x > 0 && y > 0) {
			fLeft.set(y);
			rRight.set(y);
		// right x and down y -> down right diagonal
		} else if (x > 0 && y < 0) {
			fRight.set(y);
			rLeft.set(y);
		// normal strafing
		} else {
			fRight.set(x);
			rLeft.set(x);
			fLeft.set(-x);
			rRight.set(-x);
		}
		
	}

	public DifferentialDrive getDrive() {
		return drive;
	}
    
}
