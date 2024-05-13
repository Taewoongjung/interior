package com.interior.adapter.outbound.excel;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component
public class ExcelUtils {

    private static final int MAX_ROW = 5000;

    public void download(
            final Class<?> clazz,
            final List<?> data,
            final String fileName,
            HttpServletResponse response) {

        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {
            int loop = 1;
            int listSize = data.size();

            for (int start = 0; start < listSize; start += MAX_ROW) {
                int nextPage = MAX_ROW * loop;
                if (nextPage > listSize) nextPage = listSize - 1;
                List<?> list = new ArrayList<>(data.subList(start, nextPage));
                getWorkBook(clazz, workbook, start, findHeaderNames(clazz), list, listSize);
                list.clear();
                loop++;
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName) + ".xlsx");

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException | IllegalAccessException e) {
            log.error("Excel Download Error Message = {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    private SXSSFWorkbook getWorkBook(Class<?> clazz, SXSSFWorkbook workbook, int rowIdx, List<String> headerNames, List<?> data, int maxSize) throws IllegalAccessException, IOException {
        // 각 시트 당 MAX_ROW 개씩
        String sheetName = "Sheet" + (rowIdx / MAX_ROW + 1);

        Sheet sheet = ObjectUtils.isEmpty(workbook.getSheet(sheetName)) ? workbook.createSheet(sheetName) : workbook.getSheet(sheetName);
        sheet.setDefaultColumnWidth((short) 300);   // 디폴트 너비 설정
        sheet.setDefaultRowHeight((short) 500);     // 디폴트 높이 설정

        Row row = null;
        Cell cell = null;
        int rowNo = rowIdx % maxSize; // 0, 5000, 10000, 15000, 20000 : 5000씩 증가됨

        row = sheet.createRow(0);
        createHeaders(workbook, row, cell, headerNames);
        createBody(clazz, data, sheet, row, cell, rowIdx);

        // 주기적인 flush 진행
        if (rowNo % MAX_ROW == 0) {
            ((SXSSFSheet) sheet).flushRows(MAX_ROW);
        }

        return workbook;
    }

    private void createHeaders(SXSSFWorkbook workbook, Row row, Cell cell, List<String> headerNames) {
        /**
         * header font style
         */
        Font font = workbook.createFont();
        font.setColor((short) 255);

        /**
         * header cell style
         */
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);       // 가로 가운데 정렬
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 세로 가운데 정렬

        // 테두리 설정
        headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
        headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
        headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);

        // 배경 설정
        headerCellStyle.setFillForegroundColor((short) 102);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFont(font);

        for (int i = 0, size = headerNames.size(); i < size; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(headerCellStyle);
            cell.setCellValue(headerNames.get(i));
        }
    }

    private void createBody(Class<?> clazz, List<?> data, Sheet sheet, Row row, Cell cell, int rowNo) throws IllegalAccessException, IOException {
        int startRow = 0;
        for (Object o : data) {
            List<Object> fields = findFieldValue(clazz, o);
            row = sheet.createRow(++startRow);
            for (int i = 0, fieldSize = fields.size(); i < fieldSize; i++) {
                cell = row.createCell(i);
                cell.setCellValue(String.valueOf(fields.get(i)));

                // 주기적인 flush 진행
                if (rowNo % MAX_ROW == 0) {
                    ((SXSSFSheet) sheet).flushRows(MAX_ROW);
                }
            }
        }
    }

    /**
     * 엑셀의 헤더 명칭을 찾는 로직
     */
    private List<String> findHeaderNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumnName.class))
                .map(field -> field.getAnnotation(ExcelColumnName.class).name())
                .collect(Collectors.toList());
    }

    /**
     * 데이터의 값을 추출하는 메서드
     */
    private List<Object> findFieldValue(Class<?> clazz, Object obj) throws IllegalAccessException {
        List<Object> result = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            result.add(field.get(obj));
        }
        return result;
    }
}
