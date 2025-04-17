package com.zwp.kunpengcodesandbox;

import com.zwp.kunpengcodesandbox.model.ExecuteCodeRequest;
import com.zwp.kunpengcodesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
