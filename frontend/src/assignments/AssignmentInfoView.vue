<script setup lang="ts">
import { onBeforeMount, onMounted, ref } from 'vue';
import { type Assignment, fetchAssignment } from '@/api/assignment.ts'

interface Props {
    uuid: string,
}

const props = defineProps<Props>();

const assignment = ref<Assignment | null>(null);
const isLoading = ref(false);

onMounted(async () => {
    isLoading.value = true;

    assignment.value = await fetchAssignment(props.uuid)
        .catch(error => {
            return null;
        });

    isLoading.value = false;
});
</script>

<template>
<div class="container">
    <p v-if="isLoading">
        Loading assignment...
    </p>
    <div class="assignment" v-else-if="assignment">
        <p><strong>Assigned to:</strong>{{ assignment.student.username }}</p>
        <p><strong>Mentored by:</strong>{{ assignment.mentor.username }}</p>
        <p v-if="assignment.committee1 !== null || assignment.committee2 !== null">
            <strong>Reviewed by:<br /></strong>
                {{ assignment.committee1 ? assignment.committee1.username : '' }}<br />
                {{ assignment.committee2 ? assignment.committee2.username : '' }}<br />
        </p>
        <p><strong>Subject:</strong>{{ assignment.subject }}</p>
        <p><strong>Status:</strong>{{ assignment.status }}</p>
    </div>
    <p class="error" v-else>
        Failed to load assignment!
    </p>
</div>
</template>

<style scoped>
.container {
    flex: 1;

    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.error {
    color: red;
}
</style>
