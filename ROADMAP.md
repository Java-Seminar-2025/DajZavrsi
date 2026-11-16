# Administration
- Initial setup page
    - Only shown on first launch.
    - Set up organisation information.
    - Set up admin credentials.
    - Set up global thesis validation schema?
- User management
    - Admin can create more accounts as needed for students, professors, and maybe other administrators.
    - The newly minted account has an initial random password, which must be changed after the first login.
- Considerations
    - Administration is orthogonal to the student/professor role, so it could be handled differently (e.g. admin endpoints need a secret key/token passed with the request instead of requiring an account.)

# Thesis management - Core feature
- Assignment creation
    - Mentor accounts should be able to create assignments and assign them to a student.
    - Committee members are assigned afterwards.
    - A validation schema can be provided per-assignment which inherits from a global schema.
- Thesis upload
    - Student accounts, once they have an assignment, should be able to upload their thesis in document and potentially source form (e.g. pdf, docx, LaTeX, Typst, ...).
    - Uploaded documents are stored on disk in a standardised location. Filename should be based on document hash, UUID, or similar, and the metadata should be stored in the database.
    - The student is shown a preview of the uploaded files, and is offered the option to alter the submission in case of mistakes (e.g. wrong file), before committing it for validation.
- Validation and review loop
    - After validation, the student is shown the validation report, and can either upload a new version immediately to address any issues, or if they believe it's good enough, pass it on to review.

# Thesis validation
- AI-powered validation
    - A suitable AI model, either a small one ran locally, or a stronger one ran in the cloud, is given the uploaded document for analysis. The application could provide MCP servers to give it access to tools better suited for specific file formats.
    - The model should support structured input to receive a validation schema, and structured output to generate a validation report.
    - The validation schema is a list of rules, e.g. in JSON format `[{ "rule_name": "font", "rule_description": "Allowed font sizes and families.", "size": "12pt", "family": "Latin Modern" }, { "rule_name": "page_count", "rule_description": "Suggested page count, not counting figures.", "page_count": "20", "deviation": "5" }, { "rule_name": "word_count", "rule_description": "Suggested word count.", "word_count": 1500, "deviation": 500 }]`
    - Validation rules are mere suggestions for the model, and due to the nature of LLMs, it might not always interpret them correctly, so professors should always have the final say.
    - A validation report is similarly provided in JSON format, e.g. `[{ "rule_name": "font", "location": { "page": 15 }, "message": "Segments of the page use the wrong font size." }, { "rule_name": "word_count", "word_count": 2100, "message": "The word count is slightly above the maximum suggested." }]`
    - Through MCP servers, the model might be able to highlight the specific problem areas in the document, e.g. draw a red box around a claim that's missing a citation.

# Comittee review
- Mentors and commitee members have a list of unreviewed submissions.
- They should be shown a preview of the document and the validation report, potentially with highlighted problem areas if implemented.
- The mentor goes first, and is given a similar set of tools as the validation model --- they can make highlights and write notes about certain problems with the thesis. The mentor gives their verdict on whether the submission version is complete enough for a pass, or needs revision.
- After the mentor finishes their review, it gets passed onto the committee members, who go through the same process.
- After the review is finished, it's passed back on to the student, with a similar display to the validation report.
