package com.example.sano;

public class Model {

    private String Content;
    private String CreatedDate;

    public Model(){}

    public Model(String Content, String CreatedDate){
        this.Content = Content;
        this.CreatedDate = CreatedDate;
    }

    public String getContent(){
        return Content;
    }

    public void setContent(String Content){ this.Content = Content; }

    public String getCreatedDate(){
        return CreatedDate;
    }

    public void setCreatedDate(String CreatedDate){
        this.CreatedDate = CreatedDate;
    }

}
