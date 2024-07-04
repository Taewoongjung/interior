package com.interior.application.readmodel.business.queries;

import com.interior.abstraction.domain.IQuery;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import lombok.Getter;

@Getter
public class GetExcelOfBusinessMaterialListQuery implements IQuery {

    private final Long companyId;
    private final Long businessId;
    private final String taskId;
    private final HttpServletResponse response;

    public GetExcelOfBusinessMaterialListQuery(
            final Long companyId,
            final Long businessId,
            final String taskId,
            HttpServletResponse response
    ) {
        this.companyId = companyId;
        this.businessId = businessId;
        this.taskId = taskId;
        this.response = response;
    }

    public void setResponseHeader() throws UnsupportedEncodingException {
        this.response.setHeader("Content-Disposition",
                "attachment; filename=\"" + URLEncoder.encode("재료 리스트.xlsx", "UTF-8") + "\"");
        this.response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
}
