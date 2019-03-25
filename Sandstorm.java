/*----------------------------------------------------------------------------*/
/* 2019 TEAR-A-BYTE #6905                                                     */
/* Code by: Jackson Isenberg, Sahan Reddy, Shreya Das                         */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class Sandstorm {

    private TalonSRX liftFL, liftFR, liftBL, liftBR;
    private WPI_VictorSPX liftWheelR, liftWheelL, hatchMotor;

    public Sandstorm(TalonSRX fL, TalonSRX fR, TalonSRX bL, TalonSRX bR,
                     WPI_VictorSPX wR, WPI_VictorSPX wL, WPI_VictorSPX hM) {
        liftFL = fL;
        liftFR = fR;
        liftBL = bL;
        liftBR = bR;

        liftWheelR = wR;
        liftWheelL = wL;

        hatchMotor = hM;
    }

    // takes data from the camera and calculates the distance
    // from the object
    public double calcDistance() {
        return 0;
    }

    // turns the hatch motor a certain angle
    public void turnHatchMotor(double angle) {
        return;
    }

    // turns the robot a certain angle
    public void turnRobot(double angle) {
        return;
    }

}