<template>
    <v-layout>
        <v-main>
            <v-app-bar elevation="2">
                <template v-if="$device.isMobile">
                    <v-app-bar-nav-icon
                        @click.stop="isNavDrawerOpen = !isNavDrawerOpen"
                    ></v-app-bar-nav-icon>
                </template>
                <v-app-bar-title>Work Quill</v-app-bar-title>

                <template v-slot:append>
                    <v-btn icon="mdi-translate"></v-btn>
                    <v-menu location="bottom" activator="parent">
                        <v-list>
                            <v-list-item
                                v-for="(item, index) in languages"
                                :key="index"
                                :value="item.value"
                            >
                                <v-list-item-title
                                    @click="setLocale(item.value)"
                                >
                                    {{ item.title }}
                                </v-list-item-title>
                            </v-list-item>
                        </v-list>
                    </v-menu>
                </template>
            </v-app-bar>

            <v-navigation-drawer
                v-model="isNavDrawerOpen"
                :permanent="!$device.isMobile"
                :rail="!$device.isMobile"
                :expand-on-hover="!$device.isMobile"
            >
                <v-list>
                    <template v-for="(item, index) in navItems">
                        <template v-if="item.type === 'divider'">
                            <v-divider></v-divider>
                        </template>
                        <template v-else-if="item.type === 'group'"> </template>
                        <template v-else>
                            <v-list-item
                                :link="item.props.link"
                                :to="item.props.to"
                                :prepend-icon="item.props.prependIcon"
                            >
                                <v-list-item-title>{{
                                    item.needsTranslate === true
                                        ? $t(item.title)
                                        : item.title
                                }}</v-list-item-title>
                            </v-list-item>
                        </template>
                    </template>
                </v-list>
            </v-navigation-drawer>

            <v-container>
                <slot />
            </v-container>
        </v-main>
    </v-layout>
</template>

<script setup>
import { getNavRouteData } from "~/src/routes";
const i18n = useI18n();
const localeInfo = useLocaleStore();

const setLocale = (v) => {
    localeInfo.setLocale(v);
    i18n.setLocale(v);
};

const languages = [
    {
        title: "English",
        value: "en",
    },
    {
        title: "简体中文",
        value: "zh",
    },
];

const userInfo = useAuthenticationStore();
const userTitle = userInfo.nickname
    ? userInfo.nickname
    : userInfo.username
    ? userInfo.username
    : undefined;

const isNavDrawerOpen = defineModel("isNavDrawerOpen", {
    default: !useDevice().isMobile,
});

const navItems = getNavRouteData({ userTitle });

onBeforeMount(() => {
    i18n.setLocale(localeInfo.locale);
    if (!(userInfo.token && userInfo.refreshToken)) {
        userInfo.$reset();
        navigateTo({
            path: "/login",
        });
    }
});
</script>

<style>
html {
    overflow: auto;
}
</style>
