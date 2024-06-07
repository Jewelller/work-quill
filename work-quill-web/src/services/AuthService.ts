import { Post } from "../../utils/Request";

interface LoginRequestParams {
    username: string;
    password: string;
}

export class AuthService {
    static async usernameLogin({ username, password }: LoginRequestParams) {
        return Post(API_URL.auth.login.username, {
            username,
            password,
        });
    }
}
