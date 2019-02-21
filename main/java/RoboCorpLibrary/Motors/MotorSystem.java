package RoboCorpLibrary.Motors;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;
import java.util.List;

import RoboCorpLibrary.Motors.Motor;
import RoboCorpLibrary.Resources.RobotHardware;
import RoboCorpLibrary.Resources.RobotHelpers.MotorModels;

public class MotorSystem implements RobotHardware{

    private String systemName;
    public Motor motor1, motor2;

    private int numMotors;


    private List<Motor> motors;



    //String name, HardwareMap hwMap, DcMotor.Direction direction, MotorModels motormodel)
    public MotorSystem(String name1, String name2, HardwareMap hwMap,
                            DcMotor.Direction direction, MotorModels motormodel, String _systemName){


        this.systemName = _systemName;

        motor1 = new Motor(name1, hwMap, direction, motormodel);
        motor2 = new Motor(name2, hwMap, direction, motormodel);


        motors = Arrays.asList(motor1, motor2);

        numMotors = 2;
    }



    public void setBreakMode(){
        for(Motor robotMotor : motors)
            robotMotor.setBreakMode();
    }


    public MotorSystem runUsingEncoders(){
        for(Motor motor : motors)
            motor.runUsingEncoder();
        return this;

    }

    public  MotorSystem resetEncoders(){
        for(Motor robotMotor : motors)
            robotMotor.resetEncoder();
        return this;
    }


    public void setClosedLoop(boolean closedLoop){
        for(Motor robotMotor : motors){
            robotMotor.setClosedLoop(closedLoop);
        }



    }


    public void setAcceleretation(double target){
        for(Motor robotMotor : motors){
            robotMotor.setAcceleration(target);
        }
    }

    public void setVelocity(double power){
        for(Motor robotMotor : motors){
            robotMotor.setVelocity(power);
        }
    }


    public void setPower(double power){
        for(Motor robotMotor : motors){
            robotMotor.setPower(power);
        }
    }





    public double getPower(){
        double sum = 0;
        for(Motor motor : motors){
            sum += Math.abs(motor.getPower());
        }
        return sum / numMotors;
    }

    public double getVelocity(){
        double rate = 0;
        for(Motor motor : motors){
            rate += Math.abs(motor.getVelocity());
        }
        return rate / numMotors;
    }

    public double getAcceleration(){
        double rate = 0;
        for(Motor motor : motors){
            rate += Math.abs(motor.getAcceleration());
        }
        return rate / numMotors;
    }



    public String getName(){
        return systemName;
    }


    public String[] getTelemeter(){
        return new String[]{
                "name " + systemName,
                "Velocity: " + Double.toString(getVelocity()),
                "Acceleration: " + Double.toString(getAcceleration())
        };
    }


}