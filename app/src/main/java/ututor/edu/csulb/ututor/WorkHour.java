package ututor.edu.csulb.ututor;



public class WorkHour {
    public boolean MONDAY;
    public boolean TUESDAY;
    public boolean WEDNESDAY;
    public boolean THURSDAY;
    public boolean FRIDAY;
    public boolean SATURDAY;
    public boolean SUNDAY;

    String sMonday;
    String sTuesday;
    String sWednesday;
    String sThursday;
    String sFriday;
    String sSaturday;
    String sSunday;

    public WorkHour(){
        sMonday = "";
        sTuesday = "";
        sWednesday = "";
        sThursday = "";
        sFriday = "";
        sSaturday = "";
        sSunday = "";

        MONDAY = false;
        TUESDAY = false;
        WEDNESDAY = false;
        THURSDAY = false;
        FRIDAY = false;
        SATURDAY = false;
        SUNDAY = false;
    }

    public void setMONDAY(boolean b){
        MONDAY =b;
    }



}
