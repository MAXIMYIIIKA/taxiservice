package by.nichipor.taxiservice.controller;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Max Nichipor on 21.08.2016.
 */

@Component
public class TestClass {
    private ArrayList<String> testStrings;
    boolean isNew;

    public TestClass(){
        this.testStrings = new ArrayList<>();
    }

    public ArrayList<String> getTestStrings() {
        return testStrings;
    }

    public void setTestStrings(ArrayList<String> testStrings) {
        this.testStrings = testStrings;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public void addString(String string){
        this.testStrings.add(string);
        this.isNew = true;
    }

    public int stringsNumber(){
        return this.testStrings.size();
    }
}
