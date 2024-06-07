import axios from "axios";

const instance = axios.create({
    baseURL: API_URL_PREFIX,
    timeout: 10000,
});

instance.interceptors.request.use(
    (config) => {
        const authStore = useAuthenticationStore();

        // If is not login path then pass tokens
        if (config.baseURL === API_URL.auth.login.username) {
            config.data.token = authStore.token;
            config.data.refreshToken = authStore.refreshToken;
        }

        return config;
    },
    (error) => {
        log.error(error);
    }
);

instance.interceptors.response.use(
    (response) => {
        const data = response.data.data;
        if (data.token && data.refreshToken) {
            const authStore = useAuthenticationStore();
            const { token, refreshToken } = data;
            log.debug("Received tokens");
            authStore.setToken({ token, refreshToken });
        }
        return response;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export function Get(url: string, query: object) {
    return instance.get(url, { params: query });
}

export function Post(url: string, data: object | null) {
    return instance.post(url, data);
}
