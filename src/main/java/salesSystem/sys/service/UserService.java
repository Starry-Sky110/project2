package salesSystem.sys.service;

import salesSystem.sys.domain.User;
import salesSystem.sys.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.sys.utils.ActiveUser;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;
import salesSystem.sys.vo.UserVO;

import javax.servlet.http.HttpSession;
import java.net.HttpCookie;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-03
 */
public interface UserService extends IService<User> {

    User queryUserByUsername(String username);

    DataGridView loadAllUser(UserVO userVO);

    void deleteUser(Integer id);

    void batchDeleteUser(Integer[] ids);

    void addUser(User user);

    void updateUser(User user);


    Integer queryUserMaxOrderNum();

    List<User> queryUserByDeptId(Integer deptid);

    User queryUserByUserId(Integer userId);

    void saveUserRole(Integer userId, Integer[] pids);

    void rePassword(Integer id);

    void changeUser(User user, HttpSession session);

    ResultObj changePassword(Integer id, String oldPassword, String newPassword, String confirmPassword);
}
