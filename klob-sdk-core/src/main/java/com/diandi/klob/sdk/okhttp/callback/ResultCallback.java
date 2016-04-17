package com.diandi.klob.sdk.okhttp.callback;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ResultCallback<T> {

    public Type mType;

    public ResultCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }
    public abstract void onResponse(T t);

    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            ResultCallback<String> callback = new ResultCallback<String>() {
                @Override
                public void onResponse(String s) {

                }
            };
            superclass = callback.getClass().getGenericSuperclass();
        }

        ParameterizedType parameterized = (ParameterizedType) superclass;
        return Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


}