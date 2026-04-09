package com.wbz.designpatterns.structural;

/**
 * 代理模式演示
 * 代理模式为其他对象提供一种代理以控制对这个对象的访问
 * 适用场景：延迟加载、访问控制、日志记录、缓存等
 */
public final class ProxyCase {

    private ProxyCase() {
    }

    /**
     * 运行代理模式示例
     * 演示如何使用代理实现视频播放缓存功能
     */
    public static void run() {
        VideoService service = new VideoProxy(new RealVideoService());
        System.out.println(service.play("design-patterns-intro.mp4"));
        System.out.println(service.play("design-patterns-intro.mp4"));
    }

    /**
     * 视频服务接口，定义播放操作
     */
    private interface VideoService {
        /**
         * 播放视频文件
         * @param fileName 视频文件名
         * @return 播放结果信息
         */
        String play(String fileName);
    }

    /**
     * 真实视频服务类，负责实际的视频读取和播放
     */
    private static final class RealVideoService implements VideoService {
        /**
         * 真实播放视频，模拟从磁盘或网络读取
         * @param fileName 视频文件名
         * @return 播放结果信息
         */
        @Override
        public String play(String fileName) {
            return "真实服务读取并播放：" + fileName;
        }
    }

    /**
     * 视频代理类，提供缓存功能
     * 当重复播放同一视频时，使用缓存结果而不是重新读取
     */
    private static final class VideoProxy implements VideoService {
        /** 被代理的真实视频服务 */
        private final VideoService target;
        /** 缓存的视频文件名 */
        private String cachedFileName;
        /** 缓存的播放结果 */
        private String cachedResult;

        /**
         * 构造函数，注入真实视频服务
         * @param target 真实视频服务实例
         */
        private VideoProxy(VideoService target) {
            this.target = target;
        }

        /**
         * 播放视频，先检查缓存，缓存未命中则调用真实服务
         * @param fileName 视频文件名
         * @return 播放结果信息
         */
        @Override
        public String play(String fileName) {
            if (fileName.equals(cachedFileName)) {
                return "代理命中缓存：" + cachedResult;
            }
            cachedFileName = fileName;
            cachedResult = target.play(fileName);
            return "代理转发请求：" + cachedResult;
        }
    }
}
