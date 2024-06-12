<template>
    <v-layout>
        <v-main class="d-flex justify-center align-center">
            <v-container
                class="d-flex flex-col h-100vh justify-center align-center"
            >
                <slot />
            </v-container>
        </v-main>
    </v-layout>
</template>

<script lang="ts"></script>

<style>
html,
body {
    overflow-y: hidden;
}
</style>
