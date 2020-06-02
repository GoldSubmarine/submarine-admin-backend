package com.htnova.system.tool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.tool.dto.ActivitiDeploymentDTO;
import com.htnova.system.tool.service.ActivitiService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activiti")
public class ActivitiController {

    @Resource private ActivitiService activitiService;

    /** 分页查询 */
    //    @PreAuthorize("hasAnyAuthority('fileStore.find')")
    @GetMapping("/list/page")
    public XPage<ActivitiDeploymentDTO> findListByPage(
            ActivitiDeploymentDTO activitiDeploymentDTO, XPage<ActivitiDeploymentDTO> xPage) {
        IPage<ActivitiDeploymentDTO> iPage =
                activitiService.findActivitiList(activitiDeploymentDTO, XPage.toIPage(xPage));
        xPage.setPageNum(iPage.getCurrent());
        xPage.setPageSize(iPage.getSize());
        xPage.setTotal(iPage.getTotal());
        xPage.setOrders(iPage.orders());
        xPage.setData(iPage.getRecords());
        return xPage;
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public ActivitiDeploymentDTO getById(@PathVariable String id) {
        return activitiService.getActivitiDeploymentById(id);
    }

    /** 删除 */
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable String id) {
        activitiService.deleteActiviti(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
