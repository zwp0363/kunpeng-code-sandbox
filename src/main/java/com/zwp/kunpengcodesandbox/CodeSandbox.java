package com.zwp.kunpengcodesandbox;

import com.zwp.kunpengcodesandbox.model.ExecuteCodeRequest;
import com.zwp.kunpengcodesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
