package com.interior.helper.spy;

import static business.BusinessFixture.getBusinessList;
import static business.log.BusinessMaterialLogFixture.getBusinessMaterialLogList;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

        // queryType 이 "사업관리" 면 연관 객체들도 함께 return
        if ("사업관리".equals(queryType.getType())) {
            List<Business> businessWithRealationsList = getBusinessList();

            List<Business> businessesRelatedToCompanyId = businessWithRealationsList.stream()
                    .filter(f -> companyId.equals(f.getCompanyId()))
                    .toList();

            return businessesRelatedToCompanyId.stream()
                    .filter(f -> f.getIsDeleted() == BoolType.F)
                    .collect(Collectors.toList());
        }

        // 연관 객체들 null 로 return
        List<Business> businessWithOutRealationsList = new ArrayList<>();

        Business B_1 = Business.of(
                1L, "사업 현장 1", 17L, 519L, BoolType.F,
                "01000", "서울 강서구 강서로 375", "101동 202호", "1150010400114530001010977",
                LocalDateTime.of(2024, 7, 1, 2, 3),
                LocalDateTime.of(2024, 7, 1, 2, 3),
                null, null);

        Business B_2 = Business.of(
                2L, "사업 현장 2", 23L, 519L, BoolType.F,
                "01000", "부산 해운대구 APEC로 21", "401동 1202호", "2635010500115130000000002",
                LocalDateTime.of(2024, 7, 1, 2, 3),
                LocalDateTime.of(2024, 7, 1, 2, 3),
                null, null);

        Business B_2_1 = Business.of(
                21L, "사업 현장 2", 23L, 519L, BoolType.F,
                "01000", "부산 해운대구 APEC로 21", "401동 1202호", "2635010500115130000000002",
                LocalDateTime.of(2024, 7, 1, 2, 3),
                LocalDateTime.of(2024, 7, 1, 2, 3),
                null, null);

        Business B_3 = Business.of(
                3L, "사업 현장 2", 56L, 519L, BoolType.T,
                "01000", "경기 성남시 분당구 판교대장로 7", "101동 1403호", "1171010900106580001000001",
                LocalDateTime.of(2024, 7, 1, 2, 3),
                LocalDateTime.of(2024, 7, 1, 2, 3),
                null, null);

        businessWithOutRealationsList.add(B_1);
        businessWithOutRealationsList.add(B_2);
        businessWithOutRealationsList.add(B_2_1);
        businessWithOutRealationsList.add(B_3);

        return businessWithOutRealationsList.stream()
                .filter(f -> companyId.equals(f.getCompanyId()))
                .filter(f -> f.getIsDeleted() == BoolType.F)
                .collect(Collectors.toList());
    }

    @Override
    public Business findBusinessByCompanyIdAndBusinessId(Long companyId,
            Long businessId) {

        List<Business> businessList = getBusinessList();

        Business business = businessList.stream()
                .filter(f ->
                        businessId.equals(f.getId()) &&
                                companyId.equals(f.getCompanyId()) &&
                                BoolType.F.equals(f.getIsDeleted())
                )
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        business.getBusinessMaterialList().addAll(getBusinessMaterial());
        
        return business;
    }

    @Override
    public List<Business> findAllByCompanyIdIn(List<Long> companyIdList) {

        List<Business> businessList = getBusinessList();

        return businessList.stream()
                .filter(f -> f.getIsDeleted() == BoolType.F)
                .filter(f -> companyIdList.contains(f.getCompanyId()))
                .collect(Collectors.toList());
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

        List<Business> businessList = getBusinessList();

        Business business = businessList.stream()
                .filter(f -> businessId.equals(f.getId()) && BoolType.F.equals(f.getIsDeleted()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        business.getBusinessMaterialList().addAll(getBusinessMaterial());

        business.getBusinessMaterialList().stream()
                .filter(f -> targetList.contains(f.getId()))
                .forEach(e -> e.setUsageCategory(usageCategoryName));

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

        List<BusinessMaterialLog> list = getBusinessMaterialLogList();

        return list.stream().filter(f -> businessId.equals(f.getBusinessId()))
                .collect(Collectors.toList());
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

        List<Business> businessList = getBusinessList();

        Business business = businessList.stream()
                .filter(f -> businessId.equals(f.getId()) && BoolType.F.equals(f.getIsDeleted()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NOT_EXIST_BUSINESS.getMessage()));

        business.updateBusinessProgress(progressType);

        return business;
    }
}
