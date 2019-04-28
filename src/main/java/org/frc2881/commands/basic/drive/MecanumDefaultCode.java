import org.frc2881.subsystems.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;

/*
 * Simplest program to drive a robot with mecanum drive using a single Logitech
 * Extreme 3D Pro joystick and 4 drive motors connected as follows:
 *     - PWM 0 - Connected to front left drive motor
 *     - PWM 1 - Connected to rear left drive motor
 *     - PWM 2 - Connected to front right drive motor
 *     - PWM 3 - Connected to rear right drive motor
 */

public class MecanumDefaultCode extends TimedRobot {
     //Create a robot drive object using PWMs 0, 1, 2 and 3
          Spark m_frontLeft = new Spark(1);	
		Spark m_rearLeft = new Spark(2);
		Spark m_frontRight = new Spark(3);	
		Spark m_rearRight = new Spark(4);
     //Define joystick being used at USB port 1 on the Driver Station
     Joystick m_driveStick = new Joystick(1);

     public void teleopPeriodic() 
		{
          Drive.mecanumDrive_Cartesian(m_driveStick.getX(), m_driveStick.getY(), m_driveStick.getZ());
     }
}
