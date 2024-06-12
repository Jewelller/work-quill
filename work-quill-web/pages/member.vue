<template>
    <div>
        <MemberTable
            :headers="headers"
            :data-items="data"
            :toolbar-title="$t('member.manage_member')"
        />
    </div>
</template>

<script setup>
const headers = [
    {
        title: "Member ID",
        key: "memberId",
    },
    {
        title: "Name",
        key: "name",
    },
    {
        title: "Department",
        key: "departmentId",
    },
    {
        title: "Gender",
        key: "gender",
    },
    {
        title: "Birthday",
        key: "birthday",
    },
    {
        title: "Edu. Background",
        key: "eduBackground",
    },
    {
        title: "Phone",
        key: "phone",
    },
    {
        title: "Email",
        key: "email",
    },
    {
        title: "Address",
        key: "memberAddress",
    },
    {
        title: "Actions",
        key: "actions",
    },
];
const data = [];
</script>

<style></style>
