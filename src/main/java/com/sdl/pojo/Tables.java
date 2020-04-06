package com.sdl.pojo;

import lombok.Data;

import java.util.List;

/**
 * @program databasedoc
 * @description: 表
 * @author: songdeling
 * @create: 2020/04/06 22:46
 */
@Data
public class Tables {
    private String tableName;
    private String tableComments;

    private List<Columns> columnsList;
}
