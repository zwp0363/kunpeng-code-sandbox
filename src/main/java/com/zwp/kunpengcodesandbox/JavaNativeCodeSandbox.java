package com.zwp.kunpengcodesandbox;


import com.zwp.kunpengcodesandbox.model.ExecuteCodeRequest;
import com.zwp.kunpengcodesandbox.model.ExecuteCodeResponse;

/**
 * Java 原生代码沙箱实现（直接复用模板方法）
 */
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}
