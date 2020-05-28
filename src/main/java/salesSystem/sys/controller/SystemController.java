package salesSystem.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("system")
public class SystemController {

    @RequestMapping("index")
    public String index() {
        return "system/index";
    }

    @RequestMapping("toDeskManager")
    public String DeskManager() {
        return "system/deskManager";
    }

    @RequestMapping("toLoginfoManager")
    public String toLoginfoManager() {
        return "system/loginfo/loginfoManager";
    }

    @RequestMapping("toNoticeManager")
    public String toNoticeManager() {
        return "system/notice/noticeManager";
    }

    @RequestMapping("toDeptManager")
    public String toDeptManager() {
        return "system/dept/deptManager";
    }

    @RequestMapping("toDeptLeftManager")
    public String toDeptLeftManager() {
        return "system/dept/deptLeftManager";
    }

    @RequestMapping("toDeptRightManager")
    public String toDeptRightManager() {
        return "system/dept/deptRightManager";
    }

    @RequestMapping("toMenuManager")
    public String toMenuManager() {
        return "system/menu/menuManager";
    }

    @RequestMapping("toMenuLeftManager")
    public String toMenuLeftManager() {
        return "system/menu/menuLeftManager";
    }

    @RequestMapping("toMenuRightManager")
    public String toMenuRightManager() {
        return "system/menu/menuRightManager";
    }

    @RequestMapping("toPermissionManager")
    public String toPermissionManager() {
        return "system/permission/permissionManager";
    }

    @RequestMapping("toPermissionLeftManager")
    public String toPermissionLeftManager() {
        return "system/permission/permissionLeftManager";
    }

    @RequestMapping("toPermissionRightManager")
    public String toPermissionRightManager() {
        return "system/permission/permissionRightManager";
    }

    //toRoleManager
    @RequestMapping("toRoleManager")
    public String toRoleManager() {
        return "system/role/roleManager";
    }

    //toUserManager
    @RequestMapping("toUserManager")
    public String toUserManager() {
        return "system/user/userManager";
    }

    //toUserInfo
    @RequestMapping("toUserInfo")
    public String toUserInfo() {
        return "system/user/userInfo";
    }

    //toChangePassword
    @RequestMapping("toChangePassword")
    public String toChangePassword() {
        return "system/user/changePassword";
    }


}
