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
                    <LocaleHelper />
                    <LogoutHelper />
                </template>
            </v-app-bar>

            <v-navigation-drawer
                v-model="isNavDrawerOpen"
                :permanent="!$device.isMobile"
                :rail="!$device.isMobile"
                :expand-on-hover="!$device.isMobile"
            >
                <v-list>
                    <template v-for="(item, index) in navItems" :key="index">
                        <template v-if="item.type === 'divider'">
                            <v-divider></v-divider>
                        </template>
                        <template v-else-if="item.type === 'group'"> </template>
                        <template v-else-if="hasRole(item.needsRole)">
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
                        <template v-else></template>
                    </template>
                </v-list>
            </v-navigation-drawer>

            <v-container>
                <slot />
            </v-container>
            <TokenHelper />
        </v-main>
    </v-layout>
</template>

<script setup>
import { getNavRouteData } from "~/src/routes";

const userInfo = useAuthenticationStore();
const userTitle = userInfo.getUserTitle();

const isNavDrawerOpen = defineModel("isNavDrawerOpen", {
    default: !useDevice().isMobile,
});

const navItems = getNavRouteData({ userTitle });

function hasRole(roleList) {
    let isFulfill = true;
    for (let i = 0; i < roleList.length; ++i) {
        if (!userInfo.roles.includes(roleList[i])) {
            isFulfill = false;
            break;
        }
    }
    return isFulfill;
}

// Auto Re-login associated
onBeforeMount(() => {});
</script>

<style>
html {
    overflow: auto;
}
</style>
