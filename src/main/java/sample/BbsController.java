package sample;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.annotation.Controller;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

@ApplicationScoped
@Path("bbs")
public class BbsController {

    @Inject
    private Models models;

    private SseBroadcaster broadcaster;

    private CopyOnWriteArrayList<String> entries;

    @PostConstruct
    public void init() {
        broadcaster = new SseBroadcaster();
        entries = new CopyOnWriteArrayList<>();
    }

    @GET
    @Controller
    public String index() {

        models.put("entries", entries);

        return "bbs.jsp";
    }

    @Path("listen")
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput listen() {
        EventOutput eventOutput = new EventOutput();
        broadcaster.add(eventOutput);
        return eventOutput;
    }

    @Path("post")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void post(String entry) {

        entries.add(0, entry);

        OutboundEvent event = new OutboundEvent.Builder().name("entry")
                .mediaType(MediaType.TEXT_PLAIN_TYPE).data(String.class, entry)
                .build();
        broadcaster.broadcast(event);
    }
}
