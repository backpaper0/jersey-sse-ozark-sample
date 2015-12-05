package sample;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/*
 * JAX-RSアプリケーションの設定クラス。
 * このクラスがあるとアプリケーションサーバは
 * JAX-RSアプリケーションだと認識してくれる。
 */
@ApplicationScoped
@ApplicationPath("app")
public class SampleApplication extends Application {
}
