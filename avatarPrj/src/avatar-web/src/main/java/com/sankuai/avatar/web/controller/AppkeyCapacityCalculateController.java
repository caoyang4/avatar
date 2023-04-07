package com.sankuai.avatar.web.controller;

import com.meituan.mdp.boot.starter.threadpool.NamedRunnableTask;
import com.sankuai.avatar.capacity.calculator.CalculatorResult;
import com.sankuai.avatar.capacity.service.ICapacityCollectService;
import com.sankuai.avatar.web.constant.ThreadPoolConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2022-11-08 10:46
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/api/v2/avatar/capacity/calculate")
public class AppkeyCapacityCalculateController {

    private final ICapacityCollectService capacityCollect;

    @Autowired
    public AppkeyCapacityCalculateController(ICapacityCollectService capacityCollect) {
        this.capacityCollect = capacityCollect;
    }

    @GetMapping("/{appkey}")
    public List<CalculatorResult> getAppKeyCapacityResult(@PathVariable("appkey") String appKey) {
        return capacityCollect.getCalculateResult(appKey);
    }

    @PostMapping("/trigger")
    public void capacityDataToMafka(@RequestBody @Valid @Size(min = 1, max = 100, message = "批量上报每次至少1条，至多100条，请知悉！") List<String> appKeyList) {
        for (String appkey : appKeyList) {
            ThreadPoolConstant.WEB_EXECUTOR.submit(new NamedRunnableTask("trigger", () -> {
                List<CalculatorResult> calculateResult = capacityCollect.getCalculateResult(appkey);
                capacityCollect.dealCalculateResult(calculateResult);
            }));
        }
    }

    @RequestMapping("/pre")
    public com.sankuai.avatar.capacity.dto.CapacityDTO preCapacityDataToMafka(@RequestParam(value = "appkey", required = true) String appKey,
                                                                              @RequestParam(value = "set", defaultValue = "") String set) {
        List<CalculatorResult> calculateResult = capacityCollect.getCalculateResult(appKey);
        List<CalculatorResult> result = calculateResult.stream().filter(c -> c.getAppKeyNode().getSetName().getSetName().equals(set)).collect(Collectors.toList());
        return capacityCollect.getUtilOptimizeDTO(result.get(0));
    }
}
