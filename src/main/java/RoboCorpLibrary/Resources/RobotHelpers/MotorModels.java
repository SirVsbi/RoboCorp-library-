package RoboCorpLibrary.Resources.RobotHelpers;

public enum MotorModels{





    NEVEREST40, TETRIX;








    public static double DEFAULT_CPR = 2240;
    public static double CPR(MotorModels motormodel){
        switch (motormodel){
            case NEVEREST40:
                return 1120;
            case TETRIX:
                return 1440;
        }
        return DEFAULT_CPR;
    }


    public double CPR(){
        return CPR(this);
    }





    public static int DEFAULT_RPM = 150;
    public static int RPM(MotorModels motormodel){
        switch (motormodel){
            case NEVEREST40:
                return 160;
            case TETRIX:
                return 150;
        }
        return DEFAULT_RPM;
    }


    public int RPM(){
        return RPM(this);
    }





}