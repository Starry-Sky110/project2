package salesSystem.bus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import salesSystem.bus.domain.Inport;
import salesSystem.bus.service.InportService;
import salesSystem.bus.vo.InportVO;
import salesSystem.sys.domain.User;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;
import salesSystem.sys.utils.TimeZoneInChina;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-23
 */
@RestController
@RequestMapping("inport")
public class InportController {

    @Autowired
    private InportService inportService;

    @RequestMapping("loadAllInport")
    public DataGridView loadAllinport(InportVO inportVO) {

        return inportService.loadAllInport(inportVO);
    }

    @RequestMapping("deleteInport")
    public ResultObj deleteinport(Integer id,Integer num) {
        try {
            inportService.deleteInport(id,num);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }


    //添加公告
    @RequestMapping("addInport")
    public ResultObj addInport(Inport inport, HttpSession session) {
        try {
            //操作人
            User user = (User) session.getAttribute("user");
            inport.setOperateperson(user.getName());
            inport.setInporttime(TimeZoneInChina.ChangeTimeZoneInChina());
            //存入数据库
            inportService.addInport(inport);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FAIL;
        }
    }

    @RequestMapping("updateInport")
    public ResultObj updateInport(Inport inport) {
        System.out.println(inport.toString());
        try {
            //存入数据库
            inportService.updateInport(inport);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FAIL;
        }
    }

    //loadInportById
    @RequestMapping("loadInportById")
    public DataGridView loadInportById(Integer id) {

        return new DataGridView(inportService.getById(id));
    }

}

