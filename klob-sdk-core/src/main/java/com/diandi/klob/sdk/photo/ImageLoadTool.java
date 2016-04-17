package com.diandi.klob.sdk.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.diandi.klob.sdk.util.photo.PixelUtil;
import com.diandi.klob.sdk.util.photo.ScreenUtils;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;/*
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.drawee.interfaces.DraweeController;
*/

/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2015-09-28  .
 * *********    Time : 16:07 .
 * *********    Version : 1.0
 * *********    Copyright © 2015, klob, All Rights Reserved
 * *******************************************************************************
 */
public class ImageLoadTool {
    private final static String TAG = "ImageLoadTool";
    public static int sDefaultLoadingId;
    //R.color.placeholdercolor   Color.parseColor("#e1e4eb");
    public static int sAvatarLoadingId;
    public static ImageLoadTool sInstance;
    public static DisplayImageOptions sDefaultOptions = null;
    public static DisplayImageOptions sNoCacheOptions = null;
    public static DisplayImageOptions sDefaultAvatarOptions = null;
    private static ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoadingListener animateDisplayListener = new AnimateDisplayListener();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private ImageLoadTool() {
    }

    public static ImageLoader getImageLoader() {
        return ImageLoader.getInstance();
    }

    public static ImageLoadTool getInstance() {

        if (sInstance == null) {
            synchronized (ImageLoadTool.class) {
                if (sInstance == null) {
                    sInstance = new ImageLoadTool();
                }
            }
        }
        return sInstance;
    }

    public static ImageLoadTool setsAvatarLoadingId(int sAvatarLoadingId) {
        ImageLoadTool.sAvatarLoadingId = sAvatarLoadingId;
        sDefaultAvatarOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(sAvatarLoadingId)
                .showImageForEmptyUri(sAvatarLoadingId)
                .showImageOnFail(sAvatarLoadingId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        sNoCacheOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(sAvatarLoadingId)
                .showImageForEmptyUri(sAvatarLoadingId)
                .showImageOnFail(sAvatarLoadingId)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        // .resetViewBeforeLoading(true)
        //      .displayer(new RoundedBitmapDisplayer(90))
        return sInstance;
    }

    public static ImageLoadTool setsDefaultLoadingId(int sDefaultLoadingId) {
        ImageLoadTool.sDefaultLoadingId = sDefaultLoadingId;
        sDefaultOptions = new DisplayImageOptions
                .Builder()
                .showImageOnLoading(sDefaultLoadingId)
                .showImageForEmptyUri(sDefaultLoadingId)
                .showImageOnFail(sDefaultLoadingId)
                .cacheInMemory(true)
                .considerExifParams(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return sInstance;
    }

    public static DisplayImageOptions getOptions(int drawableId) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(drawableId)
                .showImageForEmptyUri(drawableId)
                .showImageOnFail(drawableId)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .build();
    }

    public static ImageLoadTool initImageLoader(Context context) {
        // Fresco.initialize(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiskCache(StorageUtils.getCacheDirectory(context), null, new Md5FileNameGenerator()))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))
                .memoryCacheSize(5 * 1024 * 1024)
                .memoryCacheExtraOptions(ScreenUtils.getScreenWidth() / 2, ScreenUtils.getScreenHeight() / 2)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
        ImageLoadTool.getInstance();
        // Fresco.initialize(context);
        return sInstance;
    }

    private static String intToString(int length) {
        String width;
        if (length > 0) {
            width = String.valueOf(length);
        } else {
            width = "";
        }

        return width;
    }

    public void loadAvatar(CircleImageView imageView, String url) {
        if (sAvatarLoadingId <= 0) {
            Log.e(TAG, "you must call etsAvatarLoadingId() first");
            return;
        }
        imageLoader.displayImage(url, imageView, sDefaultAvatarOptions);
    }

    public void loadAvatar(ImageView imageView, String url) {
        if (sDefaultAvatarOptions == null) {
            Log.e(TAG, "you must call setsDefaultLoadingId() first");
            return;
        }
        if (imageView instanceof CircleImageView) {
            imageLoader.displayImage(url, imageView, sDefaultAvatarOptions);
            return;
        }
        imageLoader.displayImage(url, imageView, new DisplayImageOptions.Builder()
                .showImageOnLoading(sAvatarLoadingId)
                .showImageForEmptyUri(sAvatarLoadingId)
                .showImageOnFail(sAvatarLoadingId)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(90))
                .build());
    }

    public void loadImage(ImageView imageView, String url) {
       /* if (imageView instanceof SimpleDraweeView) {
            loadProgressiveImageView((SimpleDraweeView) imageView, url);
            return;
        }*/
        imageLoader.displayImage(url, imageView, sDefaultOptions);
    }

    public void loadImageFromUrl(ImageView imageView, String url) {
        if (sDefaultOptions == null) {
            Log.e(TAG, "you must call setsDefaultLoadingId() first");
            return;
        }
        imageLoader.displayImage(url, imageView, sDefaultOptions);


    }

    public void loadImage(ImageView imageView, String url, DisplayImageOptions imageOptions) {
        imageLoader.displayImage(url, imageView, imageOptions);
    }

    public void loadImageWithAnimate(ImageView imageView, String url) {
    /*    if (imageView instanceof SimpleDraweeView) {
            loadProgressiveImageView((SimpleDraweeView) imageView, url);
            return;
        }*/
        ImageAware imageAware = new ImageViewAware(imageView, false);
        imageLoader.displayImage(url, imageAware, sDefaultOptions, animateDisplayListener);
    }

    public void loadImageWithFirstAnimate(ImageView imageView, String url) {
    /*    if (imageView instanceof SimpleDraweeView) {
            loadProgressiveImageView((SimpleDraweeView) imageView, url);
            return;
        }*/
        ImageAware imageAware = new ImageViewAware(imageView, false);
        imageLoader.displayImage(url, imageAware, sDefaultOptions, animateFirstListener);
    }

    public void loadImage(String uri, ImageSize targetImageSize, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        imageLoader.loadImage(uri, targetImageSize, sDefaultOptions, listener, progressListener);
    }

    public void loadImage(String uri, ImageSize size, DisplayImageOptions imageOptions, ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
        imageLoader.loadImage(uri, size, imageOptions, listener, progressListener);
    }

    public void loadImage(String uri, ImageSize size, DisplayImageOptions imageOptions, ImageLoadingListener listener) {
        imageLoader.loadImage(uri, size, imageOptions, listener);
    }

    /*
    *
    * 	public void displayImage(String uri, ImageView imageView, DisplayImageOptions options,
                ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
            displayImage(uri, new ImageViewAware(imageView), options, listener, progressListener);
        }
    * */
    public void loadImage(ImageView imageView, String url, SimpleImageLoadingListener progressListener) {
        imageLoader.displayImage(url, imageView, sDefaultOptions, progressListener);
    }

    private static class AnimateDisplayListener extends SimpleImageLoadingListener {


        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                FadeInBitmapDisplayer.animate(imageView, 500);
            }
        }
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
    /**
     * Fresco
     * */
   /*
    public void loadProgressiveImageView(SimpleDraweeView view, String uri) {
        ImageRequest imageRequest =
                ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                        .setProgressiveRenderingEnabled(true)
                        .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(view.getController())
                .setAutoPlayAnimations(true)
                .build();
        view.setController(draweeController);
    }
*/

   /*
   //2 px Round
    public static final DisplayImageOptions optionsRounded = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.bg_pic_loading)
            .showImageForEmptyUri(R.drawable.bg_pic_loading)
            .showImageOnFail(R.drawable.bg_pic_loading)
            .cacheInMemory(true)
            .considerExifParams(true)
            .displayer(new RoundedBitmapDisplayer(2))
            .build();
    //2 dp Round
    public static final DisplayImageOptions optionsRounded2 = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.bg_pic_loading)
            .showImageForEmptyUri(R.drawable.bg_pic_loading)
            .showImageOnFail(R.drawable.bg_pic_loading)
            .cacheInMemory(true)
            .considerExifParams(true)
            .displayer(new RoundedBitmapDisplayer(PixelUtil.dp2px(2)))
            .build();
            */
}

