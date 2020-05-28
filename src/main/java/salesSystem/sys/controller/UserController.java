package salesSystem.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import salesSystem.sys.constant.Constant;
import salesSystem.sys.domain.User;
import salesSystem.sys.service.UserService;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.MD5Utils;
import salesSystem.sys.utils.ResultObj;
import salesSystem.sys.utils.TimeZoneInChina;
import salesSystem.sys.vo.UserVO;


import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-03
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVO userVO) {
        return userService.loadAllUser(userVO);
    }


    @RequestMapping("deleteUser")
    public ResultObj deleteUser(Integer id) {
        try {
            userService.deleteUser(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }

    @Transactional
    @RequestMapping("batchDeleteUser")
    public ResultObj batchDeleteUser(Integer[] ids) {
        try {
            userService.batchDeleteUser(ids);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FAIL;
        }
    }

    //添加用户
    @RequestMapping("addUser")
    public ResultObj addUser(User user, HttpSession session) {
        try {
            //入职时间
            user.setHiredate(TimeZoneInChina.ChangeTimeZoneInChina());
            //用户类型
            user.setType(Constant.USER_TYPE_NORMAL);
            //盐值
            user.setSalt(MD5Utils.getSalt());
            //默认密码
            //设置密码 1.密码值，2.盐值，3.hash几次
            user.setPwd(MD5Utils.md5(Constant.USER_DEFAULT_PWD, user.getSalt(), 2));
            //存入数据库
            userService.addUser(user);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FAIL;
        }
    }

    @RequestMapping("updateUser")
    public ResultObj updateUser(User user) {
        try {
//            user.setCreatetime(TimeZoneInChina.ChangeTimeZoneInChina());
            //存入数据库
            userService.updateUser(user);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FAIL;
        }
    }

    //loadUserById
    @RequestMapping("loadUserById")
    public DataGridView loadUserById(Integer id) {

        return new DataGridView(userService.getById(id));
    }


    //分配角色并保存
    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer userId, Integer[] rids) {
        try {
//            System.out.println("UserId================" + userId);
            userService.saveUserRole(userId, rids);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FAIL;
        }

    }

    @RequestMapping("loadUserMaxOrderNum")
    public DataGridView loadUserMaxOrderNum() {
        Integer maxOrderNumAddOne = userService.queryUserMaxOrderNum();
        return new DataGridView(maxOrderNumAddOne);
    }

    @RequestMapping("queryUserByDeptId")
    public DataGridView queryUserByDeptId(Integer deptid) {
        List<User> userList = userService.queryUserByDeptId(deptid);
        return new DataGridView(userList);
    }

    @RequestMapping("loadUserByUserId")
    public DataGridView loadUserByUserId(Integer userId) {
        User user = userService.queryUserByUserId(userId);
        return new DataGridView(user);
    }

    //rePassword 重置密码
    @RequestMapping("rePassword")
    public ResultObj rePassword(Integer id) {
        try {
            userService.rePassword(id);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FAIL;
        }
    }

    //changeUser
    @RequestMapping("changeUser")
    public ResultObj changeUser(User user, HttpSession session) {
        try {
            userService.changeUser(user, session);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FAIL;
        }
    }

    //修改密码
    @RequestMapping("changePassword")
    public ResultObj changePassword(String oldPassword,
                                    String newPassword,
                                    String confirmPassword,
                                    HttpSession session) {

        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        return userService.changePassword(id, oldPassword, newPassword, confirmPassword);

    }


}

