package salesSystem.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import salesSystem.sys.constant.Constant;
import salesSystem.sys.domain.Dept;
import salesSystem.sys.service.DeptService;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;
import salesSystem.sys.utils.TimeZoneInChina;
import salesSystem.sys.utils.TreeNode;
import salesSystem.sys.vo.DeptVO;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-10
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    //loadAllDeptTreeJson 左边菜单
    @RequestMapping("loadAllDeptTreeJson")
    public DataGridView loadAllDeptTreeJson(DeptVO deptVO) {
        deptVO.setAvailable(Constant.AVAILABLE_TRUE);
        //查询所有可用的部门数据
        List<Dept> allDept = deptService.queryAllDeptForList(deptVO);
        //数据封装
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (Dept d1 : allDept) {
            Integer id = d1.getId();
            Integer pid = d1.getPid();
            String title = d1.getTitle();
            Boolean spread = d1.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(id, pid, title, spread));
        }

        return new DataGridView(treeNodes);
    }


    @RequestMapping("loadAllDept")
    public DataGridView loadAlldept(DeptVO deptVO) {

        return deptService.loadAllDept(deptVO);
    }

    @RequestMapping("deleteDept")
    public ResultObj deletedept(Integer id) {
        try {
            deptService.deleteDept(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

//    @Transactional
//    @RequestMapping("batchDeleteDept")
//    public ResultObj batchDeletedept(Integer[] ids) {
//        try {
//            deptService.batchDeleteDept(ids);
//            return ResultObj.DELETE_SUCCESS;
//        } catch (Exception e) {
//            return ResultObj.DELETE_FAIL;
//        }
//    }

    //添加公告
    @RequestMapping("addDept")
    public ResultObj addDept(Dept dept) {
        try {
            dept.setCreatetime(TimeZoneInChina.ChangeTimeZoneInChina());
            //存入数据库
            deptService.addDept(dept);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_FAIL;
        }
    }

    @RequestMapping("updateDept")
    public ResultObj updateDept(Dept dept) {
        try {
            dept.setCreatetime(TimeZoneInChina.ChangeTimeZoneInChina());
            //存入数据库
            deptService.updateDept(dept);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_FAIL;
        }
    }

//    //loadDeptById
//    @RequestMapping("loadDeptById")
//    public DataGridView loadDeptById(Integer id) {
//
//        return new DataGridView(deptService.getById(id));
//    }

    @RequestMapping("checkCurrentDeptHasChild")
    public DataGridView checkCurrentDeptHasChild(Integer id) {
        Integer count = deptService.queryDeptCountByPid(id);
        return new DataGridView(count);
    }

    @RequestMapping("loadDeptMaxOrderNum")
    public DataGridView loadDeptMaxOrderNum() {
        Integer maxOrderNumAddOne = deptService.queryDeptMaxOrderNum();
        return new DataGridView(maxOrderNumAddOne);
    }

}

