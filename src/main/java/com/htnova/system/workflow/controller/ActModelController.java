package com.htnova.system.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.common.dto.XPageImpl;
import com.htnova.system.workflow.dto.ActModelDTO;
import com.htnova.system.workflow.service.ActModelService;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.flowable.engine.repository.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow/model")
public class ActModelController {
    @Resource
    private ActModelService actModelService;

    /** 分页查询 */
    @PreAuthorize("hasAnyAuthority('workflowModel.find')")
    @GetMapping("/list/page")
    public XPage<ActModelDTO> findListByPage(ActModelDTO actModelDTO, XPage<ActModelDTO> xPage) {
        IPage<Model> iPage = actModelService.findActModelList(actModelDTO, XPage.toIPage(xPage));
        XPageImpl<ActModelDTO> result = new XPageImpl<>();
        result.setTotal(iPage.getTotal());
        result.setPageSize(iPage.getSize());
        result.setPageNum(iPage.getCurrent());
        result.setOrders(iPage.orders());
        result.setData(iPage.getRecords().stream().map(ActModelDTO::new).collect(Collectors.toList()));
        return result;
    }

    /** 保存 */
    @PreAuthorize("hasAnyAuthority('workflowModel.edit')")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody ActModelDTO actModelDTO) {
        actModelService.save(actModelDTO);
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 部署 */
    @PreAuthorize("hasAnyAuthority('workflowModel.edit')")
    @PostMapping("/deploy/{id}")
    public Result<Void> deploy(@PathVariable String id) {
        actModelService.deploy(id);
        return Result.build(ResultStatus.DEPLOY_SUCCESS);
    }

    /** 详情 */
    @PreAuthorize("hasAnyAuthority('workflowModel.find')")
    @GetMapping("/detail/{id}")
    public ActModelDTO getById(@PathVariable String id) {
        return actModelService.getActModelById(id);
    }

    /** 删除 */
    @PreAuthorize("hasAnyAuthority('workflowModel.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable String id) {
        actModelService.deleteActModel(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
