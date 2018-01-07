/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yuchin.utils.util;

import android.os.Handler;

/**
 * 循環定時器
 */
public class LoopTimerUtils {
    private int intervalMillis;    //間隔時間
    private boolean running;    //運行狀態
    private Handler handler;    //消息處理器
    private ExecuteRunnable executeRunnable;    //執行Runnable

    /**
     * 創建一個循環定時器
     *
     * @param handler
     * @param runnable
     * @param intervalMillis
     */
    public LoopTimerUtils(Handler handler, Runnable runnable, int intervalMillis) {
        this.handler = handler;
        this.intervalMillis = intervalMillis;
        executeRunnable = new ExecuteRunnable(runnable);
    }

    /**
     * 創建一個循環定時器
     *
     * @param runnable
     * @param intervalMillis
     */
    public LoopTimerUtils(Runnable runnable, int intervalMillis) {
        this(new Handler(), runnable, intervalMillis);
    }

    /**
     * 立即啟動
     */
    public void start() {
        running = true;
        handler.removeCallbacks(executeRunnable);
        handler.post(executeRunnable);
    }

    /**
     * 延遲指定間隔毫秒啟動
     */
    public void delayStart() {
        running = true;
        handler.removeCallbacks(executeRunnable);
        handler.postDelayed(executeRunnable, intervalMillis);
    }

    /**
     * 立即停止
     */
    public void stop() {
        running = false;
        handler.removeCallbacks(executeRunnable);
    }

    /**
     * 獲取間隔毫秒
     *
     * @return
     */
    public int getIntervalMillis() {
        return intervalMillis;
    }

    /**
     * 設置間隔毫秒
     *
     * @param intervalMillis
     */
    public void setIntervalMillis(int intervalMillis) {
        this.intervalMillis = intervalMillis;
    }

    /**
     * 是否正在運行
     *
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * 設置消息處理器
     *
     * @param handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * 設置執行內容
     *
     * @param runnable
     */
    public void setRunnable(Runnable runnable) {
        executeRunnable.setRunnable(runnable);
    }

    /**
     * 執行Runnable
     */
    private class ExecuteRunnable implements Runnable {
        private Runnable runnable;

        public ExecuteRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            if (running && runnable != null) {
                runnable.run();
            }
        }

        public void setRunnable(Runnable runnable) {
            this.runnable = runnable;
        }
    }
}
