def call(Map params) {
    // Validate that params is not null
    if (params == null) {
        error "Input parameters map cannot be null."
    }
    
    // Validate that the required parameters are provided
    if (!params.containsKey('imageName') || params.imageName == null || params.imageName.trim() == '') {
        error "Image name is required."
    }
    def imageName = params.imageName

    def tag = params.get('tag', 'latest')  // Default to 'latest' if not provided
    def dockerfilePath = params.get('dockerfilePath', '.')  // Default to current directory if not provided

    // Print information about the build
    echo "Building Docker image: ${imageName}:${tag}"
    echo "Dockerfile path: ${dockerfilePath}"

    // Build Docker image using the specified Dockerfile
    script {
        try {
            // Run Docker build command
            // docker.build("${imageName}:${tag}", "-f ${dockerfilePath}/Dockerfile .")
            // Run the Docker build command using 'sh'
            sh """
                docker build -t ${imageName}:${tag} -f ${dockerfilePath}/Dockerfile .
            """
        } catch (Exception e) {
            error "Failed to build Docker image: ${e.message}"
        }
    }
}
