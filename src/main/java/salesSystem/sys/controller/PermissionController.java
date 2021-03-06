package salesSystem.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import salesSystem.sys.constant.Constant;
import salesSystem.sys.domain.Permission;
import salesSystem.sys.service.PermissionService;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;
import salesSystem.sys.utils.TreeNode;
import salesSystem.sys.vo.PermissionVO;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-05
 */
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //loadAllPermissionTreeJson 左边菜单
//    @RequestMapping("loadAllPermissionTreeJson")
//    public DataGridView loadAllPermissionTreeJson(PermissionVO permissionVO) {
//        permissionVO.setAvailable(Constant.AVAILABLE_TRUE);
//        permissionVO.setType(Constant.TYPE_PERMISSION);
//        //查询所有可用的部门数据
//        List<Permission> allPermission = permissionService.queryAllPermissionList(permissionVO);
//        //数据封装
//        ArrayList<TreeNode> treeNodes = new ArrayList<>();
//        for (Permission d1 : allPermission) {
//            Integer id = d1.getId();
//            Integer pid = d1.getPid();
//            String title = d1.getTitle();
//            Boolean spread = d1.getOpen() == 1 ? true : false;
//            treeNodes.add(new TreeNode(id, pid, title, spread));
//        }
//
//        return new DataGridView(treeNodes);
//    }


    @RequestMapping("loadAllPermission")
    public DataGridView loadAllPermission(PermissionVO permissionVO) {
        permissionVO.setAvailable(Constant.AVAILABLE_TRUE);
        permissionVO.setType(Constant.TYPE_PERMISSION);

        return permissionService.loadAllPermission(permissionVO);
    }

    @RequestMapping("deletePermission")
    public ResultObj deletePermission(Integer id) {
        try {
            permissionService.deletePermission(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

//    @Transactional
//    @RequestMapping("batchDeletePermission")
//    public ResultObj batchDeletepermission(Integer[] ids) {
//        try {
//            permissionService.batchDeletePermission(ids);
//            return ResultObj.DELETE_SUCCESS;
//        } catch (Exception e) {
//            return ResultObj.DELETE_FAIL;
//        }
//    }

    //添加公告
    @RequestMapping("addPermission")
    public ResultObj addPermission(Permission permission) {
        try {
            //存入数据库
            permission.setType(Constant.TYPE_PERMISSION);
            permissionService.addPermission(permission);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_FAIL;
        }
    }

    @RequestMapping("updatePermission")
    public ResultObj updatePermission(Permission permission) {
        try {
//            permission.setCreatetime(TimeZoneInChina.ChangeTimeZoneInChina());
            permission.setType(Constant.TYPE_PERMISSION);
            //存入数据库
            permissionService.updatePermission(permission);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_FAIL;
        }
    }

//    //loadPermissionById  //查看
//    @RequestMapping("loadPermissionById")
//    public DataGridView loadPermissionById(Integer id) {
//
//        return new DataGridView(permissionService.getById(id));
//    }

    @RequestMapping("checkCurrentPermissionHasChild")
    public DataGridView checkCurrentPermissionHasChild(Integer id) {
        Integer count = permissionService.queryPermissionCountByPid(id,Constant.TYPE_PERMISSION);
//        Integer count = permissionService.queryPermissionCountByPid(id);
        return new DataGridView(count);
    }

    @RequestMapping("loadPermissionMaxOrderNum")
    public DataGridView loadPermissionMaxOrderNum() {
        Integer maxOrderNumAddOne = permissionService.queryPermissionMaxOrderNum();
        return new DataGridView(maxOrderNumAddOne);
    }

}

