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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/act/model")
public class ActModelController {

    @Resource private ActModelService actModelService;

    /** 分页查询 */
    //    @PreAuthorize("hasAnyAuthority('fileStore.find')")
    @GetMapping("/list/page")
    public XPage<ActModelDTO> findListByPage(ActModelDTO actModelDTO, XPage<ActModelDTO> xPage) {
        IPage<Model> iPage = actModelService.findActModelList(actModelDTO, XPage.toIPage(xPage));
        XPageImpl<ActModelDTO> result = new XPageImpl<>();
        result.setTotal(iPage.getTotal());
        result.setPageSize(iPage.getSize());
        result.setPageNum(iPage.getCurrent());
        result.setOrders(iPage.orders());
        result.setData(
                iPage.getRecords().stream().map(ActModelDTO::new).collect(Collectors.toList()));
        return result;
    }

    /** 保存 */
    @PostMapping("/save")
    public Result<Void> save(@RequestBody ActModelDTO actModelDTO) {
        actModelService.saveActModel(actModelDTO);
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 部署 */
    @PostMapping("/deploy/{id}")
    public Result<Void> deploy(@PathVariable String id) {
        actModelService.deploy(id);
        return Result.build(ResultStatus.DEPLOY_SUCCESS);
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public ActModelDTO getById(@PathVariable String id) {
        return actModelService.getActModelById(id);
    }

    /** 删除 */
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable String id) {
        actModelService.deleteActModel(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
