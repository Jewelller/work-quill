import vuetify, { transformAssetUrls } from "vite-plugin-vuetify";

// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    ssr: false,
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
        "@nuxtjs/i18n",
        "@nuxtjs/device",
    ],
    i18n: {
        vueI18n: "./i18n.config.ts",
    },
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
