package io.github.kavishkamk;

import com.hazelcast.core.HazelcastInstance;
import io.quarkus.logging.Log;
import jakarta.ws.rs.*;

import java.util.Map;

@Path("/hello")
public class TestResources {

    private final HazelcastInstance hazelcastInstance;

    public TestResources(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @POST
    @Path("/{username}")
    @Consumes("application/json")
    public String hello(@PathParam("username") String username, Person person) {
        Log.info("reserved: " + username + " , person: " + person);
        try {
            Map<String, Person> persons = hazelcastInstance.getMap("user-map1");
            persons.put(username, person);
        } catch (Exception e) {
            Log.error("e:" + e.getMessage());
            throw e;
        }



        return "Hello from RESTEasy Reactive";
    }
}
