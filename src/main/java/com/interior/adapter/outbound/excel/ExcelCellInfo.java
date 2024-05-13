package com.interior.adapter.outbound.excel;

import lombok.Getter;
import org.apache.poi.ss.usermodel.CellStyle;

@Getter
public class ExcelCellInfo {

    private String name;
    private Integer width;
    private CellStyle style;

    public ExcelCellInfo(final String name, final Integer width, final CellStyle style) {
        this.name = name;
        this.width = width;
        this.style = style;
    }
}