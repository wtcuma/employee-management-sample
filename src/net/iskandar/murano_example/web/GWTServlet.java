package net.iskandar.murano_example.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GWTServlet extends RemoteServiceServlet {

	private static Map<String, String> mappings;  // Maps
										  	      // uri
												  // to
												  // bean
	
	private static synchronized void parseMappings(String mappingsStr){
		mappings = new HashMap<String,String>();
		Utils.parseMappings(mappingsStr, mappings);
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if(mappings == null){
			String mappingsStr = config.getInitParameter("mappings");
			parseMappings(mappingsStr);
		}
	}
	
	@Override
	public String processCall(String payload)
			throws SerializationException {
		String uri = this.getThreadLocalRequest().getServletPath().toString();
		String beanName = mappings.get(uri);
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		final Object bean = context.getBean(beanName);
		try {
			try {
				RPCRequest rpcRequest = RPC.decodeRequest(payload, bean
						.getClass(), this);
				String response = RPC.invokeAndEncodeResponse(bean, rpcRequest
						.getMethod(), rpcRequest.getParameters(),
						rpcRequest.getSerializationPolicy());
				return response;
			} catch (Throwable t) {
				getServletContext().log("An IncompatibleRemoteServiceException was thrown while processing this call.",	t);
				throw new RuntimeException("An IncompatibleRemoteServiceException was thrown while processing this call.", t);
			}
		} catch (IncompatibleRemoteServiceException ex) {
			getServletContext()
					.log("An IncompatibleRemoteServiceException was thrown while processing this call.",
							ex);
			return RPC.encodeResponseForFailure(null, ex);
		}
	}
	
}
