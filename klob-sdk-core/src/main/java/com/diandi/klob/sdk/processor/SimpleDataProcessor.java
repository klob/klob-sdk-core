package com.diandi.klob.sdk.processor;

import com.diandi.klob.sdk.concurrent.SimpleExecutor;
import com.diandi.klob.sdk.concurrent.SimpleTask;

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-08-09  .
 * *********    Time : 19:15 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */

/**
 * Here is a sample
 * new SimpleDataProcessor(new SimpleDataHandler() {
 * String trueUrl;
 *
 * @Override public void start() {
 * <p/>
 * HttpClient httpClient = new DefaultHttpClient();
 * HttpContext httpContext = new BasicHttpContext();
 * HttpGet httpGet = new HttpGet(item.getContent());
 * <p/>
 * try {
 * //将HttpContext对象作为参数传给execute()方法,则HttpClient会把请求响应交互过程中的状态信息存储在HttpContext中
 * HttpResponse response = httpClient.execute(httpGet, httpContext);
 * //获取重定向之后的主机地址信息,
 * HttpHost targetHost = (HttpHost) httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
 * //获取实际的请求对象的URI,
 * HttpUriRequest realRequest = (HttpUriRequest) httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
 * trueUrl = "http://" + targetHost.getHostName() + realRequest.getURI();
 * } catch (Exception e) {
 * e.printStackTrace();
 * } finally {
 * httpClient.getConnectionManager().shutdown();
 * }
 * }
 * @Override public void over() {
 * L.e(TAG, trueUrl);
 * <p/>
 * }
 * });
 */


public class SimpleDataProcessor {
    SimpleDataHandler mSimpleDataHandler;

    public SimpleDataProcessor(SimpleDataHandler dataHandler) {
        mSimpleDataHandler = dataHandler;
        SimpleExecutor.getInstance().execute(new SimpleDataTask());
    }

    class SimpleDataTask extends SimpleTask {

        @Override
        public void doInBackground() {
            mSimpleDataHandler.start();
        }

        @Override
        public void onFinish(boolean canceled) {
            post(new Runnable() {
                @Override
                public void run() {
                    mSimpleDataHandler.over();
                }
            });
        }
    }
}
