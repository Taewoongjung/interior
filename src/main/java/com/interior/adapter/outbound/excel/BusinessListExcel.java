package com.interior.adapter.outbound.excel;

import static java.util.stream.Collectors.groupingBy;

import com.interior.domain.business.Business;
import com.interior.domain.business.material.BusinessMaterial;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public void setData(final Business business) {

        // 사업명 설정 (첫 번째)
        setTitle(
                business.getName(),
                business.getStatus().getDesc(),
                business.getStatusDetail() != null ?
                        business.getStatusDetail().getDesc() : null
        );

        // 실제 데이터 설정
        setRealData(business.getBusinessMaterialList().stream()
                .collect(groupingBy(BusinessMaterial::getUsageCategory)));
    }

    private void setTitle(final String businessName, final String status, final String statusDetail) {
        Row titleRow = sheet.createRow(0);

        // 첫 번째 row 병합
        mergeRow(0, 0, 0, columnCount);

        Cell title = titleRow.createCell(0);

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setBold(true);
        titleStyle.setFont(font);

        String titleStr = statusDetail != null ?
                "■ " + businessName + " (" + status + " - " + statusDetail+ ")" :
                "■ " + businessName + " (" + status + ")";

        title.setCellValue(titleStr);
        title.setCellStyle(titleStyle);
    }

    private void mergeRow(final int firstRow, int lastRow, final int firstCol, final int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    private void setRealData(
            final Map<String, List<BusinessMaterial>> businessMaterialMap) {

        int countDataOfMajorTopic = 1;

        int rowCount = 2; // header 다음 데이터 부터 설정.

        for (String materialCategory : businessMaterialMap.keySet()) {

            // 대 주제
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 9);
            font.setBold(true);

            CellStyle cellStyleOfFirstRowOfMajorTopicCount = workbook.createCellStyle();
            cellStyleOfFirstRowOfMajorTopicCount.setAlignment(HorizontalAlignment.CENTER);
            cellStyleOfFirstRowOfMajorTopicCount.setFont(font);

            CellStyle cellStyleOfFirstRowOfMajorTopic = workbook.createCellStyle();
            cellStyleOfFirstRowOfMajorTopic.setFont(font);

            int cellCount = 0;

            Row row = sheet.createRow(rowCount);
            Cell majorTopicCountCell = row.createCell(cellCount++);
            majorTopicCountCell.setCellValue(countDataOfMajorTopic);
            majorTopicCountCell.setCellStyle(cellStyleOfFirstRowOfMajorTopicCount);

            Cell majorTopicCell = row.createCell(cellCount);
            majorTopicCell.setCellValue(materialCategory);
            majorTopicCell.setCellStyle(cellStyleOfFirstRowOfMajorTopic);

            mergeRow(rowCount, rowCount, 1, columnCount);

            rowCount++;

            int materialAllCost = 0; // 소계 - 재료비 금액의 합
            int laborAllCost = 0; // 소계 - 노무비 금액의 합
            int allTotalCost = 0; // 소계 - 합계 금액의 합

            // 소 주제
            for (BusinessMaterial businessMaterial : businessMaterialMap.get(materialCategory)) {

                if (businessMaterial.getAllMaterialCostPerUnit() != null) {
                    materialAllCost += Integer.parseInt(businessMaterial.getAllMaterialCostPerUnit());
                }

                if (businessMaterial.getAllLaborCostPerUnit() != null) {
                    laborAllCost += Integer.parseInt(businessMaterial.getAllLaborCostPerUnit());
                }

                if (businessMaterial.getTotalPrice() != null) {
                    allTotalCost += Integer.parseInt(businessMaterial.getTotalPrice());
                }

                row = sheet.createRow(rowCount);

                int cellCountInner = 2;

                List<ExcelCellInfo> cellInfoList = asExcelDataList(businessMaterial);

                for (ExcelCellInfo cellInfo : cellInfoList) {
                    Cell cellInner = row.createCell(cellCountInner);
                    cellInner.setCellValue(cellInfo.getName());

                    cellCountInner++;
                }

                rowCount++;
            }

            // 소계
            setFooter(row, rowCount, materialAllCost, laborAllCost, allTotalCost);

            rowCount++;

            // 대 주제 카운트 + 1
            countDataOfMajorTopic++;
        }
    }

    private List<ExcelCellInfo> asExcelDataList(final BusinessMaterial businessMaterial) {

        List<ExcelCellInfo> excelCellInfos = new ArrayList<>();

        if (businessMaterial != null) {

            excelCellInfos.add(new ExcelCellInfo(businessMaterial.getCategory(), null, null));
            excelCellInfos.add(new ExcelCellInfo(businessMaterial.getName(), null, null));
            excelCellInfos.add(new ExcelCellInfo(businessMaterial.getAmount().toString(), null, null));
            excelCellInfos.add(new ExcelCellInfo(businessMaterial.getUnit(), null, null));

            excelCellInfos.add(new ExcelCellInfo(
                    businessMaterial.getBusinessMaterialExpense() != null ?
                        businessMaterial.getBusinessMaterialExpense().getMaterialCostPerUnit() : null,
                    null, null));

            excelCellInfos.add(new ExcelCellInfo(businessMaterial.getAllMaterialCostPerUnit(), null, null));

            excelCellInfos.add(new ExcelCellInfo(
                    businessMaterial.getBusinessMaterialExpense() != null ?
                            businessMaterial.getBusinessMaterialExpense().getLaborCostPerUnit() : null,
                    null, null));

            excelCellInfos.add(new ExcelCellInfo(businessMaterial.getAllLaborCostPerUnit(), null, null));
            excelCellInfos.add(new ExcelCellInfo(businessMaterial.getTotalUnitPrice(), null, null));
            excelCellInfos.add(new ExcelCellInfo(businessMaterial.getTotalPrice(), null, null));
            excelCellInfos.add(new ExcelCellInfo(businessMaterial.getMemo(), null, null));
        }

        return excelCellInfos;
    }

    private void setFooter(
            Row row,
            int rowCount,
            final int materialAllCost,
            final int laborAllCost,
            final int allTotalCost
    ) {

        row = sheet.createRow(rowCount);

        CellStyle cellFooterStyle = workbook.createCellStyle();
        cellFooterStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        cellFooterStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellFooterStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);

        Cell footerCell = row.createCell(1);
        footerCell.setCellValue("소계");
        footerCell.setCellStyle(cellFooterStyle);

        mergeRow(rowCount, rowCount, 1, 5);

        CellStyle cellExtraFooterStyle = workbook.createCellStyle();
        cellExtraFooterStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        cellExtraFooterStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellExtraFooterStyle.setAlignment(HorizontalAlignment.LEFT);

        Cell cell6 = row.createCell(6);
        cell6.setCellStyle(cellExtraFooterStyle);

        Cell cell7 = row.createCell(7); // 재료비 - 금액
        cell7.setCellValue(materialAllCost);
        cell7.setCellStyle(cellExtraFooterStyle);

        Cell cell8 = row.createCell(8);
        cell8.setCellStyle(cellExtraFooterStyle);

        Cell cell9 = row.createCell(9); // 노무비 - 금액
        cell9.setCellValue(laborAllCost);
        cell9.setCellStyle(cellExtraFooterStyle);

        Cell cell10 = row.createCell(10);
        cell10.setCellStyle(cellExtraFooterStyle);

        Cell cell11 = row.createCell(11); // 합계 - 금액
        cell11.setCellValue(allTotalCost);
        cell11.setCellStyle(cellExtraFooterStyle);

        Cell cell12 = row.createCell(12);
        cell12.setCellStyle(cellExtraFooterStyle);
    }
}
