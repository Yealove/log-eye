package com.github.yealove.logeye.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * logger信息
 * Created by Yealove on 2018-11-01.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private String name;
    private String level;
    private String effectiveLevel;
}
