package com.itheima.product.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.product.exception.OrderException;
import com.itheima.product.service.OrderService;
import com.itheima.product.util.PaymentUtil;
//处理用户定单信息
public class CallBackServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String  p1_MerId = request.getParameter("p1_MerId");
		String  r0_Cmd = request.getParameter("r0_Cmd");
		String  r1_Code = request.getParameter("r1_Code");//支付结果，1代表支付成功
		String  r2_TrxId = request.getParameter("r2_TrxId");
		String  r3_Amt = request.getParameter("r3_Amt");
		String  r4_Cur = request.getParameter("r4_Cur");
		String  r5_Pid = request.getParameter("r5_Pid");
		String  r6_Order = request.getParameter("r6_Order");//商户订单号
		String  r7_Uid = request.getParameter("r7_Uid");
		String  r8_MP = request.getParameter("r8_MP");
		String  r9_BType = request.getParameter("r9_BType");//为“2”: 服务器点对点通讯.
		String  hmac = request.getParameter("hmac");
		
		boolean isOK = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		
		if(!isOK){
			out.print("支付数据有可能被篡改,请联系客服");
		}else{
			
			if("1".equals(r1_Code)){//支付结果，1代表支付成功
				if("2".equals(r9_BType)){
					out.print("success");
				}
				//out.print("hello servlet..");
				//修改定单状态
				OrderService os = new OrderService();
				try {
					os.modifyOrderState(r6_Order);
				} catch (OrderException e) {
					System.out.println(e.getMessage());
				}
				//重定向到成功页面
				response.sendRedirect(request.getContextPath()+"/paysuccess.jsp");
			}
			
		}
		
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
