import { WorkQuillWeb } from "../utils/Url";
import { Post } from "../utils/Request";

interface LoginRequestParams {
    username: string | undefined;
    password: string | undefined;
}

export class AuthService {
    static async usernameLogin({ username, password }: LoginRequestParams) {
        return Post(WorkQuillWeb.API_URL.auth.login.username, {
            username,
            password,
        });
    }
}
