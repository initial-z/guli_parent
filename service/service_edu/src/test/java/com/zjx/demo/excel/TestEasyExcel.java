package com.zjx.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
//        //实现excel写的操作
//        String filename = "D:\\write.xlsx";
//        EasyExcel.write(filename, DemoData.class).sheet().doWrite(getData());

        String filename = "D:\\write.xlsx";
        EasyExcel.read(filename, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSname("lucy" + i);
            demoData.setSno(i);
            list.add(demoData);
        }
        return list;
    }
}
