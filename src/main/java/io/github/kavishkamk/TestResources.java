package io.github.kavishkamk;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

@Path("/hello")
public class TestResources {

    @POST
    @Path("/{username}")
    @Consumes("application/json")
    public Uni<Person> hello(@PathParam("username") String username, Person person) throws ExecutionException, InterruptedException {
        HazelcastInstance hazelcastInstance = Hazelcast.getHazelcastInstanceByName("abcd");
        Log.info("reserved: " + username + " , person: " + person);

        IMap<String, Person> persons = hazelcastInstance.getMap("user-map1");
        return persons.putAsync(username, person).thenApply(x -> Uni.createFrom().item(x)).toCompletableFuture().get();

    }

    @GET
    public Uni<Response> get() throws ExecutionException, InterruptedException {
        HazelcastInstance hazelcastInstance = Hazelcast.getHazelcastInstanceByName("abcd");

        IMap<String, Person> map = hazelcastInstance.getMap("user-map1");

        return map.getAsync("madhu").thenApply(person -> Uni.createFrom().item(Response.ok(person).build())).toCompletableFuture().get();

    }
}
