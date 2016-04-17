package com.diandi.klob.sdk.datastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-28  .
 * *********    Time : 16:07 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class UniqueList<E> extends ArrayList<E> {
    @Override
    public boolean add(E object) {
        return !contains(object) && super.add(object);
    }

    @Override
    public void add(int index, E object) {
        if (!contains(object))
            super.add(index, object);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        for (E e : collection)
            add(e);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {

        int temp = index;
        for (E e : collection) {
            add(temp, e);
            temp++;
        }
        return true;
    }

    @Override
    public E remove(int index) {
        return super.remove(index);
    }

    @Override
    public boolean remove(Object object) {
        return super.remove(object);
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
    }

    public static void main(String[] args)
    {
        List<String> l=new UniqueList<>();
        l.add("12312");
    }
}
