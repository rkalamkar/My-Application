package com.rahul.myapplication.Model;

import java.util.ArrayList;

public class Response {
    public ArrayList<UserData> getResults() {
        return results;
    }

    public void setResults(ArrayList<UserData> results) {
        this.results = results;
    }

    ArrayList<UserData> results;
}
