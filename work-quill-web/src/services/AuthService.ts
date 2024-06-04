import { WorkQuillWeb } from "../utils/Url";
import { Post } from "../utils/Request";
import type { ModelRef } from "vue";

interface LoginRequestParams {
    username: string | undefined;
    password: string | undefined;
}

export class AuthService {
    static async login({ username, password }: LoginRequestParams) {
        return Post(WorkQuillWeb.API_URL.auth.login, {
            username,
            password,
        });
    }
}
