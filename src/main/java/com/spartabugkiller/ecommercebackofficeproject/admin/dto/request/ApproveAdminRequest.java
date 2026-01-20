package com.spartabugkiller.ecommercebackofficeproject.admin.dto.request;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import lombok.Getter;

@Getter
public class ApproveAdminRequest {

    private AdminStatus status;
    private String rejectedReason;
}
