package atomdu.tools.core.http;

/**
 * Created by atomdu on 2017/12/15.
 */
public class AsyncHttpClient {
    private String url;
    private String method;
    private com.mb3364.http.HttpClient httpRequest;
    private OnObjectCallback onObjectCallback;
    private OnStringCallback onStringCallback;
    private OnFileCallback onFileCallback;
    private OnBytesCallback onBytesCallback;


    public void execute() {
        httpRequest = new com.mb3364.http.AsyncHttpClient();
        if (method.equals("GET")) {
            if (onObjectCallback != null)
                httpRequest.get(url, onObjectCallback);
            if (onStringCallback != null)
                httpRequest.get(url, onStringCallback);
            if (onFileCallback != null)
                httpRequest.get(url, onFileCallback);
            if (onBytesCallback != null)
                httpRequest.get(url, onBytesCallback);
        } else if (method.equals("POST")) {
            if (onObjectCallback != null)
                httpRequest.post(url, onObjectCallback);
            if (onStringCallback != null)
                httpRequest.post(url, onStringCallback);
            if (onFileCallback != null)
                httpRequest.post(url, onFileCallback);
            if (onBytesCallback != null)
                httpRequest.post(url, onBytesCallback);
        }
    }

    public static class Builder {
        private String url;
        private String method = "GET";
        private Parser parser;
        private OnObjectCallback onObjectCallback;
        private OnStringCallback onStringCallback;
        private OnFileCallback onFileCallback;
        private OnBytesCallback onBytesCallback;

        public Builder() {
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder post() {
            this.method = "POST";
            return this;
        }

        public Builder get() {
            this.method = "GET";
            return this;
        }

        public Builder parser(Parser parser) {
            this.parser = parser;
            return this;
        }

        public <T> Builder onObjectCallback(OnObjectCallback<T> onCallback) {
            this.onStringCallback = null;
            this.onFileCallback = null;
            this.onBytesCallback = null;
            this.onObjectCallback = onCallback;
            return this;
        }

        public <T> Builder onObjectCallback(OnObjectCallback<T> onCallback, Parser parser) {
            this.onStringCallback = null;
            this.onFileCallback = null;
            this.onBytesCallback = null;
            this.onObjectCallback = onCallback;
            this.parser = parser;
            return this;
        }

        public Builder onStringCallback(OnStringCallback onCallback) {
            this.onStringCallback = onCallback;
            this.onFileCallback = null;
            this.onBytesCallback = null;
            this.onObjectCallback = null;
            return this;
        }

        public Builder onFileCallback(OnFileCallback onCallback) {
            this.onStringCallback = null;
            this.onFileCallback = onCallback;
            this.onBytesCallback = null;
            this.onObjectCallback = null;
            return this;
        }

        public Builder onBytesCallback(OnBytesCallback onCallback) {
            this.onStringCallback = null;
            this.onFileCallback = null;
            this.onBytesCallback = onCallback;
            this.onObjectCallback = null;
            return this;
        }

        public AsyncHttpClient build() {
            AsyncHttpClient httpClient = new AsyncHttpClient();
            httpClient.url = url;
            httpClient.method = method;
            httpClient.onStringCallback = onStringCallback;
            httpClient.onFileCallback = onFileCallback;
            httpClient.onBytesCallback = onBytesCallback;
            httpClient.onObjectCallback = onObjectCallback;
            if (onObjectCallback != null && parser != null) {
                onObjectCallback.setParser(parser);
            }
            return httpClient;
        }
    }
}
