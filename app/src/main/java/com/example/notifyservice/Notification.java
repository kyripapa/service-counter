package com.example.notifyservice;

import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by Σταύρος on 29/1/2017.
 */

public class Notification {
    int id;
    private String time;
    private String packageName;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private String fourthAnswer;
    private String fifthAnswer;
    private String sixthAnswer;

    public Notification(){

    }

    public Notification(int id,String time,String packageName, String firstAnswer, String secondAnswer, String thirdAnswer, String fourthAnswer, String fifthAnswer, String sixthAnswer)
    {
        this.id=id;
        this.time=time;
        this.packageName=packageName;
        this.firstAnswer=firstAnswer;
        this.secondAnswer=secondAnswer;
        this.thirdAnswer=thirdAnswer;
        this.fourthAnswer=fourthAnswer;
        this.fifthAnswer=fifthAnswer;
        this.sixthAnswer=sixthAnswer;
    }
    // constructor
    public Notification(String time, String packageName, String firstAnswer, String secondAnswer, String thirdAnswer, String fourthAnswer, String fifthAnswer, String sixthAnswer){
        this.time=time;
        this.packageName=packageName;
        this.firstAnswer=firstAnswer;
        this.secondAnswer=secondAnswer;
        this.thirdAnswer=thirdAnswer;
        this.fourthAnswer=fourthAnswer;
        this.fifthAnswer=fifthAnswer;
        this.sixthAnswer=sixthAnswer;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }
    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }
    public void setThirdAnswer(String secondAnswer) {
        this.thirdAnswer = thirdAnswer;
    }
    public void setFourthAnswer(String fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }
     public void setFifthAnswer(String fifthAnswer) {
        this.fifthAnswer = fifthAnswer;
    }
      public void setSixthAnswer(String sixthAnswer) {
        this.sixthAnswer = sixthAnswer;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public int getId() {
        return id;
    }
    public String getTime() {
        return time;
    }
    public String getFirstAnswer() {
        return firstAnswer;
    }
 public String getSecondAnswer() {
        return secondAnswer;
    }
public String getThirdAnswer() {
        return thirdAnswer;
    }
    public String getFourthAnswer() {
        return fourthAnswer;
    }
  public String getFifthAnswer() {
        return fifthAnswer;
    }
    public String getSixthAnswer() {
        return sixthAnswer;
    }

    public String getPackageName() {
        return packageName;
    }

}
