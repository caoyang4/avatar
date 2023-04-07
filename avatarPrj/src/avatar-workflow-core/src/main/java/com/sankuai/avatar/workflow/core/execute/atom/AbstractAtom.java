package com.sankuai.avatar.workflow.core.execute.atom;

import com.dianping.cat.Cat;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.workflow.core.context.FlowContext;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.context.FlowResource;
import com.sankuai.avatar.workflow.core.context.transfer.FlowDisplayTransfer;
import com.sankuai.avatar.workflow.core.display.model.DiffDisplay;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import com.sankuai.avatar.workflow.core.display.model.OutputDisplay;
import com.sankuai.avatar.workflow.core.display.model.TextDisplay;
import com.sankuai.avatar.workflow.core.execute.atom.atomOutput.AtomOutput;
import com.sankuai.avatar.workflow.core.execute.listener.AtomEvent;
import com.sankuai.avatar.workflow.core.execute.transfer.AtomRecordTransfer;
import com.sankuai.avatar.workflow.core.input.FlowInput;
import com.sankuai.avatar.dao.workflow.repository.FlowAtomRecordRepository;
import com.sankuai.avatar.dao.workflow.repository.FlowDataRepository;
import com.sankuai.avatar.dao.workflow.repository.FlowDisplayRepository;
import com.sankuai.avatar.dao.workflow.repository.request.FlowAtomRecordAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDataUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * atom 抽象类，完成一些基础工作，抽象一些公共函数
 * saveAtomRecord，存储atom执行记录
 * 提供操作publicData操作函数
 *
 * @author xk
 */
@Slf4j
public abstract class AbstractAtom implements Atom {

    /**
     * 默认重试次数
     */
    private static final int DEFAULT_RETRY_TIMES = 0;

    /**
     * 默认超时时间
     */
    private static final int DEFAULT_TIMEOUT = 300;

    @Autowired
    AtomEvent atomEvent;

    @Autowired
    FlowDataRepository flowDataRepository;

    @Autowired
    FlowDisplayRepository flowDisplayRepository;

    @Autowired
    FlowAtomRecordRepository flowAtomRecordRepository;

    /**
     * atom上下文
     */
    private AtomContext atomContext;

    /**
     * 流程上下文
     */
    private FlowContext flowContext;

    /**
     * 原子结果
     */
    private AtomResult atomResult = AtomResult.of();

    /**
     * atom公共数据, 用于个atom之间交换数据, 数据来源
     * 1, 流程入参, 创建流程时的input数据
     * 2, 各atom的执行结果
     */
    private Map<String, Object> publicData;

    /**
     * 流程的显示数据
     */
    private FlowDisplay flowDisplay;


    @Override
    public final void setAtomContext(AtomContext atomContext) {
        this.atomContext = atomContext;
        this.atomResult = atomContext.getAtomResult();
        this.flowContext = atomContext.getAtomTemplate().getFlowContext();
        this.publicData = atomContext.getAtomTemplate().getPublicData();
        this.flowDisplay = atomContext.getAtomTemplate().getFlowDisplay();
    }

    @Override
    public final void beforeProcess() {
        log.info("Atom before {}", this.getClass().getSimpleName());
        this.setAtomStatus(AtomStatus.RUNNING);
        // 设置启动时间
        this.atomResult.setStartTime(LocalDateTime.now());
    }

    @Override
    public final AtomStatus process() {
        AtomStatus oldAtomStatus = this.atomContext.getAtomStatus();
        AtomStatus newAtomStatus;
        try {
            newAtomStatus = this.doProcess();
            //TODO: 可以抛自定义异常
        } catch (Exception e) {
            newAtomStatus = AtomStatus.FAIL;
            this.atomResult.setException(e);
        }
        if (!oldAtomStatus.equals(newAtomStatus)) {
            this.setAtomStatus(newAtomStatus);
        }
        return newAtomStatus;
    }

    @Override
    public void afterProcess() {
        log.info("Atom after {}", this.getClass().getSimpleName());
        // 设置结束时间
        this.atomResult.setEndTime(LocalDateTime.now());
        // 计算执行耗时
        this.atomResult.setDuration((int) atomResult.getStartTime().until(atomResult.getEndTime(), ChronoUnit.SECONDS));
        // 存储执行记录
        this.saveAtomRecord();
    }

    @Override
    public final Integer getRetryTimes() {
        return this.atomContext.getRetryTimes() == null ? DEFAULT_RETRY_TIMES : this.atomContext.getRetryTimes();
    }

    @Override
    public final Integer getTimeout() {
        return this.atomContext.getTimeout() == null ? DEFAULT_TIMEOUT : this.atomContext.getTimeout();
    }

    /**
     * 业务逻辑实现，atom实例重写
     *
     * @return {@link AtomStatus}
     */
    protected abstract AtomStatus doProcess();

    private void setAtomStatus(AtomStatus atomStatus) {
        this.atomContext.setAtomStatus(atomStatus);
        // 状态发生变化，触发事件推送
        this.atomEvent.pushEvent(this.atomContext);
    }

    /**
     * 获得输入数据, 执行 Atom 依赖的数据
     * 可以是流程入参或者上个atom的执行结果
     *
     * @return {@link FlowInput}
     */
    protected final <T> T getInput(Class<T> t) {
        if (this.publicData == null) {
            return null;
        }
        Object inputObject;
        try {
            inputObject = JsonUtil.mapToBean(this.publicData, t);
        } catch (Exception e) {
            log.error("get {} input data error: {}", t.getSimpleName(), e);
            Cat.logError("get input data error " + t.getSimpleName(), e);
            return null;
        }
        atomResult.setInput(JsonUtil.bean2Json(inputObject));
        return t.isInstance(inputObject) ? t.cast(inputObject) : null;
    }

    /**
     * atom输出数据, 保留执行记录，也可能是其他atom输入
     *
     * @param output 输出
     */
    protected final void setOutput(AtomOutput output) {
        this.atomResult.setOutput(output);
        // 更新publicData，相同字段覆盖
        this.publicData.putAll(JsonUtil.beanToMap(output));
        this.savePublicData();
    }

    /**
     * 更新显示输入
     */
    protected final void updateDisplayInput(InputDisplay inputDisplay) {
        this.flowDisplay.getInput().add(inputDisplay);
        this.saveAtomDisplay();
    }

    /**
     * 更新显示文本
     */
    protected final void updateDisplayText(TextDisplay textDisplay) {
        this.flowDisplay.getText().add(textDisplay);
        this.saveAtomDisplay();
    }

    /**
     * 更新显示差异
     */
    protected final void updateDisplayDiff(DiffDisplay diff) {
        this.flowDisplay.setDiff(diff);
        this.saveAtomDisplay();
    }

    /**
     * 更新显示输出
     */
    protected final void updateDisplayOutput(OutputDisplay output) {
        this.flowDisplay.setOutput(output);
        this.saveAtomDisplay();
    }

    /**
     * 获取流程资源
     *
     * @return {@link FlowResource}
     */
    protected final FlowResource getFlowResource() {
        return this.flowContext.getResource();
    }


    /**
     * 获取流程创建用户名
     *
     * @return {@link String}
     */
    protected final String getCreateUser() {
        return this.flowContext.getCreateUser();
    }

    /**
     * 更新前端展示信息
     */
    private void saveAtomDisplay() {
        flowDisplayRepository.updateFlowDisplay(FlowDisplayTransfer.INSTANCE.toUpdateRequest(flowDisplay));
    }

    /**
     * 更新 PublicData 公共数据
     */
    private void savePublicData() {
        String publicDataString;
        try {
            publicDataString = JsonUtil.mapToJson(publicData);
        } catch (Exception e) {
            log.error("publicData to Json catch error", e);
            Cat.logError("publicData to Json catch error", e);
            return;
        }
        FlowDataUpdateRequest request = FlowDataUpdateRequest.builder()
                .flowId(flowContext.getId())
                .publicData(publicDataString).build();
        flowDataRepository.updateFlowDataByFlowId(request);
    }

    /**
     * 由于atom可以重试, 会有多条执行记录, 需要完整记录
     */
    private void saveAtomRecord() {
        FlowAtomRecordAddRequest flowAtomRecordAddRequest = FlowAtomRecordAddRequest.builder()
                .flowId(flowContext.getId())
                .atomName(atomContext.getName())
                .status(atomContext.getAtomStatus().getValue())
                .retryTimes(atomContext.getRetryTimes())
                .timeout(atomContext.getTimeout())
                .result(AtomRecordTransfer.INSTANCE.toExecuteResult(atomResult))
                .build();
        flowAtomRecordRepository.addAtomRecord(flowAtomRecordAddRequest);
    }
}
