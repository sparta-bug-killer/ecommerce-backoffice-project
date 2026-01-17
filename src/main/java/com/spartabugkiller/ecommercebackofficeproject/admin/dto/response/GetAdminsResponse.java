package com.spartabugkiller.ecommercebackofficeproject.admin.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.global.dto.PageInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAdminsResponse {

    private final List<AdminItems> items;
    private final PageInfo pageInfo;

    public static GetAdminsResponse from(List<AdminItems> items, PageInfo pageInfo) {
        return GetAdminsResponse.builder()
                .items(items)
                .pageInfo(pageInfo)
                .build();
    }
}
