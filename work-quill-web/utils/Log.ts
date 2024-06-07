import { createConsola } from "consola";

export const log = createConsola({
    level: import.meta.env.DEV ? 4 : 1,
});
