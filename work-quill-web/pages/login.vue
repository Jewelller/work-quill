<template>
    <div>
        <Head>
            <Title>Login</Title>
        </Head>
        <v-card>
            <v-card-title class="text-center">{{
                $t("login.title")
            }}</v-card-title>
            <v-card-subtitle class="text-center">{{
                $t("login.subtitle")
            }}</v-card-subtitle>

            <v-divider></v-divider>

            <v-form
                class="my-16"
                v-model="valid"
                @submit.prevent="login"
                fast-fail
            >
                <v-row>
                    <v-col cols="8" offset="2">
                        <v-text-field
                            v-model="username"
                            label="Username"
                            :rules="usernameRule"
                            required
                        />
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="8" offset="2">
                        <v-text-field
                            v-model="password"
                            type="password"
                            label="Password"
                            :rules="passwordRule"
                            required
                        />
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="6" offset="3">
                        <v-btn type="submit" :loading="loading" block>
                            Login
                        </v-btn>
                    </v-col>
                </v-row>
            </v-form>
        </v-card>
    </div>
</template>

<script setup>
import { AuthService } from "~/src/services/AuthService";
import { log } from "~/utils/Log";

definePageMeta({
    layout: "login-layout",
});

const valid = defineModel("vaild", { default: false });
const loading = defineModel("loading", { default: false });

const username = defineModel("username");
const password = defineModel("password");

const usernameRule = [
    (value) => {
        if (value) return true;
        return "Username is required";
    },
];
const passwordRule = [
    (value) => {
        if (value) return true;
        return "Password is required";
    },
    (value) => {
        if (value.length >= 8) return true;
        return "Password requires at lease 8 characters";
    },
];

function login() {
    log.debug("login");
    if (valid.value) {
        loading.value = true;
        log.debug("login form valid");
        AuthService.usernameLogin({
            username: username.value,
            password: password.value,
        })
            .then(() => {
                navigateTo({
                    path: "/",
                });
            })
            .catch((err) => {
                log.error(err);
            })
            .finally(() => {
                loading.value = false;
            });
    }
}
</script>

<style lang="css" scoped></style>
