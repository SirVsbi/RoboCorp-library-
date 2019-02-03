package RoboCorpLibrary.Sensors;

import RoboCorpLibrary.Resources.RobotHardware;


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



public class RobotClock implements RobotHardware{

    private String name = "CLOCK";




    private boolean isPaused = false;
    private long pauseStart,
                 pauseLength,
                 startTime;


    //Constructor
    public RobotClock(){
        this.reset();
    }

    public void reset(){
        isPaused = false;
        pauseStart = 0L;
        pauseLength = 0L;
        startTime = System.nanoTime();
    }

    public long nanoseconds(){
        return System.nanoTime() - startTime - pauseLength;
    }
    public double milliseconds(){
        return nanoseconds() * 1E-6;
    }
    public double seconds(){
        return nanoseconds() * 1E-9;
    }









    public boolean isPaused(){
        return isPaused;
    }

    public String getName(){
        return name;
    }

    public String[] getTelemeter(){
        return new String[]{
                String.format("%.5f %s", seconds(), isPaused() ? "PAUSED" : "")
        };
    }

}