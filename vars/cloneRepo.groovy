def call(Map params) {
    // Retrieve parameters with defaults
    def repoUrl = params.get('repoUrl', '')
    def branch = params.get('branch', 'main')

    // Validate required parameters
    if (!repoUrl?.trim()) {
        error "Repository URL is required and cannot be empty."
    }

    // Logging details for debugging
    echo "Cloning repository: ${repoUrl}"
    echo "Branch: ${branch}"

    // Clone the repository using the 'git' step
    git branch: branch, url: repoUrl
}
