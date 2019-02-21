package RoboCorpLibrary.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


import RoboCorpLibrary.Motors.MotorSystem;
import RoboCorpLibrary.Resources.RobotHardware;
import RoboCorpLibrary.Resources.RobotHelpers.MotorModels;


/**
 * Created by Szab on 31/1/2019. (RoboCorp RO084)
 * <p>
 * Copyright (c) 2018 RoboCorp
 * * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


public class DriveTrain implements RobotHardware {


    public MotorSystem leftDrive = null,
            rightDrive = null;


    public DriveTrain(HardwareMap hwMap) {
        leftDrive = new MotorSystem("leftBack_drive", "leftFront_drive", hwMap, DcMotor.Direction.REVERSE, MotorModels.NEVEREST40, "LeftDrive" );
        rightDrive = new MotorSystem("rightBack_drive", "rightFront_drive", hwMap, DcMotor.Direction.FORWARD, MotorModels.NEVEREST40, "RightDrive");
    }


    //------encoder and behavior(runMode) setting


    public void zeroPowerBehavior() {
        leftDrive.setBreakMode();
        rightDrive.setBreakMode();
    }

    public void runUsingEncoders() {
        leftDrive.runUsingEncoders();
        rightDrive.runUsingEncoders();
    }


    public void resetEncoders() {
        leftDrive.resetEncoders();
        rightDrive.resetEncoders();
    }


    //----------------------------------


    public void setVelocity(double Power) {

          leftDrive.setVelocity(Power);
          rightDrive.setVelocity(Power);

    }

    public void setVelocity(double leftPower, double rightPower){
        leftDrive.setVelocity(leftPower);
        rightDrive.setVelocity(rightPower);
    }


    public void setPower(double power) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }

    public void setPower(double leftPower, double rightPower) {
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }


    public void setClosedLoop(boolean closedLoop){
        leftDrive.setClosedLoop(closedLoop);
        rightDrive.setClosedLoop(closedLoop);
    }


    public String getName() {
        return "DRIVETRAIN";
    }

    public String[] getTelemeter() {
        return new String[]{
    //TODO: implement telemetry
                //            "left position" + Double.toString(leftDrive.getAbsolutePosition()),
      //          "right position" + Double.toString(rightDrive.getAbsolutePosition())
        };
    }


}