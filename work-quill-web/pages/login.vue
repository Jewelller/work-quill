<template>
    <div>
        <v-form v-model="valid" @submit="login" onsubmit="return false">
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
                        label="Password"
                        :rules="passwordRule"
                        required
                    />
                </v-col>
            </v-row>
            <v-row>
                <v-col cols="6" offset="3">
                    <v-btn type="submit" :loading="loading" block>Login</v-btn>
                </v-col>
            </v-row>
        </v-form>
    </div>
</template>

<script setup>
import { AuthService } from "~/src/services/AuthService";

const valid = defineModel("vaild", { default: false });
const loading = defineModel("loading", { default: false });

const username = defineModel("username");
const password = defineModel("password");

const usernameRule = [(v) => !!v || "Username is required"];
const passwordRule = [(v) => !!v || "Password is required"];

function login() {
    console.log("login");
    if (valid) {
        loading.value = true;
        AuthService.login({
            username: username.value,
            password: password.value,
        })
            .then((res) => {
                console.log(res);
            })
            .catch((err) => {
                console.log(err);
            })
            .finally(() => {
                loading.value = false;
            });
    }
}
</script>

<style lang="css" scoped></style>
