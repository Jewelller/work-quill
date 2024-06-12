<template></template>

<script lang="ts" setup>
const { t } = useI18n();
const userInfo = useAuthenticationStore();
const userTitle = userInfo.getUserTitle();

onMounted(() => {
    if (!(userInfo.token && userInfo.refreshToken)) {
        Notify.info(t("notify.no_token"));
        navigateTo({
            path: "/login",
        });
    } else {
        Notify.info(`${t("notify.welcome", { name: userTitle })}`);
    }
});
</script>

<style></style>
