import vuetify, { transformAssetUrls } from "vite-plugin-vuetify";

// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    app: {
        pageTransition: {
            name: "page",
            mode: "out-in",
        },
    },
    build: {
        transpile: ["vuetify"],
    },
    devtools: { enabled: true },
    modules: [
        "@tresjs/nuxt",
        (_options, nuxt) => {
            nuxt.hooks.hook("vite:extendConfig", (config) => {
                // @ts-expect-error
                config.plugins.push(vuetify({ autoImport: true }));
            });
        },
        "@pinia/nuxt",
        "@pinia-plugin-persistedstate/nuxt",
    ],
    tres: {
        devtools: true, // devtools support for tresjs
        glsl: true, // glsl support for tresjs
    },
    vite: {
        vue: {
            template: {
                transformAssetUrls,
            },
        },
    },
});
