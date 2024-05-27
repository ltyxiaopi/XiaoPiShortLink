package com.nageoffer.shortlink.admin.remote.dto.req;

import lombok.Data;

/**
 * 回收站删除对象
 */
@Data
public class RecycleBinRemoveReqDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 全部短链接
     */
    private String fullShortUrl;
}
