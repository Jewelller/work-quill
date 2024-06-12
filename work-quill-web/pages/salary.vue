<template>
    <div>
        <SalaryTable
            :headers="headers"
            :data-items="data"
            :toolbar-title="$t('salary.manage_salary')"
        />
    </div>
</template>

<script setup>
const headers = [
    {
        title: "Salary ID",
        key: "salaryId",
    },
    {
        title: "Member ID",
        key: "memberId",
    },
    {
        title: "Amount",
        key: "amount",
    },
    {
        title: "Timestamp",
        key: "timestamp",
    },
    {
        title: "Actions",
        key: "actions",
    },
];
const data = [];
</script>

<style></style>
