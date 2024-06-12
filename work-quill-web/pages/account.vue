<template>
    <div>
        <UserTable
            :headers="headers"
            :toolbar-title="$t('account.manage_account')"
        />
    </div>
</template>

<script setup>
const { t } = useI18n();
const headers = [
    {
        title: "User ID",
        key: "userId",
    },
    {
        title: "Username",
        key: "username",
    },
    {
        title: "Nickname",
        key: "nickname",
    },
    {
        title: "Password",
        key: "password",
    },
    {
        title: "Actions",
        key: "actions",
    },
];
</script>

<style></style>
