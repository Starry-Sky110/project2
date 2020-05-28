package salesSystem.sys.service;

import salesSystem.sys.domain.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.vo.DeptVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-10
 */
public interface DeptService extends IService<Dept> {

    List<Dept> queryAllDeptForList(DeptVO deptVO);

    DataGridView loadAllDept(DeptVO deptVO);

    void deleteDept(Integer id);

//    void batchDeleteDept(Integer[] ids);

    void addDept(Dept dept);

    void updateDept(Dept dept);

    Integer queryDeptCountByPid(Integer id);

    Integer queryDeptMaxOrderNum();
}
