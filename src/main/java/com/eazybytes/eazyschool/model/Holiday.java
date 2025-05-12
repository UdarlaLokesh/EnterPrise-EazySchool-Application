package com.eazybytes.eazyschool.model;


import lombok.Data;

@Data
public class Holiday {


    public Holiday( String day, String reason,Type type) {
        this.type = type;
        this.reason = reason;
        this.day = day;
    }


    public Type getType() {
        return type;
    }

    private  Type type ;






    private final String reason ;



    private final String day;



    public enum Type{
        FESTIVAL,FEDERAL
    }

}
