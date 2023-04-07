package com.sankuai.avatar.web.controller;

import com.sankuai.avatar.web.service.PlusService;
import com.sankuai.avatar.web.transfer.PlusResourceTransfer;
import com.sankuai.avatar.web.vo.appkey.PlusAppliedReleaseVO;
import com.sankuai.avatar.web.vo.appkey.PlusBindReleaseVO;
import com.sankuai.avatar.web.vo.appkey.PlusReleaseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 服务发布项
 *
 * @author qinwei05
 * @date 2022/10/19
 */
@RestController
@RequestMapping("/api/v2/avatar/plus")
public class PlusController {
    @Autowired
    private PlusService plusService;

    @GetMapping("/{appkey}/releases")
    public PlusReleaseVO getAppkeyPlusRelease(@PathVariable("appkey") String appkey){
        String realAppkey = appkey.trim();
        if (StringUtils.isBlank(realAppkey)){
            return defaultBlockHandler();
        }
        // 绑定发布项仅支持删除（需数目大于1），不支持编辑
        List<PlusBindReleaseVO> plusBindReleaseVOList = PlusResourceTransfer.INSTANCE
                .toBindVOList(plusService.getBindPlusByAppkey(realAppkey));
        String plusLinkTemplate = "https://plus.mws.sankuai.com/#/release/job/list?releaseId=%s";
        String plusEditLinkTemplate = "https://plus.mws.sankuai.com/#/release/setting/base?releaseId=%s";
        if (!plusBindReleaseVOList.isEmpty()){
            int size = plusBindReleaseVOList.size();
            plusBindReleaseVOList.forEach(plusBindReleaseVO -> {
                plusBindReleaseVO.setIsEdit(Boolean.FALSE);
                plusBindReleaseVO.setIsDel(size > 1 ? Boolean.TRUE : Boolean.FALSE);
                plusBindReleaseVO.setPlusUrl(String.format(plusLinkTemplate, plusBindReleaseVO.getId()));
                plusBindReleaseVO.setPlusEditUrl(String.format(plusEditLinkTemplate, plusBindReleaseVO.getId()));
                plusBindReleaseVO.setCodeUrl(generateCodeUrl(plusBindReleaseVO.getRepository()));
            });
        }
        // 应用发布项都可以编辑和删除
        List<PlusAppliedReleaseVO> plusAppliedReleaseVOList = PlusResourceTransfer.INSTANCE
                .toAppliedVOList(plusService.getAppliedPlusByAppkey(realAppkey));
        if (!plusAppliedReleaseVOList.isEmpty()) {
            plusAppliedReleaseVOList.forEach(plusAppliedReleaseVO -> {
                plusAppliedReleaseVO.setIsEdit(Boolean.TRUE);
                plusAppliedReleaseVO.setIsDel(Boolean.TRUE);
                plusAppliedReleaseVO.setPlusUrl(String.format(plusLinkTemplate, plusAppliedReleaseVO.getId()));
                plusAppliedReleaseVO.setPlusEditUrl(String.format(plusEditLinkTemplate, plusAppliedReleaseVO.getId()));
                plusAppliedReleaseVO.setCodeUrl(generateCodeUrl(plusAppliedReleaseVO.getRepository()));
            });
        }
        return PlusReleaseVO.builder()
                .bindPlus(plusBindReleaseVOList)
                .appliedPlus(plusAppliedReleaseVOList)
                .build();
    }

    private String generateCodeUrl(String repository) {
        String codeLinkTemplate = "http://git.sankuai.com/projects/%s/repos/%s/browse";
        final String splitFlag = "/";
        final String delStr = ".git";
        List<String> repositoryStringList = Arrays.asList(repository.split(splitFlag));
        if (repositoryStringList.size() < 5) {
            return "https://dev.sankuai.com/code/home?codeArea=sh";
        }
        String codeName = repositoryStringList.get(repositoryStringList.size() - 1);
        if (codeName.endsWith(delStr)) {
            codeName = codeName.substring(0, codeName.length() - delStr.length());
        }
        String groupName = repositoryStringList.get(repositoryStringList.size() - 2);
        return String.format(codeLinkTemplate, groupName, codeName);
    }

    /**
     * /{appkey}/releases接口降级方法
     * <pre>
     * 1、降级方法级别为public
     * 2、降级方法中不能抛出异常，否则会被Rhino捕获，从而再次抛出未经包装的异常
     * 3、降级方法返回值应与被降级方法保持一致，否则会报类型转换错误
     * 4、降级方法应与被降级方法在同一个类文件中，否则无法识别
     * 5、没有降级方法，接口返回异常如下："没有配置限流方法，或者限流方法参数不匹配"
     * {
     *     "code": 500,
     *     "message": "没有配置限流方法，或者限流方法参数不匹配",
     *     "data": "没有配置限流方法，或者限流方法参数不匹配"
     * }
     * <pre>
     * @return {@link PlusReleaseVO}
     */
    public static PlusReleaseVO defaultBlockHandler() {
        return PlusReleaseVO.builder()
                .bindPlus(new ArrayList<>())
                .appliedPlus(new ArrayList<>())
                .build();
    }
}
