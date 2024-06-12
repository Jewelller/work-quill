<template>
    <v-tooltip :text="$t('language')" location="bottom">
        <template v-slot:activator="{ props }">
            <v-btn icon="mdi-translate" v-bind="props"></v-btn>
            <v-menu location="bottom" activator="parent">
                <v-list>
                    <v-list-item
                        v-for="(item, index) in languages"
                        :key="index"
                        :value="item.value"
                    >
                        <v-list-item-title @click="setAppLocale(item.value)">
                            {{ item.title }}
                        </v-list-item-title>
                    </v-list-item>
                </v-list>
            </v-menu>
        </template>
    </v-tooltip>
</template>

<script lang="ts" setup>
const { setLocale } = useI18n();

const languages = [
    {
        title: "English",
        value: "en",
    },
    {
        title: "简体中文",
        value: "zh",
    },
];

function setAppLocale(locale: string) {
    setLocale(locale);
}
</script>

<style></style>
