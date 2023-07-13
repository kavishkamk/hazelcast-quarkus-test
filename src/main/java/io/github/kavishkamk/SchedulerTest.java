package io.github.kavishkamk;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class SchedulerTest {

    private final HazelcastInstance hazelcastInstance;


    public SchedulerTest() {
        this.hazelcastInstance =Hazelcast.getHazelcastInstanceByName("abcd");;
    }

    @Scheduled(every = "4s")
    public Uni<Void> testMethod() throws ExecutionException, InterruptedException {

        if (this.hazelcastInstance == null) {
            Log.error("No hazelcast instance");
            return Uni.createFrom().voidItem();
        }

        Log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        IMap<String, Person> capitalcities = hazelcastInstance.getMap("user-map1");

        return capitalcities.getAsync("madhu")
                .thenApply(x -> {
                    Log.info("===============" + x);
                    return Uni.createFrom().voidItem();
                }).toCompletableFuture().get();


    }
}
