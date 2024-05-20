package com.interior.application.businesss;

import static java.util.stream.Collectors.groupingBy;

import com.interior.adapter.inbound.business.webdto.CreateBusiness.CreateBusinessReqDto;
import com.interior.adapter.inbound.business.webdto.GetBusiness;
import com.interior.adapter.outbound.excel.BusinessListExcel;
import com.interior.adapter.outbound.excel.BusinessMaterialExcelDownload;
import com.interior.adapter.outbound.jpa.querydsl.BusinessDao;
import com.interior.application.businesss.dto.CreateBusinessServiceDto.CreateBusinessMaterialDto;
import com.interior.application.businesss.dto.ReviseBusinessServiceDto;
import com.interior.domain.business.Business;
import com.interior.domain.business.log.BusinessMaterialChangeFieldType;
import com.interior.domain.business.log.BusinessMaterialLog;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;
import com.interior.domain.company.Company;
import com.interior.domain.company.repository.CompanyRepository;
import com.interior.domain.user.User;
import com.interior.domain.user.repository.UserRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
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
public class BusinessService {

    private final BusinessDao businessDao;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
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

    @Transactional
    public Long createBusiness(final Long companyId, final CreateBusinessReqDto req) {

        return businessRepository.save(new CreateBusiness(
                        req.businessName(),
                        companyId,
                        null,
                        "진행중"
                )
        );
    }

    @Transactional
    public boolean createBusinessMaterial(final Long businessId,
            final CreateBusinessMaterialDto req) {

        businessRepository.save(new CreateBusinessMaterial(
                businessId,
                req.name(),
                req.usageCategory(),
                req.category(),
                req.amount(),
                req.unit(),
                req.memo(),
                req.materialCostPerUnit(),
                req.laborCostPerUnit()
        ));

        return true;
    }

    @Transactional
    public boolean deleteBusinessMaterial(final Long businessId, final Long materialId) {

        return businessRepository.deleteBusinessMaterial(businessId, materialId);
    }

    @Transactional
    public boolean deleteBusiness(final Long companyId, final Long businessId) {

        return businessRepository.deleteBusiness(companyId, businessId);
    }

    @Transactional
    public boolean reviseBusiness(
            final Long companyId,
            final Long businessId,
            final ReviseBusinessServiceDto.Req req
    ) {

        Company company = companyRepository.findById(companyId);

        company.validateDuplicateName(req.changeBusinessName());

        return businessRepository.reviseBusiness(companyId, businessId, req);
    }

    @Transactional
    public boolean reviseUsageCategoryOfMaterial(
            final Long businessId,
            final List<Long> targetList,
            final String usageCategoryName
    ) {

        return businessRepository.reviseUsageCategoryOfMaterial(
                businessId, targetList, usageCategoryName);
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


    // 재료 업데이트 시 로그 생성
    public boolean createLogOfChangeMaterials(
            final Long businessMaterialId,
            final String changeField,
            final String beforeData,
            final String afterData,
            final Long updaterId
    ) {

        User user = userRepository.findById(updaterId);

        return businessRepository.createMaterialUpdateLog(
                BusinessMaterialLog.of(
                        businessMaterialId,
                        BusinessMaterialChangeFieldType.from(changeField),
                        beforeData,
                        afterData,
                        updaterId,
                        user.getName(),
                        LocalDateTime.now()
                )
        );
    }
}
