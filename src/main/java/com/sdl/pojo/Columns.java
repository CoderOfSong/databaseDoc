package com.sdl.pojo;

import lombok.Data;

/**
 * @program databasedoc
 * @description: 列
 * @author: songdeling
 * @create: 2020/04/06 22:48
 */
@Data
public class Columns {
    private String name;
    private String datatype;
    private String len;
    private String isnull;
    private String def;
    private String comments;
}
