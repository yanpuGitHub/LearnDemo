package com.yp.pic.imageload.glideloader;

import android.content.Context;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@GlideModule
public class DefaultGlideModule extends AppGlideModule {

    public static DiskCache cache;
    public static Map<String, String> headers;

    /**
     * 通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     * 
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        /**
         * (1)配置缓存 .放在cache中 builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheDirectoryName,
         * yourSizeInBytes)
         * 
         * .配置缓存的位置 builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheDirectoryName, yourSizeInBytes)
         * 
         * (2)配置内存缓存大小 builder.setMemoryCache(new LruResourceCache(yourSizeInBytes));
         * 
         * (3)配置BitmapPool小 builder.setBitmapPool(new LruBitmapPool(sizeInBytes));
         * 
         * (4)配置bitmap格式，默认是RGB_565格式，内存占用是ARGB_8888的一半 builder.setDecodeFormat(DecodeFormat.ALWAYS_ARGB_8888);
         **/

        // 在 Android 中有两个主要的方法对图片进行解码：ARGB8888 和 RGB565。前者为每个像素使用了 4 个字节，
        // 后者仅为每个像素使用了 2 个字节。ARGB8888 的优势是图像质量更高以及能存储一个 alpha 通道。
        // Picasso 使用 ARGB8888，Glide 默认使用低质量的 RGB565。
        // 对于 Glide 使用者来说：你使用 Glide module 方法去改变解码规则。
        // builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.format(DecodeFormat.PREFER_ARGB_8888);
        // 关闭 HardwareConfig 在 8.0 以上 会出问题
        requestOptions.disallowHardwareConfig();
        builder.setDefaultRequestOptions(requestOptions);

        if (!TextUtils.isEmpty(GlideLoadStrategy.CACHE_DISK_PATH)) {
            // 设置缓存目录
            File cacheDir = new File(GlideLoadStrategy.CACHE_DISK_PATH);
            cache = DiskLruCacheWrapper.get(cacheDir, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);// 250 MB
            builder.setDiskCache(new DiskCache.Factory() {
                @Override
                public DiskCache build() {
                    return cache;
                }
            });
        }

        // 设置memory和Bitmap池的大小
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
    }

    /**
     * 为App注册一个自定义的String类型的BaseGlideUrlLoader
     *
     * @param context
     * @param registry
     */
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.append(String.class, InputStream.class, new CustomBaseGlideUrlLoader.Factory());
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());

        //Glide4.0加载https图片  https://blog.csdn.net/u010232308/article/details/78085057
        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }

    /**
     * 清单解析的开启
     *
     * 这里不开启，避免添加相同的modules两次
     * 
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}

class UnsafeOkHttpClient {
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder newBuilder = chain.request().newBuilder();
                    if(null != DefaultGlideModule.headers) {
                        for(Map.Entry<String, String> entry : DefaultGlideModule.headers.entrySet()) {
                            newBuilder.addHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    return chain.proceed(newBuilder.build());
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
