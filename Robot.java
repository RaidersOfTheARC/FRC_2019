/*----------------------------------------------------------------------------*/
/* 2019 TEAR-A-BYTE #6905                                                     */
/* Code by: Jackson Isenberg, Sahan Reddy, Shreya Das                         */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.concurrent.TimeUnit;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.drive.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
// If you rename or move this class, update the build.properties file in the project root
public class Robot extends IterativeRobot {
	
	private static final String DEFAULT_AUTO = "Default";
	private static final String CUSTOM_AUTO = "Sandstorm";
	private static final long AUTO_DURATION = 7000;
	private SendableChooser<String> chooser = new SendableChooser<>();
	private Joystick driveStick;
	private TalonSRX liftFL, liftFR, liftBL, liftBR;
	private WPI_VictorSPX liftWheelR, liftWheelL, hatchMotor;
	private WPI_VictorSPX driveFL, driveFR, driveBL, driveBR;
	private MecanumDrive drive;
	private Compressor cpress;
	private Solenoid solIn, solOut;
	private Lift lift;
	private XboxController toolOp;
	
	// private Compressor cpress;
	// private Solenoid solIn, solOut;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// joystick for the diifferential tank drive
		driveStick = new Joystick(0);
		
		// xbox controller for the different mechanisms of the robot
		toolOp = new XboxController(1);

		// solIn -> the solenoid valve that, when active, pulls in the actuator
		// solOut -> the solenoid valve that, when active, pushes out the actuator
		
		solIn = new Solenoid(1, 1);
		solOut = new Solenoid(1, 2);

		// activate the compressor
		cpress = new Compressor(1);
		cpress.setClosedLoopControl(true);
		
		// set up the defaults for all motors
		/*
		Intake.setIntake(false, false);
		Outtake.initOuttake(false);
		Channel.setChannel(false, false);
		*/
		
		driveFL = new WPI_VictorSPX(RobotMap.DRIVETRAIN_LEFT_FRONT_VICTOR);
		driveFR = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_FRONT_VICTOR);
		driveBL = new WPI_VictorSPX(RobotMap.DRIVETRAIN_LEFT_BACK_VICTOR);
		driveBR = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_BACK_VICTOR);
		
		drive = new MecanumDrive(driveFL, driveBL, driveFR, driveFL);
		lift = new Lift();

		liftWheelL = new WPI_VictorSPX(RobotMap.LIFT_WHEEL_LEFT);
		liftWheelR = new WPI_VictorSPX(RobotMap.LIFT_WHEEL_RIGHT);
		
		liftFL = new TalonSRX(RobotMap.LIFT_FRONT_LEFT);
    		liftFR = new TalonSRX(RobotMap.LIFT_FRONT_RIGHT);
   	 	liftBL = new TalonSRX(RobotMap.LIFT_BACK_LEFT);
		liftBR = new TalonSRX(RobotMap.LIFT_BACK_RIGHT);
		
		hatchMotor = new WPI_VictorSPX(RobotMap.HATCH_MOTOR);

		// send auto chooser to the radio/management
		chooser.addDefault("Do Nothing", DEFAULT_AUTO);
		chooser.addObject("Run Auto", CUSTOM_AUTO);
		SmartDashboard.putData("Auto choices", chooser);
		
		// set up the camera
		CameraServer.getInstance().startAutomaticCapture();
	}
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 * <p>
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		// if chooser -> default
		if (chooser.equals(DEFAULT_AUTO)) {
			Timer.delay(AUTO_DURATION);
		}
		// if chooser -> custom
		else if (chooser.equals(CUSTOM_AUTO)) {
			autonomousPeriodic();
		}
	}
	
	/**
	 * This is the manual sandstorm code
	 * In the future, this can be overwritten by an autonomous
	 * that can do the same function as the driver but more efficiently
	 * The goal is that, using the camera, the driver can navigate to the
	 * lower part of the rocket and place a hatch panel
	 */
	@Override
	public void autonomousPeriodic() {

		/**
		 * Autonomous Sandstorm
		 * -> drives off habitat SAFELY
		 * -> turns and drives to left left rocket
		 * -> places hatch panel on rocket and backs away
		 */
		Sandstorm auto = new Sandstorm(liftFL, liftFR, liftBL, liftBR, liftWheelL, liftWheelR, hatchMotor);
		
	}
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		// set up the driver joystick for differential
		drive.driveCartesian(driveStick.getY(), driveStick.getX(), driveStick.getZ());

		// loop through the toolOp and check for the right stick
		// -> commnands for the Nidec
		lift.setLift(toolOp);
		
		liftWheelL.set(toolOp.getY(GenericHID.Hand.kLeft));
		liftWheelR.set(toolOp.getY(GenericHID.Hand.kLeft) * -1);
		
		liftFL.set(ControlMode.PercentOutput, toolOp.getTriggerAxis(GenericHID.Hand.kLeft));
		liftFR.set(ControlMode.PercentOutput, toolOp.getTriggerAxis(GenericHID.Hand.kLeft));
		liftBL.set(ControlMode.PercentOutput, toolOp.getTriggerAxis(GenericHID.Hand.kRight));
		liftBR.set(ControlMode.PercentOutput, toolOp.getTriggerAxis(GenericHID.Hand.kRight));
		
		/*
		// call toolOp commands for various motors
    	Intake.setIntake(
			toolOp.getBumperPressed(GenericHID.Hand.kRight),
			toolOp.getBumperPressed(GenericHID.Hand.kLeft)
		);
    	Channel.setChannel(
			toolOp.getBumperPressed(GenericHID.Hand.kRight),
			toolOp.getBumperPressed(GenericHID.Hand.kLeft)
		);
		Outtake.initOuttake(toolOp.getY(GenericHID.Hand.kLeft));
		*/
		
		// Push pistons
		
		if (toolOp.getAButtonPressed()) {
			solIn.set(false);
			solOut.set(true);
		} else {
			solIn.set(true);
			solOut.set(false);
		}
		
	}
}
