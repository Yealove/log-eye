package com.github.yealove.logeye.api;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.github.yealove.logeye.vo.Log;
import com.github.yealove.logeye.vo.Result;
import com.github.yealove.logeye.vo.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;


/**
 * log-eye controller
 * <p>
 * GET（SELECT）：从服务器取出资源（一项或多项）。
 * POST（CREATE）：在服务器新建一个资源。
 * PUT（UPDATE）：在服务器更新资源（客户端提供改变后的完整资源）。
 * PATCH（UPDATE）：在服务器更新资源（客户端提供改变的属性）。
 * DELETE（DELETE）：从服务器删除资源。
 * <p>
 * Created by Yealove on 2018-11-01.
 */
@RestController
@RequestMapping("/log-eye")
public class LogbackController implements LogApi {
    private static Logger logger = LoggerFactory.getLogger(LogbackController.class);
    private LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    private List<Log> logs = null;


    private void init() {
        List<ch.qos.logback.classic.Logger> loggerList = loggerContext.getLoggerList();
        logs = new LinkedList<>();
        for (ch.qos.logback.classic.Logger log : loggerList) {
            logs.add(new Log(log.getName(), String.valueOf(log.getLevel()), String.valueOf(log.getEffectiveLevel())));
        }
    }

    @GetMapping("list")
    public Result list(@RequestParam(required = false) String name) {
        if (logs == null) {
            init();
        }

        if (name == null || "".equals(name.trim())) {
            return Result.success(logs);
        }

        List<Log> filterList = new LinkedList<>();
        for (Log log : logs) {
            if (log.getName().contains(name)) {
                filterList.add(log);
            }
        }
        return Result.success(filterList);
    }

    @PutMapping(value = "update", produces = "application/json;charset=UTF-8")
    public Result update(@RequestBody Log log) {
        try {
            loggerContext.getLogger(log.getName()).setLevel("null".equals(log.getLevel()) ? null : Level.valueOf(log.getLevel()));
            ch.qos.logback.classic.Logger logger = loggerContext.getLogger(log.getName());
            //刷新缓存
            logs = null;
            return Result.success(new Log(logger.getName(), String.valueOf(logger.getLevel()), String.valueOf(logger.getEffectiveLevel())));
        } catch (Exception e) {
            logger.error("修改日志级别失败！", e);
            return Result.failure(e.getMessage(), log);
        }
    }

    @GetMapping(value = "info/{name}")
    public Result info(@PathVariable String name) {
        try {
            ch.qos.logback.classic.Logger logger = loggerContext.getLogger(name);
            return Result.success(new Log(logger.getName(), String.valueOf(logger.getLevel()), String.valueOf(logger.getEffectiveLevel())));
        } catch (Exception e) {
            logger.error("获取日志级别失败！", e);
            return Result.failure(ResultCode.INVALID_DATA, name);
        }
    }
}
