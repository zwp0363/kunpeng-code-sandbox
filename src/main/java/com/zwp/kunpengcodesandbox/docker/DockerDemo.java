package com.zwp.kunpengcodesandbox.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import java.util.List;

public class DockerDemo {

    public static void main(String[] args) throws InterruptedException {
        // 获取默认的 Docker Client
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
//        PingCmd pingCmd = dockerClient.pingCmd();
//        pingCmd.exec(); // 执行一个简单的 ping 命令来检查 Docker 守护进程是否可达
        // 拉取镜像
        String image = "nginx:latest";
        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image); // 创建拉取镜像的命令
        // 回调接口，用于在镜像拉取过程中接收和处理服务器返回的信息（异步）
        PullImageResultCallback pullImageResultCallback = new PullImageResultCallback(){
            @Override
            public void onNext(PullResponseItem item) {
                System.out.println("下载镜像：" + item.getStatus()); // 打印出当前拉取进度信息的状态
                super.onNext(item); // 调用父类 ( PullImageResultCallback ) 的 onNext 方法，确保父类的默认处理逻辑也被执行
            }
        };
        pullImageCmd
                .exec(pullImageResultCallback) // 执行拉取镜像命令
                .awaitCompletion(); // 阻塞当前线程 (主线程)，直到镜像拉取操作完成 (成功或失败) 为止
        System.out.println("下载完成");
        // 创建容器
        CreateContainerCmd containerCmd = dockerClient.createContainerCmd(image);
        CreateContainerResponse createContainerResponse = containerCmd
                .withCmd("echo", "Hello Docker") // 携带命令
                .exec();
        System.out.println(createContainerResponse);
        String containerId = createContainerResponse.getId();

        // 查看容器状态
        ListContainersCmd listContainersCmd = dockerClient.listContainersCmd();
        List<Container> containerList = listContainersCmd.withShowAll(true).exec();
        for (Container container : containerList) {
            System.out.println(container);
        }

        // 启动容器
        dockerClient.startContainerCmd(containerId).exec();

        // 查看日志
        // 回调接口
        LogContainerResultCallback logContainerResultCallback = new LogContainerResultCallback() {
            @Override
            public void onNext(Frame item) {
                // 异步，边执行边输出日志
                System.out.println("日志：" +  new String(item.getPayload()));
                super.onNext(item);
            }
        };
        dockerClient.logContainerCmd(containerId)
                .withStdErr(true) // 错误输出
                .withStdOut(true) // 标准输出
                .exec(logContainerResultCallback)
                .awaitCompletion(); // 阻塞等待日志输出

        // 删除容器
        dockerClient.removeContainerCmd(containerId).withForce(true).exec();

        // 删除镜像
        dockerClient.removeImageCmd(image).exec(); // 强制删加withForce(true)
    }
}
