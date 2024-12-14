def call(Map params) {
    // Validate that params is not null
    if (params == null) {
        error "Input parameters map cannot be null."
    }
    
    // Validate that the required parameters are provided
    if (!params.containsKey('imageName') || params.imageName == null || params.imageName.trim() == '') {
        error "Image name is required."
    }
    // Validate that the required parameters are provided
    if (!params.containsKey('ecrRepoUrl') || params.ecrRepoUrl == null || params.ecrRepoUrl.trim() == '') {
        error "ECR repo URL is required."
    }
    def imageName = params.imageName

    def tag = params.get('tag', 'latest')  // Default to 'latest' if not provided
    def dockerfilePath = params.get('dockerfilePath', '.')  // Default to current directory if not provided

    // Print information about the build
    echo "Building Docker image: ${imageName}:${tag}"
    echo "Dockerfile path: ${dockerfilePath}"

    // Tag the image for AWS ECR (Optional)
    def ecrRepoUrl = params.get('ecrRepoUrl', '')
    
    // Build the Docker image using the Jenkins docker plugin
    def customImage = docker.build("${imageName}:${tag}", ".")

    if (ecrRepoUrl) {
        customImage.tag("${ecrRepoUrl}:${tag}")
        echo "Tagged image for ECR: ${ecrRepoUrl}:${tag}"
    }
}
