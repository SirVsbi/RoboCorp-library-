package RoboCorpLibrary.Wrappers;

import com.qualcomm.robotcore.hardware.Gamepad;

import RoboCorpLibrary.Resources.RobotUtils;


/**
 * Created by Szab on 03/2/2019. (RoboCorp RO084)
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


/**
 *  This is a wrapper for the Controller code, provides
 *      1.) remap-ability
 *      2.) multithreading support
 *
 *
 *
 */



public class RobotController extends Gamepad implements Runnable{

    private String name;
    private Gamepad gamepad;
    private boolean close = false;
    private boolean
            aPrev = false, bPrev = false, xPrev = false, yPrev = false,
            leftBumperPrev = false, rightBumperPrev = false, rightTriggerPrev = false, leftTriggerPrev = false;

    public RobotController(Gamepad g, String name){
        this.name = name;
        this.gamepad = g;
    }



    public boolean a() {return gamepad.a;}
    public boolean x() {return gamepad.x;}
    public boolean y() {return gamepad.y;}
    public boolean b() {return gamepad.b;}

    public boolean aOnPress() {return a() && !aPrev;}
    public boolean bOnPress() {return b() && !bPrev;}
    public boolean yOnPress() {return y() && !yPrev;}
    public boolean xOnPress() {return x() && !xPrev;}

    public float leftStickX(){return gamepad.left_stick_x;}
    public float leftStickY() {
        return gamepad.left_stick_y;
    }
    public float rightStickX() {
        return gamepad.right_stick_x;
    }
    public float rightStickY() {
        return gamepad.right_stick_y;
    }

    public boolean dPadUp() {return gamepad.dpad_up;}
    public boolean dPadDown() {
        return gamepad.dpad_down;
    }
    public boolean dPadLeft() {
        return gamepad.dpad_left;
    }
    public boolean dPadRight() {
        return gamepad.dpad_right;
    }

    public boolean leftBumper() {
        return gamepad.left_bumper;
    }
    public boolean rightBumper() {
        return gamepad.right_bumper;
    }

    public boolean leftBumperOnPress () {
        return leftBumper() && !leftBumperPrev;
    }
    public boolean rightBumperOnPress () {return rightBumper() && !rightBumperPrev;}

    public boolean leftStickButton() {
        return gamepad.left_stick_button;
    }
    public boolean rightStickButton() {
        return gamepad.right_stick_button;
    }

    public boolean leftTriggerPressed() {
        return leftTrigger() > 0;
    }
    public boolean rightTriggerPressed() {
        return rightTrigger() > 0;
    }
    public boolean leftTriggerOnPress() {
        return leftTriggerPressed() && !leftTriggerPrev;
    }
    public boolean rightTriggerOnPress() {return rightTriggerPressed() && !rightTriggerPrev;}

    public double getLeftStickAngle () {
        return Math.toDegrees(Math.atan2(leftStickY(), -leftStickX()));
    }
    public double getRightStickAngle () {
        return Math.toDegrees(Math.atan2(rightStickY(), -rightStickX()));
    }

    public double getTan (double x, double y) {
        double tan = -y/x;
        if (tan == Double.NEGATIVE_INFINITY || tan == Double.POSITIVE_INFINITY || tan != tan) tan = 0;
        return tan;
    }

    public float leftTrigger() {return gamepad.left_trigger;}
    public float rightTrigger() {return gamepad.right_trigger;}

    public boolean start() {return gamepad.start;}

    public synchronized void update(){
        aPrev = gamepad.a;
        bPrev = gamepad.b;
        xPrev = gamepad.x;
        yPrev = gamepad.y;
        leftBumperPrev = gamepad.left_bumper;
        rightBumperPrev = gamepad.right_bumper;
        rightTriggerPrev = rightTriggerPressed();
        leftTriggerPrev = leftTriggerPressed();
    }






    public String getName(){
        return name;
    }

    public String[] getTelemeter(){
        return new String[]{
                name,
                "A: " + Boolean.toString(a()),
                "aPress: " + Boolean.toString(aOnPress()),
                "B: " + Boolean.toString(b()),
                "bPress: " + Boolean.toString(bOnPress()),
                "X: " + Boolean.toString(x()),
                "xPress: " + Boolean.toString(xOnPress()),
                "Y: " + Boolean.toString(y()),
                "yPress: " + Boolean.toString(yOnPress()),
                "RB: " + Boolean.toString(rightBumper()),
                "rightBumperPressed: " + Boolean.toString(rightBumperOnPress()),
                "LB: " + Boolean.toString(leftBumper()),
                "leftBumperPressed: " + Boolean.toString(leftBumperOnPress()),
                "leftTrigger: " + Float.toString(leftTrigger()),
                "leftTriggerPress: " + Boolean.toString(leftTriggerPressed()),
                "rightTrigger: " + Float.toString(rightTrigger()),
                "rightTriggerPress: " + Boolean.toString(rightTriggerPressed()),
                "rightStickY: " + Double.toString(rightStickX()),
                "rightStickX: " + Double.toString(rightStickX()),
                "leftStickY: " + Double.toString(leftStickY()),
                "leftStickX: " + Double.toString(leftStickX()),
                "D_UP: " + Boolean.toString(dPadUp()),
                "D_DOWN: " + Boolean.toString(dPadDown()),
                "D_LEFT: " + Boolean.toString(dPadLeft()),
                "D_RIGHT: " + Boolean.toString(dPadRight()),
        };
    }



    @Override
    public void run(){
        boolean close = false;
        while(!close){
            update();
            close = this.close;
            RobotUtils.sleep(100);
        }
    }

    public void close() {
        close = true;
    }

    public void startUpdate(){
        new Thread(this).start();
    }

}
