<template>
    <div>
        <Head>
            <Title>Home</Title>
        </Head>

        <div>Home Page</div>
        <NuxtLink to="/login">Login Page</NuxtLink>

        <v-btn @click="logout">Logout</v-btn>
        <v-btn @click="loginInfo">Show Login Info</v-btn>
    </div>
</template>

<script lang="ts" setup>
function logout() {
    const authStore = useAuthenticationStore();
    log.debug("reset auth store");
    authStore.$reset();
}
function loginInfo() {
    const authStore = useAuthenticationStore();
    log.debug("get auth store", authStore.$state);
}
</script>

<style></style>
