package com.xiaoluo;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.xiaoluo.home.R;
import com.xiaoluo.utilities.FileUtil;
import com.xiaoluo.utilities.Trace;

import android.app.Application;
import android.content.Context;

/**
 * Copyright 2014 Xiaoluo's Studio
 * 
 * @author xiaoluo 
 * @version create time: 2014年8月6日 - 上午10:54:00
 */
public class MyApplication extends Application {
	public static Context mContext;
	public static int mAppState;
	private static DisplayImageOptions mImageOptions;
	private static ImageLoaderConfiguration mImageLoaderConfig;
	private static ImageLoader mImageLoader = ImageLoader.getInstance();;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this.getApplicationContext();
		mAppState = -1;
//		initalizeImageLoader();
		initImageLoader(mContext);
		Trace.d("MyApplication.java:onCreate():"+"initImageLoader(mContext)");
	}

	public static void initImageLoader(Context context) {
		// 感觉这里没有调用到,这是一个疑问?
		mImageLoaderConfig = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024) // 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		mImageLoader.init(mImageLoaderConfig);
		
		mImageOptions = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.displayer(new RoundedBitmapDisplayer(20))
			.build();
	}
	
	public static ImageLoaderConfiguration getmImageLoaderConfig() {
		return mImageLoaderConfig;
	}

	public static ImageLoader getmImageLoader() {
		return mImageLoader;
	}

	public static DisplayImageOptions getmImageOptions() {
		return mImageOptions;
	}
	
	public synchronized static void setAppState(int state) {
		mAppState = state;
	}
	
	public static Object getCurrentUsers() {
		return null;
	}

//	@SuppressWarnings("deprecation")
//	private static void initalizeImageLoader() {
//		File cacheDir = StorageUtils.getOwnCacheDirectory(mContext, "imageloader/Cache");
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration
//			.Builder(mContext)
//	        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions ==> max width, max height，即保存的每个缓存文件的最大长宽  
//	        .diskCacheExtraOptions(480, 800, null)// 设置缓存的详细信息，最好不要设置这个  
////	        .taskExecutor(...)
////	        .taskExecutorForCachedImages(...)
//	        .threadPoolSize(3) // default //线程池内加载的数量  
//	        .threadPriority(Thread.NORM_PRIORITY - 1) // default
//	        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//	        .denyCacheImageMultipleSizesInMemory()
//	        .memoryCache(new LruMemoryCache(2 * 1024 * 1024))// 你可以通过自己的内存缓存实现  
//	        .memoryCacheSize(2 * 1024 * 1024)
////	        .memoryCacheSizePercentage(13) // default
//	        .diskCache(new UnlimitedDiscCache(cacheDir)) // default//自定义缓存路径  
//	        .diskCacheSize(50 * 1024 * 1024)
//	        .diskCacheFileCount(100)//缓存的文件数量  
//	        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default //也可以将保存的时候的URI名称用MD5 加密,用new Md5FileNameGenerator()  
//	        .imageDownloader(new BaseImageDownloader(mContext)) // default// 也可以connectTimeout (5 s), readTimeout (30 s)超时时间,用new BaseImageDownloader(context, 5 * 1000, 30 * 1000)  
////	        .imageDecoder(new BaseImageDecoder(false)) // default
//	        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//	        .writeDebugLogs()
//	        .build();//开始构建  
//		
//		ImageLoader.getInstance().init(config);//全局初始化此配置
//		
//		mImageOptions = new DisplayImageOptions.Builder()  
//	 	.showImageOnLoading(R.drawable.ic_launcher) //设置图片在下载期间显示的图片  
//	 	.showImageForEmptyUri(R.drawable.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片  
//	 	.showImageOnFail(R.drawable.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
////	 	.cacheInMemory(true)//设置下载的图片是否缓存在内存中  
//	 	.cacheOnDisc()//设置下载的图片是否缓存在SD卡中  
////	 	.considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
////	 	.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示  
////	 	.bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//  
////	 	.decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置  
//	 	//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//	 	//设置图片加入缓存前，对bitmap进行设置  
//	 	//.preProcessor(BitmapProcessor preProcessor)  
////	 	.resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位  
////	 	.displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少  
////	 	.displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间  
//	 	.build();//构建完成 
//	}

}
