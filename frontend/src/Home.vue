<script setup lang="ts">
import { RouterLink } from 'vue-router';

</script>

<template>
<div class="container">
    <h2>Turbocharge your thesis analysis!</h2>
    <RouterLink to="/users/login">Get Started</RouterLink>
</div>
</template>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 80vh;
}
</style>
