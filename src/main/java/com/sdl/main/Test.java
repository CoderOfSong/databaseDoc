package com.sdl.main;

import com.sdl.pojo.Tables;
import com.sdl.utils.DataUtils;
import com.sdl.utils.FreeMarkUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program databasedoc
 * @description:
 * @author: songdeling
 * @create: 2020/04/07 00:02
 */
public class Test {
    public static void main(String[] args) {
        try {
            List<Tables> tableMsg = DataUtils.getTableMsg();
            Map<String,List<Tables>> map = new HashMap<>();
            map.put("tableList",tableMsg);
            FreeMarkUtil.createFileFromTemplate(map,"dbdoc.ftl","/Users/songdeling/Desktop/test/databaseDoc.doc");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
