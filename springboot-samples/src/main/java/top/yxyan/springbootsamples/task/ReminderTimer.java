package top.yxyan.springbootsamples.task;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class ReminderTimer {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(
                        null,
                        "请勿长时间使用电脑!"
                );
            }
        };
        //每隔多久提醒用户
        timer.schedule(task, 0,3 * 1000);
    }
}

