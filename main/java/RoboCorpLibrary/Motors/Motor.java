package RoboCorpLibrary.Motors;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import RoboCorpLibrary.Resources.RobotHardware;
import RoboCorpLibrary.Resources.RobotHelpers.Direction;
import RoboCorpLibrary.Resources.RobotHelpers.MotorModels;
import RoboCorpLibrary.Resources.RobotUtils;
import RoboCorpLibrary.Sensors.RobotClock;
import RoboCorpLibrary.Sensors.RobotEncoder;

import static java.lang.Math.abs;


/**
 * Created by Szab on 31/1/2019. (RoboCorp RO084)
 *
 * Copyright (c) 2018 RoboCorp
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */



//Custom motor class containing functions to set desired velocity


public class Motor implements RobotHardware{


    private MotorModels motorModel;
    private DcMotor motor;



    private int direction = 1;

    private RobotEncoder encoder;



    private String MotorName;
    private MotorModels motormodel;

    private double targetPower;
    private double motorPower;


    private double prevPos = 0;
    private double prevTime = 0;
    private double prevRate = 0;


    private double prevVelocity = 0;
    private double prevVelocityTime = 0;

    private double rpmIntegral = 0;
    private double rpmDerivate = 0;
    private double rpmPreviousError = 0;


    private double prevAcceleration = 0;
    private double prevAcceletartionSetTime = 0;



    private double destination = 0;



    private double kp = 0.1, ki = 0, kd = 0;


    private boolean stateControl;
    private boolean velocityControlState = false;


    private boolean closedLoop = false;


    //Constructor
    public Motor(String name, HardwareMap hwMap, DcMotor.Direction direction, MotorModels motormodel){
        motor = hwMap.dcMotor.get(name);
        if(direction == DcMotor.Direction.REVERSE)
            this.direction = 1;
        motor.setDirection(direction);
        this.MotorName = name;
        this.motorModel = motormodel;
        encoder = new RobotEncoder(this, motorModel);

    }


    public void resetEncoder(){
        encoder.resetEncoder();
    }



    public void runUsingEncoder(){
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setBreakMode(){
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    public double getCurrentPosition(){
        return encoder.getRelativePosition();
    }

    public double getAbsolutePosition(){
        return motor.getCurrentPosition();
    }


    public void setPower(double power){
        motor.setPower(power);
    }

    public double getPower(){
        return motor.getPower();
    }

    //-------Velocity and acceleration



//TODO: implement pid and integrator

    //TODO 2.0: implement velocity and acceleration control



    //velocity

    public double getVelocity(){

        double deltaPosition = getCurrentPosition() - prevPos;
        double tChange = System.nanoTime() - prevTime;
        prevTime = System.nanoTime();

        tChange /= 1e9;
        prevPos = getCurrentPosition();

        double rate = deltaPosition / tChange;
        rate = (rate * 60) / encoder.getClicksPerRotation();
        if(rate != 0)
            return rate;
        else{
            prevRate = rate;
            return prevRate;
        }



    }


    public void setVelocity(double power){
        targetPower = power;
        motorPower = calculateVelocityCorrection();
        if(!closedLoop) motorPower = targetPower;


        motor.setPower(motorPower);
    }


    public double calculateVelocityCorrection(){

        double error, setRPM, currentRPM, tChange;
        tChange = System.nanoTime() - prevTime;
        tChange /= 1e9;
        setRPM = encoder.getRPM() * targetPower;
        currentRPM = getVelocity();
        error = setRPM - currentRPM;
        rpmIntegral += error * tChange;
        rpmDerivate = (error - rpmPreviousError) / tChange;
        double power = (targetPower) + (direction * ((error * kp)
                + (rpmIntegral * ki) + (rpmDerivate * kd)));

        rpmPreviousError = error;
        prevTime = System.nanoTime();
        return power;


    }




    //acceleration


    public double getAcceleration(){
        double deltaVelocity = getVelocity() - prevVelocity;
        double tChange = System.nanoTime() - prevVelocityTime;
        tChange /= 1e9;
        prevVelocity = getVelocity();
        double acceleration = deltaVelocity / tChange;
        prevVelocityTime = System.nanoTime();
        if(acceleration != 0)
            return acceleration;
        else{
            prevAcceleration = acceleration;
            return prevAcceleration;
        }


    }



    public void setAcceleration(double accelerationRPMM){
        double tChange;
        tChange = System.nanoTime() - prevAcceletartionSetTime;
        double currentRPM = getVelocity();
        double newRPM = currentRPM + (accelerationRPMM * tChange);
        setVelocity(calculateAccelerationCorrection(newRPM));
        prevAcceletartionSetTime = System.nanoTime();
    }


    public double calculateAccelerationCorrection(double targetAcceleration){
        double error, currentRPM, motorPower;
        double tChange = System.nanoTime() - prevTime;
        tChange /= 1e9;
        currentRPM = getAcceleration();
        error = targetAcceleration - currentRPM;
        rpmIntegral += error * tChange;
        rpmDerivate = (error - rpmPreviousError) /tChange;
        motorPower = (targetPower) + (direction * ((error * kp) +
                (rpmIntegral * ki) + (rpmDerivate * kd)));
        rpmPreviousError = error;
        return motorPower;

    }











    //--------------------------







    //--------------------Autonomous stuff



    //sets the distance until target in encoder clicks
    public void setDistance(double distance){

        resetEncoder();
        destination = distance * encoder.getClicksPerInch();

    }


    //runs until destination is reached or until opMode is active
    public void runToPosition(Direction direction, double speed){
        RobotClock timeout = new RobotClock();
        resetEncoder();
        double clicksRemaining;
        double power;
        do{
            clicksRemaining = (destination - abs(motor.getCurrentPosition()));
            power = direction.value * speed * ((clicksRemaining /destination) * 1.3);
            power = Range.clip(power, -1.0, 1.0);
            setPower(power);

        }while(opModeIsActive() && abs(clicksRemaining) > 1 &&
                !timeout.elapsedTime(1, RobotClock.Resolution.SECONDS));
    }


//------------------------------







    public boolean opModeIsActive(){
        return RobotUtils.opModeIsActive();
    }


    public void setClosedLoop(boolean _closedLoop){
        this.closedLoop = _closedLoop;
    }















//----------Telemetry


    public String getName(){
        return MotorName;
    }


    public String[] getTelemeter(){
        return new String[]{
                "Current position" + Double.toString(getCurrentPosition()),
                "Velocity" + Double.toString(getVelocity()),
                "Acceleration" + Double.toString(getAcceleration())
        };
    }



}