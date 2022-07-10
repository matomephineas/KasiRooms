package com.example.kasirooms.Models;

import java.util.ArrayList;
import java.util.List;

public class ProvinceModel {
    private List<String> nestedList;
    private List<String> province;
    private boolean isExpandable;

    public ProvinceModel(List<String> nestedList, List<String> province) {
        this.nestedList = nestedList;
        this.province = province;
        this.isExpandable = false;
    }

    public List<String> getNestedList() {
        return nestedList;
    }

    public void setNestedList(List<String> nestedList) {
        this.nestedList = nestedList;
    }

    public List<String> getProvince() {
        return province;
    }

    public void setProvince(List<String> province) {
        this.province = province;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
