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

/**
 * 掲示板のコントローラーです。
 * 
 * メッセージを溜め込むためにApplicationScopedにしています。
 *
 */
@ApplicationScoped
@Path("bbs")
public class BbsController {

    /*
     * JSPに値を渡すためのオブジェクトです。
     * MVC 1.0で定義されているインターフェースで、
     * @Injectでコントローラーにインジェクションします。
     */
    @Inject
    private Models models;

    /*
     * 投稿されたメッセージを複数のクライアントにブロードキャストするためのクラスです。
     */
    private SseBroadcaster broadcaster;

    /*
     * これまでに投稿されたメッセージを保持します。
     */
    private CopyOnWriteArrayList<String> entries;

    /**
     * フィールドを初期化します。
     * 
     * コントローラーはCDI管理ビーンになりますが、
     * RequestScopedやApplicationScopedのCDI管理ビーンはClient Proxyが作られます。
     * 
     * Client Proxyは動的にサブクラスを生成する事で実現されますが、
     * その関係でフィールドの初期化はコンストラクタではなくPostConstructを
     * 付けたメソッドで行う方が良いです。
     * 
     */
    @PostConstruct
    public void init() {
        broadcaster = new SseBroadcaster();
        entries = new CopyOnWriteArrayList<>();
    }

    /**
     * 画面を表示します。
     * 
     * @return
     */
    @GET
    @Controller
    public String index() {

        //これまでに投稿されたメッセージ一覧をJSPに渡します。
        models.put("entries", entries);

        //JSPファイル名を返しています。
        //JSPファイルはsrc/main/webapp/WEB-INF/views/にあります。
        return "bbs.jsp";
    }

    /**
     * SSEでメッセージをブロードキャストするために準備します。
     * 
     * このリソースメソッドはJavaScript側でEventSourceをインスタンス化するときに
     * 呼び出されています。
     * 
     * @return
     */
    @Path("listen")
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput listen() {
        EventOutput eventOutput = new EventOutput();
        broadcaster.add(eventOutput);
        return eventOutput;
    }

    /**
     * 投稿されたメッセージを全てのクライアントにブロードキャストします。
     * 
     * @param entry
     */
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
