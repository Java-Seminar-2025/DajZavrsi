<script setup lang="ts">
import { defineModel, ref } from 'vue';
import { useRouter } from 'vue-router';
import { apiFetch } from '@/api.ts';
import { refreshCsrf } from '@/csrf';

const router = useRouter();

let hasError = ref(false);
let isLoading = ref(false);

const username = defineModel<string>("username", { default: "" });
const password = defineModel<string>("password", { default: "" });

async function handleLogin() {
    hasError.value = false;
    isLoading.value = false;

    let params = {
        username: username.value,
        password: password.value,
    };

    try {
        let response = await apiFetch("/api/auth/login", {
            method: "POST",
            body: new URLSearchParams(params),
        });

        isLoading.value = true;

        if (!response.ok) {
            hasError.value = true;
        } else {
            router.push("/");
        }
    } catch (e) {
        hasError.value = true;
    } finally {
        username.value = "";
        password.value = "";
        isLoading.value = false;
    }
}
</script>

<template>
<form @submit.prevent="handleLogin">
    <p v-if="hasError" class="error">Login failed. Please try again.</p>
    <input v-model="username" type="text" placeholder="username" name="username"/>
    <input v-model="password" type="password" placeholder="password" name="password"/>
    <button type="submit" :disabled="isLoading">{{ isLoading ? "Logging in..." : "Login" }}</button>
</form>
</template>

<style scoped>
form {
    display: flex;
    flex-direction: column;
    align-items: center;
}

p.error {
    color: red;
}
</style>
