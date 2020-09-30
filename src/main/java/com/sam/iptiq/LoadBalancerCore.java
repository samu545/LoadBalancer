package com.sam.iptiq;

import com.sam.iptiq.logic.LoadBalance;
import com.sam.iptiq.logic.RandomLoadBalance;
import com.sam.iptiq.logic.RoundRobinLoadBalance;

public class LoadBalancerCore {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        loadBalance();
    }

    public static void loadBalance() {
        doGetProvider(new RoundRobinLoadBalance());
        doGetProvider(new RandomLoadBalance());
    }


    public static void doGetProvider(LoadBalance loadBalance) {
        doGetProvider(loadBalance, 100);
    }

    private static void doGetProvider(LoadBalance loadBalance, int queryTimes) {
        for (int i = 0; i < queryTimes; i++) {
            String serverId = loadBalance.get();
            System.out.println(String.format("[%s] index:%s,%s",
                    loadBalance.getClass().getSimpleName(), i, serverId));
        }
    }
}
