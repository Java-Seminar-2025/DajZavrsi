interface CsrfData {
    headerName: string,
    token: string,
}

let csrfData: CsrfData | null = null;

export async function getCsrf(): Promise<CsrfData> {
    if (csrfData == null) {
        await refreshCsrf();
    }

    return csrfData!;
}

export async function refreshCsrf(): Promise<void> {
    let response = await fetch("/api/csrf", { credentials: "same-origin" });

    if (!response.ok) {
        throw new Error(`Fetching CSRF token failed! Error ${response.status}`);
    }

    csrfData = await response.json();
}
