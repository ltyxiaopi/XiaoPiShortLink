package com.nageoffer.shortlink.project.dto.req;

import lombok.Data;

/**
 * 回收站恢复对象
 */
@Data
public class RecycleBinRecoverReqDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 全部短链接
     */
    private String fullShortUrl;
}
