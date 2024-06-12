<template>
    <div>
        <DepartmentTable
            :headers="headers"
            :data-items="data"
            :toolbar-title="$t('department.manage_department')"
        />
    </div>
</template>

<script setup>
const headers = [
    {
        title: "Department ID",
        key: "departmentId",
    },
    {
        title: "Department Name",
        key: "departmentName",
    },
    {
        title: "Actions",
        key: "actions",
    },
];
const data = [];
</script>

<style></style>
