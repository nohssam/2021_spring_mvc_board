package com.ict.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ict.service.MyService;
import com.ict.service.Paging;
import com.ict.vo.VO;
import com.sun.prism.paint.Paint;

@Controller
public class MyController {

	@Autowired
	private MyService myService;
	@Autowired
	private Paging paging;

	@RequestMapping("list.do")
	public ModelAndView listCommand(@ModelAttribute("cPage")String cPage) {
		try {
			ModelAndView mv = new ModelAndView("list");
			
			// 전체 게시물의 수
			int count = myService.selectCount();
			paging.setTotalRecord(count);

			// 전체 페이지의 수
			if (paging.getTotalRecord() <= paging.getNumPerPage()) {
				paging.setTotalPage(1);
			} else {
				// 전체 페이지의 수 계산하기
				paging.setTotalPage(paging.getTotalRecord() / paging.getNumPerPage());
				// 주의 사항 : 나머지가 존재하면 전체 페이지 수에 +1를 한다.
				if (paging.getTotalRecord() % paging.getNumPerPage() != 0) {
					paging.setTotalPage(paging.getTotalPage() + 1);
				}
			}
			// 현재 페이지 구하기
			paging.setNowPage(Integer.parseInt(cPage));
			
			// 시작번호, 끝번호
			paging.setBegin((paging.getNowPage() - 1) * paging.getNumPerPage() + 1);
			paging.setEnd((paging.getBegin() - 1) + paging.getNumPerPage());

			// 시작블록, 끝블록
			paging.setBeginBlock(
					(int) ((paging.getNowPage() - 1) / paging.getPagePerBlock()) * paging.getPagePerBlock() + 1);
			paging.setEndBlock(paging.getBeginBlock() + paging.getPagePerBlock() - 1);

			// 주의사항 : endBlock 이 totalPage 보다 큰 경우 발생 할 수 있다.
			// 이 경우 endBlock를 totalPage에 맞춰야 한다.
			if (paging.getEndBlock() > paging.getTotalPage()) {
				paging.setEndBlock(paging.getTotalPage());
			}
			List<VO> list = myService.selectList(paging.getBegin(), paging.getEnd());
			
			mv.addObject("list",list);
			mv.addObject("pvo", paging);
			
			return mv;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	@RequestMapping("write.do")
	public ModelAndView writeCommand(@ModelAttribute("cPage")String cPage) {
		return new ModelAndView("write");
	}
	@RequestMapping(value = "write_ok.do", method = RequestMethod.POST)
	public ModelAndView writeOKCommand(VO vo) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}













