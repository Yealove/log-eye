package com.github.yealove.logeye.api;

import com.github.yealove.logeye.vo.Log;
import com.github.yealove.logeye.vo.Result;

/**
 * 日志查看/修改api
 * <p>
 * Created by Yealove on 2018-11-02.
 */
public interface LogApi {

    Result list(String name);

    Result update(Log log);

    Result info(String name);
}
