import axios from "axios";

const instance = axios.create({
    baseURL: API_URL_PREFIX,
    timeout: 10000,
});

instance.interceptors.request.use(
    (config) => {
        // If is not login path then pass tokens
        if (config.url != API_URL.auth.login.username) {
            const authStore = useAuthenticationStore();

            config.data = Object.assign(config.data ? config.data : {}, {
                token: authStore.token,
                refreshToken: authStore.refreshToken,
            });
        }

        if (config.data) {
            config.data = JSON.stringify(config.data);
        }
        return config;
    },
    (error) => {
        log.error(error);
        return Promise.reject(error);
    }
);

instance.interceptors.response.use(
    (response) => {
        const tokenData = response.data.data;
        if (tokenData && tokenData.token && tokenData.refreshToken) {
            const authStore = useAuthenticationStore();
            const { token, refreshToken } = tokenData;
            log.debug("Received tokens");
            authStore.setToken({ token, refreshToken });
        }

        return response;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export function Post(url: string, data: object | null) {
    return instance.post(url, data);
}

export function Put(url: string, data: object | null) {
    return instance.put(url, null, {
        data,
    });
}

export function Delete(url: string, idList: Array<number | string> | null) {
    return instance.delete(url, {
        params: {
            idList: `${idList}`.replace("[", "").replace("]", ""),
        },
    });
}
