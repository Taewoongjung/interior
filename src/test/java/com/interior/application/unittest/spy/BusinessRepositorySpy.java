package com.interior.application.unittest.spy;

import static business.BusinessFixture.getBusinessList;
import static business.material.BusinessMaterialFixture.getBusinessMaterial;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS;
import static com.interior.adapter.common.exception.ErrorType.NOT_EXIST_BUSINESS_MATERIAL;
import static com.interior.util.CheckUtil.check;

import com.interior.adapter.common.exception.ErrorType;
import com.interior.adapter.inbound.business.enumtypes.QueryType;
import com.interior.adapter.outbound.jpa.repository.business.dto.ReviseBusinessMaterial;
import com.interior.domain.business.Business;
import com.interior.domain.business.expense.BusinessMaterialExpense;
import com.interior.domain.business.log.BusinessLog;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.material.log.BusinessMaterialLog;
import com.interior.domain.business.progress.ProgressType;
import com.interior.domain.business.repository.BusinessRepository;
import com.interior.domain.business.repository.dto.CreateBusiness;
import com.interior.domain.business.repository.dto.CreateBusinessMaterial;
import com.interior.domain.util.BoolType;
import java.util.List;
import java.util.NoSuchElementException;

public class BusinessRepositorySpy implements BusinessRepository {

    @Override
    public Business findById(Long businessId) {

        List<Business> businessList = getBusinessList();

        Business business = businessList.stream()
                .filter(f -> businessId.equals(f.getId()) && BoolType.F.equals(f.getIsDeleted()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        business.getBusinessMaterialList().addAll(getBusinessMaterial());

        // 삭제된 재료들 제외
        business.getBusinessMaterialList().removeIf(e -> BoolType.T.equals(e.getIsDeleted()));

        return business;
    }

    @Override
    public List<Business> findBusinessByCompanyId(Long companyId, QueryType queryType) {
        return null;
    }

    @Override
    public Business findBusinessByCompanyIdAndBusinessId(Long companyId,
            Long businessId) {
        return null;
    }

    @Override
    public List<Business> findAllByCompanyIdIn(List<Long> companyIdList) {
        return null;
    }

    @Override
    public Long save(CreateBusiness createBusiness) {
        return null;
    }

    @Override
    public BusinessMaterial save(CreateBusinessMaterial createBusinessMaterial) {

        List<Business> businessList = getBusinessList();

        Business business = businessList.stream()
                .filter(f -> createBusinessMaterial.businessId().equals(f.getId())
                        && BoolType.F.equals(f.getIsDeleted()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        /* save */

        return null;
    }

    @Override
    public boolean deleteBusinessMaterial(Long businessId, Long materialId) {

        List<Business> businessList = getBusinessList();

        Business business = businessList.stream()
                .filter(f -> businessId.equals(f.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        business.getBusinessMaterialList().addAll(getBusinessMaterial());

        check(materialId == null || materialId == 0, ErrorType.INAPPROPRIATE_REQUEST);

        business.getBusinessMaterialList().stream()
                .filter(f -> materialId.equals(f.getId()) && BoolType.F.equals(f.getIsDeleted()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        ErrorType.NOT_EXIST_BUSINESS_MATERIAL.getMessage()));

        return true;
    }

    @Override
    public boolean deleteBusiness(Long companyId, Long businessId) {
        return false;
    }

    @Override
    public boolean reviseBusiness(Long userId, Long businessId,
            String changeBusinessName) {
        return false;
    }

    @Override
    public boolean reviseUsageCategoryOfMaterial(Long businessId, List<Long> targetList,
            String usageCategoryName) {
        return false;
    }

    @Override
    public boolean createBusinessMaterialUpdateLog(BusinessMaterialLog businessMaterialLog) {
        return false;
    }

    @Override
    public BusinessMaterial findBusinessMaterialByMaterialId(Long materialId) {
        return null;
    }

    @Override
    public List<BusinessMaterialLog> findBusinessMaterialLogByBusinessId(Long businessId) {
        return null;
    }

    @Override
    public boolean createBusinessUpdateLog(BusinessLog businessLog) {
        return false;
    }

    @Override
    public boolean reviseBusinessMaterial(Long materialId,
            ReviseBusinessMaterial reviseReq) {

        List<BusinessMaterial> businessMaterialList = getBusinessMaterial();

        BusinessMaterial businessMaterial = businessMaterialList.stream()
                .filter(f -> materialId.equals(f.getId()))
                .findFirst()
                .orElseThrow(
                        () -> new NoSuchElementException(
                                NOT_EXIST_BUSINESS_MATERIAL.getMessage()));

        if (reviseReq.getMaterialName() != null) {
            System.out.println("재료명 수정");
            businessMaterial.setBusinessMaterialName(reviseReq.getMaterialName());
        }

        if (reviseReq.getMaterialCategory() != null) {
            System.out.println("카테고리 수정");
            businessMaterial.setCategory(reviseReq.getMaterialCategory());
        }

        if (reviseReq.getMaterialAmount() != null) {
            System.out.println("수량 수정");
            businessMaterial.setAmount(reviseReq.getMaterialAmount());
        }

        if (reviseReq.getMaterialAmountUnit() != null) {
            System.out.println("단위 수정");
            businessMaterial.setUnit(reviseReq.getMaterialAmountUnit());
        }

        if (reviseReq.getMaterialMemo() != null) {
            System.out.println("메모 수정");
            businessMaterial.setMemo(reviseReq.getMaterialMemo());
        }

        if (businessMaterial.getBusinessMaterialExpense() != null) {
            if (reviseReq.getMaterialCostPerUnit() != null) {
                System.out.println("재료 단가 수정");
                businessMaterial.getBusinessMaterialExpense()
                        .setMaterialCostPerUnit(reviseReq.getMaterialCostPerUnit());
            }

            if (reviseReq.getLaborCostPerUnit() != null) {
                System.out.println("노무비 단가 수정");
                businessMaterial.getBusinessMaterialExpense()
                        .setLaborCostPerUnit(reviseReq.getLaborCostPerUnit());
            }

        } else { // 기존에 비용 정보가 없었던 재료는 새로 생성

            String materialCostPerUnit = null;
            String laborCostPerUnit = null;

            if (reviseReq.getMaterialCostPerUnit() != null) {
                System.out.println("재료 단가 수정");
                materialCostPerUnit = reviseReq.getMaterialCostPerUnit();
            }

            if (reviseReq.getLaborCostPerUnit() != null) {
                System.out.println("노무비 단가 수정");
                laborCostPerUnit = reviseReq.getLaborCostPerUnit();
            }

            businessMaterial.setBusinessMaterialExpense(
                    BusinessMaterialExpense.of(
                            materialId,
                            materialCostPerUnit,
                            laborCostPerUnit
                    )
            );
        }

        return true;
    }

    @Override
    public Business updateBusinessProgress(Long businessId, ProgressType progressType) {
        return null;
    }
}
