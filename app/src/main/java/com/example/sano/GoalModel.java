package com.example.sano;

public class GoalModel {

    private String Goal;

    public GoalModel(){};

    public GoalModel(String Goal){
        this.Goal = Goal;
    }

    public String getGoal(){return Goal;}

    public void setGoal(String Goal){this.Goal = Goal;}
}
