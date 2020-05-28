package salesSystem.sys.service;

import salesSystem.sys.domain.Permission;
import salesSystem.sys.domain.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.sys.domain.User;
import salesSystem.sys.utils.ActiveUser;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.vo.PermissionVO;
import salesSystem.sys.vo.PermissionVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-05
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> queryAllPermissionList(PermissionVO permissionVO);

//    List<Permission> queryAllPermissionForList(PermissionVO permissionVO);

    DataGridView loadAllPermission(PermissionVO permissionVO);

    void deletePermission(Integer id);

//    void batchDeletePermission(Integer[] ids);

    void addPermission(Permission permission);

    void updatePermission(Permission permission);

    Integer queryPermissionCountByPid(Integer id, String type);

    Integer queryPermissionMaxOrderNum();

    DataGridView queryPermissionByRoleId(Integer roleId);

    List<String> queryPermissionsByUserId(Integer id);

    List<Permission> queryPermissionByUserIdForList(User user);
}
