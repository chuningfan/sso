package sso.service.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sso.common.dto.SSOKey;
import sso.core.component.rpc.RequestClient;
import sso.core.internal.dto.Constant;
import sso.service.helper.SpringHelper;

@WebServlet(urlPatterns = "/validate.do", name = "sso-servlet")
public class SSOServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// private static final Logger LOG =
	// LoggerFactory.getLogger(SSOServlet.class);

	// private CheckProcessor checkProcessor;

	private static RequestClient requestClient;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (requestClient == null)
			requestClient = SpringHelper.getComponent(RequestClient.class);
		// String authId = req.getParameter(SSOKey.KEY.AUTH_ID.getKey());
		if (verify(req, resp)) {
			HttpSession session = req.getSession(false);
			session.getAttribute(session.getId());
			String callback = req.getParameter(SSOKey.KEY.CALLBACK_URL.getKey());
			callback = URLDecoder.decode(callback, "UTF-8");
			resp.sendRedirect(callback + "?" + SSOKey.KEY.AUTH_ID.getKey() + "=" + session.getId());
		} else {
			req.getRequestDispatcher(Constant.LOGIN_PAGE).forward(req, resp);
		}
	}

	private boolean verify(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession(false);
		if (session != null) {
			return true;
		}
		return false;
	}

	@Override
	public void init() throws ServletException {

	}

}
