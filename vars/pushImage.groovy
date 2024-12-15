def call(Map params) {
    // Validate that params is not null
    if (params == null) {
        error "Input parameters map cannot be null."
    }

    // Validate that the required parameters are provided
    if (!params.containsKey('ecrRepoUrl') || params.ecrRepoUrl == null || params.ecrRepoUrl.trim() == '') {
        error "ECR repo URL is required."
    }
    if (!params.containsKey('tag') || params.tag == null || params.tag.trim() == '') {
        error "Tag is required."
    }

    def ecrRepoUrl = params.ecrRepoUrl
    def tag = params.tag

    // Authenticate with AWS ECR
    echo "Authenticating with AWS ECR"
    sh """
        aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin ${ecrRepoUrl.split('/')[0]}
    """

    // Push the image to ECR
    echo "Pushing Docker image: ${ecrRepoUrl}:${tag} to AWS ECR"
    sh """
        docker push ${ecrRepoUrl}:${tag}
    """

    echo "Successfully pushed Docker image: ${ecrRepoUrl}:${tag}"
}
