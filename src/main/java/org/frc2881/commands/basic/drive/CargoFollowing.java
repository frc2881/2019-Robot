package org.frc2881.commands.basic.drive;

import org.frc2881.Robot;
import org.frc2881.subsystems.Drive;
import org.frc2881.utils.NTValue;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;

public class CargoFollowing extends Command {

    int tick = 0;
    
    public CargoFollowing(){
        requires(Robot.drive);
    }

    @Override
    protected void initialize() {
        Robot.logInitialize(this);
    }

    @Override
    protected void execute() { 

        int ticktock = --tick ;
        {
            if (ticktock == 0){
                Robot.drive.tankDrive(0,0,0);
            }
            else if(ticktock > 0) //Ok, so right here I kinda need to put if the cargo is on the right side or left side (I just don't know how).
            {
                Robot.drive.tankDrive(0,0,0);
            }
        }
    
        double Y = NTValue.getCargoY();

        if (Y < 0){
            Robot.drive.tankDrive(0,0,0);
            Robot.log("Cannot find Cargo!!!");
        } else if (Y > 60) { // greater than 120 is right if the screen rotated 90* to the left
            Robot.drive.tankDrive(.5, -.5, 0); //numbers messed up/bug
            Robot.log("Cargo is on the Right " + Y );
        } else if (Y < 60) { //less than 120 is left if the screen rotated 90* to the left
            Robot.drive.tankDrive(-.5, .5, 0); //numbers messed up/bug
            Robot.log("Cargo is on the Left " + Y );
        } else if (Y == 60) {
            Robot.drive.tankDrive(0,0,0);
            Robot.log("Cargo is Center");
        }        
       // double rotateToAngleRate = utils.NTValues; use later on for slowing down to the target
    }

    @Override
    protected boolean isFinished() {
        return false;
    }


}