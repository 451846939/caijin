package com.kdkj.caijin.util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

//@Component
public class Download {
    public <T> void down(List<T> list, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Class<? extends Object> obj = list.get(0).getClass();
        Field[] f = obj.getDeclaredFields();
        String name = obj.getName();
        String[] getname = name.split("\\.");
        String outputFile = getname[getname.length - 1] + ".xls";
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(getname[getname.length - 1]);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        for (int i = 0, j = 0; i < f.length; i++, j++) {
            f[i].setAccessible(true);
            if (f[i].getName() == "serialVersionUID") {
                j--;
                continue;
            }
            row.createCell(j).setCellValue(f[i].getName());
        }
        for (int i = 1; i <= list.size(); i++) {
            //设置一个行
            HSSFRow row1 = sheet.createRow(i);
            T obj1 = (T) list.get(i - 1);
            Field[] f1 = list.get(i - 1).getClass().getDeclaredFields();
            for (int j = 0, k = 0; j < f1.length; j++, k++) {
                try {
                    f1[j].setAccessible(true);
                    if (f1[j].getName() == "serialVersionUID") {
                        k--;
                        continue;
                    }
                    System.out.println(f1[j].get(obj1) + "");
                    row1.createCell(k).setCellValue(f1[j].get(obj1) + "");
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
        String uploadPath = request.getSession().getServletContext().getRealPath("table\\");
        File saveDir = new File(uploadPath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        FileOutputStream outf = new FileOutputStream(uploadPath + "/" + outputFile);
        wb.write(outf);
        outf.flush();
        outf.close();
        // 1.设置响应头
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
        response.setHeader("Content-Disposition", "attachment;fileName=" + outputFile);

        String rootPath = request.getSession().getServletContext().getRealPath("table");

        FileInputStream fileInputStream = new FileInputStream(new File(rootPath + "/" + outputFile));


        OutputStream outputStream = response.getOutputStream();

        int a = 0;
        byte[] bts = new byte[1024 * 4];

        while ((a = fileInputStream.read(bts)) != -1) {
            outputStream.write(bts, 0, a);
        }

        outputStream.flush();

        if (fileInputStream != null) {
            fileInputStream.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }

    public static void download(String filePath, String fileName, HttpServletResponse response) throws IOException {
        // 1.设置响应头
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);


        FileInputStream fileInputStream = new FileInputStream(new File(filePath + "/" + fileName));


        OutputStream outputStream = response.getOutputStream();

        int a = 0;
        byte[] bts = new byte[1024 * 4];

        while ((a = fileInputStream.read(bts)) != -1) {
            outputStream.write(bts, 0, a);
        }

        outputStream.flush();

        if (fileInputStream != null) {
            fileInputStream.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }
}
