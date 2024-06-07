import { defineStore } from "pinia";
import StoreId from "~/stores/Id";

interface Tokens {
    token: string;
    refreshToken: string;
}

interface UserInfo {
    token: string | null;
    refreshToken: string | null;
    roles: Array<string>;
    userId: number | null;
    username: string | null;
    nickname: string | null;
    expireTime: Date | null;
}

function base64ToUtf8(str: string) {
    const binaryStr = atob(str);
    const bytes = new Uint8Array(binaryStr.length);
    for (let i = 0; i < bytes.length; ++i) {
        bytes[i] = binaryStr.charCodeAt(i);
    }
    return new TextDecoder().decode(bytes);
}

export const useAuthenticationStore = defineStore({
    id: StoreId.authStoreId,
    persist: {
        afterRestore(context) {
            log.debug(`Restoring: ${context.store.$id}`);
        },
    },
    state: () =>
        <UserInfo>{
            token: null,
            refreshToken: null,
            roles: [],
            userId: null,
            username: null,
            nickname: null,
            expireTime: null,
        },
    actions: {
        setToken({ token, refreshToken }: Tokens) {
            this.token = token;
            this.refreshToken = refreshToken;

            const encodedPayload = token.split(".")[1];
            const userInfo = JSON.parse(base64ToUtf8(encodedPayload));
            log.debug("Get user info: ", userInfo);

            this.userId = parseInt(userInfo.userId);
            this.roles = userInfo.roles;
            this.username = userInfo.username;
            this.nickname = userInfo.nickname;
            this.expireTime = new Date(parseInt(userInfo.expireTime));
        },
    },
});
