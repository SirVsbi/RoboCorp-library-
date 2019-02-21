package RoboCorpLibrary.RobotServo;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import RoboCorpLibrary.Resources.RobotHardware;
import RoboCorpLibrary.Sensors.RobotClock;

public class RobotServo implements RobotHardware{


    private String name;
    private Servo servo;

    private double min = 0,
                   max = 1;

    RobotClock robotClock = new RobotClock();
    private double targetPosition;

    public RobotServo(String name, HardwareMap hwMap){
        setName(name);
        hwMap.servo.get(name);
    }


    public void setDirection(Servo.Direction direction){
        servo.setDirection(direction);
    }

    public void setPosition(double position){
        targetPosition = position;
        position = ((max - min) * position) + min;
        servo.setPosition(position);
    }

    public double getPositon(){
        return servo.getPosition();
    }

    public boolean isStalled(int time){
        boolean isStalled = false;
        double prevPosition = servo.getPosition();
        if((servo.getPosition() == prevPosition && servo.getPosition() != targetPosition)
                && !robotClock.elapsedTime(time, RobotClock.Resolution.SECONDS))
            isStalled = true;
        return isStalled;
    }



    private void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public String[] getTelemeter() {
        return new String[]{

        };
    }
}