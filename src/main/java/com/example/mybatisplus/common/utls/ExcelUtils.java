package com.example.mybatisplus.common.utls;


import com.example.mybatisplus.model.domain.Book;
import com.example.mybatisplus.model.domain.Commodity;
import com.example.mybatisplus.model.domain.Multibarcode;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtils {

    public final static String XLS = "xls";
    public final static String XLSX = "xlsx";

    public static List<String[]> readExcel(MultipartFile formFile) throws IOException {
        //检查文件
        //checkFile(formFile);
        //获得工作簿对象
        Workbook workbook = getWorkBook(formFile);
        //创建返回对象，把每行中的值作为一个数组，所有的行作为一个集合返回
        List<String[]> list = new ArrayList<>();
        if (null != workbook) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获取当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (null == sheet) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行之外的所有行
                for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    //后的当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    //循环当前行
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    if (cells[0].equals("名称")){
                        // checkHead(cells);
                    }else {
                        list.add(cells);
                    }
                }
            }
        }
        return list;
    }

    @SuppressWarnings("deprecation")
    private static String getCellValue(Cell cell) {
        String cellValue = "";

        if (cell == null) {
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == CellType.NUMERIC && !HSSFDateUtil.isCellDateFormatted(cell)) {
            cell.setCellType(CellType.STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC://数字
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    cellValue = DateFormatUtils.format(date, "yyyy-MM-dd");
                } else {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    DecimalFormat df = new DecimalFormat("0");
                    cellValue = df.format(cellValue);
                }
                break;
            case STRING://字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN://Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA://公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK://空值
                cellValue = "";
                break;
            case ERROR://故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }


    public static Workbook getWorkBook(MultipartFile formFile) {
        //获得文件名
        String fileName = formFile.getOriginalFilename();
        //创建Workbook工作簿对象，表示整个excel
        Workbook workbook = null;
        try {
            //获得excel文件的io流
            InputStream is = formFile.getInputStream();
            //根据文件后缀名不同（xls和xlsx）获得不同的workbook实现类对象
            if (fileName.endsWith(XLS)) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }


    /**
     * 导出商品资料，将传入的商品列表输出为.xls表格文件
     * @param response 响应
     * @param commodities 传入的商品列表
     * @throws IOException
     */
    public static void exportCommodities(HttpServletResponse response, List<Commodity> commodities) throws IOException {
        //声明一个表格对象
        HSSFWorkbook hwb = new HSSFWorkbook();
        //声明一个sheet并命名
        HSSFSheet sheet = hwb.createSheet("商品信息");
        //给sheet名称一个长度
        sheet.setDefaultColumnWidth((short) 10);
        //商品名称
        sheet.setColumnWidth(0, 30 * 256);
        //条码
        sheet.setColumnWidth(1, 20 * 256);
        //扩充条码
        sheet.setColumnWidth(2, 60 * 256);
        //分类
        sheet.setColumnWidth(3, 8 * 256);
        //库存
        sheet.setColumnWidth(4, 8 * 256);
        //单位
        sheet.setColumnWidth(5, 8 * 256);
        //规格
        sheet.setColumnWidth(6, 8 * 256);
        //进货价
        sheet.setColumnWidth(7, 12 * 256);
        //销售价
        sheet.setColumnWidth(8, 12 * 256);
        //批发价
        sheet.setColumnWidth(9, 12 * 256);
        //会员折扣
        sheet.setColumnWidth(10, 16 * 256);
        //供货商
        sheet.setColumnWidth(11, 30 * 256);
        //生产日期
        sheet.setColumnWidth(12, 20 * 256);
        //保质期
        sheet.setColumnWidth(13, 12 * 256);
        //创建时间
        sheet.setColumnWidth(14, 20 * 256);
        // 设置单元格格式为文本格式
        HSSFCellStyle textStyle = hwb.createCellStyle();
        HSSFDataFormat format = hwb.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        //设置单元格格式为"文本"
        sheet.setDefaultColumnStyle(6, textStyle);

        //设置下拉框
        // DataValidationHelper helper = sheet.getDataValidationHelper();

        //生成表头样式
        HSSFCellStyle headStyle = hwb.createCellStyle();
        headStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成一个字体
        HSSFFont headFont = hwb.createFont();
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 16);
        headFont.setBold(true);
        // 把字体应用到当前的样式
        headStyle.setFont(headFont);

        //公用样式
        HSSFCellStyle style = hwb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setWrapText(true);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成一个字体
        HSSFFont font = hwb.createFont();
        font.setFontName("Book Antiqua");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        // 把字体应用到当前的样式
        style.setFont(font);

        //表格内部样式
        HSSFCellStyle commStyle = hwb.createCellStyle();
        commStyle.setBorderBottom(BorderStyle.THIN);
        commStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        commStyle.setBorderLeft(BorderStyle.THIN);
        commStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        commStyle.setBorderRight(BorderStyle.THIN);
        commStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        commStyle.setBorderTop(BorderStyle.THIN);
        commStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        commStyle.setAlignment(HorizontalAlignment.CENTER);
        commStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFFont font1 = hwb.createFont();
        font1.setFontName("Book Antiqua");
        font1.setFontHeightInPoints((short) 10);
        commStyle.setFont(font1);
        //创建表标题
        HSSFRow headerRow = sheet.createRow(0);
        //样式字体居中
        headerRow.setHeightInPoints(20);
        headerRow.setHeight((short) (34.40 * 20));
        HSSFCell cell0 = headerRow.createCell(0);
        HSSFCell cell7 = headerRow.createCell(14);
        cell0.setCellValue("商品资料");
        cell0.setCellStyle(headStyle);
        cell7.setCellStyle(headStyle);
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 14);
        sheet.addMergedRegion(cellAddresses);

        //创建表头
        HSSFRow row = sheet.createRow(1);
        row.setHeight((short) (20.40 * 20));
        //给表头第一行一次创建单元格
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("商品名称");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("条码");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("扩充条码");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("分类");
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("库存");
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);
        cell.setCellValue("单位");
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);
        cell.setCellValue("规格");
        cell.setCellStyle(style);
        cell = row.createCell((short) 7);
        cell.setCellValue("进货价");
        cell.setCellStyle(style);
        cell = row.createCell((short) 8);
        cell.setCellValue("销售价");
        cell.setCellStyle(style);
        cell = row.createCell((short) 9);
        cell.setCellValue("批发价");
        cell.setCellStyle(style);
        cell = row.createCell((short) 10);
        cell.setCellValue("会员折扣");
        cell.setCellStyle(style);
        cell = row.createCell((short) 11);
        cell.setCellValue("供货商");
        cell.setCellStyle(style);
        cell = row.createCell((short) 12);
        cell.setCellValue("生产日期");
        cell.setCellStyle(style);
        cell = row.createCell((short) 13);
        cell.setCellValue("保质期");
        cell.setCellStyle(style);
        cell = row.createCell((short) 14);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);

        for (short i = 0; i < commodities.size(); i++) {
            String str;
            row = sheet.createRow(i + 2);
            row.setHeight((short) (20.40 * 20));
            cell = row.createCell((short) 0);
            cell.setCellStyle(commStyle);
            cell.setCellValue(commodities.get(i).getName());

            cell = row.createCell((short) 1);
            cell.setCellValue(commodities.get(i).getBarcode());
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 2);
            List<Multibarcode> multibarcodes = commodities.get(i).getMultibarcodes();
            if(multibarcodes != null){
                StringBuilder stringBuilder = new StringBuilder();
                Multibarcode multibarcode = multibarcodes.get(0);
                stringBuilder.append(multibarcode.getBarcode());
                for (int j = 1; j<multibarcodes.size(); j++) {
                    stringBuilder.append(", ");
                    stringBuilder.append(multibarcode.getBarcode());
                }
                cell.setCellValue(stringBuilder.toString().equals("null")?"":stringBuilder.toString());
            }else{
                cell.setCellValue("");
            }
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 3);
            if(commodities.get(i).getClassification() != null){
                str = commodities.get(i).getClassification().getClassification();
                cell.setCellValue(str == null?"":str);
            }else {
                cell.setCellValue("");
            }
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 4);
            cell.setCellValue(commodities.get(i).getInventory()==null?"":commodities.get(i).getInventory().toString());
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 5);
            if(commodities.get(i).getUnit() != null){
                str=commodities.get(i).getUnit().getUnit();
                cell.setCellValue(str==null?"":str);
            }else{
                cell.setCellValue("");
            }
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 6);
            str = commodities.get(i).getSpecification();
            cell.setCellValue(str==null?"":str);
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 7);
            cell.setCellValue(commodities.get(i).getPurchasePrice());
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 8);
            cell.setCellValue(commodities.get(i).getPrice());
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 9);
            cell.setCellValue(commodities.get(i).getWholesalePrice());
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 10);
            cell.setCellValue(commodities.get(i).getIsDiscount()?"是":"否");
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 11);
            if(commodities.get(i).getSupplier() != null){
                str = commodities.get(i).getSupplier().getName();
                cell.setCellValue(str == null?"":str);
            }else{
                cell.setCellValue("");
            }
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 12);
            if(commodities.get(i).getProduceDate() != null){
                str = commodities.get(i).getProduceDate().toString().substring(0, 10);
            }else{
                str = "";
            }
            cell.setCellValue(str);
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 13);
            cell.setCellValue(commodities.get(i).getExpirationTime()==null?"":commodities.get(i).getExpirationTime().toString());
            cell.setCellStyle(commStyle);

            cell = row.createCell((short) 14);
            cell.setCellValue(commodities.get(i).getCreateTime().toString().replace('T', ' '));
            cell.setCellStyle(commStyle);
        }
        hwb.write(response.getOutputStream());
        hwb.close();
    }

    public static void checkFile(MultipartFile formFile) throws IOException {
        //判断文件是否存在
        if (null == formFile) {
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = formFile.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            throw new IOException(fileName + "不是excel文件！");
        }
    }
    public static Boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    //创建下拉框
    private static void creatDropDownList(Sheet taskInfoSheet, DataValidationHelper helper, String[] list,
                                          Integer firstRow, Integer lastRow, Integer firstCol, Integer lastCol) {
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        //设置下拉框数据
        DataValidationConstraint constraint = helper.createExplicitListConstraint(list);
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        //处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        taskInfoSheet.addValidationData(dataValidation);
    }


}
