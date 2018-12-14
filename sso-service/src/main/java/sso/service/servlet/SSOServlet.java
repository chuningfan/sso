package sso.service.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sso.core.internal.dto.Constant;
import sso.core.internal.dto.Result;
import sso.core.internal.dto.SSORequest;
import sso.service.helper.SpringHelper;
import sso.service.processor.session.CheckProcessor;

@WebServlet(urlPatterns="/validate.do", name="sso-servlet")
public class SSOServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(SSOServlet.class);
	
	private CheckProcessor checkProcessor;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Result<String> result = new Result<String>();
		try {
			result = checkProcessor.process0(new SSORequest(req, resp), result);
			if (result.getData().startsWith(Constant.KEY.DIRECT_HEADER.getKey())) {
				String directURL = result.getData().replace(Constant.KEY.DIRECT_HEADER.getKey(), "");
				resp.sendRedirect(directURL);
			} else {
				req.getRequestDispatcher(result.getData()).forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			req.setAttribute("exception", e);
			req.getRequestDispatcher(Constant.PAGE.PAGE_ERROR.getPath()).forward(req, resp);
		}
	}

	@Override
	public void init() throws ServletException {
		checkProcessor = SpringHelper.getComponent(CheckProcessor.class);
	}
	
}