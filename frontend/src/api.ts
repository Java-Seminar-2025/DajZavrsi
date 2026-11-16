import { getCsrf } from "@/csrf.ts";

export async function apiFetch(input: URL | RequestInfo, init: RequestInit = {}): Promise<Response> {
    const method = init.method ?? "GET";
    const changesState = ["POST", "PUT", "PATCH", "DELETE"].includes(method);
    const headers = new Headers(init.headers);

    if (changesState) {
        const csrf = await getCsrf();
        headers.set(csrf.headerName, csrf.token);
    }

    return fetch(
        input,
        {
            ...init,
            method,
            headers,
            credentials: "same-origin",
        }
    )
}
