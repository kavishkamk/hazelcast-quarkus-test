package io.github.kavishkamk;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class HazelecastCashConfig {

    public void onStartUp(@Observes StartupEvent startupEvent) {
        Config helloWorldConfig = new Config();
        helloWorldConfig.setClusterName("hello-world");
        Config hz = new Config();
        hz.setInstanceName("abcd");
        Hazelcast.newHazelcastInstance(hz);
    }

}
