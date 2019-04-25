package ui.main;

import data.external.DatabaseEngine;

public class JARrunner {

    public static void main(String[] args){
        DatabaseEngine.getInstance().open();
        MainTester.main(args);
    }


}
