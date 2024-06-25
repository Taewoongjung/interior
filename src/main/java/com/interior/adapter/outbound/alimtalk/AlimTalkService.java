package com.interior.adapter.outbound.alimtalk;

import com.interior.adapter.outbound.alimtalk.dto.SendAlimTalk;

public interface AlimTalkService {

    void send(final SendAlimTalk sendReq);
}
