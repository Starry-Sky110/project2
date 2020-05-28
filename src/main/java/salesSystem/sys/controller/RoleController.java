package salesSystem.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import salesSystem.sys.domain.Role;
import salesSystem.sys.domain.User;
import salesSystem.sys.service.PermissionService;
import salesSystem.sys.service.RoleService;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;
import salesSystem.sys.utils.TimeZoneInChina;
import salesSystem.sys.vo.RoleVO;

import javax.servlet.http.HttpSession;
import java.text.ParseException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-14
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("loadAllRole")
    public DataGridView loadAllRole(RoleVO roleVO) {

        return roleService.loadAllRole(roleVO);
    }

    @RequestMapping("deleteRole")
    public ResultObj deleteRole(Integer id) {
        try {
            roleService.deleteRole(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

    @Transactional
    @RequestMapping("batchDeleteRole")
    public ResultObj batchDeleteRole(Integer[] ids) {
        try {
            roleService.batchDeleteRole(ids);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

    //添加角色
    @RequestMapping("addRole")
    public ResultObj addRole(Role role, HttpSession session) {
        try {
            //从session获得用户
//            User user = (User) session.getAttribute("user");

            role.setCreatetime(TimeZoneInChina.ChangeTimeZoneInChina());
            //存入数据库
            roleService.addRole(role);
            return ResultObj.ADD_SUCCESS;
        } catch (ParseException e) {
            return ResultObj.ADD_FAIL;
        }
    }

    @RequestMapping("updateRole")
    public ResultObj updateRole(Role role) {
        try {
            role.setCreatetime(TimeZoneInChina.ChangeTimeZoneInChina());
            //存入数据库
            roleService.updateRole(role);
            return ResultObj.UPDATE_SUCCESS;
        } catch (ParseException e) {
            return ResultObj.UPDATE_FAIL;
        }
    }

    //loadRoleById
    @RequestMapping("loadRoleById")
    public DataGridView loadRoleById(Integer id) {

        return new DataGridView(roleService.getById(id));
    }

    //查询用户的权限
    @RequestMapping("loadRolePermission")
    public DataGridView loadRolePermission(@RequestParam("id") Integer roleId) {

        return permissionService.queryPermissionByRoleId(roleId);
    }

    @RequestMapping("saveRolePermission")
    public ResultObj saveRolePermission(Integer roleId, Integer[] pids) {
        try {
//            System.out.println("RoleId================" + roleId);
            roleService.saveRolePermission(roleId, pids);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FAIL;
        }

    }

    //loadUserRole
    @RequestMapping("loadUserRole")
    public DataGridView loadUserRole(Integer userId) {
        return roleService.loadUserRoleByUserId(userId);
    }


}

