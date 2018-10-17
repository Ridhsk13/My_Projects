package com.noesis.bhurmalaidanmal;

/**
 * Created by noesisimac on 9/27/16.
 */
import java.io.Serializable;

/**
 * Created by noesisimac on 8/16/16.
 */
public class ItemDetail implements Serializable {


    private String name;

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    private String birth;







    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public ItemDetail(  String name) {

        this.name = name;


    }



}