package com.myke.service;

import com.myke.exception.SOAException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangjianbin
 * @date 2021年10月11日15:22
 */
@Service
public class ResponseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseService.class);

    @Autowired
    SoaClient soaClient;

    /**
     * 调用有异常时,继续执行业务流程
     *
     * @param request
     *
     * @return
     */
    public Response process(Request request) {
        Response response = null;
        try {
            SOAResponse soaResponse = soaClient.invoke(request); // 调用接口
            //"soaClient.invoke"接口抛异常时,不用执行下面的方法
            if (soaResponse != null) {
                response = new Response();
                response.setResultCode(soaResponse.isSuccess() ? 0 : -1);
                response.setResultMsg(soaResponse.getResultMsg());
            }
        } catch (SOAException ex) {
            LOGGER.error("SOAException:", ex);
        }

        // 其他业务逻辑
        if (response != null) {
            if (response.getResultCode() == 0) {
                LOGGER.info("ResultCode 为0时");
            }
            if (response.getResultCode() == -1) {
                LOGGER.info("ResultCode 为-1时");
            }
        }
        return response;
    }

    //@Getter
    //@Setter
    public static class Response {
        private Integer resultCode;
        private String resultMsg;
		public Integer getResultCode() {
			return resultCode;
		}
		public void setResultCode(Integer resultCode) {
			this.resultCode = resultCode;
		}
		public String getResultMsg() {
			return resultMsg;
		}
		public void setResultMsg(String resultMsg) {
			this.resultMsg = resultMsg;
		}
        
    }

    public static class Request {
    }

    //@Setter
    //@Getter
    public static class SOAResponse {
        private boolean success;
        private String resultMsg;
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getResultMsg() {
			return resultMsg;
		}
		public void setResultMsg(String resultMsg) {
			this.resultMsg = resultMsg;
		}
        
        
    }

    @Service
    public static class SoaClient {
        public SOAResponse invoke(Request request) {
            SOAResponse soaResponse = new SOAResponse();
            soaResponse.setResultMsg("成功");
            soaResponse.setSuccess(true);
            return soaResponse;
        }
    }
}