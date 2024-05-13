package com.interior.adapter.outbound.excel;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

@Getter
public class BusinessListExcel {

    private Workbook workbook;
    private Sheet sheet;

    private BusinessListExcel(
            final Workbook workbook,
            final Sheet sheet
    ) {
        this.workbook = workbook;
        this.sheet = sheet;
    }

    public static BusinessListExcel of(final Workbook workbook) {

        Sheet sheet = workbook.createSheet("재료 견적");

        return new BusinessListExcel(workbook, sheet);
    }

    public void setHeaders() {

        int rowCount = 0;

        // 헤더 목록
        Row headerRow = this.sheet.createRow(rowCount++);
        headerRow.createCell(0).setCellValue("카테고리");
        headerRow.createCell(1).setCellValue("재료 명");
        headerRow.createCell(2).setCellValue("수량");
        headerRow.createCell(3).setCellValue("단위");
        headerRow.createCell(4).setCellValue("재료비 - 단가");
        headerRow.createCell(5).setCellValue("재료비 - 금액");
        headerRow.createCell(6).setCellValue("노무비 - 단가");
        headerRow.createCell(7).setCellValue("노무비 - 금액");
        headerRow.createCell(8).setCellValue("합계 - 단가");
        headerRow.createCell(9).setCellValue("합계 - 금액");
        headerRow.createCell(10).setCellValue("비고");
    }
}
