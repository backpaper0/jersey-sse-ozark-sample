package sample;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * JAX-RSアプリケーションの設定クラスです。
 * このクラスがあるとアプリケーションサーバは
 * JAX-RSアプリケーションだと認識してくれます。
 */
@ApplicationScoped
@ApplicationPath("app")
public class SampleApplication extends Application {
}
