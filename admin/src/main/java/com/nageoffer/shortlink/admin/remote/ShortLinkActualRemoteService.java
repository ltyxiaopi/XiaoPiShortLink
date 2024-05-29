package com.nageoffer.shortlink.admin.remote;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.remote.dto.req.*;
import com.nageoffer.shortlink.admin.remote.dto.resp.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 短链接中台远程调用服务
 */
@FeignClient(value = "short-link-project", url = "${aggregation.remote-url:}")
public interface ShortLinkActualRemoteService {

    /**
     * 创建短链接
     * @param requestParam 创建短链接请求参数
     * @return 创建短链接响应
     */

    @PostMapping("/api/short-link/project/v1/create")
    Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam);

    /**
     * 修改短链接
     * @param requestParam 修改短链接请求参数
     */
    @PostMapping("/api/short-link/project/v1/update")
    void updateShortLink(ShortLinkUpdateReqDTO requestParam);

    /**
     * 分页查询短链接
     * @param gid 分组标识请求参数
     * @param orderFlag 排序类型请求参数
     * @param current 当前页请求参数
     * @param size 返回记录条数请求参数
     * @return 分页查询短链接响应
     */

    @GetMapping("/api/short-link/project/v1/page")
    Result<Page<ShortLinkPageRespDTO>> pageShortLink(@RequestParam("gid") String gid,
                                                     @RequestParam("orderTag")String orderFlag,
                                                     @RequestParam("current") Long current,
                                                     @RequestParam("size")Long size);


    /**
     * 查询分组短链接总量
     * @param requestParam 查询分组短链接总量请求参数
     * @return 查询分组短链接总量响应
     */
    @GetMapping("/api/short-link/project/v1/count")
    Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("requestParam") List<String> requestParam);

    /**
     * 根据 url 获取标题
     * @param url 目标网站地址
     * @return 网站标题
     */
    @GetMapping("/api/short-link/project/v1/title")
    Result<String> getTitleByUrl(@RequestParam("url") String url);

    /**
     * 保存短链接进回收站
     *
     * @param requestParam 保存短链接请求参数
     */
    @PostMapping("/api/short-link/project/v1/recycle-bin/save")
    void saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam);

    /**
     * 分页查询回收站短链接
     * @param gid 分组标识请求参数
     * @param orderFlag 排序类型请求参数
     * @param current 当前页请求参数
     * @param size 返回记录条数请求参数
     * @return 分页查询回收站短链接相应
     */
    @GetMapping("/api/short-link/project/v1/recycle-bin/page")
    Result<Page<ShortLinkPageRespDTO>> pageRecycleBinShortLink(@RequestParam("gid") String gid,
                                                                       @RequestParam("orderTag")String orderFlag,
                                                                       @RequestParam("current") Long current,
                                                                       @RequestParam("size")Long size);

    /**
     * 恢复短链接
     *
     * @param requestParam 恢复短链接请求参数
     */
    @PostMapping("/api/short-link/project/v1/recycle-bin/recover")
    void recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO requestParam);

    /**
     * 删除短链接
     *
     * @param requestParam 删除短链接请求参数
     */
    @PostMapping("/api/short-link/project/v1/recycle-bin/remove")
    void removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO requestParam);

    /**
     * 访问单个短链接指定时间内监控数据
     * @param fullShortUrl 完整短链接参数
     * @param gid 分组标识参数
     * @param startDate 开始日期参数
     * @param endDate 结束日期参数
     * @return 访问单个短链接指定时间内监控数据响应
     */
    @GetMapping("/api/short-link/project/v1/stats")
    Result<ShortLinkStatsRespDTO> oneShortLinkStats(@RequestParam("fullShortUrl") String fullShortUrl,
                                                    @RequestParam("gid")String gid,
                                                    @RequestParam("startDate")String startDate,
                                                    @RequestParam("endDate")String endDate);

    /**
     * 访问单个短链接指定时间内监控访问记录数据
     * @param fullShortUrl 完整短链接参数
     * @param gid 分组标识参数
     * @param startDate 开始日期参数
     * @param endDate 结束日期参数
     * @return 短链接监控访问记录信息响应
     */
    @GetMapping("/api/short-link/project/v1/stats/access-record")
    Result<Page<ShortLinkStatsAccessRecordRespDTO>> shortLinkStatsAccessRecord(@RequestParam("fullShortUrl") String fullShortUrl,
                                                                                @RequestParam("gid")String gid,
                                                                                @RequestParam("startDate")String startDate,
                                                                                @RequestParam("endDate")String endDate);

    /**
     * 访问分组短链接指定时间内监控数据
     * @param gid 分组标识参数
     * @param startDate 开始日期参数
     * @param endDate 结束日期参数
     * @return 分组短链接监控信息
     */
    @GetMapping("/api/short-link/project/v1/stats/group")
    Result<ShortLinkStatsRespDTO> groupShortLinkStats(@RequestParam("gid")String gid,
                                                      @RequestParam("startDate")String startDate,
                                                      @RequestParam("endDate")String endDate);

    /**
     * 访问分组短链接指定时间内监控访问记录数据
     * @param gid 分组标识参数
     * @param startDate 开始日期参数
     * @param endDate 结束日期参数
     * @return 分组短链接监控访问记录信息
     */
    @GetMapping("/api/short-link/project/v1/stats/access-record/group")
    Result<Page<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsAccessRecord(@RequestParam("gid")String gid,
                                                                                     @RequestParam("startDate")String startDate,
                                                                                     @RequestParam("endDate")String endDate);
}
