package ututor.edu.csulb.ututor;


import java.io.Serializable;

public class WorkHour implements Serializable{
    public boolean MONDAY;
    public boolean TUESDAY;
    public boolean WEDNESDAY;
    public boolean THURSDAY;
    public boolean FRIDAY;
    public boolean SATURDAY;
    public boolean SUNDAY;

    String startTime;
    String endTime;

    public WorkHour(){
        startTime = "";
        endTime = "";

        MONDAY = false;
        TUESDAY = false;
        WEDNESDAY = false;
        THURSDAY = false;
        FRIDAY = false;
        SATURDAY = false;
        SUNDAY = false;
    }

    public void setStartTime(String s){ startTime = s;}

    public void setEndTime(String s){ endTime = s;}

    public boolean getMONDAY(){ return MONDAY;}

    public boolean getTUESDAY(){ return TUESDAY;}

    public boolean getWEDNESDAY(){ return WEDNESDAY;}

    public boolean getTHURSDAY(){ return THURSDAY;}

    public boolean getFRIDAY(){ return FRIDAY;}

    public boolean getSATURDAY(){ return SATURDAY;}

    public boolean getSUNDAY(){ return SUNDAY;}

    public void setMONDAY(boolean b){
        MONDAY =b;
    }

    public void setTUESDAY(boolean b){TUESDAY =b;}

    public void setWEDNESDAY(boolean b){
        WEDNESDAY =b;
    }

    public void setTHURSDAY(boolean b){
        THURSDAY =b;
    }

    public void setFRIDAY(boolean b){
        FRIDAY =b;
    }

    public void setSATURDAY(boolean b){
        SATURDAY =b;
    }

    public void setSUNDAY(boolean b){
        SUNDAY =b;
    }

    @Override
    public String toString() {
        StringBuilder fullString = new StringBuilder("Day(s): ");
        if(SUNDAY == true){
            fullString.append("Su");
        }
        if(MONDAY == true){
            fullString.append("Mo");
        }
        if(TUESDAY == true){
            fullString.append("Tu");
        }
        if(WEDNESDAY == true){
            fullString.append("We");
        }
        if(THURSDAY == true){
            fullString.append("Th");
        }
        if(FRIDAY == true){
            fullString.append("Fr");
        }
        if(SATURDAY == true){
            fullString.append("Sa");
        }

        fullString.append("     Time: ");

        fullString.append(startTime + " - ");
        fullString.append(endTime);

        return fullString.toString();
    }



}
