package gateway.service;

public interface TraefikApi {
    String GET_ALL_PROVIDERS = "/api/providers";
    String GET_PROVIDER_BY_NAME = "/api/providers/{provider}";
    String LIST_BACKENDS = "/api/providers/{provider}/backends";
    String LIST_FRONTENDS = "/api/providers/{provider}/frontends";
    String HEALTH_METRICS = "/health";
}
