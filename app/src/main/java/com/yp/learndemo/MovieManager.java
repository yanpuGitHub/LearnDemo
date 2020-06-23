package com.yp.learndemo;


import com.yp.networklib.manager.BridgeManager;

import java.util.HashMap;
import java.util.Map;


/**
 * @author senrsl
 * @ClassName: MovieManager
 * @Package: com.jydata.situation.manager
 * @CreateTime: 2020/3/4 7:27 PM
 */
public class MovieManager extends BridgeManager {


    /**
     * 日期票房
     *
     * @param cb
     */
    public void dateBoxOffice(Callback<MoveiBeean> cb) {
        Map<String, Object> map = new HashMap<>();
        get(this, "https://www.wanandroid.com/article/list/0/json", map, cb);
    }
}
