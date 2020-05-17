package com.dmf.boot.learn.util;//package com.dmf.learn.util;
//
//import com.google.common.base.Preconditions;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.Accessors;
//import org.apache.poi.hssf.usermodel.*;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * @author dengmingfeng
// */
//public class ExcelUtils {
//
//
//    /**
//     * @param fileName 导出文件名，包含地址
//     * @throws Exception
//     */
//    public static void exportExcelSingleSheet(String fileName, SheetDto sheetDto) throws Exception {
//        HSSFWorkbook wb = new HSSFWorkbook();
//
//        HSSFSheet sheet = wb.createSheet(sheetDto.getSheetName());
//        setSheetData(sheet, sheetDto.getHeads(), sheetDto.getRows());
//        wb.write(new FileOutputStream(new File(fileName)));
//    }
//
//    /**
//     * 多sheet多线程导出
//     *
//     * @param fileName
//     * @param sheetDtos
//     * @throws Exception
//     */
//    public static void exportExcelMultiSheet(String fileName, List<SheetDto> sheetDtos) throws Exception {
//        HSSFWorkbook wb = new HSSFWorkbook();
//        CountDownLatch countDownLatch = new CountDownLatch(sheetDtos.size());
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        sheetDtos.forEach(sheetDto -> {
//            HSSFSheet sheet = wb.createSheet(sheetDto.getSheetName());
//            executorService.execute(new Task(countDownLatch, sheet, sheetDto));
//        });
//        countDownLatch.await();
//        wb.write(new FileOutputStream(new File(fileName)));
//        executorService.shutdown();
//    }
//
//    private static void setSheetData(HSSFSheet sheet, List<String> heads, List<List<String>> rows) {
//        Preconditions.checkArgument(!(heads == null || heads.size() == 0), "heads is null!");
//        Preconditions.checkArgument(!(rows == null || rows.size() == 0), "rows is null!");
//        HSSFRow headRow = sheet.createRow(0);
//        for (int i = 0; i < heads.size(); i++) {
//            HSSFCell headCell = headRow.createCell(i);
//            headCell.setCellValue(heads.get(i));
//            //headCell.setCellStyle(headStyle(wb));
//        }
//
//        for (int i = 0; i < rows.size(); i++) {
//            HSSFRow contentRow = sheet.createRow(i + 1);
//            List<String> row = rows.get(i);
//            for (int j = 0; j < row.size(); j++) {
//                HSSFCell cell1 = contentRow.createCell(j);
//                cell1.setCellValue(row.get(j));
//                //cell1.setCellStyle(contentStyle(wb));
//            }
//        }
//    }
//
//
//    /**
//     * 创建内容行样式
//     *
//     * @return
//     */
//    protected static HSSFCellStyle contentStyle(HSSFCellStyle cellStyle, HSSFFont cellFont) {
//        cellFont.setFontName("微软雅黑");
//        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//        cellFont.setColor(HSSFFont.COLOR_NORMAL);
//
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyle.setFont(cellFont);
//        return cellStyle;
//    }
//
//    /**
//     * 创建标题行样式
//     *
//     * @return
//     */
//    protected static HSSFCellStyle headStyle(HSSFCellStyle cellStyle, HSSFFont cellFont) {
//        cellFont.setFontName("微软雅黑");
//        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        cellFont.setColor(HSSFFont.COLOR_RED);
//
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        cellStyle.setFont(cellFont);
//        return cellStyle;
//    }
//
//    @AllArgsConstructor
//    private static class Task implements Runnable {
//        private CountDownLatch countDownLatch;
//        private HSSFSheet sheet;
//        private SheetDto sheetDto;
//
//        @Override
//        public void run() {
//            setSheetData(sheet, sheetDto.getHeads(), sheetDto.getRows());
//            countDownLatch.countDown();
//        }
//    }
//
//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Accessors(chain = true)
//    public static class SheetDto {
//        private String sheetName;
//        private List<String> heads;
//        private List<List<String>> rows;
//    }
//
//}
