package io.github.kavishkamk;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class SchedulerTest {

    private final HazelcastInstance hazelcastInstance;


    public SchedulerTest(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Scheduled(every = "4s")
    public void testMethod() {
        Map<String, Person> capitalcities = hazelcastInstance.getMap("user-map1");
        Log.info(capitalcities.values());;

    }
}
