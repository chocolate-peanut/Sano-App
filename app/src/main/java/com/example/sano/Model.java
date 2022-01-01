package com.example.sano;

public class Model {

    private String diary_content;
    private String diary_date;

    public Model(){}

    public Model(String diary_content, String diary_date){
        this.diary_content = diary_content;
        this.diary_date = diary_date;
    }

    public String getDiary_content(){
        return diary_content;
    }

    public void setDiary_content(String diary_content){
        this.diary_content = diary_content;
    }

    public String getDiary_date(){
        return diary_date;
    }

    public void setDiary_date(String diary_date){
        this.diary_date = diary_date;
    }

}
