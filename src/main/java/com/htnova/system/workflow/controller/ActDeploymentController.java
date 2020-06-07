package com.htnova.system.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.common.dto.XPageImpl;
import com.htnova.system.workflow.dto.ActDeploymentDTO;
import com.htnova.system.workflow.service.ActDeploymentService;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.flowable.engine.repository.Deployment;
import org.springframework.web.bind.annotation.*;

// dinghuang.github.io/2020/03/14/Activiti7.X结合SpringBoot2.1、Mybatis/
// destinywang.github.io/blog/2018/12/11/Activiti-3-数据模型设计/
@RestController
@RequestMapping("/act/deployment")
public class ActDeploymentController {

    @Resource private ActDeploymentService actDeploymentService;

    /** 分页查询 */
    //    @PreAuthorize("hasAnyAuthority('fileStore.find')")
    @GetMapping("/list/page")
    public XPage<ActDeploymentDTO> findListByPage(
            ActDeploymentDTO actDeploymentDTO, XPage<ActDeploymentDTO> xPage) {
        IPage<Deployment> iPage =
                actDeploymentService.findActDeploymentList(actDeploymentDTO, XPage.toIPage(xPage));
        XPageImpl<ActDeploymentDTO> result = new XPageImpl<>();
        result.setTotal(iPage.getTotal());
        result.setPageSize(iPage.getSize());
        result.setPageNum(iPage.getCurrent());
        result.setOrders(iPage.orders());
        result.setData(
                iPage.getRecords().stream()
                        .map(ActDeploymentDTO::new)
                        .collect(Collectors.toList()));
        return result;
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public ActDeploymentDTO getById(@PathVariable String id) {
        return actDeploymentService.getActDeploymentById(id);
    }

    /** 删除 */
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable String id) {
        actDeploymentService.deleteActDeployment(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
