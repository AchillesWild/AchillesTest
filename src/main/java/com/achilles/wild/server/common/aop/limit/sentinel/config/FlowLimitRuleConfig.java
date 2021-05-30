package com.achilles.wild.server.common.aop.limit.sentinel.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class FlowLimitRuleConfig {

    String key = "limit_test";

    @PostConstruct
    public void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("com.achilles.wild.server.business.controller.demo.FlowController.qps");
        // QPS控制在2以内
        rule.setCount(1);
        // QPS限流
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //判断的根据是资源自身，还是根据其它关联资源 (refResource)，还是根据链路入口,
        rule.setStrategy(RuleConstant.STRATEGY_DIRECT );
        rule.setLimitApp(RuleConstant.LIMIT_APP_DEFAULT);
        //流控效果（直接拒绝 / 排队等待 / 慢启动模式）,默认直接拒绝
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @PostConstruct
    public void initDegradeRuleException() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(key);
        rule.setCount(0.6);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);//资源的每秒异常总数占通过量
//        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        rule.setTimeWindow(3);//熔断时长，单位为 s
        rule.setMinRequestAmount(2);//熔断触发的最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断
        rule.setStatIntervalMs(5000);//统计时长（单位为 ms），如 60*1000 代表分钟级
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    //RuleConstant.DEGRADE_GRADE_RT
    //平均相应时间,当1s内持续进入5个请求,对应时刻平均时间(秒级)均超过阈值,那么接下来的timewindow 秒之内,对这个方法熔断
    //DEGRADE_GRADE_EXCEPTION_RATIO
    //当资源每秒请求超过5次,且每秒异常总数占通过量的比值超过阈值之后,资源在接下来的timewindow秒进入降级,异常比率[0.0,1.0]
    //DEGRADE_GRADE_EXCEPTION_COUNT
    //当资源近1分钟的异常数目超过阈值之后会进行熔断,因为是1分钟级别,若timewindow小于60s,则结束熔断妆后仍可能进入熔断

//    @PostConstruct
    public void initDegradeRuleRt() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(key);
        // set threshold rt, 200 ms
        rule.setCount(100);
        //平均相应时间,当1s内持续进入5个请求,对应时刻平均时间(秒级)均超过阈值,那么接下来的timewindow 秒之内,对这个方法熔断
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // 设置时间窗口
        rule.setTimeWindow(25);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    /**
     * 热点规则
     */
//    @PostConstruct
    public void initParamFlowRule() {

        // 定义热点限流的规则，对第一个参数设置 qps 限流模式，阈值为1
        ParamFlowRule rule = new ParamFlowRule(key);
        rule.setParamIdx(0);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);
        ParamFlowRuleManager.loadRules(Collections.singletonList(rule));
    }

    //    @PostConstruct
    public void initSystemRule() {
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setHighestSystemLoad(10);
        rule.setAvgRt(200L);
        rule.setMaxThread(300L);
        rule.setQps(1000);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }

    //    @PostConstruct
    public void initAuthorityRule() {
        AuthorityRule rule = new AuthorityRule();
        rule.setResource(key);
        rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule.setLimitApp("appA,appB");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }

}
