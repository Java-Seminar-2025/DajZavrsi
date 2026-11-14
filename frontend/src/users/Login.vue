<script setup lang="ts">
import { useRoute } from 'vue-router';

let route = useRoute();
let hasError = route.query.error !== undefined;
</script>

<template>
<form action="/api/auth/login" method="POST">
    <p v-if="hasError" class="error">Login failed. Please try again.</p>
    <input type="text" placeholder="username"/>
    <input type="password" placeholder="password"/>
    <button type="submit">Login</button>
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
