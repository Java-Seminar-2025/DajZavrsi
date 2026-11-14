<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router';

</script>

<template>
<header>
    <nav>
        <RouterLink to="/"><h1>Daj Završi</h1></RouterLink>
        <RouterLink to="/users/login">Login</RouterLink>
    </nav>
</header>
<main>
    <RouterView />
</main>
</template>

<style scoped>
header {
    position: sticky;
    top: 0;
    z-index: 100;
    border-bottom: 1px solid black;
    background: color-mix(in srgb, darkslategrey 100%, black 50%);
}

nav {
    display: flex;
    flex-direction: row;
    align-items: baseline;
    justify-content: space-between;
}

nav a {
    text-decoration: none;
}
</style>
