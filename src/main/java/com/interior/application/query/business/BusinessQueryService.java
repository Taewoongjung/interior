package com.interior.application.query.business;

import static java.util.stream.Collectors.groupingBy;

import com.interior.adapter.inbound.business.webdto.GetBusiness;
import com.interior.adapter.outbound.excel.BusinessListExcel;
import com.interior.adapter.outbound.excel.BusinessMaterialExcelDownload;
import com.interior.adapter.outbound.jpa.querydsl.BusinessDao;
import com.interior.domain.business.Business;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.repository.BusinessRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessQueryService {

    private final BusinessDao businessDao;
    private final BusinessRepository businessRepository;

    @Transactional(readOnly = true)
    public GetBusiness.Response getBusiness(final Long businessId) {

        Business business = businessRepository.findById(businessId);

        int count = 0;

        HashMap<String, List<BusinessMaterial>> businessMaterials = new HashMap<>();

        if (business.getBusinessMaterialList() != null) {

            businessMaterials =
                    (HashMap<String, List<BusinessMaterial>>)
                            business.getBusinessMaterialList().stream()
                                    .collect(groupingBy(BusinessMaterial::getUsageCategory));

            count = business.getBusinessMaterialList().size();
        }

        return new GetBusiness.Response(business.getName(), businessMaterials, count);
    }

    @Transactional(readOnly = true)
    public List<Business> getAllBusinessesByUser(final List<Long> companyIdList) {
        return businessRepository.findAllByCompanyIdIn(companyIdList);
    }

    @Transactional(readOnly = true)
    public List<Business> getBusinessesByCompanyId(final Long companyId) {
        return businessRepository.findBusinessByCompanyId(companyId);
    }

    @Transactional(readOnly = true)
    public Business getBusinessByCompanyIdAndBusinessId(final Long companyId,
            final Long businessId) {

        return businessRepository.findBusinessByCompanyIdAndBusinessId(companyId, businessId);
    }

    @Transactional(readOnly = true)
    public void getExcelOfBusinessMaterialList(
            final Long companyId,
            final Long businessId,
            HttpServletResponse response
    ) {

        BusinessListExcel businessListExcel = null;

        Business business = getBusinessByCompanyIdAndBusinessId(companyId, businessId);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + URLEncoder.encode("재료 리스트.xlsx", "UTF-8") + "\"");
            response.setContentType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            businessListExcel = BusinessListExcel.of(workbook);

            // 데이터 세팅
            businessListExcel.setData(business);

            ServletOutputStream outputStream = response.getOutputStream();
            businessListExcel.getWorkbook().write(outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<BusinessMaterialExcelDownload> getExcelOfBusinessMaterialListV2(
            final Long companyId) {

        return businessDao.getBusinessMaterialList(companyId);
    }
}