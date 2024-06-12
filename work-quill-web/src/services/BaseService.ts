import { Post, Put, Delete } from "../../utils/Request";

export class BaseService {
    url: string;

    constructor(url: string) {
        this.url = url;
    }

    async getAll() {
        return Post(`${this.url}/select-all`, {});
    }

    async getOne(id: string | number) {
        return Post(`${this.url}/${id}`, {});
    }

    async add(obj: Object) {
        return Post(this.url, obj);
    }

    async update(obj: Object) {
        return Post(`${this.url}/update`, obj);
    }

    async delete(idList: Array<number | string>) {
        return Delete(this.url, idList);
    }
}
