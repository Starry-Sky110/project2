package salesSystem.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import salesSystem.sys.domain.Notice;
import salesSystem.sys.domain.User;
import salesSystem.sys.service.NoticeService;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;
import salesSystem.sys.utils.TimeZoneInChina;
import salesSystem.sys.vo.NoticeVO;

import javax.servlet.http.HttpSession;
import java.text.ParseException;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-08
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @RequestMapping("loadAllNotice")
    public DataGridView loadAllnotice(NoticeVO noticeVO) {

        return noticeService.loadAllNotice(noticeVO);
    }

    @RequestMapping("deleteNotice")
    public ResultObj deletenotice(Integer id) {
        try {
            noticeService.deleteNotice(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

    @Transactional
    @RequestMapping("batchDeleteNotice")
    public ResultObj batchDeletenotice(Integer[] ids) {
        try {
            noticeService.batchDeleteNotice(ids);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

    //添加公告
    @RequestMapping("addNotice")
    public ResultObj addNotice(Notice notice, HttpSession session) {
        try {
            //从session获得用户
            User user = (User) session.getAttribute("user");
            //设置发布人
            notice.setOpername(user.getName());
            notice.setCreatetime(TimeZoneInChina.ChangeTimeZoneInChina());
            //存入数据库
            noticeService.addNotice(notice);
            return ResultObj.ADD_SUCCESS;
        } catch (ParseException e) {
            return ResultObj.ADD_FAIL;
        }
    }

    @RequestMapping("updateNotice")
    public ResultObj updateNotice(Notice notice){
        try {
            notice.setCreatetime(TimeZoneInChina.ChangeTimeZoneInChina());
            //存入数据库
            noticeService.updateNotice(notice);
            return ResultObj.UPDATE_SUCCESS;
        } catch (ParseException e) {
            return ResultObj.UPDATE_FAIL;
        }
    }
    //loadNoticeById
    @RequestMapping("loadNoticeById")
    public DataGridView loadNoticeById(Integer id){

        return new DataGridView(noticeService.getById(id));
    }

}

