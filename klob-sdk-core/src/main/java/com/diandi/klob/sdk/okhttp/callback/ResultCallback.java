package com.diandi.klob.sdk.okhttp.callback;

import com.diandi.klob.sdk.util.L;
import com.squareup.okhttp.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ResultCallback<T> {
    public static final ResultCallback<String> DEFAULT_RESULT_CALLBACK = new ResultCallback<String>() {
        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String response) {

        }
    };
    public Type mType;

    public ResultCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    public  static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            superclass = DEFAULT_RESULT_CALLBACK.getClass().getGenericSuperclass();
        }

        ParameterizedType parameterized = (ParameterizedType) superclass;
        return Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public void onBefore(Request request) {
    }

    public void onAfter() {
    }

    public void inProgress(float progress) {

    }

    public abstract void onError(Request request, Exception e);

    public abstract void onResponse(T response);
}