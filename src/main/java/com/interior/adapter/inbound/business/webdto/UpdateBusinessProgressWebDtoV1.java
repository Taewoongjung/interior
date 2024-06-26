package com.interior.adapter.inbound.business.webdto;

import com.interior.domain.business.progress.ProgressType;

public class UpdateBusinessProgressWebDtoV1 {

    public record Req(ProgressType progressType) {

    }

}
