package com.diandi.klob.sdk.okhttp.callback;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


 final class Preconditions {
    public Preconditions() {
    }

    public static <T> T checkNotNull(T obj) {
        if(obj == null) {
            throw new NullPointerException();
        } else {
            return obj;
        }
    }

    public static void checkArgument(boolean condition) {
        if(!condition) {
            throw new IllegalArgumentException();
        }
    }
}
