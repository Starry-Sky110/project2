package salesSystem.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import salesSystem.sys.service.LoginfoService;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;
import salesSystem.sys.vo.LogInfoVO;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-07
 */
@RestController
@RequestMapping("loginfo")
public class LoginfoController {

    @Autowired
    private LoginfoService loginfoService;

    @RequestMapping("loadAllLogInfo")
    public DataGridView loadAllLogInfo(LogInfoVO logInfoVO) {

        return loginfoService.loadAllLogInfo(logInfoVO);
    }

    @RequestMapping("deleteloginfo")
    public ResultObj deleteloginfo(Integer id) {
        try {
            loginfoService.deleteloginfo(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

    @Transactional
    @RequestMapping("batchDeleteLogInfo")
    public ResultObj batchDeleteLogInfo(Integer[] ids) {
        try {
            loginfoService.batchDeleteLogInfo(ids);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }
}

