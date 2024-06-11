import { defineStore } from "pinia";

interface LocaleInfo {
    locale: string;
}

export const useLocaleStore = defineStore({
    id: Id.localeStoreId,
    persist: {
        afterRestore(context) {
            log.debug(`Restoring: ${context.store.$id}`);
        },
    },
    state: () =>
        <LocaleInfo>{
            locale: "en",
        },
    actions: {
        setLocale(locale: "zh" | "en") {
            this.locale = locale;
        },
    },
});
