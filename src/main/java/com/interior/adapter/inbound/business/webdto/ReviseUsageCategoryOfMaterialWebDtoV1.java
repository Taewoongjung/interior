package com.interior.adapter.inbound.business.webdto;

import java.util.List;

public class ReviseUsageCategoryOfMaterialWebDtoV1 {

    public record Req(List<Long> subDataIds, String usageCategoryName) {

    }

    public record Res(String changedUsageCategoryName) {

    }
}
