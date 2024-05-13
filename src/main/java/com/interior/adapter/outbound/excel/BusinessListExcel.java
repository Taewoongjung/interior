package com.interior.adapter.outbound.excel;

import com.interior.domain.business.Business;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

@Getter
public class BusinessListExcel {

    private Workbook workbook;
    private Sheet sheet;
    private int columnCount;

    private BusinessListExcel(
            final Workbook workbook,
            final Sheet sheet,
            final int columnCount
    ) {
        this.workbook = workbook;
        this.sheet = sheet;
        this.columnCount = columnCount;
    }

    public static BusinessListExcel of(final Workbook workbook) {

        Sheet sheet = workbook.createSheet("재료 견적");

        int columnCount = setHeaders(workbook, sheet);

        return new BusinessListExcel(workbook, sheet, columnCount - 1);
    }

    private static int setHeaders(Workbook workbook, Sheet sheet) {

        List<ExcelCellInfo> cellInfoList = new ArrayList<>();

        CellStyle cellStyle = getMyStyleTitle(workbook);

        // 헤더 목록
        cellInfoList.add(new ExcelCellInfo("", 2000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("명칭", 5000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("카테고리", 7000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("재료 명", 5000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("수량", 2000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("단위", 2000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("재료비 - 단가", 7000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("재료비 - 금액", 7000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("노무비 - 단가", 7000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("노무비 - 금액", 7000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("합계 - 단가", 7000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("합계 - 금액", 7000, cellStyle));
        cellInfoList.add(new ExcelCellInfo("비고", 10000, cellStyle));

        Row headerRow = sheet.createRow(1);

        // 헤더 생성
        int cellCount = 0;
        for (ExcelCellInfo cellInfo : cellInfoList) {
            Cell cell = headerRow.createCell(cellCount);

            cell.setCellValue(cellInfo.getName()); // 컬럼명 설정
            cell.getSheet().setColumnWidth(cellCount, cellInfo.getWidth()); // width 설정
            cell.setCellStyle(cellInfo.getStyle()); // 컬럼 스타일 설정

            cellCount++;
        }

        return cellCount;
    }

    private static CellStyle getMyStyleTitle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setBold(true);

        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return cellStyle;
    }

    public void setDate(final Business business) {

        /* 사업명 row 설정 start */
        Row titleRow = sheet.createRow(0);

        // 첫 번째 row 병합
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount));

        Cell title = titleRow.createCell(0);

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        title.setCellValue("■ " + business.getName());
        title.setCellStyle(titleStyle);
        /* 사업명 row 설정 end */

    }
}
