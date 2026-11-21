import { apiFetch } from '@/api.ts';
import { fetchUser, type User } from '@/api/user.ts';
import { type Submission } from '@/api/submission.ts';

export enum AssignmentStatus {
    In_Progress = 'IN_PROGRESS',
    Passed = 'PASSED',
    Failed = 'FAILED',
}

export class Assignment {
    uuid: string;

    student: User;
    mentor: User;
    committee1: User | null;
    committee2: User | null;

    subject: string;
    status: AssignmentStatus;

    constructor(
        uuid: string,
        student: User,
        mentor: User,
        committee1: User | null,
        committee2: User | null,
        subject: string,
        status: AssignmentStatus,
    ) {
        this.uuid = uuid;
        this.student = student;
        this.mentor = mentor;
        this.committee1 = committee1;
        this.committee2 = committee2;
        this.subject = subject;
        this.status = status;
    }

    async fetchSubmissions(): Promise<Submission[]> {
        return [];
    }
}

export async function fetchAssignment(uuid: string): Promise<Assignment> {
    const response = await apiFetch(`/api/assignments/${uuid}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch assignment!");
            }

            return response.json();
        }) as AssignmentResponse;

    const student = fetchUser(response.student_id);
    const mentor = fetchUser(response.mentor_id);
    const committee1 = response.committee1_id ? fetchUser(response.committee1_id) : null;
    const committee2 = response.committee2_id ? fetchUser(response.committee2_id) : null;

    return new Assignment(
        uuid,
        await student,
        await mentor,
        await committee1,
        await committee2,
        response.subject,
        response.status,
    );
}

interface AssignmentResponse {
    student_id: string;
    mentor_id: string;
    committee1_id: string | null;
    committee2_id: string | null;
    subject: string;
    status: AssignmentStatus;
}
