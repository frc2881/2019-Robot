// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.frc2881.commands.scoring.cargo;
import edu.wpi.first.wpilibj.command.Command;
import org.frc2881.Robot;
import org.frc2881.commands.basic.rumble.RumbleNo;
import org.frc2881.utils.AmpMonitor;
import edu.wpi.first.wpilibj.GenericHID;

/**
 * 
 *
 */
public class CargoControlRollers extends Command {

    private AmpMonitor ampMonitor = new AmpMonitor(10, () -> Robot.intake.getCargoRollerCurrent());
    private boolean monitoringAmps;
    private double speedCap;
    private double joystickValue;
    private double programTime;

    public CargoControlRollers() {
        requires(Robot.intake);
}

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.log("Control Rollers has started");
        monitoringAmps = false;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (!ampMonitor.isTriggered() && (programTime == 0 || timeSinceInitialized() > 500 ||
                joystickValue - Robot.oi.manipulator.getY(GenericHID.Hand.kRight) >= 0.25)) {
            Robot.intake.cargoRollers(Robot.oi.manipulator.getY(GenericHID.Hand.kRight));
        }

        else {
            Robot.intake.cargoRollers(speedCap);
        }

        if (!monitoringAmps && timeSinceInitialized() > .2){
            ampMonitor.reset();
            monitoringAmps = true;
        }

        if (monitoringAmps && ampMonitor.isTriggered()) {
            Robot.log("Cargo Roller current limit exceeded");

            speedCap = 0.2;
            joystickValue = Robot.oi.manipulator.getY(GenericHID.Hand.kRight);
            programTime = timeSinceInitialized();

            if (Math.abs(Robot.oi.manipulator.getX(GenericHID.Hand.kRight)) <= 0.15 &&
                    Robot.oi.manipulator.getY(GenericHID.Hand.kRight) > 0) {
                Robot.intake.cargoRollers(-speedCap);
            }

            else if (Math.abs(Robot.oi.manipulator.getX(GenericHID.Hand.kRight)) <= 0.15 &&
                    Robot.oi.manipulator.getY(GenericHID.Hand.kRight) < 0 ){
                Robot.intake.cargoRollers(speedCap);
            }

            new RumbleNo(Robot.oi.manipulator).start();
        }
}

  

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.log("Cargo Control Rollers has ended");
    }
}
