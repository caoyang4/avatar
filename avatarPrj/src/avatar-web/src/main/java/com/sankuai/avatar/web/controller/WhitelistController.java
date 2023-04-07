package com.sankuai.avatar.web.controller;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sankuai.avatar.capacity.calculator.CalculatorResult;
import com.sankuai.avatar.capacity.service.ICapacityCollectService;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.whitelist.constant.WhiteType;
import com.sankuai.avatar.sdk.entity.servicecatalog.AppKey;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import com.sankuai.avatar.web.dto.whitelist.OwtSetWhitelistDTO;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.request.OwtSetWhitelistPageRequest;
import com.sankuai.avatar.web.request.WhitelistPageRequest;
import com.sankuai.avatar.web.service.OwtSetWhitelistService;
import com.sankuai.avatar.web.service.WhitelistService;
import com.sankuai.avatar.web.transfer.whitelist.OwtSetWhitelistVOTransfer;
import com.sankuai.avatar.web.transfer.whitelist.ServiceWhitelistVOTransfer;
import com.sankuai.avatar.web.transfer.whitelist.WhitelistAppVOTransfer;
import com.sankuai.avatar.web.util.UserUtils;
import com.sankuai.avatar.web.vo.whitelist.CancelWhiteParamVO;
import com.sankuai.avatar.web.vo.whitelist.OwtSetWhitelistVO;
import com.sankuai.avatar.web.vo.whitelist.ServiceWhitelistVO;
import com.sankuai.avatar.web.vo.whitelist.WhitelistAppVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author chenxinli
 * 
 */
@Validated
@RestController
@RequestMapping("/api/v2/avatar/whitelist")
public class WhitelistController {


    private final ServiceCatalogAppKey serviceCatalogAppKey;
    private final WhitelistService whitelistService;
    private final OwtSetWhitelistService owtSetWhitelistService;
    private final ICapacityCollectService capacityCollect;

    @Autowired
    public WhitelistController(ServiceCatalogAppKey serviceCatalogAppKey,
                               WhitelistService whitelistService,
                               OwtSetWhitelistService owtSetWhitelistService,
                               ICapacityCollectService capacityCollect) {
        this.serviceCatalogAppKey = serviceCatalogAppKey;
        this.whitelistService = whitelistService;
        this.owtSetWhitelistService = owtSetWhitelistService;
        this.capacityCollect = capacityCollect;
    }

    @GetMapping("")
    public PageResponse<ServiceWhitelistVO> getPageServiceWhitelist(@Valid WhitelistPageRequest pageRequest){
        PageResponse<ServiceWhitelistDTO> dtoPageResponse = whitelistService.queryPage(pageRequest);
        return toPageServiceWhitelistVO(dtoPageResponse);
    }


    @GetMapping("/{appkey}")
    public List<ServiceWhitelistVO> getAppkeyValidWhitelist(@PathVariable("appkey") String appkey){
        return ServiceWhitelistVOTransfer.INSTANCE.toVOList(whitelistService.getServiceWhitelistByAppkey(appkey));
    }

    @DeleteMapping("/{type}/{appkey}")
    public Integer deleteAppkeyWhitelist(@PathVariable("type") String app, @PathVariable("appkey") String appkey){
        WhiteType type = Arrays.stream(WhiteType.values())
                .filter(whiteType -> Objects.equals(app, whiteType.getWhiteType())).findFirst().orElse(null);
        if (Objects.isNull(type)) {
            throw new SupportErrorException("白名单类型不正确，请确认");
        }
        return whitelistService.deleteWhitelistByAppkeyAndType(appkey, type) ? 1: -1;
    }

    @GetMapping("/apps")
    public List<WhitelistAppVO> getAllWhitelistType(){
        return WhitelistAppVOTransfer.INSTANCE.toVOList(whitelistService.getAllWhitelistType());
    }

    @PostMapping("")
    public Integer saveServiceWhitelist(@Valid @RequestBody ServiceWhitelistVO serviceWhitelistVO) throws Exception {
        if (StringUtils.isEmpty(serviceWhitelistVO.getApplication()) || StringUtils.isEmpty(serviceWhitelistVO.getOrgIds())) {
            // TODO serviceCatalogAppKey待替换
            AppKey scAppKey = serviceCatalogAppKey.getAppKey(serviceWhitelistVO.getAppkey());
            serviceWhitelistVO.setApplication(scAppKey.getApplicationName().toLowerCase());
            serviceWhitelistVO.setOrgIds(scAppKey.getOrgIds());
        }
        boolean success = whitelistService.saveServiceWhitelist(ServiceWhitelistVOTransfer.INSTANCE.toDTO(serviceWhitelistVO));
        return success ? 1 : -1;
    }

    @PostMapping("/owt")
    public Integer saveOwtWhitelist(@Valid @RequestBody OwtSetWhitelistVO owtSetWhitelistVO){
        boolean success = owtSetWhitelistService.saveOwtSetWhitelist(
                OwtSetWhitelistVOTransfer.INSTANCE.toDTO(owtSetWhitelistVO));
        return success ? 1 : -1;
    }

    @GetMapping("/owt")
    public PageResponse<OwtSetWhitelistVO> getPageOwtSetWhitelist(OwtSetWhitelistPageRequest pageRequest){
        PageResponse<OwtSetWhitelistVO> pageResponse = new PageResponse<>();
        PageResponse<OwtSetWhitelistDTO> dtoPageResponse = owtSetWhitelistService.queryPage(pageRequest);
        pageResponse.setPage(dtoPageResponse.getPage());
        pageResponse.setPageSize(dtoPageResponse.getPageSize());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setItems(OwtSetWhitelistVOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    @DeleteMapping("/owt/{id}")
    public Integer deleteOwtWhitelistByPk(@PathVariable("id") Integer id) {
        return owtSetWhitelistService.deletetOwtSetWhitelistByPk(id) ? 1 : -1;
    }

    @DeleteMapping("/{id}")
    public Integer deleteServiceWhitelistByPk(@PathVariable("id") Integer id) {
        return whitelistService.deleteWhitelistByPk(id) ? 1 : -1;
    }

    @GetMapping("/{application}/appkey")
    public PageResponse<ServiceWhitelistVO> getApplicationOrgWhitelist(@PathVariable("application") String application,
                                                                       WhitelistPageRequest pageRequest)  {
        final String comma = ",";
        // 过滤参数首尾去空格
        application = application.trim();
        if (StringUtils.isNotEmpty(pageRequest.getQuery())) {
            pageRequest.setQuery(pageRequest.getQuery().trim());
        }
        // 按照部门查
        if (application.split(comma).length > 1 || application.matches("[0-9]+")) {
            pageRequest.setOrgIds(application);
            pageRequest.setApplication(null);
        }
        // 按照应用查询
        else {
            pageRequest.setApplication(application.toLowerCase());
        }
        PageResponse<ServiceWhitelistDTO> dtoPageResponse = whitelistService.queryPage(pageRequest);
        return toPageServiceWhitelistVO(dtoPageResponse);
    }

    private PageResponse<ServiceWhitelistVO> toPageServiceWhitelistVO(PageResponse<ServiceWhitelistDTO> dtoPageResponse){
        PageResponse<ServiceWhitelistVO> pageResponse = new PageResponse<>();
        pageResponse.setPage(dtoPageResponse.getPage());
        pageResponse.setPageSize(dtoPageResponse.getPageSize());
        pageResponse.setTotalPage(dtoPageResponse.getTotalPage());
        pageResponse.setTotalCount(dtoPageResponse.getTotalCount());
        pageResponse.setItems(ServiceWhitelistVOTransfer.INSTANCE.toVOList(dtoPageResponse.getItems()));
        return pageResponse;
    }

    @PostMapping("/cancel")
    public Boolean cancelWhite(@Valid @RequestBody CancelWhiteParamVO param){
        final String user = StringUtils.isEmpty(param.getUser()) ? param.getUser() : UserUtils.getCurrentCasUser().getLoginName();
        String appkey = param.getAppkey();
        List<String> cellNames = param.getCellNames();
        WhiteType type = Arrays.stream(WhiteType.values())
                .filter(whiteType -> Objects.equals(param.getApp(), whiteType.getWhiteType())).findFirst().orElse(null);
        if (Objects.isNull(type)) {
            throw new SupportErrorException("白名单类型不正确，请确认");
        }
        List<ServiceWhitelistDTO> whitelistDTOList = whitelistService.getServiceWhitelistByAppkey(appkey, type);
        if (CollectionUtils.isEmpty(whitelistDTOList)) {
            return false;
        }
        Boolean cancel = whitelistService.cancelWhitelist(appkey, type, cellNames);
        // 异步发起容灾计算，及发送通知
        if (WhiteType.CAPACITY.equals(type) || WhiteType.AUTO_MIGRATE.equals(type)) {
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("cancel-whitelist-pool").build();
            ExecutorService singleThreadPool = new ThreadPoolExecutor(
                    1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
            singleThreadPool.execute(() -> {
                List<CalculatorResult> calculateResult = capacityCollect.getCalculateResult(appkey);
                capacityCollect.dealCalculateResult(calculateResult);
                // 发送通知
                if (cancel) {
                    whitelistService.sendCancelWhiteNotice(user, appkey, type, cellNames, whitelistDTOList.get(0));
                }
            });
            singleThreadPool.shutdown();
        }
        return cancel;
    }

}
