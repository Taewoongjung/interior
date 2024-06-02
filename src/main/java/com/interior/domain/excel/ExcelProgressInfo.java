package com.interior.domain.excel;

import java.util.Map;
import lombok.Getter;

@Getter
public class ExcelProgressInfo {

    private int totalCount;

    private int completeCount;

    private ExcelProgressInfo(final int totalCount, final int completeCount) {
        this.totalCount = totalCount;
        this.completeCount = completeCount;
    }

    public static ExcelProgressInfo of(final Map<String, String> progressInfo) {

        return new ExcelProgressInfo(
                Integer.parseInt(progressInfo.get("totalCount")),
                Integer.parseInt(progressInfo.get("completeCount"))
        );
    }
}
