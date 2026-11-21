import { apiFetch } from "@/api.ts";

export enum UserRole {
    Student = "STUDENT",
    Mentor = "MENTOR",
    Admin = "ADMIN",
}

export class User {
    uuid: string;

    username: string;
    role: UserRole;

    constructor(uuid: string, username: string, role: UserRole) {
        this.uuid = uuid;
        this.username = username;
        this.role = role;
    }
}

export async function fetchUser(uuid: string): Promise<User> {
    let response = await apiFetch(`/api/users/${uuid}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch user!");
            }

            return response.json();
        }) as UserResponse;

    return new User(uuid, response.username, response.role);
}

interface UserResponse {
    username: string;
    role: UserRole,
}
