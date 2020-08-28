package com.example.walabotsmartsecuritydefense.group;

import java.util.ArrayList;

public class Parent {
    public String zoneName;//zoneName
    private String warningNumber;//warningNumber
    private ArrayList<Child> children;
    private boolean isChecked;///cal...


    public Parent(String zoneName) {
        this.zoneName = zoneName;
        children = new ArrayList<Child>();
        this.isChecked = false;
    }

    public void setWarningNumber(String warningNumber) {
        this.warningNumber = warningNumber;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void toggle() {
        this.isChecked = !this.isChecked;
    }


    //get

    public String getZoneName() {
        return zoneName;
    }

    public String getWarningNumber() {
        return warningNumber;
    }

    public boolean getChecked() {
        return this.isChecked;
    }

    public int getChildrenCount() {
        return children.size();
    }

    public Child getChildItem(int index) {
        return children.get(index);
    }

    public void addChildrenItem(Child child) {
        children.add(child);
    }

    public ArrayList<Child> getItems() {
        return children;
    }
}
