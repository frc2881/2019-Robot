package org.frc2881.commands.basic.drive;

import org.frc2881.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CargoFollowing extends Command {

    @Override
    protected void initialize() {
        Robot.logInitialize(this);
        int angle = getCargoY();  
        
    }

    private int getCargoY() {
        return 0;
    }

    @Override
    protected void execute() { 
    }

    @Override
    protected boolean isFinished() {
        return false;
    }


}