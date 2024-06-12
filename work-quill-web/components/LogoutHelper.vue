<template>
    <v-tooltip :text="`${$t('logout')}?`" location="bottom">
        <template v-slot:activator="{ props }">
            <v-btn v-bind="props" icon="mdi-logout" @click="logout"></v-btn>
        </template>
    </v-tooltip>
</template>

<script setup>
const { t } = useI18n();

function logout() {
    const authStore = useAuthenticationStore();
    log.debug("reset auth store");
    authStore.$reset();
    Notify.info(t("logout_already"));
    navigateTo("/login");
}
</script>
