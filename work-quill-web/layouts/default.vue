<template>
    <v-layout>
        <v-main>
            <v-navigation-drawer>
                <v-list-item
                    title="My Application"
                    subtitle="Vuetify"
                ></v-list-item>
                <v-divider></v-divider>
                <v-list-item link title="Home" to="/"></v-list-item>
                <v-list-item link title="Login" to="/login"></v-list-item>
            </v-navigation-drawer>
            <v-container>
                <slot />
            </v-container>
        </v-main>
    </v-layout>
</template>

<script lang="ts" setup></script>

<style>
html {
    overflow: auto;
}
</style>
