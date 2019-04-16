package com.mygdx.game.creator.dungeon;

public class Test {

    public Test() {
        System.out.println("Hejj");
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        SubClass.class.newInstance();
    }

    class SubClass {

    }
}
