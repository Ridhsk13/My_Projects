package com.noesis.bhurmalaidanmal;

/**
 * Created by noesisimac on 9/27/16.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by noesisimac on 8/16/16.
 */
public class Category implements Serializable {


    private String name;

    private List<ItemDetail> itemList = new ArrayList<ItemDetail>();

    public Category( String name) {
        this.name = name;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<ItemDetail> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemDetail> itemList) {
        this.itemList = itemList;
    }



}