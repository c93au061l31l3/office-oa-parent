package com.AlexChang.auth.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.Task;

/**
 * ClassName:MyTaskListener
 * Description:
 *
 * @author Alex
 * @create 2023-10-19 下午 07:25
 * @Version:1.0
 */
public class MyTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask task) {
        if(task.getName().equals("經理審批")){
            //分配任務
            task.setAssignee("jack");
        }else if (task.getName().equals("人事審批")){
            task.setAssignee("tom");
        }
    }
}
