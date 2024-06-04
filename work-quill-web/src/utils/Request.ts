import axios from "axios";
import { WorkQuillWeb } from "./Url";

const instance = axios.create({
    baseURL: WorkQuillWeb.API_URL_PREFIX,
    timeout: 10000,
});

export function Get(endpoint: string, query: object | string | null) {
    return instance.get(endpoint, { params: query });
}

export function Post(endpoint: string, data: object) {
    return instance.post(endpoint, data);
}
